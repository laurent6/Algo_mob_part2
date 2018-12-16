import jbotsim.Color;
import jbotsim.Point;

import java.sql.SQLOutput;

public class DeviateState implements  StateCar {

    public boolean startDev;
    public Point dest;
    public boolean firstStep;
    public DeviateState(Point p ){
        startDev = true;
        dest = p;
        firstStep = true;
        System.out.println(" rentre dans deviaion");

    }
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
        if(interact.state instanceof BreakdownState){

            if(context.getDirection() ==  interact.getDirection()  ){
                context.cleanDestination();
                context.setColor(Color.BLACK);
                context.speed = 0;
                context.setState(new BreakdownState());
                this.setState(new BreakdownState());
            }


        }else if (context.getDirection() !=  interact.getDirection()  ){
            context.send(interact, context.listBreakDown);
        }
    }

    @Override
    public void setState(StateCar state) {

    }

    @Override
    public void computeDir(CarInCity car) {
        Point inter = car.nextIntersect.getLocation();
        Point nextInter = null;
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
                car.addDestination(inter.x,  inter.y-20);
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

                nextInter = new Point(inter.x,(inter.y+car.nextIntersectH));
                car.addDestination(inter.x,(inter.y+car.nextIntersectH));
                car.direction = CarInCity.SOUTH;
            }

            else if(car.direction == CarInCity.WEST){

                nextInter = new Point(inter.x,(inter.y-car.nextIntersectH));
                car.addDestination(inter.x+20,(inter.y-car.nextIntersectH+20));
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
                car.addDestination((inter.x+car.nextIntersectW-20),inter.y);
                car.direction = CarInCity.EAST;
            }

            else if(car.direction == CarInCity.WEST){
                nextInter = new Point((inter.x-car.nextIntersectW),inter.y);
                car.addDestination((inter.x-car.nextIntersectW-20),inter.y);
                car.direction = CarInCity.WEST;
            }

            else if(car.direction == CarInCity.NORTH){
                nextInter = new Point(inter.x,(inter.y-car.nextIntersectH));
                car.addDestination(inter.x,(inter.y-car.nextIntersectH-20));
                car.direction = CarInCity.NORTH;
            }

            else if(car.direction == CarInCity.SOUTH ){

                nextInter = new Point(inter.x,(inter.y+car.nextIntersectH));
                car.addDestination(inter.x,(inter.y+car.nextIntersectH+20));
                car.direction = CarInCity.SOUTH;
            }

        }



        if(car.listBreakDown.isInAlert(inter,nextInter)){
            car.cleanDestination();
            this.firstStep = true;
            this.dest = nextInter;
        }

        else{
            car.addDestination(nextInter);
            if(dest.distance(nextInter) < 50){
                this.setState(new AlertState(false));
            }
        }


    }

    @Override
    public void interact(CarInCity car, Intersect inter) {
        System.out.println(" dest " + this.dest);
                if(this.firstStep){
                    car.dir = CarInCity.Dir.Straight;
                    firstStep = false;
                }

                else{
                    car.dir = CarInCity.Dir.Left;
                }


                car.nextIntersect = inter;
    }
}
