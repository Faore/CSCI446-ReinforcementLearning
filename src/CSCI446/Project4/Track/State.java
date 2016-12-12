package CSCI446.Project4.Track;

import CSCI446.Project4.Tuple;

import java.util.Objects;

public class State {

    final int velocityX;
    final int velocityY;

    final int positionX;
    final int positionY;

    State(int positionX, int positionY, int velocityX, int velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public State(Tuple position, Tuple velocity) {
        velocityX = velocity.getX();
        velocityY = velocity.getY();
        positionX = position.getX();
        positionY = position.getY();
    }
    public Tuple getLocation(){
        return new Tuple(positionX, positionY);
    }

    public Tuple getVelocity() {
        return new Tuple(velocityX, velocityY);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;

        if(!(obj instanceof State)) return false;

        final State other = (State) obj;

        return this.positionY == other.positionY && this.positionX == other.positionX && this.velocityY == other.velocityY && this.velocityX == other.velocityX;
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocityX, velocityY, positionX, positionY);
    }
}
