package CSCI446.Project4;

/**
 * Created by cetho on 12/4/2016.
 */
public class Tuple {
    public final int x;
    public final int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            //Check for equality (Not identicality).
            if(this.x == ((Tuple) obj).x && this.y == ((Tuple) obj).x) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return super.equals(obj);
        }
    }
}
