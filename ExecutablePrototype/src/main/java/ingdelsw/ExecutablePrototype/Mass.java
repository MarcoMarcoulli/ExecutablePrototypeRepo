package ingdelsw.ExecutablePrototype;

import ingdelsw.ExecutablePrototype.MassIcon;
import ingdelsw.ExecutablePrototype.Math.Point;

public class Mass {
    private Point currentPosition;
    private MassIcon iconType;

    // Costruttore
    public Mass(Point startPosition, MassIcon iconType) {
        this.currentPosition = startPosition;
        this.iconType = iconType;
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

    // Altri metodi logici, come il calcolo della posizione futura, possono essere aggiunti qui
}
