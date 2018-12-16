import jbotsim.Color;
import jbotsim.Point;

/**
 * Created by lbouquin on 01/12/18.
 */
public class NormalState implements StateCar {
    @Override


    public void action(CarInCity context) {
        double random = Math.random();

        if(random < 0.0001){ // 0.001 is a good testing number
            context.speed = 0;
            context.setColor(Color.black);
            context.setState(new BreakdownState());

        }

    }

    @Override
    public void onInteract(CarInCity context, CarInCity interact) {
        if(interact.state instanceof BreakdownState ){
            if(context.getDirection() != interact.getDirection() ){
                context.setState(new AlertState(false));
                context.setColor(Color.red);
                context.listBreakDown.addAlert(interact.getLocation());
            }
            else {
                context.setState(new BreakdownState());
                context.setColor(Color.black);
            }
        }



    }

    public void onCollision(CarInCity context){

    }


    @Override
    public void setState(StateCar state) {

    }

    @Override
    public void computeDir(CarInCity car ) {
        Point inter = car.nextIntersect.getLocation();
        if(car.dir == CarInCity.Dir.Left){

            if(car.direction == CarInCity.EAST){
                car.addDestination(inter.x+20,  inter.y);
                car.addDestination(inter.x+20 ,(inter.y-car.nextIntersectH+20));
                car.direction = CarInCity.NORTH;

            }

            else if( car.direction == CarInCity.WEST){
                car.addDestination(inter.x-20,  inter.y);
                car.addDestination(inter.x-20,(inter.y+car.nextIntersectH-20));
                car.direction = CarInCity.SOUTH;

            }

            else if(car.direction == CarInCity.NORTH){
                car.addDestination(inter.x,  inter.y-20);
                car.addDestination((inter.x-car.nextIntersectW+20),inter.y-20);
                car.direction = CarInCity.WEST;

            }

            else if(car.direction == CarInCity.SOUTH){
                car.addDestination(inter.x+20,  inter.y+20);
                car.addDestination((inter.x+car.nextIntersectW-20),inter.y+20);
                car.direction = CarInCity.EAST;
            }

        }
        else if( car.dir == CarInCity.Dir.Right){
            if(car.direction == CarInCity.EAST){
                car.addDestination(inter.x-20,(inter.y+car.nextIntersectH-20));
                car.direction = CarInCity.SOUTH;
            }

            else if(car.direction == CarInCity.WEST){

                car.addDestination(inter.x+20,(inter.y-car.nextIntersectH+20));
                car.direction = CarInCity.NORTH;
            }

            else if(car.direction == CarInCity.NORTH){
                car.addDestination((inter.x+car.nextIntersectW-20),inter.y+20);
                car.direction = CarInCity.EAST;
            }

            else if(car.direction == CarInCity.SOUTH){

                car.addDestination((inter.x-car.nextIntersectW+20),inter.y-20);
                car.direction = CarInCity.WEST;
            }

        }
        else{
            if(car.direction == CarInCity.EAST){
                car.addDestination((inter.x+car.nextIntersectW-20),car.getLocation().getY());
                car.direction = CarInCity.EAST;
            }

            else if(car.direction == CarInCity.WEST){
                car.addDestination((inter.x-car.nextIntersectW-20),car.getLocation().getY());
                car.direction = CarInCity.WEST;
            }

            else if(car.direction == CarInCity.NORTH){
                car.addDestination(car.getLocation().x,(inter.y-car.nextIntersectH-20));
                car.direction = CarInCity.NORTH;
            }

            else if(car.direction == CarInCity.SOUTH ){

                car.addDestination(car.getLocation().x,(inter.y+car.nextIntersectH+20));
                car.direction = CarInCity.SOUTH;
            }

        }
    }

    @Override
    public void interact(CarInCity car, Intersect inter) {
        double dec = Math.random();

        if (dec > 0.75) {
            car.dir = CarInCity.Dir.Left;
            car.nextIntersect = inter;

        }
        else if (dec < 0.25) {
            car.dir = CarInCity.Dir.Right;
            car.nextIntersect = inter;
        }
        else {
            if(inter.getLocation().getX() > 0 && inter.getLocation().getY() >0 &&  car.getLocation().getX() < car.nextIntersectW*4 && car.getLocation().getY() < car.nextIntersectH*4){
                car.dir = CarInCity.Dir.Straight;
            }
            else{
                car.dir = CarInCity.Dir.Right;
            }

            car.nextIntersect = inter;
        }
    }


}
