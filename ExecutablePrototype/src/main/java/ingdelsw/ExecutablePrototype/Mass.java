package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.MassIcon;
import javafx.geometry.Point2D;

public class Mass {
    private Point2D currentPosition;
    private MassIcon iconType;

    // Costruttore
    public Mass(Point2D startPosition, MassIcon iconType) {
        this.currentPosition = startPosition;
        this.iconType = iconType;
    }

    // Getter e Setter per la posizione corrente
    public Point2D getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point2D newPosition) {
        this.currentPosition = newPosition;
    }

    // Getter per il tipo di icona (usato per la visualizzazione nell'interfaccia)
    public MassIcon getIconType() {
        return iconType;
    }

    // Altri metodi logici, come il calcolo della posizione futura, possono essere aggiunti qui
}
