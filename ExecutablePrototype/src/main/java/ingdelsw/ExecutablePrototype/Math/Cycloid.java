package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Cycloid extends Curve {
	
	private double alfa;
	private double r; // Raggio del cerchio generatore della cicloide

    public Cycloid(Point2D startPoint, Point2D endPoint) {
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
    
    public ArrayList<Point2D> calculatePointList(Point2D origin, int numPoints) {
    	ArrayList<Point2D> points = new ArrayList<>();
    	double deltaAlfa = alfa / (double) (numPoints - 1);
    	
    	for (int i = 0; i < numPoints; i++) {
    		double a = i * deltaAlfa;
            points.add(new Point2D(origin.getX()+evaluateX(a),origin.getY()+evaluateY(a)));
        }
    	return points;
    }

}
