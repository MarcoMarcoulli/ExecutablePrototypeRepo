package ingdelsw.ExecutablePrototype.Math.Curves;

import java.util.ArrayList;
import java.util.Random;

import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Curve {
	
	protected double intervalX, intervalY;
	protected Point startPoint, endPoint;
	protected int randomRed, randomGreen, randomBlue;
	protected static final int numPoints = 1000;
	
	public abstract Point[] calculatePointList();
	public abstract double[] slope();
	
	public Curve(Point startPoint, Point endPoint)
	{
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		intervalX = endPoint.getX()-startPoint.getX();
		intervalY = endPoint.getY()-startPoint.getY();
		Random random = new Random();
		randomRed = random.nextInt(255);
		randomBlue = random.nextInt(255);
		randomGreen = random.nextInt(255);
		
	}
	
	public static int getNumPoints()
	{
		return numPoints;
	}
	
	public void drawCurve(GraphicsContext gc) {
		System.out.println("drawCurve chiamato");
		Random random = new Random();
        gc.setStroke(Color.rgb(randomRed, randomGreen, randomBlue));
        gc.setLineWidth(2);
        Point[] points = new Point[numPoints];
        points = calculatePointList();
        for (int i = 0; i < numPoints - 1; i++) 
        	gc.strokeLine(points[i].getX(), points[i].getY(), points[i+1].getX(), points[i+1].getY());
    }
}
