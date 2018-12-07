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
        double distX = Math.pow((context.getX() - context.lastBreakDown.getX()),2);
        double distY =  Math.pow((context.getY() - context.lastBreakDown.getY()),2);
        if(Math.sqrt(distX + distY) > 350){
            context.setState(new NormalState());
            context.setColor(null);
        }
    }

    @Override
    public void onInteract(CarInCity context, CarInCity interact) {
        if(interact.state instanceof BreakdownState){
            if(context.getDirection() == interact.getDirection() ){
                this.setState(new BreakdownState());
            }


        }else if (interact.state instanceof NormalState && context.getDirection() != interact.getDirection() ){
            context.send(interact, "alert");
        }


    }

    @Override
    public void setState(StateCar state) {

    }
}
