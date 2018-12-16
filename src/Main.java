import jbotsim.*;
import jbotsim.Point;
import jbotsimx.ui.JViewer;

import java.awt.*;
import java.awt.Color;
import java.lang.reflect.Array;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;

import static java.awt.Color.*;


/**
 * Created by vichatelain on 29/11/18.
 */
public class Main {



    public static void main(String[] args) {

        // Code
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        Topology tp = new Topology(1300, 600);


        ArrayList<Street> intersec= new ArrayList<>();
        Point coord[] = new Point[25];
        int distHeight = tp.getHeight()/4;
        int distWidth = tp.getWidth()/4;



        // column 1

        coord[0] = new Point(distWidth,distHeight);
        coord[1] = new Point(distWidth,distHeight*2);
        coord[2] = new Point(distWidth,distHeight*3);
        coord[3] = new Point(distWidth,distHeight*4);
        coord[4] = new Point(distWidth,0);



        //column 2
        coord[5] = new Point(distWidth*2,distHeight);
        coord[6] = new Point(distWidth*2,distHeight*2);
        coord[7] = new Point(distWidth*2,distHeight*3);
        coord[8] = new Point(distWidth*2,distHeight*4);
        coord[9] = new Point(distWidth*2,0);

        // column 3
        coord[10] = new Point(distWidth*3,distHeight);
        coord[11] = new Point(distWidth*3,distHeight*2);
        coord[12] = new Point(distWidth*3,distHeight*3);
        coord[13] = new Point(distWidth*3,distHeight*4);
        coord[14] = new Point(distWidth*3,0);

        //column 0
        coord[15] = new Point(0,distHeight);
        coord[16] = new Point(0,distHeight*2);
        coord[17] = new Point(0,distHeight*3);
        coord[18] = new Point(0,distHeight*4);
        coord[19] = new Point(0,0);

        //column 5
        coord[20] = new Point(distWidth*4,distHeight);
        coord[21] = new Point(distWidth*4,distHeight*2);
        coord[22] = new Point(distWidth*4,distHeight*3);
        coord[23] = new Point(distWidth*4,distHeight*4);
        coord[24] = new Point(distWidth*4,0);

        for(int i =0; i < 25; i++){
            Intersect n = new Intersect();
            n.setLocation(coord[i]);
           // n.setIcon("");
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


        Point positionTop = new Point(0,distHeight*2+20);
        Point positionBottom = new Point(distWidth*4,distHeight*2-20);


        tp.addNode(positionTop.x+1, positionTop.y , new  CarInCity(CarInCity.EAST,tp,positionTop));
        tp.addNode(positionTop.x+1, positionTop.y , new  CarInCity(CarInCity.EAST,tp,positionTop));
        tp.addNode(positionTop.x+1, positionTop.y , new  CarInCity(CarInCity.EAST,tp,positionTop));
        tp.addNode(positionTop.x+1, positionTop.y , new  CarInCity(CarInCity.EAST,tp,positionTop));
        tp.addNode(positionTop.x+1, positionTop.y , new  CarInCity(CarInCity.EAST,tp,positionTop));
        tp.addNode(positionTop.x+1, positionTop.y , new  CarInCity(CarInCity.EAST,tp,positionTop));

        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp,positionBottom));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp,positionBottom));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp,positionBottom));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp,positionBottom));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp,positionBottom));
        tp.addNode(positionBottom.x, positionBottom.y ,new CarInCity(CarInCity.WEST,tp,positionBottom));

        tp.setDefaultNodeModel(CarInCity.class);
        //tp.setMessageEngine(new jbotsimx.messaging.DelayMessageEngine(10));

        JViewer jv = new JViewer(tp);

        jv.getJTopology().addBackgroundPainter(new BackgroundPainter(intersec));

        tp.start();







    }
}
