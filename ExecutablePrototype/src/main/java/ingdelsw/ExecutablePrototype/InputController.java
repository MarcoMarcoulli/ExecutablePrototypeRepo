package ingdelsw.ExecutablePrototype;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class InputController {
    private Point startPoint, endPoint;
    private ArrayList<Point> intermediatePoints;
    private static InputController theController = null;
    
    private InputController() {
    	startPoint = null;
    	endPoint = null;
        intermediatePoints = new ArrayList<>();
    }
    
    
    //Singleton pattern implementation
    public static InputController getController()
    {
    	if(theController == null)
    		theController = new InputController();
    	return theController;
    }

    public Point getStartPoint() {
        return startPoint;
    }
    
    public Point getEndPoint() {
    	return endPoint;
    }
    
    public List<Point> getIntermediatePoint() {
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
    	//System.out.println("endPoint : X : " + endPoint.getX() + " Y : " + endPoint.getY());
    }
    
    public void addIntermediatePoint(Point p) {
    	for(int i = 0; i<intermediatePoints.size(); i++)
    	{
    		if(intermediatePoints.get(i).getX() == p.getX())
				return;
    	}
    	if(endPoint.getX() <= p.getX() && startPoint.getX() <= p.getX() || endPoint.getX() >= p.getX() && startPoint.getX() >= p.getX())
    		throw new IllegalArgumentException("I punti intermedi devono essere compresi tra il punto di partenza e il punto di arrivo");
    	/*else if(startPoint.getY() >= p.getY())
    		throw new IllegalArgumentException("I punti intermedi devono avere quota più bassa del punto di partenza");*/
    	else{
    		intermediatePoints.add(p);
    		//System.out.println("IntermediatePoint : " + p);
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
