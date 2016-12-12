package CSCI446.Project4.Track;

import CSCI446.Project4.Car;
import CSCI446.Project4.Tuple;

import java.util.ArrayList;

public class Track {

    // If true, does what it says. If false, will drop character at last spot before crash.
    private boolean crashRevertsToBeginning;

    //Stores the actual grid and start cells.
    final CellType[][] map; //NOTE: MAP IS INDEXED VERTICALLY THEN HORIZONTALLY. (X,Y coordinate is at Y,X)
    private ArrayList<Tuple> startCells;

    //Start position management. If the boolean is true, the car will be randomly placed on the starting line.
    private boolean useDefaultRandomStart;
    private int startX;
    private int startY;

    private Car car;

    public Track(String trackName, boolean crashRevertsToBeginning) throws Exception {
        this.map = TrackParser.parseTrack(trackName);
        this.crashRevertsToBeginning = crashRevertsToBeginning;
        this.useDefaultRandomStart = false;

        this.startCells = new ArrayList<Tuple>();

        for(int vert = 0; vert < this.map.length; vert++) {
            for (int hor = 0; hor < this.map[vert].length; hor++) {
                if(this.map[vert][hor] == CellType.Start) {
                    startCells.add(new Tuple(hor, vert));
                }
            }
        }
        //Randomly place the car on the start line.
        Tuple startCell = this.startCells.get((int) (Math.random() * this.startCells.size()));
        car = new Car(startCell.getTuple(), new PhysX(this));
        this.startX = startCell.getX();
        this.startY = startCell.getY();
    }

    public CellType getCellType(Tuple loc){
        if(map.length > loc.getY() && map[0].length > loc.getX() && loc.getX() > 0 && loc.getY() > 0)
            return map[loc.getY()][loc.getX()];
        else
            return CellType.Wall;
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
     * Reset the track, and dump the car on the start position;
     */
    public void reset() {
        if(crashRevertsToBeginning) {
            Tuple position = getNextStartPosition();
            car.resetState(new State(position, new Tuple(0, 0)));
        }else{
            car.goToLastState();
        }
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

    public Car getCar() {
        return car;
    }
}
