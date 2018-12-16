import jbotsim.Point;

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

        for(Point p : alerts){
            if(p.x >= start.x && p.x <= end.x && p.y >= start.y && p.y < end.y){
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
