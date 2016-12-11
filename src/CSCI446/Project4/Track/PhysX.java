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
    protected State calculateNextState(int accelerationX, int accelerationY) {
        int nextVelocityX;
        int nextVelocityY;
        //Insert some non-determinism.
        if(Math.random() < 0.8d) {
            nextVelocityX = track.velocityX + accelerationX;
            nextVelocityY = track.velocityY + accelerationY;
        } else {
            nextVelocityX = track.velocityX;
            nextVelocityY = track.velocityY;
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

        int nextPositionX = track.positionX + nextVelocityX;
        int nextPositionY = track.positionY + nextVelocityY;
        return new State(nextPositionX, nextPositionY, nextVelocityX, nextVelocityY);
    }

    protected Result findResult(State state) throws Exception {

        Tuple[] checkCells = getIntersectingCells(state);

        for(Tuple cell : checkCells) {
            //Remember, map accessed backwards.
            if(isOutOfBounds(cell) || this.track.map[cell.y][cell.x] == CellType.Wall) {
                return Result.Crash;
            }
            if(this.track.map[cell.y][cell.x] == CellType.Finish) {
                return Result.Finished;
            }
        }

        return Result.Success;
    }

    protected Tuple findLastSafeLocation(State state) throws Exception {
        Tuple[] checkCells = getIntersectingCells(state);
        Tuple last = null;
        for(Tuple cell : checkCells) {
            if (isOutOfBounds(cell) || this.track.map[cell.y][cell.x] == CellType.Wall) {
                return last;
            }
            last = cell;
        }
        //It should never get here, but! if it does...
        throw new Exception("Somehow managed not to find the last safe cell. Did the agent start on an invalid cell?");
    }

    protected boolean isOutOfBounds(Tuple tuple) {
        return tuple.y >= track.map.length
                || tuple.x >= track.map[0].length
                || tuple.x < 0
                || tuple.y < 0;
    }

    /*
     * Get the cells this car would "pass through".
     *
     * To get the intersecting cells, the function will find the longest distance traveled in X or Y.
     * The largest axis's travel distance will be split up 2J equal pieces. Where J is the velocity for that axis.
     * Floating point values will be calculated along the vector for these 2J points.
     * The cell that these values appear in will become the intersecting cells.
     */
    protected Tuple[] getIntersectingCells(State state) throws Exception {
        int numberOfSplits;
        boolean useXAxis;
        //Find out if the magnitude of X or Y's velocity is higher.
        if(Math.abs(state.velocityX) > Math.abs(state.velocityY)) {
            //X has the greater magnitude
            numberOfSplits = Math.abs(state.velocityX * 2);
            useXAxis = true;
        } else {
            numberOfSplits = Math.abs(state.velocityY * 2);
            useXAxis =false;
        }

        //Line can be represented nicely in point-slope form.
        //There's a cool helper class aptly called LineSegment to help with some math.

        LineSegment line = new LineSegment(new Tuple(track.positionX, track.positionY), new Tuple(state.positionX, state.positionY), ((double)state.velocityY)/((double)state.velocityX));
        if(state.velocityX == 0 && state.velocityY == 0) {
            return new Tuple[]{new Tuple(state.positionX, state.positionY)};
        }

        if(useXAxis) {
            return line.getIntersectingCellsX(numberOfSplits);
        } else {
            return line.getIntersectingCellsY(numberOfSplits);
        }
    }
}
