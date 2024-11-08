package ingdelsw.ExecutablePrototype.Math.Curves;

import ingdelsw.ExecutablePrototype.Math.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class CubicSpline extends Curve {
	private PolynomialSplineFunction splineFunction;  // Funzione spline generata
	private Point[] controlPoints;  // Array dei punti di controllo
	
	 public CubicSpline(Point startPoint, Point endPoint, ArrayList<Point> intermediatePoints) {
	        super(startPoint, endPoint);

	        int n = intermediatePoints.size() + 2;
	    	controlPoints = new Point[n];
	    	controlPoints[0] = startPoint;
	    	for(int i = 1; i < n-1; i++)
	    	{
	    		controlPoints[i] = intermediatePoints.get(i-1);
	    	}
	    	controlPoints[n-1] = endPoint;
	    	
	    	Arrays.sort(controlPoints, Comparator.comparingDouble(Point::getX));
	    	
	    	double[] x = new double[n];
	    	double[] y = new double[n];

	        // Estrai le coordinate x e y ordinate dei punti di controllo
	    	for(int i=0; i < n; i++)
	    	{
	    		x[i] = controlPoints[i].getX();
	    		y[i] = controlPoints[i].getY();
	    	}
	    	
	        // Costruisci la funzione spline cubica utilizzando Apache Commons Math
	    	if(controlPoints.length>2) {
	    		SplineInterpolator interpolator = new SplineInterpolator();
	    		splineFunction = interpolator.interpolate(x, y);
	    	}
	    	else{
	    		splineFunction = null;
	    	}
	    }
	 
	 	// Metodo per valutare la spline in un punto x
	    public double evaluateY(double x) {
	    	if(splineFunction == null)
	    	{
	    		double m = (controlPoints[1].getY() - controlPoints[0].getY()) / (controlPoints[1].getX() - controlPoints[0].getX());
	    		return m * (x - controlPoints[0].getX()) + controlPoints[0].getY();
	    	}
	    	else return splineFunction.value(x);
	    }
	    
	    public Point[] calculatePointList()
        {
        	Point[] points = new Point[numPoints];
        	double deltaX = intervalX / (double) numPoints;
        	double x = startPoint.getX();
        	double y = startPoint.getY();
        	for (int i=0; i < numPoints; i++) {
        		y = evaluateY(x);
        		points[i] = new Point(x, y);
        		x += deltaX;
            }
        	return points;
        }
	    
	    public double[] slope()
	    {
	    	double[] slopes = new double[numPoints];
	    	if(splineFunction == null)
	    	{
	    		double m = (controlPoints[1].getY() - controlPoints[0].getY()) / (controlPoints[1].getX() - controlPoints[0].getX());
	    		for(int i=0; i < numPoints; i++)
	    		{	
	    			slopes[i] = m;
	    			System.out.println((slopes[i]/Math.PI)*180);
	    		}
	    		return slopes;
	    	}
	    	else {
		    	double deltaX = intervalX / (double) numPoints;
		    	double x = startPoint.getX();
		    	for (int i=0; i < numPoints; i++) {
		            slopes[i] =  Math.atan(splineFunction.derivative().value(x));
		            x += deltaX;
		            System.out.println((slopes[i]/Math.PI)*180);
		        }
		    	return slopes;
	    	}
	    }
}
