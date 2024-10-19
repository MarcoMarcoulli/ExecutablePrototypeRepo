package ingdelsw.ExecutablePrototype.Math;

public class CubicSegment {

    private double a, b, c, d;  // Coefficienti del segmento cubico
    private double x0, x1;      // Estremi del segmento (coordinate X)

    // Costruttore che accetta due punti e le derivate nei due punti
    public CubicSegment(Point p0, Point p1, double derivativeP0, double derivativeP1) {
        this.x0 = p0.getX();
        this.x1 = p1.getX();

        // Calcolo delle differenze tra i punti
        double dx = x1 - x0;
        double dy = p1.getY() - p0.getY();

        // Calcolo dei coefficienti usando le equazioni per i segmenti cubici
        this.a = (derivativeP0 + derivativeP1 - 2 * (dy / dx)) / (dx * dx);
        this.b = (3 * (dy / dx) - 2 * derivativeP0 - derivativeP1) / dx;
        this.c = derivativeP0;
        this.d = p0.getY();
    }

    // Metodo per valutare il segmento cubico in un punto x
    public double evaluate(double x) {
        double dx = x - x0;
        return a * dx * dx * dx + b * dx * dx + c * dx + d;
    }

    // Getter per i coefficienti, se necessario
    public double getA(){ 
    	return a; 
    }
    public double getB(){ 
    	return b; 
    }
    public double getC(){ 
    	return c; 
    }
    public double getD(){
    	return d; 
    }
}