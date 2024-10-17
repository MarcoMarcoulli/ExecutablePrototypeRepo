package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Parabola extends Curve {
	
	private double a;  // Coefficiente della parabola

    public Parabola(Point2D startPoint, Point2D endPoint) {
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
    
    public ArrayList<Point2D> calculatePointList(Point2D startPoint, int numPoints) {
    	ArrayList<Point2D> points = new ArrayList<>();
    	double deltaX = intervalX / (double) (numPoints - 1);
    	double x = startPoint.getX();
    	double y = startPoint.getY();
    	points.add(new Point2D(x,y));
    	for (int i = 0; i < numPoints-1; i++) {
    		x += deltaX;
            y = startPoint.getY() + evaluateY(x-startPoint.getX());
            points.add(new Point2D(x, y));
            System.out.println("x : " + x + "y : " + y);
        }
    	return points;
	}
}
