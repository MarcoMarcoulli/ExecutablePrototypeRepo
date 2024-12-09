package ingdelsw.ExecutablePrototype;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FallSimulator extends Application{

    @Override
    public void start(Stage primaryStage) {
    	
        EventHandler eventHandler = EventHandler.getHandler();
        
        // Configura la finestra per aprirsi massimizzata
        primaryStage.setMaximized(true);
    
        // Configura la scena
        Scene scene = new Scene(eventHandler.getLayout().getBorderPane(), 1000, 700);
        primaryStage.setTitle("Fall Simulator");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
