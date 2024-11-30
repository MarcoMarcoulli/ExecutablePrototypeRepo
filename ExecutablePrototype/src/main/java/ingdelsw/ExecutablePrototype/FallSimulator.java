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

public class FallSimulator extends Application implements WindowResizingListener{

	private InputController inputController;
    private ArrayList<SimulationManager> simulations;
    private StateManager stateManager;
    private EventHandler eventHandler;
    
    private ArrayList<Label> arrivalTimeMessages = new ArrayList<Label>();
    private ArrayList<Label> neverArriveMessages = new ArrayList<Label>();

    @Override
    public void start(Stage primaryStage) {
        
        //Inizializzazione InputManager
        inputController = InputController.getController();
        eventHandler = eventHandler.getHandler();
        
        simulations = new ArrayList<SimulationManager>();
        
        // Configura la finestra per aprirsi massimizzata
        primaryStage.setMaximized(true);
    
        // Configura la scena
        Scene scene = new Scene(eventHandler.getLayout().getBorderPane(), 1000, 700);
        primaryStage.setTitle("Fall Simulator");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    
    Random random = new Random();
    int randomRed, randomGreen, randomBlue;
    
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
        simulations.getLast().setMass(new Mass(inputController.getStartPoint(), iconType, mass));
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
    public void onWindowResizing()
    {
    	eventHandler.handleCancelInputClick(layout);
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
    
    public static void main(String[] args) {
        launch(args);
    }
}
