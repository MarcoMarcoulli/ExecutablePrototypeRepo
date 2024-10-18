package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

public class Circumference extends Curve {
	
	private double r; // Raggio della circonferenza

    public Circumference(Point startPoint, Point endPoint, double r) {
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
    
    public ArrayList<Point> calculatePointList(Point startPoint, int numPoints) {
    	ArrayList<Point> points = new ArrayList<>();
    	
    	double deltaX = intervalX / (double) (numPoints - 1);
    	double x = startPoint.getX();
    	double y = startPoint.getY();
    	double xCenter = xCenter(startPoint) + x;
    	double yCenter = yCenter(startPoint) + y;
    	points.add(new Point(x,y));
    	for (int i = 0; i < numPoints-1; i++) {
    		x += deltaX;
            y = yCenter + evaluateY(x + r - xCenter);
            points.add(new Point(x, y));
            System.out.println("x : " + x + "y : " + y);
        }
    	return points;
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
    
    private double xCenter(Point startPoint)
    {	
    	double xCenter;
    	xCenter = (bCoefficient() + (intervalX/Math.abs(intervalX)) * Math.sqrt(delta()))/(2*aCoefficient());
    	System.out.print("xCenter : " + (xCenter+startPoint.getX()));
    	System.out.print(" xR : " + (xCenter+startPoint.getX()-r));
    	return xCenter;
    }
    
    private double yCenter(Point startPoint)
    {
    	double yCenter = (Math.pow(intervalX, 2) + Math.pow(intervalY, 2) - 2*xCenter(startPoint)*intervalX)/(2*intervalY);
    	System.out.println(" yCenter : " + (yCenter+startPoint.getY()));
    	return yCenter;
    } 
}