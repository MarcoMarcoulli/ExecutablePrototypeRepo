package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Curve {
	
	protected double intervalX, intervalY;
	
	public Curve(Point2D startPoint, Point2D endPoint)
	{
		intervalX = endPoint.getX()-startPoint.getX();
		intervalY = endPoint.getY()-startPoint.getY();
	}
	
	public abstract double evaluateY(double parametro);
	
	public void drawCurve(Point2D startPoint, int numPoints, GraphicsContext gc) {
		System.out.println("drawCurve chiamato");
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        ArrayList<Point2D> points = calculatePointList(startPoint, numPoints);
        for (int i = 0; i < points.size() - 1; i++) 
        	gc.strokeLine(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
    }
	
	protected ArrayList<Point2D> calculatePointList(Point2D startPoint, int numPoints) {
    	ArrayList<Point2D> points = new ArrayList<>();
    	double deltaX = intervalX / (double) (numPoints - 1);
    	double x=startPoint.getX();
    	double y=startPoint.getY();
    	points.add(new Point2D(x,y));
    	for (int i = 0; i < numPoints-1; i++) {
    		x += deltaX;
            y = startPoint.getY() + evaluateY(x-startPoint.getX());
            points.add(new Point2D(x, y));
            System.out.println("x : " + x + "y : " + y);
        }
    	return points;
    }
	
}
