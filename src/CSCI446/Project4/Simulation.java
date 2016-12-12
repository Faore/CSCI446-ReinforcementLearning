package CSCI446.Project4;

import CSCI446.Project4.Algorithms.Action;
import CSCI446.Project4.Algorithms.SARSA;
import CSCI446.Project4.Track.CellType;
import CSCI446.Project4.Track.Result;
import CSCI446.Project4.Track.State;
import CSCI446.Project4.Track.Track;

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

    Result stepSimulation() throws Exception{
        int reward = getReward();
        State state = getState();
        Action action = ai.decision(state);
        Tuple actionT = action.getTuple();
        if(lastAction != null)
            ai.learn(lastState, lastAction, reward, state, action);
        Result result = track.makeMove(actionT.getX(), actionT.getY());
        this.lastState = state;
        this.lastAction = action;
        return result;
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

    void startSimulation(int iterations){
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

            count++;
            System.out.println("Iteration: " + count + "Result: " + stepResult.toString());
            if(iterations > 0)
                maxLoops--;
        }
    }
}
