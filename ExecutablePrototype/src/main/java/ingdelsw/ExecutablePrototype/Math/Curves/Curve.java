package ingdelsw.ExecutablePrototype.Math.Curves;

import java.util.ArrayList;

import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Curve {
	
	protected double intervalX, intervalY;
	protected Point startPoint, endPoint;
	protected static final int numPoints = 10000;
	
	public abstract ArrayList<Point> calculatePointList();
	
	public Curve(Point startPoint, Point endPoint)
	{
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		intervalX = endPoint.getX()-startPoint.getX();
		intervalY = endPoint.getY()-startPoint.getY();
	}
	
	public void drawCurve(GraphicsContext gc) {
		System.out.println("drawCurve chiamato");
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        ArrayList<Point> points = calculatePointList();
        for (int i = 0; i < points.size() - 1; i++) 
        	gc.strokeLine(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
    }
}