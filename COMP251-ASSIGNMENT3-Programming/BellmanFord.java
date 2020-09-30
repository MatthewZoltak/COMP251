
public class BellmanFord{

	
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;


    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */
        
        /* YOUR CODE GOES HERE */

        this.source = source;
        int nodes1[] = new int[g.getNbNodes()];


        //create an int array of all the possible nodes
        for (Edge edge : g.getEdges()){
            int tempNode1 = edge.nodes[0];
            int tempNode2 = edge.nodes[1];
            if (nodes1[tempNode1]!=tempNode1){
                nodes1[tempNode1]=tempNode1;
            }
            if (nodes1[tempNode2]!=tempNode2){
                nodes1[tempNode2]=tempNode2;
            }
        }
        int maxWeight = 1000000;

        //initialze the distances[] and predecesors[] to size of the number nodes
        this.distances = new int[nodes1.length];
        this.predecessors = new int[nodes1.length];

        //since could not use inifite, used a max weight of 1000000
        //initially used Integer.MAX_VALUE, however, this led to numbers wrapping around
        //and getting -Integer.MAX_VALUE which is not what I wanted

        for(int i = 0; i< nodes1.length;i++){
            this.distances[i] = maxWeight;
            this.predecessors[i] = i;
        }

        //distance of source is always zero
        this.distances[this.source] = 0;


            //relaxing edges
            for (int i = 0; i < nodes1.length; i++){
                for (Edge edge : g.getEdges()){
                    if (edge.nodes[0]==i){
                        if((this.distances[i]+edge.weight)<this.distances[edge.nodes[1]]){
                            this.distances[edge.nodes[1]] = this.distances[i] + edge.weight;
                            predecessors[edge.nodes[1]]=i;
                        }
                    }
                }
            }

            //if we can relax an edge again after going through all the edges, it means there is a negative weighted cycle
            //and should throw exception
            for (Edge edge : g.getEdges()){
                if((this.distances[edge.nodes[0]]+edge.weight)<this.distances[edge.nodes[1]]){
                    throw new NegativeWeightException();
                }
            }




    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */

        //create empty array that will hold the path
        int path[] = new int[this.predecessors.length];
        for(int i = 0; i < path.length; i++){
            path[i] = 1000000;
        }
        //current is the destination
        int current = destination;
        path[path.length-1] = current;
        int index = 2;

            //checking all predecors of destination
            //if we have reached the source, then we return
            //if we have reached trhe end of the array, or that the predessor of one of the
            //nodes that we reach has itself as a predecor, that means there is no path to it
            //and we throw exception

            while(current!=source){
                if (path.length-index<0||this.predecessors[current]==current){
                    throw new PathDoesNotExistException();
                }
                path[path.length-index] = this.predecessors[current];
                current = this.predecessors[current];
                index++;
            }



        //need to know size of new array (i know this could've been done better)
        int newPathLength = 0;
        for (int i = 0; i < path.length; i++){
            if(path[i]==source){
                newPathLength = path.length-i;
                break;
            }
        }

        //create path without the 1000000 value
        int newPathIndex = 0;
        int newPath[] = new int[newPathLength];
        for(int i = 0; i<path.length;i++){
            if (path[i]==1000000){
                continue;
            }
            else{
                newPath[newPathIndex] = path[i];
                newPathIndex++;
            }
        }

        return newPath;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        String file = args[0];

        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }
}
