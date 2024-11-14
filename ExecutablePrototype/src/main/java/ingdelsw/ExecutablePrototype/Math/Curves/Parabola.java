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
    
    public Point[] calculatePoints() 
    {
    	Point[] points = new Point[numPoints];
    	double x = startPoint.getX();
    	double y = startPoint.getY();
    	double t, xPow;
    	for (int i=0; i < numPoints; i++) {
    		t = (double) i / (numPoints - 1); // Parametro normale da 0 a 1
            xPow = intervalX * Math.pow(t, 3);   // Densità maggiore all'inizio con t^2.5
            x = startPoint.getX() + xPow;
            y = startPoint.getY() + evaluateY(x-startPoint.getX());
            points[i] = new Point(x,y);
    		System.out.println("punto " + i + "-esimo - X : " + x + " Y : "+ y );
        }
    	return points;
	}
    
    public double[] calculateSlopes()
    {
    	double[] slopes = new double[numPoints];
    	double x = startPoint.getX();
    	double t, xPow;
    	for (int i=0; i < numPoints; i++) {
    		t = (double) i / (numPoints - 1); // Parametro normale da 0 a 1
            xPow = intervalX * Math.pow(t, 3);     // Densità maggiore all'inizio con t^2.5
    		slopes[i] =  Math.atan(0.5 * Math.sqrt(1/(a*xPow)));
            System.out.println((slopes[i]/Math.PI)*180);
        }
    	return slopes;
    }
    
    public String curveName()
	{
		return "parabola";
	}
    
}
