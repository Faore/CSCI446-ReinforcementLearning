package CSCI446.Project4;

import CSCI446.Project4.Track.CellType;
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
    }

}
