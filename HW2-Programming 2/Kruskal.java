import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        DisjointSets disjointSets = new DisjointSets(g.getNbNodes());
        WGraph wGraph = new WGraph();

        ArrayList<Edge> sortedEdges = g.listOfEdgesSorted();
        for (Edge edge : sortedEdges){
            if(IsSafe(disjointSets,edge)){
                disjointSets.union(edge.nodes[0],edge.nodes[1]);
                wGraph.addEdge(edge);
            }
        }
        return wGraph;
    }

    /*
    To check if safe

     */

    public static Boolean IsSafe(DisjointSets p, Edge e){

        int v1 = p.find(e.nodes[0]);
        int v2 = p.find(e.nodes[1]);

        if(v1==v2){
            return false;
        }
        else{
            return true;
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
