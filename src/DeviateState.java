import jbotsim.Point;

public class DeviateState implements  StateCar {

    public boolean startDev;
    public Point dest;
    public boolean first;
    public DeviateState(Point p ){
        startDev = true;
        dest = p;
        first = true;
    }
    @Override
    public void action(CarInCity context) {

    }

    @Override
    public void onInteract(CarInCity context, CarInCity interact) {
        if(interact.state instanceof BreakdownState){
            if(context.getDirection() == interact.getDirection() ){
                this.setState(new BreakdownState());
            }


        }else if (context.getDirection() != interact.getDirection() ){
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
                nextInter = new Point(inter.x+20 ,(inter.y-car.nextIntersectH+20));
                car.direction = CarInCity.NORTH;

            }

            else if( car.direction == CarInCity.WEST){
                car.addDestination(inter.x-20,  inter.y);
                nextInter = new Point(inter.x-20,(inter.y+car.nextIntersectH-20));
                car.direction = CarInCity.SOUTH;

            }

            else if(car.direction == CarInCity.NORTH){
                car.addDestination(inter.x,  inter.y-20);
                nextInter = new Point((inter.x-car.nextIntersectW+20),inter.y-20);
                car.direction = CarInCity.WEST;

            }

            else if(car.direction == CarInCity.SOUTH){
                car.addDestination(inter.x+20,  inter.y+20);
                nextInter = new Point((inter.x+car.nextIntersectW-20),inter.y+20);
                car.direction = CarInCity.EAST;
            }

        }
        else if( car.dir == CarInCity.Dir.Right){
            if(car.direction == CarInCity.EAST){

                nextInter = new Point(inter.x-20,(inter.y+car.nextIntersectH-20));
                car.direction = CarInCity.SOUTH;
            }

            else if(car.direction == CarInCity.WEST){

                nextInter = new Point(inter.x+20,(inter.y-car.nextIntersectH+20));
                car.direction = CarInCity.NORTH;
            }

            else if(car.direction == CarInCity.NORTH){
                nextInter = new Point((inter.x+car.nextIntersectW-20),inter.y+20);
                car.direction = CarInCity.EAST;
            }

            else if(car.direction == CarInCity.SOUTH){

                nextInter = new Point((inter.x-car.nextIntersectW+20),inter.y-20);
                car.direction = CarInCity.WEST;
            }

        }
        else{
            if(car.direction == CarInCity.EAST){
                nextInter = new Point((inter.x+car.nextIntersectW-20),car.getLocation().getY());
                car.direction = CarInCity.EAST;
            }

            else if(car.direction == CarInCity.WEST){
                nextInter = new Point((inter.x-car.nextIntersectW-20),car.getLocation().getY());
                car.direction = CarInCity.WEST;
            }

            else if(car.direction == CarInCity.NORTH){
                nextInter = new Point(car.getLocation().x,(inter.y-car.nextIntersectH-20));
                car.direction = CarInCity.NORTH;
            }

            else if(car.direction == CarInCity.SOUTH ){

                nextInter = new Point(car.getLocation().x,(inter.y+car.nextIntersectH+20));
                car.direction = CarInCity.SOUTH;
            }

        }



        if(car.listBreakDown.isInAlert(inter,nextInter)){
            car.cleanDestination();
            car.setState(new DeviateState(nextInter));

        }

        else{
            car.addDestination(nextInter);
        }
        if(dest.distance(nextInter) < 50){
            this.setState(new AlertState(false));
        }

    }

    @Override
    public void interact(CarInCity car, Intersect inter) {

            if(first){
                car.dir = CarInCity.Dir.Straight;

            }else{
                car.dir = CarInCity.Dir.Left;
            }

        car.nextIntersect = inter;
    }
}
