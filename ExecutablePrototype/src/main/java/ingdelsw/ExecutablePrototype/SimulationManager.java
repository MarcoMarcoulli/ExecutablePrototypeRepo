package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.Math.Point;
import ingdelsw.ExecutablePrototype.Math.Curves.Curve;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SimulationManager {
	

    private Mass mass;  // Oggetto rappresentante la massa
    private Curve curve;
    private Point[] points;
    private double[] slopes;
    private double[] times;
    
    Pane controlPane;
    private MassArrivalListener listener;
   
    private static int g = 300;
    private long startTime; // Tempo di inizio dell'animazione in nanosecondi

    public SimulationManager(Curve curve, MassArrivalListener listener) {
        mass = null;
        this.curve = curve;
        this.points = curve.calculatePoints();
        this.listener = listener;
        //System.out.println("punti calcolati");
    }

    public Curve getCurve() {
        return curve;
    }

    public void setMass(Mass mass) {
        this.mass = mass;
    }

    public Mass getMass() {
        return mass;
    }

    public Point[] getPoints() {
        return points;
    }
    
    public double getArrivalTime() {
    	return times[points.length - 1];
    }

    public void setSlopes(double[] slopes) {
        this.slopes = slopes;
    }
    

    public double[] calculateTimeParametrization() {
        times = new double[points.length];
        times[0] = 0;
        times[1] = Double.MIN_VALUE;
        double h;
        System.out.println("parametrizzazione curva rispetto al tempo");
        for (int i = 1; i < points.length-1; i++) {
        	h = points[i].getY() - curve.getStartPoint().getY();
        	//System.out.println(" h : " + h);
        	
        	if(h==0)
        	{
        		times[i+1] = times[i] + Double.MIN_VALUE;
        	}
        	
        	else times[i+1] = times[i] + (1/(Math.sqrt(2*g*h) * Math.abs(Math.sin(slopes[i])))) * (Math.abs(points[i+1].getY() - points[i].getY()));
        	
        	System.out.println((1/(Math.sqrt(2*g*h) * Math.abs(Math.sin(slopes[i])))) * (Math.abs(points[i+1].getY() - points[i].getY())));
        	System.out.println(" velocità : " + Math.sqrt(2*g*h));
            System.out.println(" tempi : " + times[i+1]);
        }
        return times;
    }

    private AnimationTimer timer; 
	
	public void setMassArrivalListener(MassArrivalListener listener)
    {
    	this.listener = listener;
    }
	
    public void startAnimation() {
    	
        mass.getIcon().relocate(points[0].getX() - mass.getMassDiameter() / 2, points[0].getY() - mass.getMassDiameter() / 2);

        startTime = 0;
        
        timer = new AnimationTimer() {
        	
            @Override
            public void handle(long now) {
                if (startTime == 0) 
                	startTime = now; // Inizializza il tempo di inizio

                double elapsedTime = (now - startTime) / 1_000_000_000.0; // Tempo trascorso in secondi
                
                // Trova il punto più vicino al tempo trascorso
                for (int i = 0; i < times.length - 1; i++) {
                	
                    if(elapsedTime >= times[i] && elapsedTime < times[i + 1]) 
                    {
                    	if(points[i+1].getY() < points[0].getY())
                    	{
                    		mass.getIcon().relocate(points[i].getX() - mass.getMassDiameter() / 2, points[i].getY() - mass.getMassDiameter() / 2);
                            this.stop();
                            listener.onMassArrival(SimulationManager.this, false);
                    	}
                    	
                        // Calcola la posizione interpolata tra points[i] e points[i+1]
                        double ratio = (elapsedTime - times[i]) / (times[i + 1] - times[i]);
                        double x = points[i].getX() + (points[i + 1].getX() - points[i].getX()) * ratio;
                        double y = points[i].getY() + (points[i + 1].getY() - points[i].getY()) * ratio;

                        // Posiziona la massa
                        mass.getIcon().relocate(x - mass.getMassDiameter() / 2, y - mass.getMassDiameter() / 2);
                        break;
                    }
       
                }
                
     
                
                
                // Ferma l'animazione se abbiamo raggiunto l'ultimo punto
                if (elapsedTime >= times[times.length - 1]) {
                	
                	mass.getIcon().relocate(points[points.length-1].getX() - mass.getMassDiameter() / 2, points[points.length-1].getY() - mass.getMassDiameter() / 2);
                    this.stop();
                    listener.onMassArrival(SimulationManager.this, true);
                }
            }
        };

        // Avvia l'AnimationTimer
        timer.start();
    }
}
