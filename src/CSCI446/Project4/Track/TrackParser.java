package CSCI446.Project4.Track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrackParser {

    public static CellType[][] parseTrack(String trackName) throws IOException, Exception {
        String file = "tracks/" + trackName + ".txt";

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String sizeString = bufferedReader.readLine();
        String[] sizeStrings = sizeString.split(",");

        int mapHeight = Integer.parseInt(sizeStrings[0]);
        int mapWidth = Integer.parseInt(sizeStrings[1]);

        CellType[][] map = new CellType[mapHeight][mapWidth];

        String line = bufferedReader.readLine();
        int verticalIndex = 0;

        while(line != null) {
            char[] characters = line.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                switch(characters[i]) {
                    case '#':
                        map[verticalIndex][i] = CellType.Wall;
                        break;
                    case 'F':
                        map[verticalIndex][i] = CellType.Finish;
                        break;
                    case 'S':
                        map[verticalIndex][i] = CellType.Start;
                        break;
                    case '.':
                        map[verticalIndex][i] = CellType.Track;
                        break;
                    default:
                        throw new Exception("Found an invalid character in track: " + characters[i]);
                }
            }
            verticalIndex++;
            line = bufferedReader.readLine();
        }

        return map;
    }

    public static void printMap(CellType[][] map) {
        for (int vert = 0; vert < map.length; vert++) {
            for (int hor = 0; hor < map[vert].length; hor++) {
                switch(map[vert][hor]) {
                    case Wall:
                        System.out.print('#');
                        break;
                    case Finish:
                        System.out.print('F');
                        break;
                    case Start:
                        System.out.print('S');
                        break;
                    case Track:
                        System.out.print('.');
                        break;
                }
            }
            System.out.println();
        }
    }
}
