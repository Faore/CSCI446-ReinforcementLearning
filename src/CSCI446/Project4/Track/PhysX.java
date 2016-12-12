package CSCI446.Project4.Track;

import CSCI446.Project4.Tuple;

import java.util.ArrayList;

public class PhysX {

    protected Track track;

    protected PhysX(Track track) {
        this.track = track;
    }

    /*
     * Calculate the next state that the agent will be in when applying the given accelerations.
     */
    public State calculateNextState(Tuple newAcc, State curState) {
        int nextVelocityX;
        int nextVelocityY;
        //Insert some non-determinism.
        if(Math.random() < 0.8d) {
            nextVelocityX = curState.velocityX + newAcc.getX();
            nextVelocityY = curState.velocityY + newAcc.getY();
        } else {
            nextVelocityX = curState.velocityX;
            nextVelocityY = curState.velocityY;
        }
        // Can't let the car go past the speed limit.
        if(nextVelocityX > 5) {
            nextVelocityX = 5;
        } else if(nextVelocityX < -5) {
            nextVelocityX = -5;
        }
        if(nextVelocityY > 5) {
            nextVelocityY = 5;
        } else if(nextVelocityY < -5) {
            nextVelocityY = -5;
        }

        int nextPositionX = curState.positionX + nextVelocityX;
        int nextPositionY = curState.positionY + nextVelocityY;
        return new State(nextPositionX, nextPositionY, nextVelocityX, nextVelocityY);
    }

    public Result findResult(State oldState, State newState) throws Exception {

        Tuple[] checkCells = getIntersectingCells(oldState, newState);

        for(Tuple cell : checkCells) {
            //Remember, map accessed backwards.
            if(isOutOfBounds(cell) || this.track.map[cell.getY()][cell.getX()] == CellType.Wall) {
                return Result.Crash;
            }
            if(this.track.map[cell.getY()][cell.getX()] == CellType.Finish) {
                return Result.Finished;
            }
        }

        return Result.Success;
    }

//    protected Tuple findLastSafeLocation(State state) throws Exception {
//        Tuple[] checkCells = getIntersectingCells(state);
//        Tuple last = null;
//        for(Tuple cell : checkCells) {
//            if (isOutOfBounds(cell) || this.track.map[cell.getY()][cell.getX()] == CellType.Wall) {
//                return last;
//            }
//            last = cell;
//        }
//        //It should never get here, but! if it does...
//        throw new Exception("Somehow managed not to find the last safe cell. Did the agent start on an invalid cell?");
//    }

    private boolean isOutOfBounds(Tuple tuple) {
        return tuple.getY() >= track.map.length
                || tuple.getX() >= track.map[0].length
                || tuple.getX() < 0
                || tuple.getY() < 0;
    }

    /*
     * Get the cells this car would "pass through".
     *
     * To get the intersecting cells, the function will find the longest distance traveled in X or Y.
     * The largest axis's travel distance will be split up 2J equal pieces. Where J is the velocity for that axis.
     * Floating point values will be calculated along the vector for these 2J points.
     * The cell that these values appear in will become the intersecting cells.
     */
    private Tuple[] getIntersectingCells(State oldState, State newState) throws Exception {

        boolean useXAxis;
        //Find out if the magnitude of X or Y's velocity is higher.
        //X has the greater magnitude
        useXAxis = Math.abs(newState.velocityX) > Math.abs(newState.velocityY);

        //Line can be represented nicely in point-slope form.
        //There's a cool helper class aptly called LineSegment to help with some math.

        LineSegment line = new LineSegment(new Tuple(oldState.positionX, oldState.positionY), new Tuple(newState.positionX, newState.positionY), ((double)newState.velocityY)/((double)newState.velocityX));
        if(newState.velocityX == 0 && newState.velocityY == 0) {
            return new Tuple[]{new Tuple(newState.positionX, newState.positionY)};
        }

        if(useXAxis) {
            return line.getIntersectingCellsX();
        } else {
            return line.getIntersectingCellsY();
        }
    }
}
