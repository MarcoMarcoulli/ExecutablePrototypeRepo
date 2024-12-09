package ingdelsw.ExecutablePrototype.Math.Curves;


import ingdelsw.ExecutablePrototype.Math.Point;



public class Circumference extends Curve {
	
	private double r; 
	private int convexity;

    
    //initial circumference constructor
    public Circumference(Point startPoint, Point endPoint, int convexity) {
    	super(startPoint, endPoint);
        this.convexity=convexity;
        if(convexity == -1)
        	this.r = (Math.pow(intervalX, 2)+Math.pow(intervalY, 2))/(2 * intervalY) + 1;
        else this.r = (Math.pow(intervalX, 2)+Math.pow(intervalY, 2))/(2 * intervalX);
    }
    
    public Circumference(Point startPoint, Point endPoint, int convexity, double r) {
    	super(startPoint, endPoint);
    	this.r = r;
        this.convexity = convexity;
    }
    
    
    public double getR()
    {
    	return r;
    }
    
    public double evaluateFunction(double var) {
    	return Math.sqrt(2*var*r - Math.pow(var, 2));
    }
    
    public Point[] calculatePoints() {
    	Point[] points = new Point[numPoints];
    	double xCenter = xCenter(startPoint) + startPoint.getX();
    	double yCenter = yCenter(startPoint) + startPoint.getY();
    	double x, y;
    	
    	if(convexity == 1)
    	{
    		double t, xPow;
    		
        	for (int i=0; i < numPoints; i++) {
        		t = (double) i / (numPoints - 1); // Parametro normale da 0 a 1
                xPow = intervalX * Math.pow(t, 3);     // Densità maggiore all'inizio con t^2
        		x = startPoint.getX() + xPow;
                y = yCenter + evaluateFunction(x + r - xCenter);
                points[i] = new Point(x,y);
            }
    	}
    	
    	else if(convexity == -1)
    	{
    		double t, yPow;
    		
        	for (int i=0; i < numPoints; i++) {
        		t = (double) i / (numPoints - 1); // Parametro normale da 0 a 1
                yPow = intervalY * Math.pow(t, 3);     // Densità maggiore all'inizio con t^2
        		y = startPoint.getY() + yPow;
                x = xCenter + (intervalX/Math.abs(intervalX))*evaluateFunction(y + r - yCenter);
                points[i] = new Point(x,y);
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
    	//System.out.print("xCenter : " + (xCenter+startPoint.getX()));
    	//System.out.print(" xR : " + (xCenter+startPoint.getX()-r));
    	return xCenter;
    }
    
    private double yCenter(Point startPoint)
    {
    	double yCenter = (Math.pow(intervalX, 2) + Math.pow(intervalY, 2) - 2*xCenter(startPoint)*intervalX)/(2*intervalY);
    	//System.out.println(" yCenter : " + (yCenter+startPoint.getY()));
    	return yCenter;
    } 
    
    public double[] calculateSlopes()
    {
    	double[] slopes = new double[numPoints];
    	
    	if(convexity == 1)
    	{
    		double t, xCubic, x;
    		double x0 = r - xCenter(startPoint);
        	for (int i=0; i < numPoints; i++) {
        		t = (double) i / (numPoints - 1); // Parametro normale da 0 a 1
                xCubic = intervalX * Math.pow(t, 3);     // Densità maggiore all'inizio con t^3
                x = x0 +xCubic;
                slopes[i] = Math.atan((r-x)/Math.sqrt(2*r*x - Math.pow(x, 2)));
                //System.out.println((slopes[i]/Math.PI)*180);
            }
    	}
    	
    	else if(convexity == -1)
    	{
    		double t, yPow, y;
    		double y0 = r - yCenter(startPoint);
        	for (int i=0; i < numPoints; i++) {
        		t = (double) i / (numPoints - 1); // Parametro normale da 0 a 1
                yPow = intervalY * Math.pow(t, 3);     // Densità maggiore all'inizio con t^3
                y = y0 + yPow;
                slopes[i] = Math.PI/2 - Math.atan((r-y)/Math.sqrt(2*r*y - Math.pow(y, 2)));
                //System.out.println((slopes[i]/Math.PI)*180);
            }
    	}
    	return slopes;
    }
    
    public String curveName()
	{
		return "circonferenza";
	}
}