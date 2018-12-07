import jbotsim.Topology;

import java.awt.*;

public class BackgroundPainter2 implements jbotsimx.ui.painting.BackgroundPainter {
    @Override
    public void paintBackground(Graphics2D g, Topology tp) {

        /*
        // Paints a background image
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.getImage(getClass().getResource("/forest.jpg"));
        g.drawImage(image, 0, 0, null);
        */

        // Draws a grid (line by line)
        g.setColor(Color.gray);

        g.drawLine(0, tp.getHeight()/2 - 30,tp.getWidth(), tp.getHeight()/2 - 30);
        g.drawLine(0, tp.getHeight()/2, tp.getWidth() , tp.getHeight()/2);
        g.drawLine(0, tp.getHeight()/2 + 30,tp.getWidth(), tp.getHeight()/2 + 30);
    }
}