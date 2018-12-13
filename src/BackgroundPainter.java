import jbotsim.Topology;

import java.awt.*;
import java.util.ArrayList;

public class BackgroundPainter
        implements jbotsimx.ui.painting.BackgroundPainter {

    private ArrayList<Street> intersec;
    private int firstTime;
    public BackgroundPainter(ArrayList<Street> list){
        super();
        this.intersec = new ArrayList<>();
        intersec = list;

    }
    @Override
    public void paintBackground(Graphics2D g, Topology tp) {

            int distwidth = tp.getWidth()/4;
            int distHeight = tp.getHeight()/4;
            for(int j=0; j <= tp.getHeight(); j += distHeight)
            for(int i=0; i<= tp.getWidth(); i += distwidth ){
                float dash1[] = {10.0f};
                BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
                g.setColor(Color.BLACK);
                g.setStroke(dashed);

                // middle line
                g.drawLine(i,j+40,i,j+distHeight-40);
                g.drawLine(i+40,j,i+distwidth-40,j);


                // out line.
                g.setStroke(new BasicStroke());
                g.drawLine(i-40,j+40,i-40,j+distHeight-40);
                g.drawLine(i+40,j+40,i+40,j+distHeight-40);
                g.drawLine(i+40,j-40,i+distwidth-40,j-40);
                g.drawLine(i+40,j+40,i+distwidth-40,j+40);


            }

    }
}