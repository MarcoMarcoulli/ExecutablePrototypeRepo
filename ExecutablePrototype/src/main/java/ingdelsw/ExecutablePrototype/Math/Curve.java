package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Curve {
	
	protected double intervalX, intervalY;
	
	public abstract ArrayList<Point> calculatePointList(Point startPoint, int numPoints);
	public abstract double evaluateY(double parametro);
	
	public Curve(Point startPoint, Point endPoint)
	{
		intervalX = endPoint.getX()-startPoint.getX();
		intervalY = endPoint.getY()-startPoint.getY();
	}
	
	public void drawCurve(Point startPoint, int numPoints, GraphicsContext gc) {
		System.out.println("drawCurve chiamato");
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        ArrayList<Point> points = calculatePointList(startPoint, numPoints);
        for (int i = 0; i < points.size() - 1; i++) 
        	gc.strokeLine(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
    }
}
