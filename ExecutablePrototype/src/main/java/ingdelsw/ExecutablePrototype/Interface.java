package ingdelsw.ExecutablePrototype;

import java.util.ArrayList;

import ingdelsw.ExecutablePrototype.Math.Circumference;
import ingdelsw.ExecutablePrototype.Math.Curve;
import ingdelsw.ExecutablePrototype.Math.Cycloid;
import ingdelsw.ExecutablePrototype.Math.Parabola;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Interface extends Application {

	private InputManager inputManager;
    private ArrayList<SimulationManager> simulations;
    private UIStates state;
    
    private Canvas pointsCanvas;
    private Canvas curveCanvas;
    private VBox controlPanel;
    private HBox curveButtons;
    private Label startPointMessage, endPointMessage, chooseCurveMessage, intermediatePointsMessage, chooseMassMessage, chooseRadiusMessage;
    private Button btnCancelInput, btnCycloid, btnParabola, btnCubicSpline, btnCircumference, btnConfirmRadius;
    private Slider radiusSlider;
    
    public enum UIStates {
    	WAITING_FOR_START_POINT,
        WAITING_FOR_END_POINT,
        CHOOSING_CURVE,
        INSERTING_INTERMEDIATE_POINTS,
        CHOOSING_RADIUS,
        CHOOSING_MASS,
        WAITING_TO_START_SIMULATION,
        SIMULATING,
        SHOWING_SIMULATION_RESULTS
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout principale
        BorderPane root = new BorderPane();
        
        startPointMessage = new Label("Inserisci il punto di partenza");
        endPointMessage = new Label("Inserisci il punto di arrivo");
        chooseCurveMessage = new Label("scegli una curva");
        intermediatePointsMessage = new Label("Inserisci dei punti intermedi da interpolare");
        chooseMassMessage = new Label("Inserisci chi vuoi far scivolare"); 
        chooseRadiusMessage = new Label("Seleziona il raggio della circonferenza");
        
        btnCancelInput = new Button("Cancella Input");
        // Pulsanti per le curve
        btnCubicSpline = new Button("Spline Cubica");
        btnCycloid = new Button("Cicloide");
        btnParabola = new Button("Parabola");
        btnCircumference = new Button("Circonferenza");
        btnConfirmRadius = new Button("Seleziona Raggio");
        
        curveButtons = new HBox(2);
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);

        // Pannello di controllo (a sinistra)
        controlPanel = new VBox(10);
        controlPanel.setPrefWidth(308); // Imposta la larghezza predefinita
        controlPanel.setStyle("-fx-background-color: lightgray;"); // Colore grigio chiaro per il controllo
        root.setLeft(controlPanel);
        
        // Canvas per disegno (a destra)
        curveCanvas = new Canvas();
        pointsCanvas = new Canvas();
        
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue() - controlPanel.getWidth();
            curveCanvas.setWidth(newWidth);
            pointsCanvas.setWidth(newWidth);
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            curveCanvas.setHeight(newHeight);
            pointsCanvas.setHeight(newHeight);
        });
        
        
        
        // Aggiungi entrambi i Canvas al centro del layout
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(curveCanvas, pointsCanvas);
        root.setCenter(stackPane);
        
        root.setLeft(controlPanel);
        
       
        
        // Inizializzazione InputManager
        inputManager = new InputManager();
        simulations = new ArrayList<>();
        controlPanel.getChildren().add(startPointMessage);
        state = UIStates.WAITING_FOR_START_POINT;

        // Gestione del click sul pannello di disegno
        pointsCanvas.setOnMouseClicked(this::handleMouseClick);
        btnCancelInput.setOnAction(e -> handleCancelInputClick());
        btnParabola.setOnAction(e -> handleParabolaClick());
        btnCycloid.setOnAction(e -> handleCycloidClick());
        btnCircumference.setOnAction(e -> handleCircumferenceClick());
        btnCubicSpline.setOnAction(e-> handleCubicSplineClick());
        btnConfirmRadius.setOnAction(e -> handleConfirmRadiusClick(radiusSlider.getValue()));
    
        // Configura la scena
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Simulatore di Curve");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Gestione dei click per selezionare il punto di partenza
    private void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        GraphicsContext gc = pointsCanvas.getGraphicsContext2D();
        switch (state) {
        case WAITING_FOR_START_POINT:
            inputManager.setStartpoint(new Point2D(x, y));
            gc.setFill(Color.RED);
            gc.fillOval(x - 5, y - 5, 10, 10);  // Cerchio rosso per il punto di partenza
            controlPanel.getChildren().remove(startPointMessage);
            controlPanel.getChildren().addAll(endPointMessage, btnCancelInput);
            state = UIStates.WAITING_FOR_END_POINT;
            break;
        case WAITING_FOR_END_POINT:
            try {
                inputManager.setEndpoint(new Point2D(x, y));
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
        }
    }
    
    private void handleCancelInputClick() {
        pointsCanvas.getGraphicsContext2D().clearRect(0, 0, pointsCanvas.getWidth(), pointsCanvas.getHeight());
        curveCanvas.getGraphicsContext2D().clearRect(0, 0, curveCanvas.getWidth(), curveCanvas.getHeight());
        controlPanel.getChildren().clear();
        controlPanel.getChildren().add(startPointMessage);
        state = UIStates.WAITING_FOR_START_POINT;
    }
    
  //gestore del click sul pulsante CubicSpline
    private void handleCubicSplineClick()
    {
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(intermediatePointsMessage, btnCancelInput);
    	state = UIStates.INSERTING_INTERMEDIATE_POINTS;
    }
    
    //gestore del click sul pulsante Cycloid
    private void handleCycloidClick()
    {
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseMassMessage, btnCancelInput);
    	Cycloid cycloid = new Cycloid(inputManager.getStartPoint(),inputManager.getEndPoint());
    	simulations.add(new SimulationManager(cycloid, curveCanvas));
    	cycloid.drawCurve(inputManager.getStartPoint(), 1000, curveCanvas.getGraphicsContext2D());
    	state = UIStates.CHOOSING_MASS;
    }
    
    //gestore del click sul pulsante Parabola
    private void handleParabolaClick()
    {
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseMassMessage, btnCancelInput);
    	Parabola parabola = new Parabola(inputManager.getStartPoint(),inputManager.getEndPoint());
    	simulations.add(new SimulationManager(parabola, curveCanvas));
    	parabola.drawCurve(inputManager.getStartPoint(), 1000, curveCanvas.getGraphicsContext2D());
    	state = UIStates.CHOOSING_MASS;
    }
    
    //gestore del click sul pulsante Circumference
    private void handleCircumferenceClick()
    {
    	System.out.println("handleCircumferenceClick chiamato");
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().add(chooseRadiusMessage);

    	double x = inputManager.getEndPoint().getX() - inputManager.getStartPoint().getX();
    	double y = inputManager.getEndPoint().getY() - inputManager.getStartPoint().getY();
    	Circumference circumferenceInitial = new Circumference(inputManager.getStartPoint(),inputManager.getEndPoint(), (Math.pow(x, 2)+Math.pow(y, 2))/(2*x));
    	circumferenceInitial.drawCurve(inputManager.getStartPoint(), 1000, curveCanvas.getGraphicsContext2D());
    	
    	radiusSlider = new Slider((x/Math.abs(x))*circumferenceInitial.getR(), (x/Math.abs(x))*circumferenceInitial.getR()*3, (x/Math.abs(x))*circumferenceInitial.getR());
    	// Aggiungi un listener per il valore dello slider e chiama la funzione
        radiusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            handleSliderChange(newValue.doubleValue());
        });
    	controlPanel.getChildren().addAll(radiusSlider, btnConfirmRadius, btnCancelInput);
    	state = UIStates.CHOOSING_RADIUS;
    }
    
    private void handleSliderChange(double radius)
    {
    	curveCanvas.getGraphicsContext2D().clearRect(0, 0, curveCanvas.getWidth(), curveCanvas.getHeight());
    	Circumference circumference = new Circumference(inputManager.getStartPoint(),inputManager.getEndPoint(), radius);
    	circumference.drawCurve(inputManager.getStartPoint(), 1000, curveCanvas.getGraphicsContext2D());
    }
    
    private void handleConfirmRadiusClick(double radius)
    {
    	controlPanel.getChildren().clear();
    	controlPanel.getChildren().addAll(chooseMassMessage, btnCancelInput);
    	simulations.add(new SimulationManager(new Circumference(inputManager.getStartPoint(),inputManager.getEndPoint(), radius), curveCanvas));
    	state = UIStates.CHOOSING_MASS;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
