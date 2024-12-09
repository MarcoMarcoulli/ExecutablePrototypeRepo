package ingdelsw.ExecutablePrototype.Math.Curves;

import ingdelsw.ExecutablePrototype.Math.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class CubicSpline extends Curve {
	private PolynomialSplineFunction splineFunction;  // Funzione spline generata
	private Point[] controlPoints;  // Array dei punti di controllo
	
	 public CubicSpline(Point startPoint, Point endPoint, List<Point> list) {
	        super(startPoint, endPoint);

	        int n = list.size() + 2;
	    	controlPoints = new Point[n];
	    	controlPoints[0] = startPoint;
	    	for(int i = 1; i < n-1; i++)
	    	{
	    		controlPoints[i] = list.get(i-1);
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
	    
	    public Point[] calculatePoints()
        {
        	Point[] points = new Point[numPoints];
        	double x, y,  t, xPow;
        	for (int i=0; i < numPoints - 1; i++) {
        		t = (double) i / (numPoints - 1); // Parametro normale da 0 a 1
                xPow = intervalX * Math.pow(t, 3);     // Densità maggiore all'inizio con t^2
                x = startPoint.getX() + xPow;
        		y = evaluateY(x);
        		points[i] = new Point(x, y);
        		//System.out.println("punto " + i + "-esimo - X : " + x + " Y : "+ y );
            }
        	points[numPoints-1] = endPoint;
        	return points;
        }
	    
	    public double[] calculateSlopes()
	    {
	    	double[] slopes = new double[numPoints];
	    	if(splineFunction == null)
	    	{
	    		double m = (controlPoints[1].getY() - controlPoints[0].getY()) / (controlPoints[1].getX() - controlPoints[0].getX());
	    		for(int i=0; i < numPoints; i++)
	    		{	
	    			slopes[i] = Math.atan(m);
	    			System.out.println((slopes[i]/Math.PI)*180);
	    		}
	    		return slopes;
	    	}
	    	else {
	    		double x;
	    		double  t;
	    		double  xPow;
		    	for (int i=0; i < numPoints - 1; i++) {
		    		t = (double) i / (numPoints - 1); // Parametro normale da 0 a 1
	                xPow = intervalX * Math.pow(t, 3);     // Densità maggiore all'inizio con t^2
	                x = startPoint.getX() + xPow;
		            slopes[i] = Math.atan(splineFunction.derivative().value(x));
		            //System.out.println("slopes "+ i + "-esima : " + (slopes[i]/Math.PI)*180);
		    	}
		    	slopes[numPoints-1] = slopes[numPoints-2];
		    	return slopes;
	    	}
	    }
	    
	    public String curveName()
		{
			return "spline";
		}
}
