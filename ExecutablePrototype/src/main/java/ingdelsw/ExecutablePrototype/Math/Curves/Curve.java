package ingdelsw.ExecutablePrototype.Math.Curves;

import java.util.ArrayList;
import java.util.Random;

import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Curve {
	
	protected double intervalX, intervalY;
	protected Point startPoint, endPoint;
	protected int red, green, blue;
	protected static final int numPoints = 1000;
	
	public abstract Point[] calculatePoints();
	public abstract double[] calculateSlopes();
	
	public Curve(Point startPoint, Point endPoint)
	{
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		intervalX = endPoint.getX()-startPoint.getX();
		intervalY = endPoint.getY()-startPoint.getY();
		
	}
	
	public void setRandomColors()
	{
		Random random = new Random();
		red = random.nextInt(230);
		blue = random.nextInt(230);
		green = random.nextInt(230);
	}
	
	public void setRed(int red)
	{
		this.red = red;
	}
	
	public void setGreen(int green)
	{
		this.green = green;
	}
	
	public void setBlue(int blue)
	{
		this.blue = blue;
	}
	
	public int getRed()
	{
		return red;
	}
	
	public int getGreen()
	{
		return green;
	}
	
	public int getBlue()
	{
		return blue;
	}
	
	public static int getNumPoints()
	{
		return numPoints;
	}
	
	public Point getStartPoint()
	{
		return startPoint;
	}
	
}
