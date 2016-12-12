package CSCI446.Project4.Algorithms;

import CSCI446.Project4.Tuple;

public class Action{
    private int x;
    private int y;
    public Action(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Tuple getTuple(){
        return new Tuple(this.x, this.y);
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}