package api.DOM;

import api.datastructs.Arrdep;
import api.datastructs.Geoloc;
import api.datastructs.Station;
import api.datastructs.TripNode;
import api.datastructs.Vehicle;
import api.datastructs.Via;
import api.datastructs.ViaTripNode;
import api.datastructs.Connection;
import api.datastructs.Liveboard;
import api.datastructs.Stop;
import api.datastructs.VehicleInformation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author pieterc
 */
public class Parser {

    private static Document getDocument(String url) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(url);
    }

    public static Liveboard parseLiveboard(String url) throws Exception {
        Document doc = getDocument(url);
        Station s = null;
        ArrayList<Arrdep> nodes = null;
        Element rootNode = doc.getDocumentElement();
        NodeList liveboardNodes = rootNode.getChildNodes();
        if (rootNode.getNodeName().equals("error")) {
            throw new Exception("error:" + rootNode.getFirstChild().getNodeValue());
        }
        for (int i = 0; i < liveboardNodes.getLength(); i++) {
            if (liveboardNodes.item(i).getNodeName().equals("station")) {
                s = readStation(liveboardNodes.item(i));
            } else if (liveboardNodes.item(i).getNodeName().equals("arrivals") || liveboardNodes.item(i).getNodeName().equals("departures")) {
                nodes = readLiveboardNodes(liveboardNodes.item(i).getChildNodes());
            }

        }
        return new Liveboard(s, nodes);
    }

    public static VehicleInformation parseVehicle(String url) throws Exception {
        Document doc = getDocument(url);
        ArrayList<Stop> stops = new ArrayList<Stop>();
        Element rootNode = doc.getDocumentElement();
        NodeList connectionNodes = rootNode.getChildNodes();
        Vehicle v = null;
        if (rootNode.getNodeName().equals("error")) {
            throw new Exception("error:" + rootNode.getFirstChild().getNodeValue());
        }
        for (int i = 0; i < connectionNodes.getLength(); i++) {
            Node vehinfo = connectionNodes.item(i);
            if (vehinfo.getNodeName().equals("vehicle")) {
                v = readVehicle(vehinfo);
            } else if (vehinfo.getNodeName().equals("stops")) {
                stops = readStoplist(vehinfo.getChildNodes());
            }

        }
        return new VehicleInformation(v, stops);
    }

    public static List<Connection> parseConnections(String url) throws Exception {
        Document doc = getDocument(url);
        ArrayList<Connection> cons = new ArrayList<Connection>();
        Element rootNode = doc.getDocumentElement();
        NodeList connectionNodes = rootNode.getChildNodes();
        if (rootNode.getNodeName().equals("error")) {
            throw new Exception("error:" + rootNode.getFirstChild().getNodeValue());
        }
        for (int i = 0; i < connectionNodes.getLength(); i++) {
            Node connectionNode = connectionNodes.item(i);
            Connection c = readConnection(connectionNode.getChildNodes());
            cons.add(c);
        }
        return cons;
    }

    public static List<Station> parseStations(String url) throws Exception {
        Document doc = getDocument(url);
        ArrayList<Station> stations = new ArrayList<Station>();
        Element rootNode = doc.getDocumentElement();
        NodeList stationNodes = rootNode.getChildNodes();
        if (rootNode.getNodeName().equals("error")) {
            throw new Exception("error:" + rootNode.getFirstChild().getNodeValue());
        }
        for (int i = 0; i < stationNodes.getLength(); i++) {
            Node stationNode = stationNodes.item(i);
            Station s = readStation(stationNode);
            stations.add(s);
        }
        return stations;
    }

    private static Connection readConnection(NodeList childNodes) {
        TripNode dep = null, arr = null;
        int duration = 0;
        List<Via> vias = null;
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node n = childNodes.item(i);
            if (n.getNodeName().equals("departure")) {
                dep = readTripNode(n.getChildNodes());
            } else if (n.getNodeName().equals("arrival")) {
                arr = readTripNode(n.getChildNodes());
            } else if (n.getNodeName().equals("duration")) {
                duration = Integer.parseInt(n.getFirstChild().getNodeValue());
            } else if (n.getNodeName().equals("vias")) {
                vias = readVias(n.getChildNodes());
            }
        }
        return new Connection(arr, dep, duration, vias);
    }

    private static TripNode readTripNode(NodeList nodes) {
        Station st = null;
        String platform = null;
        Vehicle v = null;
        Date t = null;
        int delay = 0;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeName().equals("station")) {
                st = readStation(n);
            } else if (n.getNodeName().equals("platform")) {
                if (n.getFirstChild() != null) {
                    platform = n.getFirstChild().getNodeValue();
                }
            } else if (n.getNodeName().equals("vehicle")) {
                v = readVehicle(n);
            } else if (n.getNodeName().equals("time")) {

                t = new Date(Long.parseLong(n.getFirstChild().getNodeValue()) * 1000);
            } else if (n.getNodeName().equals("delay")) {
                delay = Integer.parseInt(n.getFirstChild().getNodeValue());
            }
        }
        return new TripNode(st, platform, v, t, delay);
    }

    private static List<Via> readVias(NodeList nodes) {
        ArrayList<Via> vias = new ArrayList<Via>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Via v = readVia(nodes.item(i).getChildNodes());
            vias.add(v);
        }
        return vias;
    }

    private static Station readStation(Node n) {
        NamedNodeMap m = n.getAttributes();
        String x = m.getNamedItem("locationX").getFirstChild().getNodeValue();
        String y = m.getNamedItem("locationY").getFirstChild().getNodeValue();
        Geoloc g = new Geoloc(Double.parseDouble(x), Double.parseDouble(y));
        //ID not yet implemented
        return new Station(n.getFirstChild().getNodeValue(), "IDNYI", g);
    }

    private static Vehicle readVehicle(Node n) {
        String parts[] = n.getFirstChild().getNodeValue().split("\\.");
        return new Vehicle(parts[2], parts[1], parts[0]);
    }

    private static Via readVia(NodeList nodes) {
        ViaTripNode arr = null, dep = null;
        Vehicle v = null;
        int timeBetween = 0;
        Station s = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeName().equals("station")) {
                s = readStation(n);
            } else if (n.getNodeName().equals("arrival")) {
                arr = readViaTripNode(n.getChildNodes());
            } else if (n.getNodeName().equals("departure")) {
                dep = readViaTripNode(n.getChildNodes());
            } else if (n.getNodeName().equals("vehicle")) {
                v = readVehicle(n);
            } else if (n.getNodeName().equals("timeBetween")) {
                timeBetween = Integer.parseInt(n.getFirstChild().getNodeValue());
            }
        }
        return new Via(arr, dep, timeBetween, v, s);
    }

    private static ViaTripNode readViaTripNode(NodeList nodes) {
        String platform = null;
        Date t = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeName().equals("platform")) {
                platform = n.getFirstChild().getNodeValue();
            } else if (n.getNodeName().equals("time")) {
                t = new Date(Long.parseLong(n.getFirstChild().getNodeValue()) * 1000);
            }
        }
        return new ViaTripNode(platform, t);
    }

    private static ArrayList<Arrdep> readLiveboardNodes(NodeList nodes) {
        ArrayList<Arrdep> a = new ArrayList<Arrdep>();
        for (int i = 0; i < nodes.getLength(); i++) {
            NodeList n = nodes.item(i).getChildNodes();
            Station s = null;
            Vehicle v = null;
            Date d = null;
            String p = null;
            for (int j = 0; j < n.getLength(); j++) {
                if (n.item(j).getNodeName().equals("vehicle")) {
                    v = readVehicle(n.item(j));
                } else if (n.item(j).getNodeName().equals("time")) {
                    d = new Date(Long.parseLong(n.item(j).getFirstChild().getNodeValue()) * 1000);
                } else if (n.item(j).getNodeName().equals("station")) {
                    s = readStation(n.item(j));
                } else if (n.item(j).getNodeName().equals("platform")) {
                    p = n.item(j).getFirstChild().getNodeValue();
                }
            }
            Arrdep ad = new Arrdep(s, v, d, p);
            a.add(ad);

        }
        return a;
    }

    private static ArrayList<Stop> readStoplist(NodeList nodes) {
        ArrayList<Stop> a = new ArrayList<Stop>();
        for (int i = 0; i < nodes.getLength(); i++) {
            NodeList stoplist = nodes.item(i).getChildNodes();
            Station s = null;
            int delay = Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("delay").getFirstChild().getNodeValue());
            Date time = null;
            for (int j = 0; j < stoplist.getLength(); j++) {
                if (stoplist.item(j).getNodeName().equals("station")) {
                    s = readStation(stoplist.item(j));
                } else if (stoplist.item(j).getNodeName().equals("time")) {
                    time = new Date(Long.parseLong(stoplist.item(j).getFirstChild().getNodeValue()) * 1000);
                }
            }
            a.add(new Stop(s, time, delay));
        }
        return a;

    }
}
