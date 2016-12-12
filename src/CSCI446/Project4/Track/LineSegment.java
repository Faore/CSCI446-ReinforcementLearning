package CSCI446.Project4.Track;

import CSCI446.Project4.DoubleTuple;
import CSCI446.Project4.Tuple;

import java.util.ArrayList;

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
        if(!((Y >= point1.y && Y <= point2.y) || (Y <= point1.y && Y >= point2.y))) {
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
        if(!((X >= point1.x && X <= point2.x) || (X <= point1.x && X >= point2.x))) {
            throw new Exception("LineSegment: Attempted to get value outside range of line segment.");
        }
        return slope*(X - (double)point1.x) + (double)point1.y;
    }

    public DoubleTuple[] getPointsUsingXAxis() throws Exception {
        if(horizontalLine || verticalLine) {
            throw new Exception("Cannot build point list for horizontal or vertical line.");
        }
        double stepSize = 0.5d;
        ArrayList<DoubleTuple> points = new ArrayList<DoubleTuple>();

        if (point1.x < point2.x) {
            //Start incrementing by step size
             for(double x = point1.x; x <= point2.x; x += stepSize) {
                 points.add(new DoubleTuple(x, getYValueForXValue(x)));
             }
        } else {
            //Start incrementing by step size
            for(double x = point1.x; x >= point2.x; x -= stepSize) {
                points.add(new DoubleTuple(x, getYValueForXValue(x)));
            }
        }
        DoubleTuple[] arr = new DoubleTuple[points.size()];
        points.toArray(arr);
        return arr;
    }

    public DoubleTuple[] getPointsUsingYAxis() throws Exception {
        if(horizontalLine || verticalLine) {
            throw new Exception("Cannot build point list for horizontal or vertical line.");
        }
        double stepSize = 0.5d;
        ArrayList<DoubleTuple> points = new ArrayList<DoubleTuple>();

        if (point1.y < point2.y) {
            //Start incrementing by step size
            for(double y = point1.y; y <= point2.y; y += stepSize) {
                points.add(new DoubleTuple(getXValueForYValue(y), y));
            }
        } else {
            //Start incrementing by step size
            for(double y = point1.y; y >= point2.y; y -= stepSize) {
                points.add(new DoubleTuple(getXValueForYValue(y), y));
            }
        }
        DoubleTuple[] arr = new DoubleTuple[points.size()];
        points.toArray(arr);
        return arr;
    }

    public Tuple[]  discretizePoints(DoubleTuple[] arr) {
        //Will always round down to nearest X and Y values. Casting to int conveniently does that.
        ArrayList<Tuple> points = new ArrayList<Tuple>();
        for(int i = 0; i < arr.length; i++) {
            points.add(new Tuple((int) arr[i].x, (int) arr[i].y));
        }
        //Check for and remove duplicates.
        boolean duplicates = true;
        while (duplicates) {
            duplicates = false;
            duplicateCheck:
            for(int i = 0; i < points.size(); i++) {
                for(int j = 0; j < points.size(); j++) {
                    if(i != j) {
                        if(points.get(i).x == points.get(j).x) {
                            if(points.get(i).y == points.get(j).y) {
                                //Duplicate, remove one.
                                points.remove(j);
                                duplicates = true;
                                break duplicateCheck;
                            }
                        }
                    }
                }
            }
        }


        Tuple[] ret = new Tuple[points.size()];
        points.toArray(ret);
        return ret;
    }

    public Tuple[] getIntersectingCellsY() throws Exception {

        if(verticalLine) {
            ArrayList<Tuple> list = new ArrayList<Tuple>();
            if(point1.y > point2.y) {
                for(int i = point1.y; i >= point2.y; i--) {
                    list.add(new Tuple(point1.x, i));
                }
                Tuple[] arr = new Tuple[list.size()];
                list.toArray(arr);
                return arr;
            } else {
                for(int i = point1.y; i <= point2.y; i++) {
                    list.add(new Tuple(point1.x, i));
                }
                Tuple[] arr = new Tuple[list.size()];
                list.toArray(arr);
                return arr;
            }
        }

        return discretizePoints(getPointsUsingYAxis());
    }
    public Tuple[] getIntersectingCellsX() throws Exception {

        if(horizontalLine) {
            ArrayList<Tuple> list = new ArrayList<Tuple>();
            if(point1.x > point2.x) {
                for(int i = point1.x; i >= point2.x; i--) {
                    list.add(new Tuple(i, point1.y));
                }
                Tuple[] arr = new Tuple[list.size()];
                list.toArray(arr);
                return arr;
            } else {
                for(int i = point1.x; i <= point2.x; i++) {
                    list.add(new Tuple(i, point1.y));
                }
                Tuple[] arr = new Tuple[list.size()];
                list.toArray(arr);
                return arr;
            }
        }

        return discretizePoints(getPointsUsingXAxis());
    }
}
