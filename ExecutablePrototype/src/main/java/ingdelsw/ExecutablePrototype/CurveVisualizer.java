package ingdelsw.ExecutablePrototype;

import java.util.Random;

import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CurveVisualizer {
		
		public static void drawCurve(Point[] points, GraphicsContext gc, int red, int green , int blue) {
			
	        gc.setStroke(Color.rgb(red, green, blue));
	        gc.setLineWidth(2);
	        for (int i = 0; i < points.length - 1; i++) 
	        	gc.strokeLine(points[i].getX(), points[i].getY(), points[i+1].getX(), points[i+1].getY());
		}
}
