import jbotsim.*;
import jbotsim.event.SelectionListener;

import java.util.Random;

/**
 * Created by lbouquin on 06/12/18.
 */
public class CarInCity extends WayPointNode implements SelectionListener {

    public static int direction;
    public static final int EAST = 0;
    public static final int WEST = 1;


    public double speed;
    public static final double MINSPEED = 0.5;
    public static final double MAXSPEED = 1.5;
    public static double RANGE = MAXSPEED - MINSPEED;

    public static final double SENSINGRANGE = 100;
    public static final String ICONPATH = "/car.png";

    public static final double ALERTRANGE = 60;
    public Point lastBreakDown;


    public enum Dir {
        Left,
        Right,
        Straight;


    }
    public Dir dir;
    public Intersect nextIntersect;
    public StateCar state;
    public double newSpeed;

    public CarInCity(int directionn, Topology topo){
        super();
        topo.addSelectionListener(this);
        speed = (Math.random() * RANGE) + MINSPEED;
        this.dir = Dir.Straight;
        this.nextIntersect = null;
        this.direction = direction;
        if(direction == EAST){
            this.setDirection(0);
            this.addDestination(this.getLocation());
        }

        else if(direction == WEST)
            this.setDirection(Math.PI);

        this.setIcon(ICONPATH);
        this.setSize(30);
        this.setSensingRange(SENSINGRANGE);
        state = new NormalState();
        newSpeed = -1;

    }



    public void onClock() {
         //super.onClock();
        if(!(this.state instanceof BreakdownState) ){
            this.move(speed);
            wrapLocation();
        }
        state.action(this);
        if(this.nextIntersect != null && this.nextIntersect.distance(this) < 12){

            if(this.dir == Dir.Left){
                // replacer au bon endroit.
              /*  if(this.getDirection()  == 3*Math.PI/2){
                    System.out.println("rentre1");
                   this.setLocation(this.getX(), this.getY()+10);
                }
                if(this.getDirection() % Math.PI/2 == Math.PI/2){

                    this.setLocation(this.getX(), this.getY()-10);
                }
                if((this.getDirection()) == Math.PI){

                    this.setLocation(this.getX()+10, this.getY());
                }
                if((this.getDirection())== 0){

                    this.setLocation(this.getX()-10, this.getY());
                }
*/
                this.setDirection((this.getDirection()+Math.PI/2));
                this.dir = Dir.Straight;

            }
            else if( this.dir == Dir.Right){
                this.setDirection((this.getDirection()-Math.PI/2));
                this.dir = Dir.Straight;
            }
        }



    }

    @Override
    public void onSensingIn(Node node) {
        super.onSensingIn(node);

        if(node instanceof CarInCity){
            state.onInteract(this,(CarInCity)node);

        }
        else if (node instanceof Intersect){
            double dec = Math.random();
            if( dec > 0.75){

                this.dir = Dir.Left;
                this.nextIntersect = (Intersect)node;
            }
            if(dec < 0.25 ){
                this.dir = Dir.Right;
                this.nextIntersect = (Intersect)node;
            }
        }


    }

    public void onSensingOut(Node node) {
        super.onSensingOut(node);
        state.onInteract(this, (CarInCity) node);
    }

    public void onMessage(Message message) {
        super.onMessage(message);

        if(message.getContent().equals("alert")){
            this.setColor(Color.red);
            this.speed = speed/2;
        }
    }

    public void setState(StateCar state ){
        this.state = state;
    }

    @Override
    public void onSelection(Node node) {
        if(node instanceof  CarInCity){
            ( (CarInCity)node).setState(new BreakdownState());
            ( (CarInCity)node).speed = 0;
            ( (CarInCity)node).setColor(Color.black);
        }

    }


}
