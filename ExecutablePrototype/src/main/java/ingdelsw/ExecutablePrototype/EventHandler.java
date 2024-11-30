package ingdelsw.ExecutablePrototype;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import ingdelsw.ExecutablePrototype.Math.Point;
import ingdelsw.ExecutablePrototype.Math.Curves.Circumference;
import ingdelsw.ExecutablePrototype.Math.Curves.CubicSpline;
import ingdelsw.ExecutablePrototype.Math.Curves.Cycloid;
import ingdelsw.ExecutablePrototype.Math.Curves.Parabola;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EventHandler implements MassArrivalListener{
	
	private static Layout layout;
	private static EventHandler theHandler = null;

	public EventHandler(InputController inputController, StateManager stateManager){
		layout = Layout.getLayout();
		// Gestione del click sul pannello di disegno
        layout.getPointsCanvas().setOnMouseClicked(e -> handleMouseClick(e, layout, stateManager, inputController));
        layout.getBtnCancelInput().setOnAction(e -> handleCancelInputClick(layout));
        layout.getBtnParabola().setOnAction(e -> handleParabolaClick());
        layout.getBtnCycloid().setOnAction(e -> handleCycloidClick());
        layout.getBtnCircumference().setOnAction(e -> handleCircumferenceClick());
        layout.getBtnCubicSpline().setOnAction(e-> handleCubicSplineClick());
        layout.getBtnConvexityUp().setOnAction(e -> handleConvexityUpClick());
        layout.getBtnConvexityDown().setOnAction(e -> handleConvexityDownClick());
        layout.getBtnStopIntermediatePointsInsertion().setOnAction(e -> handleStopIntermediatePointsInsertionClick());
        layout.getBtnInsertAnotherCurve().setOnAction(e -> handleInsertAnotherCurveClick());
        layout.getBtnStartSimulation().setOnAction(e -> handleStartSimulationClick());
		layout.getIconViewBernoulli().setOnMouseClicked(e -> eventHandler.handleMassSelection(iconType, (ImageView) e.getSource()));
		layout.getIconViewGalileo().setOnMouseClicked(e -> eventHandler.handleMassSelection(iconType, (ImageView) e.getSource()));
		layout.getIconViewJakob().setOnMouseClicked(e -> eventHandler.handleMassSelection(iconType, (ImageView) e.getSource()));
		layout.getIconViewLeibnitz().setOnMouseClicked(e -> eventHandler.handleMassSelection(iconType, (ImageView) e.getSource()));
		layout.getIconViewNewton().setOnMouseClicked(e -> eventHandler.handleMassSelection(iconType, (ImageView) e.getSource()));
	}
	
	public static EventHandler getHandler()
    {
    	if(theHandler == null)
    		theHandler = new EventHandler();
    	return theHandler;
    }
	
	public Layout getLayout()
	{
		return layout;
	}
	
	// Gestione dei click per selezionare il punto di partenza
    public void handleMouseClick(MouseEvent event, Layout layout, StateManager stateManager, InputController inputController) {
        double x = event.getX();
        double y = event.getY();
        Point p = new Point(x,y);
        stateManager.handleUIState(layout, inputController, p);
    }
    
    public void handleCancelInputClick(Layout layout){
        layout.clear();
        simulations.clear();
    	state = UIStates.WAITING_FOR_START_POINT;
    }
	 
	//gestore del click sul pulsante CubicSpline
    public void handleCubicSplineClick()
    {
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(intermediatePointsMessage, btnStopIntermediatePointsInsertion, btnCancelInput);
    	Random random = new Random();
        randomRed = random.nextInt(230);
        randomGreen = random.nextInt(230);
        randomBlue = random.nextInt(230);
    	state = UIStates.INSERTING_INTERMEDIATE_POINTS;
    }
	    
    //gestore del click sul pulsante Cycloid
    public void handleCycloidClick()
    {
    	controlPanel.getChildren().clear();
    	Cycloid cycloid = new Cycloid(inputController.getStartPoint(),inputController.getEndPoint());
    	cycloid.setRandomColors();
    	simulations.add(new SimulationManager(cycloid, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, curveCanvas.getGraphicsContext2D(), red,  green,  blue);
    	simulations.getLast().setSlopes(cycloid.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization();
    	controlPanel.getChildren().addAll(chooseMassMessage, iconButtons, btnCancelInput);
    	curveButtons.getChildren().remove(btnCycloid);
    	state = UIStates.CHOOSING_MASS;
    }
	    
    //gestore del click sul pulsante Parabola
    public void handleParabolaClick()
    {
    	controlPanel.getChildren().clear();
    	Parabola parabola = new Parabola(inputController.getStartPoint(),inputController.getEndPoint());
    	parabola.setRandomColors();
    	simulations.add(new SimulationManager(parabola, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, curveCanvas.getGraphicsContext2D(), red,  green, blue);
    	
    	simulations.getLast().setSlopes(parabola.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization();
    	controlPanel.getChildren().addAll(chooseMassMessage, iconButtons, btnCancelInput);
    	curveButtons.getChildren().remove(btnParabola);
    	state = UIStates.CHOOSING_MASS;
    }
    
    //gestore del click sul pulsante Circumference
    public void handleCircumferenceClick()
    {
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().add(chooseConvexityMessage);
    	controlPanel.getChildren().addAll(convexityButtons, btnCancelInput);
    }
    
    public void handleConvexityUpClick()
    {
    	Circumference circumference = new Circumference(inputController.getStartPoint(),inputController.getEndPoint(), 1);
    	circumference.setRandomColors();
    	
    	simulations.add(new SimulationManager(circumference, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, curveCanvas.getGraphicsContext2D(), red,  green,  blue);
    	
    	double deltaX = inputController.getEndPoint().getX() - inputController.getStartPoint().getX();
    	double initialRadius = (deltaX/Math.abs(deltaX))*circumference.getR();
    	radiusSlider = new Slider(initialRadius, initialRadius*3, initialRadius);
    	// Aggiungi un listener per il valore dello slider e chiama la funzione
        radiusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            handleSliderChange(newValue.doubleValue(), 1);
        });
        btnConfirmRadius.setOnAction(e -> handleConfirmRadiusClick(radiusSlider.getValue(), 1));
        controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseRadiusMessage, radiusSlider, btnConfirmRadius, btnCancelInput);
    	state = UIStates.CHOOSING_RADIUS;
    }
    
    public void handleConvexityDownClick()
    {
    	Circumference circumference = new Circumference(inputController.getStartPoint(),inputController.getEndPoint(), -1);
    	circumference.setRandomColors();
    	simulations.add(new SimulationManager(circumference, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, curveCanvas.getGraphicsContext2D(), red, green,  blue);
    	
    	radiusSlider = new Slider(circumference.getR(), circumference.getR()*3, circumference.getR());
    	// Aggiungi un listener per il valore dello slider e chiama la funzione
        radiusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            handleSliderChange(newValue.doubleValue(), -1);
        });
        btnConfirmRadius.setOnAction(e -> handleConfirmRadiusClick(radiusSlider.getValue(), -1));
        controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseRadiusMessage, radiusSlider, btnConfirmRadius);
    	state = UIStates.CHOOSING_RADIUS;
    }
    
    public void handleSliderChange(double radius, int convexity)
    {
    	curveCanvas.getGraphicsContext2D().clearRect(0, 0, curveCanvas.getWidth(), curveCanvas.getHeight());
    	Circumference circumference = new Circumference(inputController.getStartPoint(),inputController.getEndPoint(), convexity, radius);
    	circumference.setRed(simulations.getLast().getCurve().getRed());
    	circumference.setGreen(simulations.getLast().getCurve().getGreen());
    	circumference.setBlue(simulations.getLast().getCurve().getBlue());
    	simulations.removeLast();
    	simulations.add(new SimulationManager(circumference, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, curveCanvas.getGraphicsContext2D(), red, green,  blue);
    	for (int i = 0; i < simulations.size() - 1; i++) {
    		
    		points = simulations.get(i).getPoints();
    		
    		red = simulations.get(i).getCurve().getRed();
    		green = simulations.get(i).getCurve().getGreen();
    		blue = simulations.get(i).getCurve().getBlue();
    		
    	    CurveVisualizer.drawCurve(points, curveCanvas.getGraphicsContext2D(), red, green, blue);
    	}
    }
    
    public void handleConfirmRadiusClick(double radius, int convexity)
    {
    	double[] slopes = simulations.getLast().getCurve().calculateSlopes();
    	simulations.getLast().setSlopes(slopes);
    	simulations.getLast().calculateTimeParametrization();
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseMassMessage, iconButtons, btnCancelInput);
    	state = UIStates.CHOOSING_MASS;
    }
    
    public void handleStopIntermediatePointsInsertionClick()
    {
    	controlPanel.getChildren().clear();
    	CubicSpline spline = new CubicSpline(inputController.getStartPoint(),inputController.getEndPoint(), inputController.getIntermediatePoint());
    	spline.setRandomColors();
    	inputController.clearIntermediatePoints();
    	simulations.add(new SimulationManager(spline, this));
    	
    	Point[] points = simulations.getLast().getPoints();
    	
    	int red = simulations.getLast().getCurve().getRed();
    	int green = simulations.getLast().getCurve().getGreen();
    	int blue = simulations.getLast().getCurve().getBlue();
    	
    	CurveVisualizer.drawCurve(points, curveCanvas.getGraphicsContext2D(), red,  green,  blue);
    	simulations.getLast().setSlopes(spline.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization();
    	controlPanel.getChildren().addAll(chooseMassMessage, iconButtons, btnCancelInput);
    	state = UIStates.CHOOSING_MASS;
    }
 
    private int numberOfSimulations;
    
    @Override
    public void onMassArrival(SimulationManager source, boolean arrived) {
    	numberOfSimulations--;
    	int i = simulations.indexOf(source);
    	if(i>=0)
    	{
    		if(arrived)
    		{
    			massArrivalMessagesBox.getChildren().removeAll(arrivalTimeMessages);
    			String massName = simulations.get(i).getMass().getIconTypeString();
    			double arrive = simulations.get(i).getArrivalTime();
    			String arrivalTime = String.format("%.5f", arrive);
    			String curveName = simulations.get(i).getCurve().curveName();
    			arrivalTimeMessages.add(new Label(massName + " sulla " + curveName + " è arrivato in " + arrivalTime + " secondi."));
    			arrivalTimeMessages.sort(Comparator.comparingInt(label -> extractNumber(label.getText())));
    			massArrivalMessagesBox.getChildren().addAll(0, arrivalTimeMessages);
    		}
    		else {
    			massArrivalMessagesBox.getChildren().removeAll(neverArriveMessages);
    			String massName = simulations.get(i).getMass().getIconTypeString();
    			String curveName = simulations.get(i).getCurve().curveName();
    			neverArriveMessages.add(new Label(massName + " sulla " + curveName +" non arriverà mai a destinazione"));
    			massArrivalMessagesBox.getChildren().addAll(neverArriveMessages);
    		}
    	}
    	
    	if(numberOfSimulations == 0)
    	{
    		controlPanel.getChildren().clear();
    		if(iconButtons.getChildren().isEmpty())
            	controlPanel.getChildren().addAll(btnStartSimulation, btnCancelInput, massArrivalMessagesBox); 
            else controlPanel.getChildren().addAll(btnStartSimulation, btnInsertAnotherCurve, btnCancelInput, massArrivalMessagesBox); 
    	}
    }
    
    public void handleStartSimulationClick(Layout layout, ArrayList<SimulationManager> simulations)
    {
    	numberOfSimulations = simulations.size();
    	layout.getControlPanel().getChildren().clear(); 
    	layout.getArrivalTimeMessages().clear();
    	layout.getNeverArriveMessages().clear();
    	layout.getMassArrivalMessagesBox().getChildren().clear();
    	
    	layout.getControlPanel().getChildren().addAll(layout.getSimulatingMessage(), layout.getBtnCancelInput(), layout.getMassArrivalMessagesBox());
    	
    	for(int i=0; i<simulations.size(); i++)
    	{
    		simulations.get(i).startAnimation();
    	}
    }

}
