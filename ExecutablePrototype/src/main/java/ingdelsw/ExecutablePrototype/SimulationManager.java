package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.Math.Point;
import ingdelsw.ExecutablePrototype.Math.Curves.Curve;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

import javafx.scene.canvas.Canvas;

public class SimulationManager {
	
	private Mass mass;  // Oggetto rappresentante la massa
    private Curve curve;
    private Point[] points;
    private double[] slopes;
    private double[] times;
   
    private Timeline timeline;  // Animazione della simulazione
    private static int g = 100;

	public SimulationManager(Curve curve) {
        mass=null;
        this.curve = curve;
        this.points = curve.calculatePoints();
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
	
	public Point[] getPoints()
	{
		return points;
	}
	
	public void setSlopes(double[] slopes)
	{
		this.slopes = slopes;
	}
	
	public double[] calculateTimeParametrization()
	{
		times = new double[Curve.getNumPoints()];
		times[0] = 0;
		System.out.println("parametrizzazione curva rispetto al tempo");
		for(int i = 0; i < Curve.getNumPoints() - 1; i++)
		{
			times[i+1] = times[i] + (1/(Math.sqrt(2*g*points[i + 1].getY()) * Math.sin(slopes[i + 1]))) * (Math.abs(points[i+1].getY() - points[i].getY()));
			System.out.println(times[i]);
		}
		return times;
	}
	

    public void startAnimation() {

        // Crea un KeyFrame per ciascun punto della curva
        for (int i = 0; i <points.length ; i++) {
            Point point = points[i];
            System.out.println("X : " + points[i].getX() + " Y : " + points[i].getY());
            System.out.println("Tempi calcolati: " + times[i]);

            Duration duration = Duration.seconds(times[i]);

            // Creiamo un evento personalizzato che userà relocate per spostare la massa
            KeyFrame keyFrame = new KeyFrame(duration, event -> {
                mass.getIcon().relocate(point.getX() - 30, point.getY() - 30);
            });

            // Aggiunge il KeyFrame alla Timeline
            timeline.getKeyFrames().add(keyFrame);
        }
        // Avvia l'animazione
        timeline.play();
    }

}
