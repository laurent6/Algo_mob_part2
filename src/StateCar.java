/**
 * Created by lbouquin on 01/12/18.
 */
public interface StateCar {
    void action(CarInCity context);
    void onInteract(CarInCity context, CarInCity interact);
    void setState(StateCar state);
    void computeDir(CarInCity car);
    void interact(CarInCity car,Intersect inter);
}
