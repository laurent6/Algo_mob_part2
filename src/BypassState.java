/**
 * Created by lbouquin on 06/12/18.
 */
public class BypassState implements StateCar {

    public boolean onBypass=true;
    public boolean finish= false;
    public CarInCity car;

    public BypassState(CarInCity car ){

        this.car = car;
        car.setLocation(this.car.getX(), this.car.getY()+30);
    }

    @Override
    public void action(CarInCity context) {

      /*  if(onBypass && ( context.getTime() - time) < 0.5){



            }*/


    }

    @Override
    public void onInteract(CarInCity context, CarInCity interact) {
         if(interact == car && onBypass){
             System.out.println("Rentre");
             car.setLocation(this.car.getX(), this.car.getY()-30);
             car.setState(new NormalState());
         }
    }


    @Override
    public void setState(StateCar state) {

    }

    @Override
    public void computeDir(CarInCity car) {

    }


    @Override
    public void interact(CarInCity car, Intersect inter) {

    }


}
