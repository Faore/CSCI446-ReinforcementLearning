package CSCI446.Project4.Track;

import CSCI446.Project4.Tuple;

import java.util.ArrayList;

/**
 * Created by cetho on 12/4/2016.
 */
public class Track {

    // If true, does what it says. If false, will drop character at last spot before crash.
    protected boolean crashRevertsToBeginning;

    //PhysX - So we don't completely fill up this class
    protected PhysX physX;

    //Stores the actual grid and start cells.
    protected CellType[][] map; //NOTE: MAP IS INDEXED VERTICALLY THEN HORIZONTALLY. (X,Y coordinate is at Y,X)
    protected ArrayList<Tuple> startCells;

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

    public Track(String trackName, boolean crashRevertsToBeginning) throws Exception {
        this.map = TrackParser.parseTrack(trackName);
        this.crashRevertsToBeginning = crashRevertsToBeginning;
        this.useDefaultRandomStart = true;

        this.physX = new PhysX(this);

        for(int vert = 0; vert < this.map.length; vert++) {
            for (int hor = 0; hor < this.map[vert].length; hor++) {
                if(this.map[vert][hor] == CellType.Start) {
                    startCells.add(new Tuple(hor, vert));
                }
            }
        }
    }

    public void makeMove(int accelerationX, int accelerationY) {

    }

    /*
     * Sets the cars next start position to an X and Y coordinate.
     */
    public void setNextStartPosition(int x, int y) {
        this.useDefaultRandomStart = false;
        this.startX = x;
        this.startY = y;
    }

    public void enableDefaultStartPosition() {
        this.useDefaultRandomStart = true;
    }

    /*
     * Reset the penalty function, track, and dump the car on the start position;
     */
    public void reset() {
        this.penaltyValue = 0;
        this.velocityX = 0;
        this.velocityY = 0;

        Tuple position = getNextStartPosition();
        this.positionX = position.x;
        this.positionY = position.y;
    }

    /*
     * Use to change how crashes work on the next and following resets. (Don't want to change in middle of a run.)
     */
    public void reset(boolean crashRevertsToBeginning) {
        this.crashRevertsToBeginning = crashRevertsToBeginning;
        this.reset();
    }

    /*
     * Gets the next start position based on the settings.
     */
    protected Tuple getNextStartPosition() {
        if(this.useDefaultRandomStart) {
            return this.startCells.get((int) (Math.random() * this.startCells.size()));
        } else {
            return new Tuple(startX, startY);
        }
    }
}
