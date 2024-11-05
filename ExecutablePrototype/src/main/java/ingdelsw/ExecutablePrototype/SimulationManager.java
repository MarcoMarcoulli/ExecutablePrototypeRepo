package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.Mass;
import ingdelsw.ExecutablePrototype.Math.Curves.Curve;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;

public class SimulationManager {
	
	private Mass mass;  // Oggetto rappresentante la massa
    private Curve curve;
   
    private Canvas drawingCanvas;  // Pannello di disegno per la visualizzazione
    private Timeline timeline;  // Animazione della simulazione
    private int numSteps = 300;  // Numero di passi della simulazione

	public SimulationManager(Curve curve, Canvas drawingCanvas) {
        mass=null;
        this.drawingCanvas = drawingCanvas;
        this.curve = curve;
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

}
