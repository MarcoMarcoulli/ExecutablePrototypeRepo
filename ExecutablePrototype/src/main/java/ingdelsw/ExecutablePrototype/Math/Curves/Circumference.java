package ingdelsw.ExecutablePrototype.Math.Curves;

import java.util.ArrayList;

import ingdelsw.ExecutablePrototype.Math.Point;

public class Circumference extends Curve {
	
	private double r; 
	private int convexity;

    public Circumference(Point startPoint, Point endPoint, double r, int convexity) {
    	super(startPoint, endPoint);
        this.r=r;
        this.convexity=convexity;
    }
    
    //initial circumference constructor
    public Circumference(Point startPoint, Point endPoint, int convexity) {
    	super(startPoint, endPoint);
    	if(convexity == 1)
    		r = (Math.pow(intervalX, 2)+Math.pow(intervalY, 2))/(2*intervalX);
    	else r = (Math.pow(intervalX, 2)+Math.pow(intervalY, 2))/(2*intervalY);
        this.convexity=convexity;
    }
    
    public void setR(double r)
    {
    	this.r=r;
    }
    
    public double getR()
    {
    	return r;
    }
    
    public double evaluateFunction(double var) {
    	return Math.sqrt(2*var*r - Math.pow(var, 2));
    }
    
    public ArrayList<Point> calculatePointList() {
    	ArrayList<Point> points = new ArrayList<>();
    	double x = startPoint.getX();
    	double y = startPoint.getY();
    	double xCenter = xCenter(startPoint) + x;
    	double yCenter = yCenter(startPoint) + y;
    	points.add(new Point(x,y));
    	
    	if(convexity == 1)
    	{
    		double deltaX = intervalX / (double) numPoints;
    		
        	for (int i = 0; i < numPoints; i++) {
        		x += deltaX;
                y = yCenter + evaluateFunction(x + r - xCenter);
                points.add(new Point(x, y));
                System.out.println("x : " + x + "y : " + y);
            }
    	}
    	
    	else if(convexity == -1)
    	{
    		double deltaY = intervalY / (double) (numPoints - 1);
    		
        	for (int i = 0; i < numPoints-1; i++) {
        		y += deltaY;
                x = xCenter + (intervalX/Math.abs(intervalX))*evaluateFunction(y + r - yCenter);
                points.add(new Point(x, y));
                System.out.println("x : " + x + "y : " + y);
            }
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
    	xCenter = (bCoefficient() + convexity*(intervalX/Math.abs(intervalX))*Math.sqrt(delta()))/(2*aCoefficient());
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