package ingdelsw.ExecutablePrototype;

import java.util.ArrayList;
import java.util.Comparator;

import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class InputManager {
    private Point startPoint, endPoint;
    private ArrayList<Point> intermediatePoints;
    
    public InputManager() {
    	startPoint = null;
    	endPoint = null;
        intermediatePoints = new ArrayList<>();
    }

    public Point getStartPoint() {
        return startPoint;
    }
    
    public Point getEndPoint() {
    	return endPoint;
    }
    
    public ArrayList<Point> getIntermediatePoint() {
    	return intermediatePoints;
    }

    public void setStartpoint(Point startPoint) {
        this.startPoint = startPoint;
        System.out.println("startPoint : X : " + startPoint.getX() + " Y : " + startPoint.getY());
    }
    
    public void setEndpoint(Point endPoint) {
    	if(endPoint.getY()<=startPoint.getY())
    		throw new IllegalArgumentException("Il punto di arrivo deve essere più in basso di quello di partenza");
    	else if(endPoint.getX() == startPoint.getX())
    		this.endPoint = new Point(endPoint.getX() + 1, endPoint.getY());
    	else {
    		this.endPoint=endPoint;
    	}
    	System.out.println("endPoint : X : " + endPoint.getX() + " Y : " + endPoint.getY());
    }
    
    public void addIntermediatePoint(Point p) {
    	if(endPoint.getX() <= p.getX() && startPoint.getX() <= p.getX() || endPoint.getX() >= p.getX() && startPoint.getX() >= p.getX())
    		throw new IllegalArgumentException("I punti intermedi devono essere compresi tra il punto di partenza e il punto di arrivo");
    	/*else if(startPoint.getY() >= p.getY())
    		throw new IllegalArgumentException("I punti intermedi devono avere quota più bassa del punto di partenza");*/
    	else {
    		intermediatePoints.add(p);
    		System.out.println("IntermediatePoint : " + p);
    	}
    }
    
    public void handleException(Exception e) {
        // Mostra una finestra di errore quando viene catturata l'eccezione
        Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
        alert.showAndWait();
    }
    
    // Metodo per cancellare i punti intermedi
    public void clearIntermediatePoints() {
        intermediatePoints.clear();;
    }
    
    // Metodo per cancellare tutti i punti inseriti
    public void clearInput() {
        startPoint=null;
        endPoint=null;
        intermediatePoints.clear();
    }
}
