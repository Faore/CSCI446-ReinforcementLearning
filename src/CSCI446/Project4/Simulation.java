package CSCI446.Project4;

import CSCI446.Project4.Algorithms.Action;
import CSCI446.Project4.Algorithms.SARSA;
import CSCI446.Project4.Track.*;

import java.io.IOException;

class Simulation {
    private SARSA ai;
    private Track track;
    private int crashR;
    private int normalR;
    private int finishR;

    private State lastState;
    private Action lastAction;

    Simulation(SARSA ai, Track track){
        this.ai = ai;
        this.track = track;

        crashR = -100;
        normalR = -1;
        finishR = 100;
    }

    private Result stepSimulation() throws Exception{
        int reward = getReward();
        State state = getState();
        Action action = ai.decision(state);
        Tuple actionT = action.getTuple();
        if(lastAction != null)
            ai.learn(lastState, lastAction, reward, state, action);
        this.lastState = state;
        this.lastAction = action;

        return track.makeMove(actionT.getX(), actionT.getY());
    }

    private State getState() {
        return track.getCurrentState();
    }

    private int getReward() {
        CellType cellType = track.getCellType(track.getCurrentLocation());
        switch(cellType){
            case Finish:
                return finishR;
            case Wall:
                return crashR;
            case Track:
            default:
                return normalR;
        }
    }

    void startSimulation(int iterations) throws IOException {
        int maxLoops = 1;
        int count = 0;
        if(iterations > 0)
            maxLoops = iterations;
        Result stepResult = Result.Success;
        while(stepResult != Result.Finished){
            // Simulate stuff
            try {
                stepResult = stepSimulation();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

            if(stepResult == Result.Crash){
                track.reset();
            }

            count++;
            System.out.print("\033[H\033[2J");
            System.out.println("\nIteration: " + count + " Result: " + stepResult.toString());
            track.printTrack();
            if(iterations > 0)
                maxLoops--;
        }
    }
}
