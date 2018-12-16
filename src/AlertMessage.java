import jbotsim.Point;

import java.awt.*;
import java.util.ArrayList;

public class AlertMessage {
    public ArrayList<Point> alerts;
    public AlertMessage(){
        alerts = new ArrayList<>();
    }
    public void addAlert(Point p){
        alerts.add(p);
    }
    public ArrayList<Point> getAlerts(){
        return alerts;
    }
    public boolean isInAlert(Point s, Point e){

        Point base = new Point(0,0);
        Point start,end;
        double dist1 = base.distance(s);
        double dist2 = base.distance(e);
        if( dist1 < dist2){
            start = s;
            end = e;
        }
        else{
            start = e;
            end = s;

        }

        Rectangle r;

        if( start.x != end.x)
         r = new Rectangle((int)start.x, (int)start.y-20, (int)(end.x - start.x), 20);
        else
            r = new Rectangle((int)start.x-20, (int)start.y, 20, (int)(end.y - start.y));

        for(Point p : alerts){

            if( r.contains(new java.awt.Point((int)p.x, (int)p.y))){
                return true;
            }
        }
        return false;
    }
    public void copyAlertMessage(AlertMessage newalerts){
        ArrayList<Point> getAllAlert = newalerts.getAlerts();
        this.alerts.addAll(getAllAlert);
    }

}
