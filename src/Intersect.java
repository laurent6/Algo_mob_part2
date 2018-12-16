import jbotsim.Color;
import jbotsim.Message;
import jbotsim.Node;
import jbotsim.Point;

/**
 * Created by lbouquin on 06/12/18.
 */
public class Intersect extends Node {



    public void onMessage(Message message) {

        System.out.println("hey");
        System.out.println(" why"+ message.getContent());

    }
}
