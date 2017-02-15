package core;

import javafx.geometry.Point3D;
import javafx.scene.paint.Material;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.*;

/**
 * Created by NF on 2/13/2017.
 */
public class Graph {

    private Map<Vertex, ArrayList<Edge>> adjMapList;

    private XformWorld world;

    public Graph(XformWorld world){
        adjMapList = new HashMap<Vertex,ArrayList<Edge>>();
        setWorld(world);
    }

    public XformWorld getWorld() {
        return world;
    }

    public void setWorld(XformWorld world) {
        this.world = world;
    }


    /**
     * Get the number of vertices (road intersections) in the graph
     * @return The number of vertices in the graph.
     */
    public int getNumVertices(){
        return adjMapList.size();
    }

    /**
     * Return the intersections, which are the vertices in this graph.
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<Vertex> getVertices(){
        Set<Vertex> intersections = new HashSet<Vertex>();

        for(Vertex gp : adjMapList.keySet()){
            intersections.add(gp);
        }

        return intersections;
    }

    /**
     * Get the number of road segments in the graph
     * @return The number of edges in the graph.
     */
    public int getNumEdges(){
        int ne=0;

        for(Vertex gp : adjMapList.keySet()){
            for(Edge gpe : adjMapList.get(gp)){
                ne++;
            }
        }


        return ne;
    }

    public List<Edge> getEdges(){

        List<Edge> edges = new ArrayList<Edge>();

        for(Vertex gp : adjMapList.keySet()){
            for(Edge gpe : adjMapList.get(gp)){
                edges.add(gpe);
            }
        }

        return edges;
    }


    public boolean addVertex(Vertex point){
        if(!adjMapList.containsKey(point)){
            adjMapList.put(point,new ArrayList<Edge>());
            return true;
        }
        return false;
    }

    public boolean updateVertex(Vertex point){
        if(adjMapList.containsKey(point)){
            ArrayList<Edge> tempList = adjMapList.get(point);
            adjMapList.put(point,tempList);
            return true;
        }
        return false;
    }

    public void addEdge(Vertex from, Vertex to, Material material, Object uniqueProperties) throws IllegalArgumentException {

//        if(!adjMapList.containsKey(from) || !adjMapList.containsKey(to)) {
//            throw new IllegalArgumentException();
//        }else{

            if(!adjMapList.containsKey(from)){
                addVertex(from);
            }

            if(!adjMapList.containsKey(to)){
                addVertex(to);
            }

            ArrayList<Edge> tempList = adjMapList.get(from);
            Edge se = createEdge(from,to);

            //TODO find better way to assign material
            se.setMaterial(material);
            se.setUniqueProperties(uniqueProperties);

            tempList.add(se);

            adjMapList.put(from,tempList);
//        }

    }

    public List<Vertex> outNeighbors(Vertex of){
        List<Vertex> outNbs = new ArrayList<Vertex>();
        if(!adjMapList.containsKey(of)){
            return null;
        }

        for(Edge se : adjMapList.get(of)){
            outNbs.add(se.getEndPoint());
        }
        return outNbs;
    }

    public List<Edge> outNeigborEdges(Vertex of){
        List<Edge> outNbs = new ArrayList<Edge>();
        if(!adjMapList.containsKey(of)){
            return null;
        }

        for(Edge se : adjMapList.get(of)){
            outNbs.add(se);
        }
        return outNbs;
    }

    public List<Vertex> inNeighbors(Vertex of){
        List<Vertex> inNbs = new ArrayList<Vertex>();

        if(!adjMapList.containsKey(of)){
            return null;
        }

        for(Vertex start : adjMapList.keySet()){
            for(Edge se : adjMapList.get(start)){
                if(se.getEndPoint().equals(of)) {
                    inNbs.add(se.getEndPoint());
                }
            }
        }

        return inNbs;
    }

    public List<Edge> inNeighborsEdges(Vertex of){
        List<Edge> inNbs = new ArrayList<Edge>();

        if(!adjMapList.containsKey(of)){
            return null;
        }

        for(Vertex start : adjMapList.keySet()){
            for(Edge se : adjMapList.get(start)){
                if(se.getEndPoint().equals(of)) {
                    inNbs.add(se);
                }
            }
        }

        return inNbs;
    }

    public Edge createEdge(Vertex point1, Vertex point2) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = point2.getPoint3D().subtract(point1.getPoint3D());// subtract(point1);
        double height = diff.magnitude();

        Point3D mid = point2.getPoint3D().midpoint(point1.getPoint3D());
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation =  diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Edge line = new Edge(1, height, point1, point2);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    public void transformEdge(Edge edge, Vertex point1, Vertex point2){
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = point2.getPoint3D().subtract(point1.getPoint3D());

        Point3D mid = point2.getPoint3D().midpoint(point1.getPoint3D());
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation =  diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        edge.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
    }

    public void transformVertex(Vertex vertex, Point3D destination){
        List<Edge> edges = outNeigborEdges(vertex);

        edges.addAll(inNeighborsEdges(vertex));

        vertex.setTranslateX(destination.getX());
        vertex.setTranslateY(destination.getY());
        vertex.setTranslateZ(destination.getZ());

        if(edges!=null && edges.size()>0){
//            for(Edge ed: edges){
//                if(ed.getEndPoint().equals(vertex)){
//                    transformEdge(ed,ed.getStartPoint(),vertex);
//                }else {
//                    transformEdge(ed,vertex,ed.getEndPoint());
//                }
//            }
        }

    }

}