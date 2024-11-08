package ingdelsw.ExecutablePrototype.Math.Curves;

import java.util.ArrayList;

import ingdelsw.ExecutablePrototype.Math.Point;

public class Cycloid extends Curve {
	
	private double alfa;
	private double r; // Raggio del cerchio generatore della cicloide

    public Cycloid(Point startPoint, Point endPoint) {
    	super(startPoint, endPoint);
    	alfa=calculateAlfa(intervalX,intervalY);
        r=calculateR(intervalX,intervalY);
    }
    
    private double f(double a, double x, double y) {
        return ((a - Math.sin(a)) / (1 - Math.cos(a))) - (x/y);
    }
    
    // Derivata di f(t)
    private double df(double a) {
        double numerator = Math.pow(Math.sin(a), 2)-a*Math.sin(a);
        double denominator = Math.pow(1-Math.cos(a), 2);
        return 1 + numerator / denominator;
    }
    
    // Metodo di Newton-Raphson per trovare t
    private double calculateAlfa(double x, double y) {
        double alfa = 4*Math.atan(x/(2*y)); //buona approssimazione iniziale
        for (int i = 0; i < 100; i++) {
            double f_alfa = f(alfa, x, y);
            double df_alfa = df(alfa);
            double alfa_new = alfa - f_alfa / df_alfa;

            // Controllo la convergenza
            if (Math.abs(alfa_new - alfa) < 1e-6) {
            	System.out.println("alfa : " + alfa_new);
                return alfa_new; // Ritorna il valore di a quando è sufficientemente vicino
            }
            alfa = alfa_new;
        }
        throw new RuntimeException("Il metodo non converge dopo " + 100 + " iterazioni.");
    }
    
    private double calculateR(double x,double y)
    {
    	double r = y/(1-Math.cos(calculateAlfa(x,y)));
    	System.out.println("raggio : " + r);
    	return r;
    }
    
    public double evaluateX(double a) {
    	return r*(a-Math.sin(a));
    }
    
    public double evaluateY(double a) {
    	return r*(1-Math.cos(a));
    }
    
    public Point[] calculatePointList() {
    	Point[] points = new Point[numPoints];
    	double deltaAlfa = alfa / (double) numPoints;
    	double a = 0;
    	for (int i=0; i < numPoints; i++) {
    		points[i] = new Point(startPoint.getX() + evaluateX(a), startPoint.getY() + evaluateY(a));
    		a += deltaAlfa;
        }
    	return points;
    }
    
    public double[] slope()
    {
    	double[] slopes = new double[numPoints];
    	double deltaAlfa = alfa / (double) numPoints;
    	double a = 0;
    	slopes[0] = Math.atan(Double.POSITIVE_INFINITY);
    	System.out.println((slopes[0]/Math.PI)*180);
    	for (int i=1; i < numPoints; i++) {
    		a += deltaAlfa;
            slopes[i] =  Math.atan(Math.sin(a)/(1-Math.cos(a)));
            System.out.println((slopes[i]/Math.PI)*180);
        }
    	return slopes;
    }

}
