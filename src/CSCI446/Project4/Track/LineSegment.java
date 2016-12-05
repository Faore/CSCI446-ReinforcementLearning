package CSCI446.Project4.Track;

import CSCI446.Project4.Tuple;

/**
 * Created by cetho on 12/5/2016.
 */
public class LineSegment {

    public final Tuple point1;
    public final Tuple point2;

    public final double slope;

    public final boolean verticalLine;
    public final boolean horizontalLine;

    public LineSegment(Tuple point1, Tuple point2, double slope) {
        this.point1 = point1;
        this.point2 = point2;
        this.slope = slope;
        if (point1.x == point2.x) {
            this.verticalLine = true;
        } else {
            this.verticalLine = false;
        }
        if (point1.y == point2.y) {
            this.horizontalLine = true;
        } else {
            this.horizontalLine = false;
        }
    }

    public double getXValueForYValue(double Y) throws Exception {
        //Can't give you an X value for a horizontal line
        if(horizontalLine) {
            throw new Exception("LineSegment: Attempted to get Y value for a vertical line.");
        }
        //Make sure the Y value is within the line segment
        if(!((Y > point1.y && Y < point2.y) || (Y < point1.y && Y > point2.y))) {
            throw new Exception("LineSegment: Attempted to get value outside range of line segment.");
        }

        return (Y - (double)point1.y)/slope + (double)point1.x;
    }

    public double getYValueForXValue(double X) throws Exception {
        //Can't give you a Y value for a vertical line.
        if(verticalLine) {
            throw new Exception("LineSegment: Attempted to get Y value for a vertical line.");
        }
        //Make sure the X value is within the line segment
        if(!((X > point1.y && X < point2.y) || (X < point1.y && X > point2.y))) {
            throw new Exception("LineSegment: Attempted to get value outside range of line segment.");
        }
        return slope*(X - (double)point1.x) + (double)point1.y;
    }
}
