package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.MassIcon;
import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Mass {
    private Point currentPosition;
    private MassIcon iconType;
    private ImageView icon;

    // Costruttore
    public Mass(Point startPosition, MassIcon iconType, ImageView icon) {
        this.currentPosition = startPosition;
        this.iconType = iconType;
        icon.setFitWidth(60);
        icon.setFitHeight(60);
        this.icon = icon;
        this.icon.setX(startPosition.getX() - 30);
        this.icon.setY(startPosition.getY() - 30);
    }

    // Getter e Setter per la posizione corrente
    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point newPosition) {
        this.currentPosition = newPosition;
    }

    // Getter per il tipo di icona (usato per la visualizzazione nell'interfaccia)
    public MassIcon getIconType() {
        return iconType;
    }
    
    public ImageView getIcon()
    {
    	return icon;
    }
    

    // Altri metodi logici, come il calcolo della posizione futura, possono essere aggiunti qui
}
