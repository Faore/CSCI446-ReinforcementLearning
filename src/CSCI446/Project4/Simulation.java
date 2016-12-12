package CSCI446.Project4;

import CSCI446.Project4.Algorithms.Action;
import CSCI446.Project4.Algorithms.SARSA;
import CSCI446.Project4.Track.*;

class Simulation {
    private SARSA ai;
    private Track track;
    private Car car;
    private int crashR;
    private int normalR;
    private int finishR;

    Simulation(SARSA ai, Track track){
        this.ai = ai;
        this.track = track;
        car = this.track.getCar();

        crashR = -100;
        normalR = -1;
        finishR = 100;
    }

    private Result stepSimulation() throws Exception{
        int reward = getReward();
        State state = getState();
        ai.update(state);
        Action action = ai.decision(state);
        if(car.getLastAction() != null)
            ai.learn(car.getLastState(), car.getLastAction(), reward, state, action);
        return car.applyAction(action);
//        return track.makeMove(actionT.getX(), actionT.getY());
    }

    private State getState() {
        return car.getCurrentState();
    }

    private int getReward() {
        CellType cellType = track.getCellType(car.getCurrentLocation());
        switch(cellType){
            case Finish:
                return finishR;
            case Wall:
                track.reset();
                return crashR;
            case Track:
            default:
                return normalR;
        }
    }

    void startSimulation() throws Exception {
        int count = 0;
        Result stepResult = Result.Success;
        while(stepResult != Result.Finished){
            // Simulate stuff
            stepResult = stepSimulation();

            count++;
//            System.out.println("\nIteration: " + count + " Result: " + stepResult.toString());
//            track.printTrack();
        }
        System.out.println(String.format("\tIteration: %d Result: %s", count, stepResult));
        System.out.println();
    }
}
