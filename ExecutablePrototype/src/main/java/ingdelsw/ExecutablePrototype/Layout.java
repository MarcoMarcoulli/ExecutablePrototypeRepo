
package ingdelsw.ExecutablePrototype;

import java.util.ArrayList;

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
    private HBox curveButtons, iconButtons, convexityButtons;
    private Label startPointMessage, endPointMessage, chooseCurveMessage, intermediatePointsMessage, 
                  chooseMassMessage, chooseRadiusMessage, chooseConvexityMessage, simulatingMessage;
    private Button btnCancelInput, btnCycloid, btnParabola, btnCubicSpline, btnCircumference, 
                   btnConfirmRadius, btnConvexityUp, btnConvexityDown, btnStopIntermediatePointsInsertion, 
                   btnStartSimulation, btnInsertAnotherCurve;
    private Slider radiusSlider;
    private VBox massArrivalMessagesBox;
    private StackPane stackPane;
    private ImageView iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton;
    
    private ArrayList<Label> arrivalTimeMessages;
    private ArrayList<Label> neverArriveMessages;
    
    GraphicsContext gc = pointsCanvas.getGraphicsContext2D();
    
    private static Layout theLayout = null;
    
    private WindowResizingListener listener;

    private Layout() {
    	root = new BorderPane();
    	// Carica il file CSS
        root.getStylesheets().add(
        	    getClass().getClassLoader().getResource("style.css").toExternalForm()
        	);
        
        controlPanel = new VBox(10);
        controlPanel.getStyleClass().add("control-panel");
        controlPanel.getStyleClass().add("control-panel");
        controlPanel.getChildren().add(startPointMessage);
        
        // Canvas per disegno (a destra)
        curveCanvas = new Canvas();
        pointsCanvas = new Canvas();
        animationPane = new Pane();
        animationPane.setMouseTransparent(true); // Rendi animationPane trasparente agli eventi di mouse
        
        curveButtons = new HBox(10);
        curveButtons.getStyleClass().add("curve-buttons");
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);
        
        iconButtons = new HBox(10); // Layout per tenere insieme le icone
        iconButtons.getStyleClass().add("icon-buttons");
        iconButtons.getChildren().addAll(iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton);
        
        convexityButtons= new HBox(6);
        convexityButtons.getChildren().addAll(btnConvexityUp, btnConvexityDown);
        
        massArrivalMessagesBox = new VBox();
        stackPane = new StackPane(curveCanvas, pointsCanvas, animationPane);
        
        startPointMessage = new Label("Inserisci il punto di partenza".toUpperCase());
        startPointMessage.getStyleClass().add("label".toUpperCase());
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
        
        arrivalTimeMessages = new ArrayList<Label>();
        neverArriveMessages = new ArrayList<Label>();
        
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
            listener.onWindowResizing();
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            curveCanvas.setHeight(newHeight);
            pointsCanvas.setHeight(newHeight);
            animationPane.setPrefHeight(newHeight);
            listener.onWindowResizing();
        });
        
    }
    
    public static Layout getLayout()
    {
    	if(theLayout == null)
    		theLayout = new Layout();
    	return theLayout;
    }
    
    final double IconButtonDiameter = 70;
    
    // Metodo helper per creare un pulsante icona
    private ImageView createIconButton(Image image, MassIcon iconType) {
        ImageView iconView = new ImageView(image);
        iconView.setFitWidth(IconButtonDiameter); // Imposta la larghezza desiderata per l'icona
        iconView.setFitHeight(IconButtonDiameter);
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

    public HBox getIconButtons() {
        return iconButtons;
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
        iconButtons.getChildren().clear();
        iconButtons.getChildren().addAll(iconViewBernoulli, iconViewGalileo, iconViewJakob, iconViewLeibnitz, iconViewNewton);
        curveButtons.getChildren().clear();
        curveButtons.getChildren().addAll(btnCycloid, btnCircumference, btnParabola, btnCubicSpline);
        arrivalTimeMessages.clear();
    	neverArriveMessages.clear();
    	massArrivalMessagesBox.getChildren().clear();
    }

	public ArrayList<Label> getArrivalTimeMessages() {
		return arrivalTimeMessages;
	}
	
	public ArrayList<Label> getNeverArriveMessages() {
		return neverArriveMessages;
	}
}