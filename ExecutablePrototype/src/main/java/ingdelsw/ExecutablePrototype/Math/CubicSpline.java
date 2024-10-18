package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

public class CubicSpline extends Curve {
	
		public CubicSpline(Point startPoint, Point endPoint) {
			super(startPoint, endPoint);
		}
		
		private double intervalX;
		
		public double evaluateY(double x) {
	    	return Math.sqrt(x);
	    }
	    
	    public ArrayList<Point> calculatePointList(Point origin, int numPoints) {
	    	ArrayList<Point> points = new ArrayList<>();
	    	double deltaX = intervalX / (double) (numPoints - 1);
	    	
	    	for (int i = 0; i < numPoints; i++) {
	    		double x = origin.getX() + i * deltaX;
	            double y = origin.getY() + evaluateY(x-origin.getX());
	            points.add(new Point(x, y));
	        }
	    	return points;
	    }
}


