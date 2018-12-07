import jbotsim.Point;

/**
 * Created by lbouquin on 06/12/18.
 */
public class Street {
    private Point startPos;
    private Point endPos;

    public Street(Point start , Point end ){
        this.startPos = start;
        this.endPos = end;

    }

    public void setEndPos(Point endPos) {
        this.endPos = endPos;
    }

    public void setStartPos(Point startPos) {

        this.startPos = startPos;
    }

    public Point getEndPos() {

        return endPos;
    }

    public Point getStartPos() {

        return startPos;
    }
}
