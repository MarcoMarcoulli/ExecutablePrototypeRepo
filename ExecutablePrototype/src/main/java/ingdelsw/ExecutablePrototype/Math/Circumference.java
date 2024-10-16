package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Circumference extends Curve {
	
	private double r; // Raggio della circonferenza

    public Circumference(Point2D startPoint, Point2D endPoint, double r) {
    	super(startPoint, endPoint);
    	
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
    
    @Override
    protected ArrayList<Point2D> calculatePointList(Point2D startPoint, int numPoints) {
    	ArrayList<Point2D> points = new ArrayList<>();
    	
    	double deltaX = intervalX / (double) (numPoints - 1);
    	double x = startPoint.getX() - xCenter() - r;
    	double y = startPoint.getY();
    	
    	for (int i = 0; i < numPoints; i++) {
    		x += i * deltaX;
            y += evaluateY(x-startPoint.getX());
            points.add(new Point2D(x, y));
            System.out.println("x : " + x + "y : " + y);
        }
    	return points;
    }
    
    private double aCoefficient()
    {
    	return 4*(Math.pow(intervalX, 2) + Math.pow(intervalY, 2));
    }
    
    private double bCoefficient()
    {
    	return 4*(Math.pow(intervalX, 3) + intervalX* Math.pow(intervalY, 2));
    }
    
    private double cCoefficient()
    {
    	return Math.pow(Math.pow(intervalX, 2) + Math.pow(intervalY, 2), 2) - 4*Math.pow(intervalY, 2)*Math.pow(r, 2);
    }
    
    private double delta()
    {
    	return Math.pow(bCoefficient(), 2) - 4*aCoefficient()*cCoefficient();
    }
    
    private double xCenter()
    {	
    	return (bCoefficient()-Math.sqrt(delta()))/2*aCoefficient();
    }
    
    private double yCenter()
    {
    	return (Math.pow(intervalX, 2) + Math.pow(intervalY, 2) - 2*xCenter()*intervalX)/2*intervalY;
    }
    
    
}