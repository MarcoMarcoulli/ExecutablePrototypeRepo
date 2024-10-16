package ingdelsw.ExecutablePrototype.Math;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Parabola extends Curve {
	
	private double a;  // Coefficiente della parabola

    public Parabola(Point2D startPoint, Point2D endPoint) {
    	intervalX=endPoint.getX()-startPoint.getX();
        a=(endPoint.getX()-startPoint.getX())/Math.pow(endPoint.getY()-startPoint.getY(), 2);
    }
    
    public double getA()
    {
    	return a;
    }
    
    public double evaluateY(double x) {
    	return Math.sqrt(x/a);
    }
}
