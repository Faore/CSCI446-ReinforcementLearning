package CSCI446.Project4;

import CSCI446.Project4.Algorithms.Action;

import java.util.Objects;

public class Tuple {
    public int x;
    public int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Tuple getTuple() {
        return new Tuple(x, y);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;

        if(!(obj instanceof Tuple)) return false;

        final Tuple other = (Tuple) obj;

        return this.y == other.y && this.x == other.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
