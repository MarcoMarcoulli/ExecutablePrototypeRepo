package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.Mass;
import ingdelsw.ExecutablePrototype.Math.Point;
import ingdelsw.ExecutablePrototype.Math.Curves.Curve;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;

public class SimulationManager {
	
	private Mass mass;  // Oggetto rappresentante la massa
    private Curve curve;
    private Point[] points;
    private double[] slopes;
    private double[] times;
   
    private Canvas drawingCanvas;  // Pannello di disegno per la visualizzazione
    private Timeline timeline;  // Animazione della simulazione
    private static int g = 100;

	public SimulationManager(Curve curve, Canvas drawingCanvas) {
        mass=null;
        this.drawingCanvas = drawingCanvas;
        this.curve = curve;
        this.points = curve.calculatePointList();
        this.timeline = new Timeline();
    }
	
	public Curve getCurve()
	{
		return curve;
	}
	
	public void setMass(Mass mass)
	{
		this.mass = mass;
	}
	
	public Mass getMass()
	{
		return mass;
	}
	
	public void setSlopes(double[] slopes)
	{
		this.slopes = slopes;
	}
	
	public double[] calculateTimeParametrization()
	{
		times = new double[Curve.getNumPoints()];
		times[0] = 0;
		int i=0;
		System.out.println("parametrizzazione curva rispetto al tempo");
		while(true)
		{
			times[i+1] = times[i] + (1/(Math.sqrt(2*g*points[i + 1].getY()) * Math.sin(slopes[i + 1]))) * (points[i+1].getY() - points[i].getY());
			System.out.println(times[i]);
			i++;
			if(i == Curve.getNumPoints() - 1)
				break;
		}
		return times;
	}

}
