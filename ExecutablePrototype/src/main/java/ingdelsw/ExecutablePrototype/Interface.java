package ingdelsw.ExecutablePrototype;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import ingdelsw.ExecutablePrototype.Math.Point;
import ingdelsw.ExecutablePrototype.Math.Curves.Circumference;
import ingdelsw.ExecutablePrototype.Math.Curves.CubicSpline;
import ingdelsw.ExecutablePrototype.Math.Curves.Curve;
import ingdelsw.ExecutablePrototype.Math.Curves.Cycloid;
import ingdelsw.ExecutablePrototype.Math.Curves.Parabola;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Interface extends Application implements MassArrivalListener{

	private InputManager inputManager;
    private ArrayList<SimulationManager> simulations;
    private UIStates state;
    
    private Canvas pointsCanvas;
    private Canvas curveCanvas;
    private Pane animationPane;
    private VBox controlPanel;
    private HBox curveButtons, iconButtons, convexityButtons;
    private Label startPointMessage, endPointMessage, chooseCurveMessage, intermediatePointsMessage, chooseMassMessage, 
    				chooseRadiusMessage, chooseConvexityMessage;
    private Button btnCancelInput, btnCycloid, btnParabola, btnCubicSpline, btnCircumference, btnConfirmRadius, btnConvexityUp, 
    				btnConvexityDown, btnStopIntermediatePointsInsertion, btnStartSimulation, btnInsertAnotherCurve;
    private ImageView iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton;
    private Slider radiusSlider;
    private VBox massArrivalMessagesBox = new VBox();
    private VBox arrivalTimeMessagesBox = new VBox();
    private ArrayList<Label> arrivalTimeMessages = new ArrayList<Label>();
    
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

    @Override
    public void start(Stage primaryStage) {
    	// Layout principale
        BorderPane root = new BorderPane();
        
        // Carica il file CSS
        root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        
        startPointMessage = new Label("Inserisci il punto di partenza".toUpperCase());
        startPointMessage.getStyleClass().add("label".toUpperCase());
        endPointMessage = new Label("Inserisci il punto di arrivo".toUpperCase());
        endPointMessage.getStyleClass().add("label");
        chooseCurveMessage = new Label("scegli una curva");
        chooseCurveMessage.getStyleClass().add("label");
        intermediatePointsMessage = new Label("Inserisci dei punti intermedi da interpolare".toUpperCase());
        chooseMassMessage = new Label("Inserisci chi vuoi far scivolare".toUpperCase()); 
        chooseRadiusMessage = new Label("Seleziona il raggio della circonferenza".toUpperCase());
        chooseConvexityMessage = new Label("scegli la convessita".toUpperCase());
        
        
        btnCancelInput = new Button("Cancella Input");
        btnCancelInput.getStyleClass().add("button");
        btnCancelInput.getStyleClass().add("cancel-button");
        // Pulsanti per le curve
        btnCubicSpline = new Button("Spline Cubica");
        btnCubicSpline.getStyleClass().add("button");
        btnCycloid = new Button("Cicloide");
        btnCycloid.getStyleClass().add("button");
        btnParabola = new Button("Parabola");
        btnParabola.getStyleClass().add("button");
        btnCircumference = new Button("Circonferenza");
        btnCircumference.getStyleClass().add("button");
        btnConfirmRadius = new Button("Seleziona Raggio");
        btnConvexityUp = new Button("verso l'alto");
        btnConvexityDown = new Button("verso il basso");
        btnStopIntermediatePointsInsertion = new Button("Fine immissione");
        btnStartSimulation = new Button("avvia simulazione");
        btnInsertAnotherCurve = new Button("inserisci un' altra curva");
        

        // Carica le icone
        Image iconBernoulli = new Image(getClass().getResource("/images/Bernoulli.png").toExternalForm());
        Image iconGalileo = new Image(getClass().getResource("/images/Galileo.png").toExternalForm());
        Image iconJakob = new Image(getClass().getResource("/images/Jakob.png").toExternalForm());
        Image iconLeibnitz = new Image(getClass().getResource("/images/Leibnitz.png").toExternalForm());
        Image iconNewton = new Image(getClass().getResource("/images/Newton.png").toExternalForm());

        // Crea pulsanti immagine
        iconViewBernoulli = createIconButton(iconBernoulli, MassIcon.BERNOULLI);
        iconViewGalileo = createIconButton(iconGalileo,MassIcon.GALILEO);
        iconViewJakob = createIconButton(iconJakob, MassIcon.JAKOB);
        iconViewLeibnitz = createIconButton(iconLeibnitz, MassIcon.LEIBNITZ);
        iconViewNewton = createIconButton(iconNewton, MassIcon.NEWTON);
        
        iconButtons = new HBox(10); // Layout per tenere insieme le icone
        iconButtons.getStyleClass().add("icon-buttons");
        iconButtons.getChildren().addAll(iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton);
        
        curveButtons = new HBox(10);
        curveButtons.getStyleClass().add("curve-buttons");
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);
        
        convexityButtons= new HBox(6);
        convexityButtons.getChildren().addAll(btnConvexityUp, btnConvexityDown);
        
        

        // Pannello di controllo (a sinistra)
        controlPanel = new VBox(10);
        controlPanel.getStyleClass().add("control-panel");
        controlPanel.getStyleClass().add("control-panel");
        root.setLeft(controlPanel);
        
        // Canvas per disegno (a destra)
        curveCanvas = new Canvas();
        pointsCanvas = new Canvas();
        animationPane = new Pane();
        
        animationPane.setMouseTransparent(true); // Rendi animationPane trasparente agli eventi di mouse
        
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue() - controlPanel.getWidth();
            curveCanvas.setWidth(newWidth);
            pointsCanvas.setWidth(newWidth);
            animationPane.setPrefWidth(newWidth);
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            curveCanvas.setHeight(newHeight);
            pointsCanvas.setHeight(newHeight);
            animationPane.setPrefHeight(newHeight);
        });
        
        
        
        // Aggiungi entrambi i Canvas al centro del layout
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(curveCanvas, pointsCanvas, animationPane);
        root.setCenter(stackPane);
        
        root.setLeft(controlPanel);
        
        // Inizializzazione InputManager
        inputManager = new InputManager();
        simulations = new ArrayList<SimulationManager>();
        controlPanel.getChildren().add(startPointMessage);
        state = UIStates.WAITING_FOR_START_POINT;

        // Gestione del click sul pannello di disegno
        pointsCanvas.setOnMouseClicked(this::handleMouseClick);
        btnCancelInput.setOnAction(e -> handleCancelInputClick());
        btnParabola.setOnAction(e -> handleParabolaClick());
        btnCycloid.setOnAction(e -> handleCycloidClick());
        btnCircumference.setOnAction(e -> handleCircumferenceClick());
        btnCubicSpline.setOnAction(e-> handleCubicSplineClick());
        btnConvexityUp.setOnAction(e -> handleConvexityUpClick());
        btnConvexityDown.setOnAction(e -> handleConvexityDownClick());
        btnStopIntermediatePointsInsertion.setOnAction(e -> handleStopIntermediatePointsInsertionClick());
        btnInsertAnotherCurve.setOnAction(e -> handleInsertAnotherCurveClick());
        btnStartSimulation.setOnAction(e -> handleStartSimulationClick());
        
        // Configura la finestra per aprirsi massimizzata
        primaryStage.setMaximized(true);
    
        // Configura la scena
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Fall Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    Random random = new Random();
    int randomRed, randomGreen, randomBlue;

    // Gestione dei click per selezionare il punto di partenza
    private void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        
        
        
        GraphicsContext gc = pointsCanvas.getGraphicsContext2D();
        switch (state) {
	        case WAITING_FOR_START_POINT:
	            inputManager.setStartpoint(new Point(x, y));
	            gc.setFill(Color.RED);
	            gc.fillOval(x - 5, y - 5, 10, 10);  // Cerchio rosso per il punto di partenza
	            controlPanel.getChildren().remove(startPointMessage);
	            controlPanel.getChildren().addAll(endPointMessage, btnCancelInput);
	            state = UIStates.WAITING_FOR_END_POINT;
	            break;
	        case WAITING_FOR_END_POINT:
	            try {
	                inputManager.setEndpoint(new Point(x, y));
	            } catch (IllegalArgumentException e) {
	                inputManager.handleException(e);
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
	                inputManager.addIntermediatePoint(new Point(x,y));
	            } catch (IllegalArgumentException e) {
	                inputManager.handleException(e);
	                return;
	            }
	            gc.setFill(Color.rgb(randomRed, randomGreen, randomBlue));
	            gc.fillOval(x - 5, y - 5, 10, 10);  // Cerchio verde per il punto intermedio
	            break;
        }
    }
    
    private void handleCancelInputClick() {
        pointsCanvas.getGraphicsContext2D().clearRect(0, 0, pointsCanvas.getWidth(), pointsCanvas.getHeight());
        curveCanvas.getGraphicsContext2D().clearRect(0, 0, curveCanvas.getWidth(), curveCanvas.getHeight());
        animationPane.getChildren().clear();
        simulations.clear();
        controlPanel.getChildren().clear();
        controlPanel.getChildren().add(startPointMessage);
        iconButtons.getChildren().clear();
        iconButtons.getChildren().addAll(iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton);
        curveButtons.getChildren().clear();
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);
        arrivalTimeMessages.clear();
    	arrivalTimeMessagesBox.getChildren().clear();
    	massArrivalMessagesBox.getChildren().clear();
    	state = UIStates.WAITING_FOR_START_POINT;
    }
    
  //gestore del click sul pulsante CubicSpline
    private void handleCubicSplineClick()
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
    private void handleCycloidClick()
    {
    	controlPanel.getChildren().clear();
    	Cycloid cycloid = new Cycloid(inputManager.getStartPoint(),inputManager.getEndPoint());
    	cycloid.setRandomColors();
    	simulations.add(new SimulationManager(cycloid, this));
    	CurveVisualizer.drawCurve(simulations.getLast().getPoints(), curveCanvas.getGraphicsContext2D(), simulations.getLast().getCurve().getRed(),  simulations.getLast().getCurve().getGreen(),  simulations.getLast().getCurve().getBlue());
    	simulations.getLast().setSlopes(cycloid.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization();
    	controlPanel.getChildren().addAll(chooseMassMessage, iconButtons, btnCancelInput);
    	curveButtons.getChildren().remove(btnCycloid);
    	state = UIStates.CHOOSING_MASS;
    }
    
    //gestore del click sul pulsante Parabola
    private void handleParabolaClick()
    {
    	controlPanel.getChildren().clear();
    	Parabola parabola = new Parabola(inputManager.getStartPoint(),inputManager.getEndPoint());
    	parabola.setRandomColors();
    	simulations.add(new SimulationManager(parabola, this));
    	CurveVisualizer.drawCurve(simulations.getLast().getPoints(), curveCanvas.getGraphicsContext2D(), parabola.getRed(),  parabola.getGreen(),  parabola.getBlue());
    	simulations.getLast().setSlopes(parabola.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization();
    	controlPanel.getChildren().addAll(chooseMassMessage, iconButtons, btnCancelInput);
    	curveButtons.getChildren().remove(btnParabola);
    	state = UIStates.CHOOSING_MASS;
    }
    
    //gestore del click sul pulsante Circumference
    private void handleCircumferenceClick()
    {
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().add(chooseConvexityMessage);
    	controlPanel.getChildren().addAll(convexityButtons, btnCancelInput);
    }
    
    private void handleConvexityUpClick()
    {
    	double deltaX = inputManager.getEndPoint().getX() - inputManager.getStartPoint().getX();
    	Circumference circumference = new Circumference(inputManager.getStartPoint(),inputManager.getEndPoint(), 1);
    	circumference.setRandomColors();
    	simulations.add(new SimulationManager(circumference, this));
    	CurveVisualizer.drawCurve(simulations.getLast().getPoints(), curveCanvas.getGraphicsContext2D(), simulations.getLast().getCurve().getRed(),  simulations.getLast().getCurve().getGreen(),  simulations.getLast().getCurve().getBlue());
    	
    	radiusSlider = new Slider((deltaX/Math.abs(deltaX))*circumference.getR(), (deltaX/Math.abs(deltaX))*circumference.getR()*3, (deltaX/Math.abs(deltaX))*circumference.getR());
    	// Aggiungi un listener per il valore dello slider e chiama la funzione
        radiusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            handleSliderChange(newValue.doubleValue(), 1);
        });
        btnConfirmRadius.setOnAction(e -> handleConfirmRadiusClick(radiusSlider.getValue(), 1));
        controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseRadiusMessage, radiusSlider, btnConfirmRadius, btnCancelInput);
    	state = UIStates.CHOOSING_RADIUS;
    }
    
    private void handleConvexityDownClick()
    {
    	Circumference circumference = new Circumference(inputManager.getStartPoint(),inputManager.getEndPoint(), -1);
    	circumference.setRandomColors();
    	simulations.add(new SimulationManager(circumference, this));
    	CurveVisualizer.drawCurve(simulations.getLast().getPoints(), curveCanvas.getGraphicsContext2D(), simulations.getLast().getCurve().getRed(),  simulations.getLast().getCurve().getGreen(),  simulations.getLast().getCurve().getBlue());
    	
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
    
    private void handleSliderChange(double radius, int convexity)
    {
    	curveCanvas.getGraphicsContext2D().clearRect(0, 0, curveCanvas.getWidth(), curveCanvas.getHeight());
    	Circumference circumference = new Circumference(inputManager.getStartPoint(),inputManager.getEndPoint(), convexity, radius);
    	circumference.setRed(simulations.getLast().getCurve().getRed());
    	circumference.setGreen(simulations.getLast().getCurve().getGreen());
    	circumference.setBlue(simulations.getLast().getCurve().getBlue());
    	simulations.removeLast();
    	simulations.add(new SimulationManager(circumference, this));
    	CurveVisualizer.drawCurve(simulations.getLast().getPoints(), curveCanvas.getGraphicsContext2D(), simulations.getLast().getCurve().getRed(),  simulations.getLast().getCurve().getGreen(),  simulations.getLast().getCurve().getBlue());
    	for (int i = 0; i < simulations.size() - 1; i++) {
    	    CurveVisualizer.drawCurve(simulations.get(i).getPoints(), curveCanvas.getGraphicsContext2D(), simulations.get(i).getCurve().getRed(), simulations.get(i).getCurve().getGreen(), simulations.get(i).getCurve().getBlue());
    	}
    }
    
    private void handleConfirmRadiusClick(double radius, int convexity)
    {
    	simulations.getLast().setSlopes(simulations.getLast().getCurve().calculateSlopes());
    	simulations.getLast().calculateTimeParametrization();
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseMassMessage, iconButtons, btnCancelInput);
    	state = UIStates.CHOOSING_MASS;
    }
    
    private void handleStopIntermediatePointsInsertionClick()
    {
    	CubicSpline spline = new CubicSpline(inputManager.getStartPoint(),inputManager.getEndPoint(), inputManager.getIntermediatePoint());
    	spline.setRandomColors();
    	inputManager.clearIntermediatePoints();
    	simulations.add(new SimulationManager(spline, this));
    	CurveVisualizer.drawCurve(simulations.getLast().getPoints(), curveCanvas.getGraphicsContext2D(), simulations.getLast().getCurve().getRed(),  simulations.getLast().getCurve().getGreen(),  simulations.getLast().getCurve().getBlue());
    	simulations.getLast().setSlopes(spline.calculateSlopes());
    	simulations.getLast().calculateTimeParametrization();
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseMassMessage, iconButtons, btnCancelInput);
    	state = UIStates.CHOOSING_MASS;
    }
    
    final double IconButtonDiameter = 70;
    
    // Metodo helper per creare un pulsante icona
    private ImageView createIconButton(Image image, MassIcon iconType) {
        ImageView iconView = new ImageView(image);
        iconView.setFitWidth(IconButtonDiameter); // Imposta la larghezza desiderata per l'icona
        iconView.setFitHeight(IconButtonDiameter);
        iconView.setOnMouseClicked(e -> handleMassSelection(iconType, (ImageView) e.getSource()));
        return iconView;
    }
    
    // Gestione della selezione della massa
    private void handleMassSelection(MassIcon iconType, ImageView selectedMass) {
        ImageView mass = new ImageView(selectedMass.getImage());
        simulations.getLast().setMass(new Mass(inputManager.getStartPoint(), iconType, mass));
        animationPane.getChildren().add(simulations.getLast().getMass().getIcon());
        controlPanel.getChildren().clear();
        iconButtons.getChildren().remove(selectedMass);
        if(iconButtons.getChildren().isEmpty())
        	controlPanel.getChildren().addAll(btnStartSimulation, btnCancelInput); 
        else controlPanel.getChildren().addAll(btnStartSimulation, btnInsertAnotherCurve, btnCancelInput); 
    }
    
    private void handleInsertAnotherCurveClick()
    {
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseCurveMessage, curveButtons, btnCancelInput);
    }
    
    
    @Override
    public void onMassArrival(SimulationManager source, boolean arrived) {
    	int i = simulations.indexOf(source);
    	if(i>=0)
    	{
    		if(arrived)
    		{
    			arrivalTimeMessagesBox.getChildren().clear();
    			String massName = simulations.get(i).getMass().getIconTypeString();
    			String arrivalTime = String.format("%.5f", simulations.get(i).getArrivalTime());
    			arrivalTimeMessages.add(new Label(massName + " sulla " + simulations.get(i).getCurve().curveName() + " è arrivato in " + arrivalTime + " secondi."));
    			arrivalTimeMessages.sort(Comparator.comparingInt(label -> extractNumber(label.getText())));
    			arrivalTimeMessagesBox.getChildren().addAll(arrivalTimeMessages);
    			massArrivalMessagesBox.getChildren().addFirst(arrivalTimeMessagesBox);
    		}
    		else {
    			System.out.println("kwdjnck");
    			Label neverArriveMessage = new Label(simulations.get(i).getMass().getIconTypeString() + " sulla " + simulations.get(i).getCurve().curveName() +" non arriverà mai a destinazione");
    			massArrivalMessagesBox.getChildren().addLast(neverArriveMessage);
    		}
    	}
    }
    
    // Funzione per estrarre il numero dal testo della Label
    private static int extractNumber(String text) {
        // Rimuove tutto tranne i numeri
        String numberStr = text.replaceAll("[^\\d]", ""); // "\\d" corrisponde a cifre, il caret "^" nega tutto il resto
        try {
            return Integer.parseInt(numberStr); // Converte in intero
        } catch (NumberFormatException e) {
            return 0; // Valore di default se non ci sono numeri
        }
    }
   
    private void handleStartSimulationClick()
    {
    	controlPanel.getChildren().clear(); 
    	arrivalTimeMessages.clear();
    	arrivalTimeMessagesBox.getChildren().clear();
    	massArrivalMessagesBox.getChildren().clear();
    	if(iconButtons.getChildren().isEmpty())
        	controlPanel.getChildren().addAll(btnStartSimulation, btnCancelInput, massArrivalMessagesBox); 
        else controlPanel.getChildren().addAll(btnStartSimulation, btnInsertAnotherCurve, btnCancelInput, massArrivalMessagesBox); 
    	for(int i=0; i<simulations.size(); i++)
    	{
    		simulations.get(i).startAnimation();
    	}
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
