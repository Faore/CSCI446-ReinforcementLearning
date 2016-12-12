package CSCI446.Project4.Algorithms;

import CSCI446.Project4.Track.State;
import CSCI446.Project4.Tuple;

import java.util.Objects;

public class Action{
    private int x;
    private int y;
    Action(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Tuple getTuple(){
        return new Tuple(this.x, this.y);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;

        if(!(obj instanceof Action)) return false;

        final Action other = (Action) obj;

        return this.y == other.y && this.x == other.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}