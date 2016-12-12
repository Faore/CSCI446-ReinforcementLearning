package CSCI446.Project4.Algorithms;

import CSCI446.Project4.Car;
import CSCI446.Project4.DoubleTuple;
import CSCI446.Project4.Track.Result;
import CSCI446.Project4.Track.State;
import CSCI446.Project4.Track.Track;
import CSCI446.Project4.Tuple;

import java.util.ArrayList;

/**
 * Created by cetho on 12/12/2016.
 */
public class StateGradientAgent {

    //x,y,xv,yx
    private Double[][][][] utilityTable;
    private Track track;
    private Car car;
    public int iterations;
    public int crashes;

    public StateGradientAgent(Double[][][][] utilityTable, Track track) {
        this.utilityTable = utilityTable;
        this.track = track;
        this.car = track.getCar();
    }

    public boolean attemptTrack() throws Exception {
        this.track.enableDefaultStartPosition();
        this.track.reset();

        boolean done = false;
        State currentState;
        Tuple newAcceleration;
        Result result = Result.Success; //Just to so Java doesn't scream that the variable might not be initialized.
        iterations = 0;
        crashes = 0;
        while(!done) {
            //Make a move.
            currentState = car.getCurrentState();
            newAcceleration = getAcceleration(currentState, findBestStateToTransitionTo(currentState));
            result = car.applyAction(new Action(newAcceleration.x, newAcceleration.y));
            if(result == Result.Crash) {
                crashes++;
                car.goToSafeState();
            }
            iterations++;
            if(iterations > 50000 || result == Result.Finished) {
                done = true;
            }
        }
        return result == Result.Finished;
    }

    public State findBestStateToTransitionTo(State currentState) {
        double highest = -100;
        int highestIndex = -1;
        ArrayList<State> states = new ArrayList<State>();
        //Enumerate all possible changes (Minus staying the same)
        states.add(car.physx.calculateNextIdealState(new Tuple(0,0), currentState));

        states.add(car.physx.calculateNextIdealState(new Tuple(-1,-1), currentState));
        states.add(car.physx.calculateNextIdealState(new Tuple(-1,0), currentState));
        states.add(car.physx.calculateNextIdealState(new Tuple(-1,1), currentState));

        states.add(car.physx.calculateNextIdealState(new Tuple(0,-1), currentState));
        states.add(car.physx.calculateNextIdealState(new Tuple(0,1), currentState));

        states.add(car.physx.calculateNextIdealState(new Tuple(1,-1), currentState));
        states.add(car.physx.calculateNextIdealState(new Tuple(1,0), currentState));
        states.add(car.physx.calculateNextIdealState(new Tuple(1,1), currentState));

        for(int i = 0; i < states.size(); i++) {
            State state = states.get(i);
            //Position Checks
            if(state.positionY >= 0 && state.positionY < utilityTable.length
                    && state.positionX >= 0 && state.positionX < utilityTable[0].length
                    && state.velocityY + 5 >= 0 && state.velocityY + 5 < utilityTable[0][0].length
                    && state.velocityX + 5 >= 0 && state.velocityX + 5 < utilityTable[0][0][0].length) {
                Double utilityValue = this.utilityTable[state.positionY][state.positionX][state.velocityY + 5][state.velocityX + 5];
                if(utilityValue != null) {
                    if (utilityValue >= highest) {
                        highest = utilityValue;
                        highestIndex = i;
                    }
                } else {
                    System.out.println();
                }
            }
        }
        return states.get(highestIndex);
    }

    public Tuple getAcceleration(State currentState, State newState) {
        return new Tuple(newState.velocityX - currentState.velocityX, newState.velocityY - currentState.velocityY);
    }
}
