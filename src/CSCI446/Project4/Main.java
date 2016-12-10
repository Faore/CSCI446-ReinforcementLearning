package CSCI446.Project4;

import CSCI446.Project4.Track.*;

import java.io.IOException;

/**
 * Created by cetho on 12/4/2016.
 */
public class Main {

    public static void main(String[] args) throws Exception, IOException {
        Track track = new Track("L-Track", true);
        System.out.println("Track loaded.");
        TrackParser.printMap(track.map);

        //Testing Line Segments
        Tuple p1 = new Tuple(1,1);
        Tuple p2 = new Tuple(2,5);
        LineSegment testSegment = new LineSegment(p1, p2, 4); //Represents moving up 4, across 1.
        Tuple[] points = testSegment.getIntersectingCellsY(8); //Using the Y axis to split because it is larger, using twice the size of Y as splitting points.

        System.out.println("If the car moves from point " + p1 + " to " + p2 + ", it will cross the following cells:");
        for(Tuple point : points) {
            System.out.println(point);
        }
        System.out.println("\nTesting track traversal:");
        System.out.println("Start Location:" + track.getCurrentLocation());
        while(track.makeMove((int) (Math.random()*2 - 1),(int) (Math.random()*2 - 1)) == Result.Success) {
            System.out.println(track.getCurrentLocation());
        }
        System.out.println("Crash or Finish.");

    }

}
