package CSCI446.Project4.Algorithms;

import java.util.*;
import CSCI446.Project4.Track.*;
import java.io.IOException;

/**
 * Created by cetho on 12/4/2016.
 */
public class ValueIteration {
    //cost to move: 1
    private Track track;
    private double discountFactor = 0.9;
    private double threshhold = 0.01;
    private double change = 1.0;
    private Double utilityArray[][];//has the utility values for each space on the track
    private Double rewardArray[][];//has the reward values for each space on the track
    private int itteration = 0;
    private int count = 0;
    private boolean conv = false;
    
    
    public ValueIteration(Track track) throws Exception, IOException{
        this.track = track;
        this.discountFactor = discountFactor;
        this.threshhold = threshhold;
        this.change = change;
        this.utilityArray = utilityArray;
        this.rewardArray = rewardArray;
        
        //create a blank array for utility and set reward values
        utilityArray = new Double[track.map.length][track.map[0].length];
        rewardArray = new Double[track.map.length][track.map[0].length];
        for(int i = 0; i < utilityArray.length; i ++){
            for(int j = 0; j < utilityArray[i].length; j ++){
                if(track.map[i][j].toString().matches("Finish")){
                    utilityArray[i][j] = 0.0;
                    rewardArray[i][j] = 100.0;
                }
                if(track.map[i][j].toString().matches("Track")){
                    utilityArray[i][j] = 0.0;
                    rewardArray[i][j] = -0.04;
                }
                if(track.map[i][j].toString().matches("Start")){
                    utilityArray[i][j] = 0.0;
                    rewardArray[i][j] = -0.04;
                }
                if(track.map[i][j].toString().matches("Wall")){
                    utilityArray[i][j] = null;
                    rewardArray[i][j] = -1.0;
                }
            }
        }
        //printArray(utilityArray);
        //printArray(rewardArray);
        
            //adding all possible actions
            ArrayList<Action> actions = new ArrayList<>();
            actions.add(new Action(0,1));
            actions.add(new Action(0,-1));
            actions.add(new Action(1,0));
            actions.add(new Action(1,1));
            actions.add(new Action(1,-1));
            actions.add(new Action(-1,0));
            actions.add(new Action(-1,1));
            actions.add(new Action(-1,-1));
            
            //arraylist of candidate utilities
            ArrayList<Double> candidateUtils;
//            System.out.println(t);

            count = 1;
            //while loop runs until convergence @threshhold * (1 - discountFactor)) / discountFactor)
            while (conv == false){
                conv = checkConv();
                for(int i = 0; i < utilityArray.length; i ++){
                    for(int j = 0; j < utilityArray[i].length; j ++){
                        if(utilityArray[i][j] != null){
                        candidateUtils = new ArrayList<>();//wipes the arraylist
                        double reward = rewardArray[i][j];
                        // the utility of its neighbors
                        for (int k = 0; k < actions.size(); k++) {
                            int x = i + actions.get(k).getX();
                            int y = j + actions.get(k).getY();
                            if(utilityArray[i][j] != null && utilityArray[x][y] != null){
                                candidateUtils.add(reward + discountFactor * (0.8 * (utilityArray[x][y]) + 0.2 * (utilityArray[i][j])));
                            }
                        }
                        //Choose max utility out of all the options
                        double temp = utilityArray[i][j];
                        utilityArray[i][j] = (Collections.max(candidateUtils));
                        if (Math.abs(utilityArray[i][j] - temp) > change) {
                            change = Math.abs(utilityArray[i][j] - temp);
                            //System.out.println(change);
                        }}
                    }
                }
                    //debugging prints
                    System.out.println("\nIteration " + count);
                    System.out.println("Change " + change + "\n");
                    System.out.println("  x   y  Utility");
                    for(int i = 0; i < track.map.length; i ++){
                        for(int j = 0; j < track.map[i].length; j ++){
                            if(utilityArray[i][j] != null){
                            System.out.println("(" + i + ", " + j + ") " + utilityArray[i][j]);
                            }
                        }
                    }
                count = count + 1;
                }
    }
    
    //print utility array
    public void printArray(Double temp[][]){
        for(int i = 0; i < temp.length; i ++){
            for(int j = 0; j < temp[i].length; j ++){
                System.out.print(temp[i][j] + " "); 
            }
            System.out.println();
        }
    }
    
    //check for convergence
    public boolean checkConv(){
        if(change < (threshhold * (1 - discountFactor)) / discountFactor){
            return true;
        }
        return false;
    }
}
