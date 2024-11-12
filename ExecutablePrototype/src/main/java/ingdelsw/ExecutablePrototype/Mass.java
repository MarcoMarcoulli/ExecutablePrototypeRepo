package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.MassIcon;
import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Mass {
    private Point currentPosition;
    private MassIcon iconType;
    private ImageView icon;
    private final double massDiameter = 40;

    // Costruttore
    public Mass(Point startPosition, MassIcon iconType, ImageView icon) {
        this.currentPosition = startPosition;
        this.iconType = iconType;
        icon.setFitWidth(massDiameter);
        icon.setFitHeight(massDiameter);
        this.icon = icon;
        this.icon.setX(startPosition.getX() - massDiameter/2);
        this.icon.setY(startPosition.getY() - massDiameter/2);
    }

    // Getter e Setter per la posizione corrente
    public Point getCurrentPosition() {
        return currentPosition;
    }
    
    public double getMassDiameter()
    {
    	return massDiameter;
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
