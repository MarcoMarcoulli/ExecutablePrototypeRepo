package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

public class CubicSpline extends Curve {
		
	    private Point[] controlPoints;
	    private CubicSegment[] segments;
		
	
		public CubicSpline(Point startPoint, Point endPoint, ArrayList<Point> intermediatePoints) {
			super(startPoint, endPoint);
			int n = intermediatePoints.size() + 2;
			controlPoints = new Point[n];
			controlPoints[0] = startPoint;
			for(int i = 1; i <= n-2; i++)
			{
				controlPoints[i] = intermediatePoints.get(i);
			}
			controlPoints[n-1] = endPoint;
			
			segments = new CubicSegment[n-1];
			for(int i=0; i < n-1; i++)
			{
				
			}
		}
		
		
		
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


