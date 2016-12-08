package CSCI446.Project4;

/**
 * Created by cetho on 12/8/2016.
 */
public class DoubleTuple {
    public final double x;
    public final double y;

    public DoubleTuple(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            //Check for equality (Not identicality).
            if(this.x == ((Tuple) obj).x && this.y == ((DoubleTuple) obj).x) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
