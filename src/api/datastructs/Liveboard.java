package api.datastructs;

import java.util.ArrayList;

/**
 *
 * @author pieterc
 */
public class Liveboard {
    private Station s;
    private ArrayList<Arrdep> nodes;

    public Liveboard(Station s, ArrayList<Arrdep> nodes) {
        this.s = s;
        this.nodes = nodes;
    }

    public ArrayList<Arrdep> getNodes() {
        return nodes;
    }

    public Station getS() {
        return s;
    }
}