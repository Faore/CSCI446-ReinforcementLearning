package CSCI446.Project4;

import CSCI446.Project4.Track.CellType;
import CSCI446.Project4.Track.LineSegment;
import CSCI446.Project4.Track.TrackParser;

import java.io.IOException;

/**
 * Created by cetho on 12/4/2016.
 */
public class Main {

    public static void main(String[] args) throws Exception, IOException {
        CellType[][] track = TrackParser.parseTrack("L-Track");
        System.out.println("Track loaded.");
        TrackParser.printMap(track);

        //Testing Line Segments
        Tuple p1 = new Tuple(1,1);
        Tuple p2 = new Tuple(2,5);
        LineSegment testSegment = new LineSegment(p1, p2, 4); //Represents moving up 4, across 1.
        Tuple[] points = testSegment.getIntersectingCellsY(8); //Using the Y axis to split because it is larger, using twice the size of Y as splitting points.

        System.out.println("If the car moves from point " + p1 + " to " + p2 + ", it will cross the following cells:");
        for(Tuple point : points) {
            System.out.println(point);
        }
    }

}
