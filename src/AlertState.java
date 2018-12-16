import jbotsim.Color;
import jbotsim.Point;

/**
 * Created by lbouquin on 01/12/18.
 */
public class AlertState implements StateCar {
    public boolean sameDir;

    public AlertState(boolean isDir){
        sameDir= isDir;
    }
    @Override
    public void action(CarInCity context) {
       /* double random = Math.random();

        if(random < 0.0001){ // 0.001 is a good testing number
            context.speed = 0;
            context.setColor(Color.black);
            context.setState(new BreakdownState());

        }*/
    }

    @Override
    public void onInteract(CarInCity context, CarInCity interact) {
        if(interact.state instanceof BreakdownState){

            if(context.getDirection() == interact.getDirection()){
               context.cleanDestination();
                context.setColor(Color.BLACK);
                context.speed = 0;
                context.setState(new BreakdownState());
            }
            else {
                context.listBreakDown.addAlert(interact.getLocation());
            }
        }
        else if (context.getDirection() !=  interact.getDirection() ){
            context.send(interact, context.listBreakDown);
        }


    }

    @Override
    public void setState(StateCar state) {

    }

    @Override
    public void computeDir(CarInCity car) {
        Point inter = car.nextIntersect.getLocation();
        Point nextInter= null ;
        int temp = car.direction;
        if(car.dir == CarInCity.Dir.Left){

            if(car.direction == CarInCity.EAST){
                car.addDestination(inter.x+20,  inter.y);
                nextInter = new Point(inter.x ,(inter.y-car.nextIntersectH));
                car.addDestination(inter.x+20 ,(inter.y-car.nextIntersectH+20));
                car.direction = CarInCity.NORTH;

            }

            else if( car.direction == CarInCity.WEST){
                car.addDestination(inter.x-20,  inter.y);
                nextInter = new Point(inter.x,(inter.y+car.nextIntersectH));
                car.addDestination(inter.x-20,(inter.y+car.nextIntersectH-20));
                car.direction = CarInCity.SOUTH;

            }

            else if(car.direction == CarInCity.NORTH){
                car.addDestination(inter.x-20,  inter.y-20);
                nextInter = new Point((inter.x-car.nextIntersectW),inter.y);
                car.addDestination((inter.x-car.nextIntersectW+20),inter.y-20);
                car.direction = CarInCity.WEST;

            }

            else if(car.direction == CarInCity.SOUTH){
                car.addDestination(inter.x+20,  inter.y+20);
                nextInter = new Point((inter.x+car.nextIntersectW),inter.y);
                car.addDestination((inter.x+car.nextIntersectW-20),inter.y+20);
                car.direction = CarInCity.EAST;
            }

        }
        else if( car.dir == CarInCity.Dir.Right){
            if(car.direction == CarInCity.EAST){
                car.addDestination(inter.x-20,(inter.y+car.nextIntersectH-20));
                nextInter = new Point(inter.x,(inter.y+car.nextIntersectH));
                car.direction = CarInCity.SOUTH;
            }

            else if(car.direction == CarInCity.WEST){

                car.addDestination(inter.x+20,(inter.y-car.nextIntersectH+20));
                nextInter = new Point(inter.x,(inter.y-car.nextIntersectH));
                car.direction = CarInCity.NORTH;
            }

            else if(car.direction == CarInCity.NORTH){
                nextInter = new Point((inter.x+car.nextIntersectW),inter.y);
                car.addDestination((inter.x+car.nextIntersectW-20),inter.y+20);
                car.direction = CarInCity.EAST;
            }

            else if(car.direction == CarInCity.SOUTH){

                nextInter = new Point((inter.x-car.nextIntersectW),inter.y);
                car.addDestination((inter.x-car.nextIntersectW+20),inter.y-20);
                car.direction = CarInCity.WEST;
            }

        }
        else{
            if(car.direction == CarInCity.EAST){
                nextInter = new Point((inter.x+car.nextIntersectW),inter.y);
                car.addDestination((inter.x+car.nextIntersectW-20),inter.y+20);
                car.direction = CarInCity.EAST;
            }

            else if(car.direction == CarInCity.WEST){
                nextInter = new Point((inter.x-car.nextIntersectW),inter.y);
                car.addDestination((inter.x-car.nextIntersectW+20),inter.y-20);
                car.direction = CarInCity.WEST;
            }

            else if(car.direction == CarInCity.NORTH){

                nextInter = new Point(inter.x  ,(inter.y-car.nextIntersectH));
                car.addDestination(inter.x +20 ,(inter.y-car.nextIntersectH-20));
                car.direction = CarInCity.NORTH;
            }

            else if(car.direction == CarInCity.SOUTH ){

                nextInter = new Point(inter.x ,(inter.y+car.nextIntersectH));
                car.addDestination(inter.x -20,(inter.y+car.nextIntersectH+20));
                car.direction = CarInCity.SOUTH;
            }

        }

        if(car.listBreakDown.isInAlert(inter,nextInter)){

            car.cleanDestination();

            car.setState(new DeviateState(nextInter,car, car.nextIntersect, car.direction, temp));

        }
    }



    @Override
    public void interact(CarInCity car, Intersect inter) {
        double dec = Math.random();
       if (dec > 0.75) {

            car.TurnRight(inter);
            car.nextIntersect = inter;

        }
        else if (dec < 0.25) {

            car.TurnLeft(inter);

            car.nextIntersect = inter;
        }
        else {
            car.goStraight(inter);
            car.nextIntersect = inter;
        }


    }
}
