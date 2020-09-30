import java.io.*;
import java.util.*;


public class FordFulkerson {



	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		Boolean visitedNodes[] = new Boolean[graph.getNbNodes()];

		//create a list of unvisited nodes
		for (int i = 0; i< visitedNodes.length;i++){
			visitedNodes[i] = false;
		}
		//create arraylist of unvisited edges
		ArrayList<Edge> unvisitedEdges = new ArrayList<Edge>();
		for (Edge e : graph.getEdges()){
			unvisitedEdges.add(e);
		}

		//start from source
		Integer current = source;

		//add source to stack
		Stack.add(current);
		visitedNodes[current] = true;

		//while stack size is not zero keep going
		//if it is zero, then there is no path
		//and we will return an empty stack
		while (Stack.size()!=0){
			int index = 0;
			//for every unvisited edge
			//origninally wnated to reduce runtime and remove the visited edges
			//but was short for time, kept giving errors

			for (Edge edge : unvisitedEdges){
				//if the first node is the current node and the second node along the edge has not been visited
				//set the second node as the current, set it as visited and add it to the stack
				if (edge.nodes[0]==current&&visitedNodes[edge.nodes[1]]==false){
					current = edge.nodes[1];
					visitedNodes[current] = true;
					Stack.add(current);
//					unvisitedEdges.remove(index);
					break;
				}
				//if we get to the end of the unvisited edge, pop top off of stack
				//and set current to the new top of stack
				else if(edge.equals(unvisitedEdges.get(unvisitedEdges.size()-1))){
					Stack.remove(Stack.size()-1);
					if(Stack.size()==0){
						break;
					}
					current = Stack.get(Stack.size()-1);
				}
				index++;
			}
			//if current is the destination, then we are done
			if(current==destination){
				break;
			}
		}
		return Stack;
	}
	
	public static ArrayList<Edge> pathToEdges(ArrayList<Integer> path){
		ArrayList<Edge> edgeArrayList = new ArrayList<Edge>();
		Integer temp = path.get(0);
			path.remove(0);
			for (Integer vertex : path){
				Edge edge = new Edge(temp,vertex,0);
				edgeArrayList.add(edge);
				temp = vertex;
			}
			return edgeArrayList;
	}
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260787832"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		WGraph wGraph = new WGraph();

		ArrayList<Edge> toBeRemoved = new ArrayList<Edge>();

		//need second graph for residual graph
		//and will compare with original to get max flow
		for (Edge e : graph.getEdges()){
			Edge newEdge = new Edge(e.nodes[0],e.nodes[1],e.weight);
			wGraph.addEdge(newEdge);
		}


		//get first dfs path
		ArrayList<Integer> path = pathDFS(source,destination,wGraph);

		//while u still return a path from source to dest
		while(path.size()>0){

			int path_flow = Integer.MAX_VALUE;
			ArrayList<Edge> edgeArrayList = pathToEdges(path);
			//go through ever edge nd get the min
			for(Edge edge : edgeArrayList){
				for (Edge ogEdge : wGraph.getEdges()){
					if(ogEdge.nodes[0]==edge.nodes[0]&&ogEdge.nodes[1]==edge.nodes[1]){
						path_flow = Math.min(ogEdge.weight,path_flow);
						break;
					}
				}
			}
			maxFlow = maxFlow + path_flow;
			ArrayList<Edge> newEdges = new ArrayList<Edge>();

			//new edges (in residual graph)
			for (Edge edge : edgeArrayList){
				if(wGraph.getEdge(edge.nodes[0],edge.nodes[1])!=null){
					wGraph.getEdge(edge.nodes[0],edge.nodes[1]).weight -= path_flow;
				}
				if(wGraph.getEdge(edge.nodes[1],edge.nodes[0])==null){
					Edge edge1 = new Edge(edge.nodes[1],edge.nodes[0],path_flow);
					newEdges.add(edge1);
				}
				else if(wGraph.getEdge(edge.nodes[1],edge.nodes[0])!=null){
					wGraph.getEdge(edge.nodes[1],edge.nodes[0]).weight += path_flow;

				}
			}
			for(Edge edge1: newEdges){
				toBeRemoved.add(edge1);
				wGraph.addEdge(edge1);
			}
			Iterator<Edge> edgeIterator = wGraph.getEdges().iterator();
			while(edgeIterator.hasNext()){
				//if a weight is less than or equla to zero, remove it
				Edge edge = edgeIterator.next();
				if(edge.weight<=0){
					edgeIterator.remove();
				}
			}

			path = pathDFS(source, destination, wGraph);
		}

		//comparing residual graph to orinignal graph
		for(Edge edge : wGraph.getEdges()){
			if(graph.getEdge(edge.nodes[1],edge.nodes[0])!=null){
				graph.getEdge(edge.nodes[1],edge.nodes[0]).weight=edge.weight;
			}
			//if they are the same, in residual graph and og, then the flow is zero
			if((graph.getEdge(edge.nodes[0],edge.nodes[1])!=null)&&(graph.getEdge(edge.nodes[0],edge.nodes[1]).weight==edge.weight)){
				graph.getEdge(edge.nodes[0],edge.nodes[1]).weight=0;
			}
		}

		answer += maxFlow + "\n" + graph.toString();
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){

		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
