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
    
    public Point[] calculatePoints() {
    	Point[] points = new Point[numPoints];
    	double deltaX = intervalX / (double) (numPoints-1);
    	double x = startPoint.getX();
    	double y = startPoint.getY();
    	for (int i=0; i < numPoints; i++) {
    		points[i] = new Point(x,y);
    		x += deltaX;
            y = startPoint.getY() + evaluateY(x-startPoint.getX());
        }
    	return points;
	}
    
    public double[] calculateSlopes()
    {
    	double[] slopes = new double[numPoints];
    	double deltaX = intervalX / (double) (numPoints-1);
    	double x = 0;
    	for (int i=0; i < numPoints; i++) {
    		slopes[i] =  Math.atan(0.5 * Math.sqrt(1/(a*x)));
    		x += deltaX;
            System.out.println((slopes[i]/Math.PI)*180);
        }
    	return slopes;
    }
    
}
