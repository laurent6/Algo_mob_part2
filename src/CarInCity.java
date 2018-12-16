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
    public Point lastBreakDown;
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
        //topo.addSelectionListener(this);
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

    }

    public void computeNextDir(){

        Point inter = this.nextIntersect.getLocation();
        if(this.dir == Dir.Left){

            if(this.direction == EAST){
                this.addDestination(inter.x+20,  inter.y);
                this.addDestination(inter.x+20 ,(inter.y-this.nextIntersectH+20));
                this.direction = NORTH;

            }

            else if( this.direction == WEST){
                this.addDestination(inter.x-20,  inter.y);
                this.addDestination(inter.x-20,(inter.y+this.nextIntersectH-20));
                this.direction = SOUTH;

            }

            else if(this.direction == NORTH){
                this.addDestination(inter.x,  inter.y-20);
                this.addDestination((inter.x-this.nextIntersectW+20),inter.y-20);
                this.direction = WEST;

            }

            else if(this.direction == SOUTH){
                this.addDestination(inter.x+20,  inter.y+20);
                this.addDestination((inter.x+this.nextIntersectW-20),inter.y+20);
                this.direction = EAST;
            }

        }
        else if( this.dir == Dir.Right){
            if(this.direction == EAST){
                this.addDestination(inter.x-20,(inter.y+this.nextIntersectH-20));
                this.direction = SOUTH;
            }

            else if(this.direction == WEST){

                this.addDestination(inter.x+20,(inter.y-this.nextIntersectH+20));
                this.direction = NORTH;
            }

            else if(this.direction == NORTH){
                this.addDestination((inter.x+this.nextIntersectW-20),inter.y+20);
                this.direction = EAST;
            }

            else if(this.direction == SOUTH){

                this.addDestination((inter.x-this.nextIntersectW+20),inter.y-20);
                this.direction = WEST;
            }

        }
        else{
            if(this.direction == EAST){
                this.addDestination((inter.x+this.nextIntersectW-20),this.getLocation().getY());
                this.direction = EAST;
            }

            else if(this.direction == WEST){
                this.addDestination((inter.x-this.nextIntersectW-20),this.getLocation().getY());
                this.direction = WEST;
            }

            else if(this.direction == NORTH){
                this.addDestination(this.getLocation().x,(inter.y-this.nextIntersectH-20));
                this.direction = NORTH;
            }

            else if(this.direction == SOUTH ){

                this.addDestination(this.getLocation().x,(inter.y+this.nextIntersectH+20));
                this.direction = SOUTH;
            }

        }
    }

    public void onClock() {
        super.onClock();
        //this.wrapLocation();
        //state.action(this);






    }

    public void onArrival(){
        this.computeNextDir();
    }

    @Override
    public void onSensingIn(Node node) {
        super.onSensingIn(node);

        if (node instanceof CarInCity) {
            //state.onInteract(this, (CarInCity) node);
        } else if (node instanceof Intersect) {
            this.dir = Dir.Straight;
            this.nextIntersect = (Intersect) node;
            double dec = Math.random();

           if (dec > 0.75) {
                    this.dir = Dir.Left;
                    this.nextIntersect = (Intersect) node;

            }
            else if (dec < 0.25) {
                this.dir = Dir.Right;
                this.nextIntersect = (Intersect) node;
            }
            else {
                if(node.getLocation().getX() > 0 && node.getLocation().getY() >0 &&  this.getLocation().getX() < this.nextIntersectW*4 && this.getLocation().getY() < this.nextIntersectH*4){
                    this.dir = Dir.Straight;
                }
                else{
                    this.dir = Dir.Right;
                }

                    this.nextIntersect = (Intersect) node;
                }

        }



    }

    public void onSensingOut(Node node) {

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
