package CSCI446.Project4.Algorithms;

import java.util.*;
import CSCI446.Project4.Track.*;
import CSCI446.Project4.*;
import java.io.IOException;

/**
 * Created by cetho on 12/4/2016.
 */
public class ValueIteration {
    //cost to move: 1
    private Track track;
    private double discountFactor = 0.9;//insures later adjustments are valued less
    private double threshhold = 0.01;//threshhold for convergence
    private double change = 1.0;//A non-zero start amount of change
    private Double utilityArray[][][][];//has the utility values for each space on the track
    private Double rewardArray[][];//has the reward values for each space on the track
    private int count = 0;//counts number of iterations
    private boolean conv = false;//boolean to check for convergence
    
    public ValueIteration(Track track) throws Exception, IOException{
        this.track = track;
        this.discountFactor = discountFactor;
        this.threshhold = threshhold;
        this.change = change;
        this.utilityArray = utilityArray;
        this.rewardArray = rewardArray;
        
        //create a blank array for utility and set reward values
        utilityArray = new Double[track.map.length][track.map[0].length][11][11];
        rewardArray = new Double[track.map.length][track.map[0].length];
        //create a list of touples that are the track spaces
        ArrayList<Tuple> tupleList = new ArrayList<>();
        //initialize values
        for(int i = 0; i < utilityArray.length; i ++){
            for(int j = 0; j < utilityArray[i].length; j ++){
                for(int l = -5; l < 5; l++){                        
                    for(int m = -5; m < 5; m ++){
                if(track.map[i][j].toString().matches("Finish")){
                    utilityArray[i][j][l+ 5][m+ 5] = 100.0;
                    rewardArray[i][j] = 100.0;
                }
                if(track.map[i][j].toString().matches("Track")){
                    utilityArray[i][j][l+ 5][m+ 5] = 0.0;
                    rewardArray[i][j] = -1.0;
                    tupleList.add(new Tuple(i,j));
                }
                if(track.map[i][j].toString().matches("Start")){
                    utilityArray[i][j][l + 5][m+ 5] = 0.0;
                    rewardArray[i][j] = -1.0;
                    tupleList.add(new Tuple(i,j));
                }
                if(track.map[i][j].toString().matches("Wall")){
                    utilityArray[i][j][l + 5][m + 5] = -10.0;
                    rewardArray[i][j] = -10.0;
                }
            }
        }}}
        //printArray(utilityArray);
        //printArray(rewardArray);
        
            //adding all possible actions
            ArrayList<Action> actions = new ArrayList<>();
            actions.add(new Action(0,-1));
            actions.add(new Action(0,0));
            actions.add(new Action(0,1));
            actions.add(new Action(1,-1));
            actions.add(new Action(1,0));
            actions.add(new Action(1,1));
            actions.add(new Action(-1,-1));
            actions.add(new Action(-1,0));
            actions.add(new Action(-1,1));
            
            //arraylist of candidate utilities
            ArrayList<Double> candidateUtils;   //array list of candidate utility values
            //System.out.println(t);

            count = 1;  //need a non-zero count to enter the loop
            while (conv == false){
                conv = checkConv();
                    change = 0.0;   //set change to zero initially
                for(int i = 0; i < tupleList.size(); i ++){                     //List of Xs and Ys on the track
                            double reward = rewardArray[tupleList.get(i).x][tupleList.get(i).y];//value of reward = reward at that location
                            candidateUtils = new ArrayList<>();                 //wipes the arraylist
                            for(int l = -5; l < 5; l++){                        //all x velocities
                                for(int m = -5; m < 5; m ++){                   //all y velocities
                                    for (int k = 0; k < actions.size(); k++) {  //all acceleration options
                                        int x = tupleList.get(i).x + actions.get(k).x ;
                                        int y = tupleList.get(i).y + actions.get(k).y ;
                                        if(utilityArray[tupleList.get(i).x][tupleList.get(i).y][l+5][m+5] != null && utilityArray[x][y][l+5][m+5] != null){
                                            candidateUtils.add(reward + discountFactor * (0.8 * (utilityArray[x][y][l+5][m+5]) + 0.2 * (utilityArray[tupleList.get(i).x][tupleList.get(i).y][l+5][m+5])));
                                        }
                                    }
                                }
                            }
                            //Choose max utility out of all the options
                            for(int l = -5; l < 5; l++){                        //all x velocities
                                for(int m = -5; m < 5; m ++){
                                    double temp = utilityArray[tupleList.get(i).x][tupleList.get(i).y][l+ 5][m+ 5];
                                    utilityArray[tupleList.get(i).x][tupleList.get(i).y][l+ 5][m+ 5] = (Collections.max(candidateUtils));
                                    if (Math.abs(utilityArray[tupleList.get(i).x][tupleList.get(i).y][l+ 5][m+ 5] - temp) > change) {
                                        change = Math.abs(utilityArray[tupleList.get(i).x][tupleList.get(i).y][l+ 5][m+ 5] - temp);
                                        //System.out.println(change);
                                    }
                                }
                            }
                    }
                
                    //debugging prints
                    System.out.println("");
                    System.out.println("ITERATION #: " + count);
                    System.out.println("LARGEST CHANGE: " + change);
                    for(int i = 0; i < track.map.length; i ++){
                        for(int j = 0; j < track.map[i].length; j ++){
                            for(int l = -5; l < 5; l++){                        //all x velocities
                                for(int m = -5; m < 5; m ++){
                            if(utilityArray[i][j] != null){
                            System.out.println("(" + i + ", " + j + ") " + utilityArray[i][j][l+5][m+5]);
                            }}}
                        }
                    }
                count = count + 1;
                }
            printArray(utilityArray);
    }
    
    //print utility array
    public void printArray(Double temp[][][][]){
        for(int i = 0; i < temp.length; i ++){
            for(int j = 0; j < temp[i].length; j ++){
                
                System.out.print(temp[i][j][5][5] + " "); 
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
