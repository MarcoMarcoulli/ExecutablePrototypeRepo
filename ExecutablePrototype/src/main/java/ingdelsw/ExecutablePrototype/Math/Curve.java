package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Curve {
	
	protected double intervalX;
	
	public abstract double evaluateY(double parametro);
	
	public void drawCurve(Point2D origin, int numPoints, GraphicsContext gc) {
		System.out.println("drawCurve chiamato");
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        ArrayList<Point2D> points = calculatePointList(origin, numPoints);
        for (int i = 0; i < points.size() - 1; i++) 
        	gc.strokeLine(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
    }
	
	public ArrayList<Point2D> calculatePointList(Point2D origin, int numPoints) {
    	ArrayList<Point2D> points = new ArrayList<>();
    	double deltaX = intervalX / (double) (numPoints - 1);
    	
    	for (int i = 0; i < numPoints; i++) {
    		double x = origin.getX() + i * deltaX;
            double y = origin.getY() + evaluateY(x-origin.getX());
            points.add(new Point2D(x, y));
            System.out.println("x : " + x + "y : " + y);
        }
    	return points;
    }
	
}
