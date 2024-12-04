
package ingdelsw.ExecutablePrototype;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Layout {
			// Layout principale
    private BorderPane root;
    
    private VBox controlPanel;
    
    private Canvas pointsCanvas;
    private Canvas curveCanvas;
    
    private Pane animationPane;
    
    private HBox curveButtons;
    private HBox  massIconButtons;
    private HBox  convexityButtons;
    private HBox  planetIconButtons;
    
    private Label startPointMessage;
    private Label  endPointMessage;
    private Label  chooseCurveMessage;
    private Label  intermediatePointsMessage;
    private Label  chooseMassMessage;
    private Label  chooseRadiusMessage;
    private Label  chooseConvexityMessage;
    private Label  selectGravityMessage;
    private Label  simulatingMessage;
    
    private Button btnCancelInput;
    private Button  btnCycloid;
    private Button  btnParabola;
    private Button  btnCubicSpline;
    private Button  btnCircumference;
    private Button  btnConfirmRadius;
    private Button  btnConvexityUp;
    private Button  btnConvexityDown;
    private Button  btnStopIntermediatePointsInsertion;
    private Button  btnStartSimulation;
    private Button  btnInsertAnotherCurve;
    
    private Slider radiusSlider;
    
    private VBox massArrivalMessagesBox;
    
    private StackPane stackPane;
    
    private ImageView  iconViewBernoulli;
    private ImageView  iconViewGalileo;
    private ImageView  iconViewJakob;
    private ImageView  iconViewLeibnitz;
    private ImageView  iconViewNewton;
    
    private ImageView  iconViewMoon;
    private ImageView  iconViewMars;
    private ImageView  iconViewEarth;
    private ImageView  iconViewJupiter;
    private ImageView  iconViewSun;
    
    private ArrayList<Label> arrivalTimeMessages;
    private ArrayList<Label> neverArriveMessages;
    
    private GraphicsContext gc;
    
    private static Layout theLayout = null;
    
    private WindowResizingListener listener;

    private Layout(WindowResizingListener listener) {
    	
    	this.listener = listener;
    	
    	root = new BorderPane();
    	// Carica il file CSS
        root.getStylesheets().add(
        	    getClass().getClassLoader().getResource("style.css").toExternalForm()
        	);
        
        controlPanel = new VBox(10);
        controlPanel.getStyleClass().add("control-panel");
        controlPanel.getStyleClass().add("control-panel");
        
        // Canvas per disegno (a destra)
        curveCanvas = new Canvas();
        pointsCanvas = new Canvas();
        animationPane = new Pane();
        animationPane.setMouseTransparent(true); // Rendi animationPane trasparente agli eventi di mouse
        
        curveButtons = new HBox(10);
        curveButtons.getStyleClass().add("curve-buttons");
        
        massIconButtons = new HBox(10); // Layout per tenere insieme le icone
        massIconButtons.getStyleClass().add("icon-buttons");
        
        planetIconButtons = new HBox(10); // Layout per tenere insieme le icone
        planetIconButtons.getStyleClass().add("icon-buttons");
        
        convexityButtons= new HBox(6);
        
        massArrivalMessagesBox = new VBox();
        stackPane = new StackPane(curveCanvas, pointsCanvas, animationPane);
        
        radiusSlider = null;
        
        selectGravityMessage = new Label("scegli il campo gravitazionale in cui far avvenire la caduta".toUpperCase());
        controlPanel.getChildren().addAll(selectGravityMessage, planetIconButtons);
        startPointMessage = new Label("Inserisci il punto di partenza".toUpperCase());
        startPointMessage.getStyleClass().add("label".toUpperCase());
        controlPanel.getChildren().add(startPointMessage);
        endPointMessage = new Label("Inserisci il punto di arrivo".toUpperCase());
        endPointMessage.getStyleClass().add("label");
        chooseCurveMessage = new Label("scegli una curva".toUpperCase());
        chooseCurveMessage.getStyleClass().add("label");
        intermediatePointsMessage = new Label("Inserisci dei punti intermedi da interpolare".toUpperCase());
        chooseMassMessage = new Label("Inserisci chi vuoi far scivolare".toUpperCase()); 
        chooseRadiusMessage = new Label("Seleziona il raggio della circonferenza".toUpperCase());
        chooseConvexityMessage = new Label("scegli la convessita".toUpperCase());
        simulatingMessage = new Label("Simulazione in corso".toUpperCase());
        
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
        btnConvexityUp = new Button("Verso l'alto");
        btnConvexityDown = new Button("Verso il basso");
        btnStopIntermediatePointsInsertion = new Button("Fine immissione");
        btnStartSimulation = new Button("Avvia simulazione");
        btnInsertAnotherCurve = new Button("Inserisci un' altra curva");
        
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);
        convexityButtons.getChildren().addAll(btnConvexityUp, btnConvexityDown);
        
        // Carica le icone delle masse
        Image iconBernoulli = new Image(getClass().getResource("/images/Bernoulli.png").toExternalForm());
        Image iconGalileo = new Image(getClass().getResource("/images/Galileo.png").toExternalForm());
        Image iconJakob = new Image(getClass().getResource("/images/Jakob.png").toExternalForm());
        Image iconLeibnitz = new Image(getClass().getResource("/images/Leibnitz.png").toExternalForm());
        Image iconNewton = new Image(getClass().getResource("/images/Newton.png").toExternalForm());

        // Crea pulsanti immagine
        iconViewBernoulli = createIconButton(iconBernoulli);
        iconViewGalileo = createIconButton(iconGalileo);
        iconViewJakob = createIconButton(iconJakob);
        iconViewLeibnitz = createIconButton(iconLeibnitz);
        iconViewNewton = createIconButton(iconNewton);
        
        massIconButtons.getChildren().addAll(iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton);
        
     // Carica le icone delle masse
        Image iconMoon = new Image(getClass().getResource("/images/moon.png").toExternalForm());
        Image iconMars = new Image(getClass().getResource("/images/mars.png").toExternalForm());
        Image iconEarth = new Image(getClass().getResource("/images/earth.png").toExternalForm());
        Image iconJupiter = new Image(getClass().getResource("/images/jupiter.png").toExternalForm());
        Image iconSun = new Image(getClass().getResource("/images/sun.png").toExternalForm());

        // Crea pulsanti immagine
        iconViewMoon = createIconButton(iconMoon);
        iconViewMars = createIconButton(iconMars);
        iconViewEarth = createIconButton(iconEarth);
        iconViewJupiter = createIconButton(iconJupiter);
        iconViewSun = createIconButton(iconSun);
        
        planetIconButtons.getChildren().addAll(iconViewMoon, iconViewMars, iconViewEarth, iconViewJupiter, iconViewSun);
        
        arrivalTimeMessages = new ArrayList<>();
        neverArriveMessages = new ArrayList<>();
        
        gc = pointsCanvas.getGraphicsContext2D();
        
        // Aggiungi entrambi i Canvas al centro del layout
        stackPane = new StackPane();
        stackPane.getChildren().addAll(curveCanvas, pointsCanvas, animationPane);
        root.setCenter(stackPane);
        
        root.setLeft(controlPanel);
        
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue() - controlPanel.getWidth();
            curveCanvas.setWidth(newWidth);
            pointsCanvas.setWidth(newWidth);
            animationPane.setPrefWidth(newWidth);
            this.listener.onWindowResizing();
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            curveCanvas.setHeight(newHeight);
            pointsCanvas.setHeight(newHeight);
            animationPane.setPrefHeight(newHeight);
            this.listener.onWindowResizing();
        });
        
    }
    
    public static Layout getLayout(WindowResizingListener listener)
    {
    	if(theLayout == null)
    		theLayout = new Layout(listener);
    	return theLayout;
    }
    
    private static final double iconButtonDiameter = 70;
    
    // Metodo helper per creare un pulsante icona
    private ImageView createIconButton(Image image) {
        ImageView iconView = new ImageView(image);
        iconView.setFitWidth(iconButtonDiameter); // Imposta la larghezza desiderata per l'icona
        iconView.setFitHeight(iconButtonDiameter);
        return iconView;
    }
    
    public BorderPane getBorderPane()
    {
    	return root;
    }

    public VBox getControlPanel() {
        return controlPanel;
    }

    public Canvas getPointsCanvas() {
        return pointsCanvas;
    }

    public Canvas getCurveCanvas() {
        return curveCanvas;
    }

    public Pane getAnimationPane() {
        return animationPane;
    }

    public HBox getCurveButtons() {
        return curveButtons;
    }

    public HBox getMassIconButtons() {
        return massIconButtons;
    }
    
    public HBox getPlanetIconButtons() {
        return planetIconButtons;
    }

    public HBox getConvexityButtons() {
        return convexityButtons;
    }

    public VBox getMassArrivalMessagesBox() {
        return massArrivalMessagesBox;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public Label getStartPointMessage() {
        return startPointMessage;
    }

    public Label getEndPointMessage() {
        return endPointMessage;
    }

    public Label getChooseCurveMessage() {
        return chooseCurveMessage;
    }

    public Label getIntermediatePointsMessage() {
        return intermediatePointsMessage;
    }

    public Label getChooseMassMessage() {
        return chooseMassMessage;
    }
    
    public Label getSelectGravityMessage() {
        return selectGravityMessage;
    }

    public Label getChooseRadiusMessage() {
        return chooseRadiusMessage;
    }

    public Label getChooseConvexityMessage() {
        return chooseConvexityMessage;
    }

    public Label getSimulatingMessage() {
        return simulatingMessage;
    }

    public Button getBtnCancelInput() {
        return btnCancelInput;
    }

    public Button getBtnCycloid() {
        return btnCycloid;
    }

    public Button getBtnParabola() {
        return btnParabola;
    }

    public Button getBtnCubicSpline() {
        return btnCubicSpline;
    }

    public Button getBtnCircumference() {
        return btnCircumference;
    }

    public Button getBtnConfirmRadius() {
        return btnConfirmRadius;
    }

    public Button getBtnConvexityUp() {
        return btnConvexityUp;
    }

    public Button getBtnConvexityDown() {
        return btnConvexityDown;
    }

    public Button getBtnStopIntermediatePointsInsertion() {
        return btnStopIntermediatePointsInsertion;
    }

    public Button getBtnStartSimulation() {
        return btnStartSimulation;
    }
    
    public Button getBtnInsertAnotherCurve() {
        return btnInsertAnotherCurve;
    }

    public Slider getRadiusSlider() {
        return radiusSlider;
    }
    
    public void setRadiusSlider(Slider radiusSlider) {
        this.radiusSlider = radiusSlider;
    }

    public ImageView getIconViewMoon() {
        return iconViewMoon;
    }

    public ImageView getIconViewMars() {
        return iconViewMars;
    }

    public ImageView getIconViewEarth() {
        return iconViewEarth;
    }

    public ImageView getIconViewJupiter() {
        return iconViewJupiter;
    }

    public ImageView getIconViewSun() {
        return iconViewSun;
    }
    
    public ImageView getIconViewBernoulli() {
        return iconViewBernoulli;
    }

    public ImageView getIconViewGalileo() {
        return iconViewGalileo;
    }

    public ImageView getIconViewJakob() {
        return iconViewJakob;
    }

    public ImageView getIconViewLeibnitz() {
        return iconViewLeibnitz;
    }

    public ImageView getIconViewNewton() {
        return iconViewNewton;
    }
    
    public GraphicsContext getGC()
    {
    	return gc;
    }
    
    public void setWindowResizingListener(WindowResizingListener listener)
    {
    	this.listener = listener;
    }
    
    public void clear()
    {
    	pointsCanvas.getGraphicsContext2D().clearRect(0, 0, pointsCanvas.getWidth(), pointsCanvas.getHeight());
        curveCanvas.getGraphicsContext2D().clearRect(0, 0, curveCanvas.getWidth(), curveCanvas.getHeight());
        animationPane.getChildren().clear();
        controlPanel.getChildren().clear();
        controlPanel.getChildren().add(startPointMessage);
        massIconButtons.getChildren().clear();
        massIconButtons.getChildren().addAll(iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton);
        curveButtons.getChildren().clear();
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);
        arrivalTimeMessages.clear();
    	neverArriveMessages.clear();
    	massArrivalMessagesBox.getChildren().clear();
    }

	public List<Label> getArrivalTimeMessages() {
		return arrivalTimeMessages;
	}
	
	public List<Label> getNeverArriveMessages() {
		return neverArriveMessages;
	}
}