import jbotsim.*;
import jbotsim.event.SelectionListener;

/**
 * Created by lbouquin on 06/12/18.
 */
public class CarInCity extends WaypointNode implements SelectionListener {

    public  int direction;
    public static final int EAST = 0;
    public static final int WEST = 1;
    public static final int SOUTH = 2;
    public static final int NORTH = 3;


    public static final double MINSPEED = 0.5;
    public static final double MAXSPEED = 1.5;
    public static double RANGE = MAXSPEED - MINSPEED;

    public static final double SENSINGRANGE = 100;
    public static final String ICONPATH = "/car.png";

    public static final double ALERTRANGE = 60;
    public AlertMessage listBreakDown;
    public double nextIntersectW;
    public double nextIntersectH;


    public enum Dir {
        Left,
        Right,
        Straight;


    }
    public Dir dir;
    public Intersect nextIntersect;
    public StateCar state;
    public double newSpeed;

    public CarInCity(int direction, Topology topo, Point position){

        this.nextIntersectW = topo.getWidth()/4;
        this.nextIntersectH = topo.getHeight()/4;
        this.setLocation(position);
        topo.addSelectionListener(this);
        setCommunicationRange(120);
        setSensingRange(60);
        speed = 1.5;//(Math.random() * RANGE) + MINSPEED;
        this.dir = Dir.Straight;
        this.nextIntersect = null;
        this.direction = direction;
        if(direction == EAST){
            this.setDirection(0);
            this.addDestination((topo.getWidth()/4)-15,this.getLocation().getY());
        }
        else if(direction == WEST){
            this.setDirection(Math.PI);
            this.addDestination(new Point(topo.getWidth()-topo.getWidth()/4+15,this.getLocation().getY()));
        }


        this.setIcon(ICONPATH);
        this.setSize(30);

        state = new NormalState();
        newSpeed = -1;
        this.listBreakDown = new AlertMessage();
    }


    public void onClock() {
        super.onClock();

        state.action(this);






    }

    public void onArrival(){
        state.computeDir(this);
    }

    @Override
    public void onSensingIn(Node node) {
        super.onSensingIn(node);

        if (node instanceof CarInCity) {
            state.onInteract(this, (CarInCity) node);
        } else if (node instanceof Intersect) {
            state.interact(this,(Intersect) node);

        }



    }

    public void onMessage(Message message) {
        super.onMessage(message);

        if(message.getContent() instanceof AlertMessage){
            this.listBreakDown.copyAlertMessage((AlertMessage)message.getContent());
            this.setColor(Color.red);
            this.speed = speed/2;
            if( !(state  instanceof  AlertState)){
                this.setState(new AlertState(false));
            }
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
