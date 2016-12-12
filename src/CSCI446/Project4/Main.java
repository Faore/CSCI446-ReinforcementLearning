package CSCI446.Project4;

import CSCI446.Project4.Algorithms.SARSA;
import CSCI446.Project4.Track.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception, IOException {
        //L-Track simulator
        startSimulator("L-track", new SARSA(.5, .5), false);
        //O-Track Simulator
        startSimulator("O-track", new SARSA(.5, .5), false);
        //R-Track Simulator
        startSimulator("R-track", new SARSA(.5, .5), false);
        //R-Track Simulator startOver
        startSimulator("R-track", new SARSA(1, 1), true);
    }

    private static void startSimulator(String trackName, SARSA sarsa, boolean startOver) throws Exception {
        System.out.println(String.format("Starting SARSA simulator for track: %s with start from beginning: %b", trackName, startOver));
        for(int i = 0; i < 10; i++) {
            System.out.println(String.format("\tSimulation number: %d", i+1));
            Track track = new Track(trackName, startOver);
            Simulation simulation = new Simulation(sarsa, track);
            simulation.startSimulation();
        }
        System.out.println();
    }

}
