package CSCI446.Project4.Track;

/**
 * Created by cetho on 12/4/2016.
 */
public class State {

    public final int velocityX;
    public final int velocityY;

    public final int positionX;
    public final int positionY;

    public State(int positionX, int positionY, int velocityX, int velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = positionX;
        this.positionY = positionY;
    }

}
