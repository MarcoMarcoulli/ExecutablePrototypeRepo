package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class CubicSpline extends Curve {
	
		public CubicSpline(Point2D startPoint, Point2D endPoint) {
			super(startPoint, endPoint);
		}
		
		private double intervalX;
		
		public double evaluateY(double x) {
	    	return Math.sqrt(x);
	    }
	    
	    public ArrayList<Point2D> calculatePointList(Point2D origin, int numPoints) {
	    	ArrayList<Point2D> points = new ArrayList<>();
	    	double deltaX = intervalX / (double) (numPoints - 1);
	    	
	    	for (int i = 0; i < numPoints; i++) {
	    		double x = origin.getX() + i * deltaX;
	            double y = origin.getY() + evaluateY(x-origin.getX());
	            points.add(new Point2D(x, y));
	        }
	    	return points;
	    }
}


