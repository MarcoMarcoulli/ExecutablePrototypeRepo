package ingdelsw.ExecutablePrototype.Math.Curves;

import java.util.ArrayList;

import ingdelsw.ExecutablePrototype.Math.Point;

public class Parabola extends Curve {
	
	private double a;  // Coefficiente della parabola

    public Parabola(Point startPoint, Point endPoint) {
    	super(startPoint,endPoint);
        a=(endPoint.getX()-startPoint.getX())/Math.pow(endPoint.getY()-startPoint.getY(), 2);
    }
    
    public double getA()
    {
    	return a;
    }
    
    public double evaluateY(double x) {
    	return Math.sqrt(x/a);
    }
    
    public ArrayList<Point> calculatePointList() {
    	ArrayList<Point> points = new ArrayList<>();
    	double deltaX = intervalX / (double) numPoints;
    	double x = startPoint.getX();
    	double y = startPoint.getY();
    	points.add(new Point(x,y));
    	for (int i = 0; i < numPoints; i++) {
    		x += deltaX;
            y = startPoint.getY() + evaluateY(x-startPoint.getX());
            points.add(new Point(x, y));
            System.out.println("x : " + x + "y : " + y);
        }
    	return points;
	}
}
