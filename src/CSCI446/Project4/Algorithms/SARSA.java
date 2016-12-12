package CSCI446.Project4.Algorithms;

import CSCI446.Project4.Track.State;
import CSCI446.Project4.Track.Track;
import CSCI446.Project4.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SARSA implements Algorithm{
    private double a;
    private double g;
    private double e;
    //Store all the q values
    //Lookup w/ State x Action
    private Map<State, Map<Action, Double>> Q;
    private List<Action> actions;

    public SARSA(double a, double g){
        this.a = a;
        this.g = g;
        this.e = 0;
        this.Q = new HashMap<>();

        actions = generateActions(-1, 1);
    }

    // Generate a list of actions from min/max inclusive
    private List<Action> generateActions(int min, int max) {
        List<Action> generatedActions = new ArrayList<>();
        for(int i = min; i <= max; i++){
            for(int j = min; j <= max; j++){
                generatedActions.add(new Action(i,j));
            }
        }

        return generatedActions;
    }

    // Get Q value based on state and action
    private double getQ(State state, Action action){
        if(!(this.Q.containsKey(state) && this.Q.get(state).containsKey(action))){
            if(Q.containsKey(state)){
               Q.get(state).put(action, 0.0);
            }else{
                Map<Action, Double> tmpMap = new HashMap<>();
                tmpMap.put(action, 0.0);
                this.Q.put(state, tmpMap);
            }
        }
        return Q.get(state).get(action);
    }

    // Choose an action based on state
    public Action decision(State state){
        if(Math.random() < this.e)
            return randomAction(); //TODO: Implement random action

        if(!Q.containsKey(state)) {
            Map<Action, Double> tmp = new HashMap<>();
            for(int i = 0; i < actions.size(); i++){
                tmp.put(actions.get(i), 0.0);
            }
            Q.put(state, tmp);
        }
        // Find max Qs
        // If more than 1, return random from Q
        // Loop through actions
        double maxQ = 0.0;
        int maxQi = -1; //TODO: Random
        for(int i = 0; i < actions.size(); i++){
            if(Q.get(state).containsKey(actions.get(i))){
                if(Q.get(state).get(actions.get(i)) > maxQ){
                    maxQ = Q.get(state).get(actions.get(i));
                    maxQi = i;
                }
            }
        }
        if(maxQi > 0)
            return actions.get(maxQi);
        else
            return randomAction();
    }

    private Action randomAction(){
        int x = -1 + (int)(Math.random() * ((1 - -1) + 1));
        int y = -1 + (int)(Math.random() * ((1 - -1) + 1));
        return new Action(x,y);
    }
    public void learn(State s1, Action a1, double r, State s2, Action a2){
        double nextQ = this.getQ(s2, a2);
        this.generateQ(s1, a1, r, r + this.g * nextQ);
    }

    private void generateQ(State s, Action a, double r, double v){
        if(this.Q.containsKey(s) && this.Q.get(s).containsKey(a)) {
            double oldQ = this.Q.get(s).get(a);
            if (oldQ == 0.0) {
                this.Q.get(s).put(a, r);
            } else {
                double tmp = oldQ + this.a * (v - oldQ);
                this.Q.get(s).put(a, tmp);
            }
        }else{
            if(this.Q.containsKey(s)) {
                this.Q.get(s).put(a, r);
            }else{
                Map<Action, Double> tmpMap = new HashMap<>();
                tmpMap.put(a, r);
                this.Q.put(s, tmpMap);
            }
        }
    }
}

