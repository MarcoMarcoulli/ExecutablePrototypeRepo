package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Circumference extends Curve {
	
	private double r; // Raggio della circonferenza

    public Circumference(Point2D startPoint, Point2D endPoint, double r) {
    	intervalX = endPoint.getX()-startPoint.getX();
        this.r=r;
    }
    
    public void setR(double r)
    {
    	this.r=r;
    }
    
    public double getR()
    {
    	return r;
    }
    
    public double evaluateY(double x) {
    	return Math.sqrt(2*x*r - Math.pow(x, 2));
    }
}
