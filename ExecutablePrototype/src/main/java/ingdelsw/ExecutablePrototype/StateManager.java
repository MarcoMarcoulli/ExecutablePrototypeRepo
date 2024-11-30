package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.FallSimulator.UIStates;
import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StateManager {
	
	public enum UIStates {
    	WAITING_FOR_START_POINT,
        WAITING_FOR_END_POINT,
        CHOOSING_CURVE,
        INSERTING_INTERMEDIATE_POINTS,
        CHOOSING_CONVEXITY,
        CHOOSING_RADIUS,
        CHOOSING_MASS,
        WAITING_TO_START_SIMULATION,
        SIMULATING,
        SHOWING_SIMULATION_RESULTS;
    }
	
	private UIStates state;
	
	public UIStates getState()
	{
		return state;
	}
	
	public void setState(UIStates state)
	{
		this.state = state;
	}

	public StateManager() {
		state = UIStates.WAITING_FOR_START_POINT;
	}
	
	public void handleUIState(Layout layout, InputController inputController, Point p)
	{
		switch (state) {
	    case WAITING_FOR_START_POINT:
	        inputController.setStartpoint(p);
	        GraphicsContext gc = layout.getGC();
	        gc.setFill(Color.RED);
	        gc.fillOval(x - 5, y - 5, 10, 10);  // Cerchio rosso per il punto di partenza
	        layout.getControlPanel().getChildren().remove(layout.getStartPointMessage());
	        layout.getControlPanel().getChildren().addAll(layout.getEndPointMessage(), layout.getBtnCancelInput());
	        state = UIStates.WAITING_FOR_END_POINT;
	        break;
	    case WAITING_FOR_END_POINT:
	        try {
	            inputController.setEndpoint(new Point(x, y));
	        } catch (IllegalArgumentException e) {
	            inputController.handleException(e);
	            return;
	        }
	        gc.setFill(Color.BLUE);
	        gc.fillOval(x - 5, y - 5, 10, 10);  // Cerchio blu per il punto di arrivo
	        controlPanel.getChildren().clear();
	        controlPanel.getChildren().addAll(chooseCurveMessage, curveButtons, btnCancelInput);
	        state = UIStates.CHOOSING_CURVE;
	        break;
	    case INSERTING_INTERMEDIATE_POINTS:
	    	try {
	            inputController.addIntermediatePoint(new Point(x,y));
	        } catch (IllegalArgumentException e) {
	            inputController.handleException(e);
	            return;
	        }
	        gc.setFill(Color.rgb(randomRed, randomGreen, randomBlue));
	        gc.fillOval(x - 5, y - 5, 10, 10);  // Cerchio verde per il punto intermedio
	        break;
		}
	}
}
