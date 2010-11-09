package api.datastructs;

/**
 *
 * @author pieterc
 */
public class Geoloc {

    double X, Y;

    public Geoloc(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }
}
