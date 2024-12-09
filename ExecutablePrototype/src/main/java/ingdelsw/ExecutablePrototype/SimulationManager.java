package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.Math.Point;
import ingdelsw.ExecutablePrototype.Math.Curves.Curve;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class SimulationManager {
	

    private Mass mass;  // Oggetto rappresentante la massa
    private Curve curve;
    private Point[] points;
    private double[] slopes;
    private double[] times;
    
    Pane controlPane;
    private MassArrivalListener listener;
   
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
    

    public double[] calculateTimeParametrization(double g) {
        times = new double[points.length];
        times[0] = 0;
        times[1] = Double.MIN_VALUE;
        double h1;
        double h2;
        double v1;
        double v2;
        double v1y;
        double v2y;
        double integrand;
        double dy;
        System.out.println("parametrizzazione curva rispetto al tempo");
        for (int i = 1; i < points.length-1; i++) {
        	h1 = points[i].getY() - curve.getStartPoint().getY();
        	
        	if(h1==0){
        		times[i+1] = times[i] + Double.MIN_VALUE;
        		continue;
        	}
        	
        	h2 = points[i+1].getY() - curve.getStartPoint().getY();
        	v1 = Math.sqrt(2*g*h1);
        	v2 = Math.sqrt(2*g*h2);
        	v1y = v1*Math.abs(Math.sin(slopes[i]));
        	v2y = v2*Math.abs(Math.sin(slopes[i+1]));
        	dy = (Math.abs(points[i+1].getY() - points[i].getY()));
        	integrand = ((1/v1y + 1/v2y)/2);
        	
        	times[i+1] = times[i] + integrand * dy;
        	
        	//System.out.println(h1);
        	//System.out.println((slopes[i]/Math.PI)*180);
        	//System.out.println(i +") velocità : " + v1y);
            //System.out.println(" tempi : " + times[i+1]);
        }
        return times;
    }

	
	public void setMassArrivalListener(MassArrivalListener listener)
    {
    	this.listener = listener;
    }
	
    public void startAnimation() {
    	
    	AnimationTimer timer; 
    	
    	double x0 = points[0].getX() - mass.getMassDiameter() / 2;
    	double y0 = points[0].getY() - mass.getMassDiameter() / 2;
    	
        mass.getIcon().relocate(x0, y0);

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
                    	if(i<(times.length - 2) && (points[i+2].getY() < points[0].getY()))
                    	{
                    		mass.getIcon().relocate(points[i].getX() - mass.getMassDiameter() / 2, points[i].getY() - mass.getMassDiameter() / 2);
                            this.stop();
                            listener.onMassArrival(SimulationManager.this, false);
                    	}
                    	
                        // Calcola la posizione interpolata tra points[i] e points[i+1]
                        double ratio = (elapsedTime - times[i]) / (times[i + 1] - times[i]);
                        double x = points[i].getX() + (points[i + 1].getX() - points[i].getX()) * ratio - mass.getMassDiameter() / 2;
                        double y = points[i].getY() + (points[i + 1].getY() - points[i].getY()) * ratio - mass.getMassDiameter() / 2;

                        // Posiziona la massa
                        mass.getIcon().relocate(x, y);
                        break;
                    }
       
                }
                
                // Ferma l'animazione se abbiamo raggiunto l'ultimo punto
                if (elapsedTime >= times[times.length - 1]) {
                	double newX = points[points.length-1].getX() - mass.getMassDiameter() / 2;
                	double newY = points[points.length-1].getY() - mass.getMassDiameter() / 2;
                	mass.getIcon().relocate(newX, newY);
                    this.stop();
                    listener.onMassArrival(SimulationManager.this, true);
                }
            }
        };

        // Avvia l'AnimationTimer
        timer.start();
    }
}
