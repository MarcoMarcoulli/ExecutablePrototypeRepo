package ingdelsw.ExecutablePrototype;

import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class InputManager {
    private Point2D startPoint, endPoint;

    public Point2D getStartPoint() {
        return startPoint;
    }
    
    public Point2D getEndPoint() {
    	return endPoint;
    }

    public void setStartpoint(Point2D startPoint) {
        this.startPoint = startPoint;
        System.out.println("StartPoint : " + startPoint);
    }
    
    public void setEndpoint(Point2D endPoint) {
    	if(endPoint.getY()<=startPoint.getY())
    		throw new IllegalArgumentException("Il punto di arrivo deve essere più in basso di quello di partenza");
    	else if(endPoint.getX() == startPoint.getX())
    		this.endPoint = new Point2D(endPoint.getX()+1, endPoint.getY());
    	else {
    		this.endPoint=endPoint;
    		System.out.println("EndPoint : " + endPoint);
    	}
    }
    
    public void handleException(Exception e) {
        // Mostra una finestra di errore quando viene catturata l'eccezione
        Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
        alert.showAndWait();
    }
    
 // Metodo per cancellare tutti i punti inseriti
    public void clearInput() {
        startPoint=null;
        endPoint=null;
    }
}
