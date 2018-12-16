import jbotsim.Color;
import jbotsim.Point;

import java.sql.SQLOutput;

public class DeviateState implements  StateCar {

    public boolean startDev;
    public Point dest;
    public boolean firstStep;
    public int endDir;
    public int currentDir;
    public int inc;
    public DeviateState(Point p, CarInCity car, Intersect inter, int dir, int diractual){
        startDev = true;
        dest = p;
        firstStep = true;
        endDir = dir;
        car.direction = diractual;
        this.currentDir = car.direction;
        this.interact(car,inter);
        inc =0;
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
        if(inc > 3){
            car.setState(new AlertState(false));
        }
        else{
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
            if(!firstStep && inc > 3){
                if(car.listBreakDown.isInAlert(inter,nextInter)){
                    car.cleanDestination();
                    car.setColor(Color.black);
                    car.speed = 0;

                }

            }

            firstStep = false;
        }


    }

    @Override
    public void interact(CarInCity car, Intersect inter) {

                if(this.firstStep){
                    if(endDir == CarInCity.WEST || endDir == CarInCity.EAST){

                        car.dir = CarInCity.Dir.Straight;
                        car.nextIntersect = inter;
                        inc ++;
                        this.computeDir(car);

                    }
                    else{
                        inc ++;
                        car.dir = CarInCity.Dir.Right;
                        car.nextIntersect = inter;
                    }

                }

                else{

                    if((endDir == CarInCity.WEST && currentDir == CarInCity.NORTH) || (endDir == CarInCity.EAST && currentDir == CarInCity.SOUTH)){
                        car.dir = CarInCity.Dir.Left;
                        car.nextIntersect = inter;
                    }
                    else if((endDir == CarInCity.EAST && currentDir == CarInCity.NORTH) ||(endDir == CarInCity.WEST && currentDir == CarInCity.SOUTH)  ){
                        car.dir = CarInCity.Dir.Right;
                        car.nextIntersect = inter;
                    }
                    else {
                        car.dir = CarInCity.Dir.Left;
                        car.nextIntersect = inter;
                    }
                    inc++;
                }



    }
}
