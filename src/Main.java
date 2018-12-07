import jbotsim.Node;
import jbotsim.Point;
import jbotsim.Topology;
import jbotsimx.ui.JViewer;

import java.awt.*;
import java.lang.reflect.Array;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;


/**
 * Created by vichatelain on 29/11/18.
 */
public class Main {
    public static void main(String[] args) {

        // Code
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        Topology tp = new Topology(1600, 600);
        tp.setDefaultNodeModel(CarInCity.class);

        JViewer jv = new JViewer(tp);
        ArrayList<Street> intersec= new ArrayList<>();
        Point coord[] = new Point[9];
        int distHeight = tp.getHeight()/4;
        int distWidth = tp.getWidth()/4;
        // column 1
        coord[0] = new Point(distWidth,distHeight);
        coord[1] = new Point(distWidth,distHeight*2);
        coord[2] = new Point(distWidth,distHeight*3);

        //column 2
        coord[3] = new Point(distWidth*2,distHeight);
        coord[4] = new Point(distWidth*2,distHeight*2);
        coord[5] = new Point(distWidth*2,distHeight*3);

        // column 3
        coord[6] = new Point(distWidth*3,distHeight);
        coord[7] = new Point(distWidth*3,distHeight*2);
        coord[8] = new Point(distWidth*3,distHeight*3);

       for(int i =0; i < 9; i++){
           Intersect n = new Intersect();
           n.setLocation(coord[i]);
           tp.addNode(n);

           Point dest = new Point(coord[i].getX(), coord[i].getY()-distHeight);
           Street s = new Street(coord[i], dest);
           intersec.add(s);

           dest = new Point(coord[i].getX(), coord[i].getY()+distHeight);
           s = new Street(coord[i], dest);
           intersec.add(s);

           dest = new Point(coord[i].getX()+distWidth, coord[i].getY());
           s = new Street(coord[i], dest);
           intersec.add(s);

           dest = new Point(coord[i].getX()-distWidth, coord[i].getY());
           s = new Street(coord[i], dest);
           intersec.add(s);



       }


        Point positionTop = new Point(0,distHeight+10);
        Point positionBottom = new Point(distWidth*4,distHeight*2-10);
        tp.addNode(positionTop.x, positionTop.y ,new CarInCity(CarInCity.EAST,tp));
        tp.addNode(positionTop.x,positionTop.y  ,new CarInCity(CarInCity.EAST,tp));
        tp.addNode(positionTop.x, positionTop.y ,new CarInCity(CarInCity.EAST,tp));
        tp.addNode(positionTop.x,positionTop.y ,new CarInCity(CarInCity.EAST,tp));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp));
        jv.getJTopology().addBackgroundPainter(new BackgroundPainter(intersec));


        tp.start();

    }
}
