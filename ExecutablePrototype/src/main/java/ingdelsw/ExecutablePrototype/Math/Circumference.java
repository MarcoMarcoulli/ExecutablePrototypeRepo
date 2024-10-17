package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    protected ArrayList<Point2D> calculatePointList(Point2D startPoint, int numPoints, GraphicsContext gc) {
    	ArrayList<Point2D> points = new ArrayList<>();
    	
    	double deltaX = intervalX / (double) (numPoints - 1);
    	double x = startPoint.getX();
    	double y = startPoint.getY();
    	double xCenter = xCenter(startPoint) + x;
    	double yCenter = yCenter(startPoint) + y;
    	visualizePoint(gc, xCenter, yCenter);
    	points.add(new Point2D(x,y));
    	for (int i = 0; i < numPoints-1; i++) {
    		x += deltaX;
            y = yCenter + evaluateY(x + r - xCenter);
            points.add(new Point2D(x, y));
            System.out.println("x : " + x + "y : " + y);
        }
    	return points;
    }
    
    private void visualizePoint(GraphicsContext gc, double x, double y)
    {
    	gc.setFill(Color.GREEN);
        gc.fillOval(x - 5, y - 5, 10, 10);
    }
    
    
    private double aCoefficient()
    {
    	return Math.pow(intervalX, 2) + Math.pow(intervalY, 2);
    }
    
    private double bCoefficient()
    {
    	return intervalX*((Math.pow(intervalX, 2) + Math.pow(intervalY, 2)));
    }
    
    private double cCoefficient()
    {
    	return (Math.pow(Math.pow(intervalX, 2) + Math.pow(intervalY, 2), 2)/4) - Math.pow(intervalY, 2)*Math.pow(r, 2);
    }
    
    private double delta()
    {
    	return Math.pow(bCoefficient(), 2) - 4*aCoefficient()*cCoefficient();
    }
    
    private double xCenter(Point2D startPoint)
    {	
    	double xCenter = (bCoefficient() + Math.sqrt(delta()))/(2*aCoefficient());
    	System.out.print("xCenter : " + (xCenter+startPoint.getX()));
    	System.out.print(" xR : " + (xCenter+startPoint.getX()-r));
    	return xCenter;
    }
    
    private double yCenter(Point2D startPoint)
    {
    	double yCenter = (Math.pow(intervalX, 2) + Math.pow(intervalY, 2) - 2*xCenter(startPoint)*intervalX)/(2*intervalY);
    	System.out.println(" yCenter : " + (yCenter+startPoint.getY()));
    	return yCenter;
    } 
}