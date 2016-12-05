package CSCI446.Project4.Track;

/**
 * Created by cetho on 12/4/2016.
 */
public class Track {

    // If true, does what it says. If false, will drop character at last spot before crash.
    protected boolean crashRevertsToBeginning;
    //Stores the actual grid.
    protected CellType[][] map;

    //Start position management. If the boolean is true, the car will be randomly placed on the starting line.
    protected boolean useDefaultRandomStart;
    protected int startX;
    protected int startY;

    //The constantly changing values during simulation
    protected int penaltyValue;
    protected int positionX;
    protected int positionY;
    protected int velocityX;
    protected int velocityY;

    public Track(String trackName, boolean crashRevertsToBeginning) {

    }

    public void makeMove(int accelerationX, int accelerationY) {

    }

    /*
     * Sets the cars next start position to an X and Y coordinate.
     */
    public void setNextStartPosition() {

    }

    /*
     * Reset the penalty function, track, and dump the car on the start position;
     */
    public void reset() {

    }

    /*
     * Use to change how crashes work on the next and following resets.
     */
    public void reset(boolean crashRevertsToBeginning) {

    }
}
