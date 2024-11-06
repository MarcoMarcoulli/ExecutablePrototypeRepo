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
		    	for (int i=1; i < numPoints; i++) {
		            slopes[i] =  Math.atan(splineFunction.derivative().value(x));
		            x += deltaX;
		            System.out.println((slopes[i]/Math.PI)*180);
		        }
		    	return slopes;
	    	}
	    }
}

/*
 * import ingdelsw.ExecutablePrototype.Math.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CubicSpline extends Curve {

    private class CubicSegment {
        private double a, b, c, d;  // Coefficienti del segmento cubico
        private Point p0, p1;

        public CubicSegment(double a, double b, double c, double d, Point p0, Point p1) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.p0 = p0;
            this.p1 = p1;
        }

        public double evaluateY(double x) {
            double dx = x - p0.getX();
            return a + b * dx + c * dx * dx + d * dx * dx * dx;
        }
    }

    private Point[] controlPoints;
    private CubicSegment[] segments;

    public CubicSpline(Point startPoint, Point endPoint, ArrayList<Point> intermediatePoints) {
        super(startPoint, endPoint);
        int n = intermediatePoints.size() + 2;
        controlPoints = new Point[n];
        controlPoints[0] = startPoint;

        for (int i = 1; i < n - 1; i++) {
            controlPoints[i] = intermediatePoints.get(i - 1);
        }
        controlPoints[n - 1] = endPoint;

        Arrays.sort(controlPoints, Comparator.comparingDouble(Point::getX));
        double[] moments = calculateMoments();
        segments = new CubicSegment[n - 1];

        for (int i = 0; i < n - 1; i++) {
            double h = controlPoints[i + 1].getX() - controlPoints[i].getX();
            double a = controlPoints[i].getY();
            double b = (controlPoints[i + 1].getY() - controlPoints[i].getY()) / h
                       - h * (2 * moments[i] + moments[i + 1]) / 3;
            double c = moments[i] / 2;
            double d = (moments[i + 1] - moments[i]) / (3 * h);

            segments[i] = new CubicSegment(a, b, c, d, controlPoints[i], controlPoints[i + 1]);
        }
    }

    private double[] calculateMoments() {
        int n = controlPoints.length;
        double[] moments = new double[n];
        double[] h = new double[n - 1];
        double[] alpha = new double[n - 1];

        for (int i = 0; i < n - 1; i++) {
            h[i] = controlPoints[i + 1].getX() - controlPoints[i].getX();
            alpha[i] = (controlPoints[i + 1].getY() - controlPoints[i].getY()) / h[i];
        }

        double[] l = new double[n];
        double[] mu = new double[n];
        double[] z = new double[n];

        l[0] = 1;
        z[0] = 0;
        mu[0] = 0;

        for (int i = 1; i < n - 1; i++) {
            double g = 2 * (h[i - 1] + h[i]) - h[i - 1] * mu[i - 1];
            mu[i] = h[i] / g;
            z[i] = (3 * (controlPoints[i + 1].getY() * h[i - 1]
                       - controlPoints[i].getY() * (controlPoints[i + 1].getX() - controlPoints[i - 1].getX())
                       + controlPoints[i - 1].getY() * h[i]) / (h[i - 1] * h[i])
                       - h[i - 1] * z[i - 1]) / g;
        }

        l[n - 1] = 1;
        z[n - 1] = 0;
        moments[n - 1] = 0;

        for (int j = n - 2; j >= 0; j--) {
            moments[j] = z[j] - mu[j] * moments[j + 1];
        }

        return moments;
    }

    public ArrayList<Point> calculatePointList(int numPoints) {
        ArrayList<Point> points = new ArrayList<>();
        for (CubicSegment segment : segments) {
            double deltaX = (segment.p1.getX() - segment.p0.getX()) / numPoints;
            for (int i = 0; i < numPoints; i++) {
                double x = segment.p0.getX() + i * deltaX;
                double y = segment.evaluateY(x);
                points.add(new Point(x, y));
            }
        }
        return points;
    }
}
*/
