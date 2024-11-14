package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.MassIcon;
import ingdelsw.ExecutablePrototype.Math.Point;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Mass {
    private Point position;
    private MassIcon iconType;
    private ImageView icon;
    private final double massDiameter = 40;

    // Costruttore
    public Mass(Point startPosition, MassIcon iconType, ImageView icon) {
        this.position = startPosition;
        this.icon = icon;
        this.icon.setX(position.getX() - massDiameter/2);
        this.icon.setY(position.getY() - massDiameter/2);
        this.iconType = iconType;
        icon.setFitWidth(massDiameter);
        icon.setFitHeight(massDiameter);
    }

    // Getter e Setter per la posizione corrente
    public Point getCurrentPosition() {
        return position;
    }
    
    public double getMassDiameter()
    {
    	return massDiameter;
    }

    public void setCurrentPosition(Point newPosition) {
        position = newPosition;
        icon.relocate(position.getX() - massDiameter/2, position.getY() - massDiameter/2);
    }

    // Getter per il tipo di icona (usato per la visualizzazione nell'interfaccia)
    public String getIconTypeString() {
        return iconType.name();
    }
    
    public ImageView getIcon()
    {
    	return icon;
    }
    

    // Altri metodi logici, come il calcolo della posizione futura, possono essere aggiunti qui
}
