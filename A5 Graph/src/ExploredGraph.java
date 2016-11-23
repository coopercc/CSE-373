import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

/**
 * Things to add to code: 
 *  + list object thing for OPEN and Closed
 *  + successors method to return all V's that come after current V
 *  
 */

/**
 * @author Cooper Cain
 * 
 * Solution to Assignment 5 in CSE 373, Autumn 2016
 * University of Washington.
 * 
 * (Based on starter code v1.3. By Steve Tanimoto.)
 *
 * Java version 8 or higher is recommended.
 *
 */

// Here is the main application class:
public class ExploredGraph {
	Set<Vertex> Ve; // collection of explored vertices
	Set<Edge> Ee;   // collection of explored edges
	Hashtable<Vertex, Vertex> preds; //vertexes and their predecessor
	
	public ExploredGraph() {
		initialize();
	}

	// sets the collection of vertices and edges as new sets, and creates a new table
	// for the predecessors.
	public void initialize() {
		// Implement this
		Ve = new LinkedHashSet<Vertex>();
		Ee = new LinkedHashSet<Edge>();
		preds = new Hashtable<Vertex, Vertex>();
		
	}
	
	//return number of vertices
	public int nvertices() {
		return Ve.size();
	}
	
	//return number of edges
	public int nedges() {
		return Ee.size();
	}    
	
	/*
	 * Accepts a beginning and end vertex and maps out the vertexes between them 
	 * through depth-first searching
	 */
	public void idfs(Vertex vi, Vertex vj) {
		search(vi, vj, "idfs");
	} 
	
	/* 
	 * Accepts a beginning and end vertex and maps out the vertexes between them 
	 * through Breadth-First Search
	 */
	public void bfs(Vertex vi, Vertex vj) {
		search(vi, vj, "bfs");
	}
	
	/*
	 * Accepts a beginning and end vertex as well as the type of search
	 * and maps the different states until it finds the end vertex
	 */
	public void search(Vertex vi, Vertex vj, String type) {
		initialize();
		LinkedList<Vertex> open = new LinkedList<Vertex>();
		LinkedList<Vertex> closed = new LinkedList<Vertex>();
		open.add(vi);
		while (!open.isEmpty()) {
			Vertex v = open.removeFirst();
			List<Vertex> S = successors(v);
			for (Vertex s : S) {
				if (!open.contains(s) && !closed.contains(s)) {
					if(type.equals("idfs")) {
						open.addFirst(s);
					} else {
						open.addLast(s);
					}
					preds.put(s, v);
					Edge temp = new Edge(v, s);
					Ee.add(temp);
					Vertex pred = preds.get(s);
					System.out.println(s.toString() + " to " + pred.toString());
				}
			}
			closed.add(v);
			Ve.add(v);
			if (v.equals(vj)) {
				open.clear();
			}
		}
	}
	
	/*
	 * accepts a vertex and returns the possible vertexes that can follow it
	 */
	public List<Vertex> successors(Vertex v) {
		Operator[] arr = new Operator[6];
		arr[0] =  new Operator(0, 1);
		arr[1] = new Operator(0, 2);
		arr[2] = new Operator(1, 0);
		arr[3] = new Operator(1, 2);
		arr[4] = new Operator(2, 0);
		arr[5] = new Operator(2, 1);
		
		List<Vertex> ret = new ArrayList<Vertex>();
		for(int i = 0; i < arr.length; i++) {
			Vertex temp = new Vertex(v.toString());
			boolean test = arr[i].precondition(temp);
			if (test) {
				ret.add(arr[i].transition(temp));
			}
		}
		return ret;
		
	}
	
	/*
	 * returns the path between the passed in vertex and the beginning vertex by
	 * searching through the predecessor table
	 */
	public ArrayList<Vertex> retrievePath(Vertex vj) {
		ArrayList<Vertex> ls = new ArrayList<Vertex>();
		Vertex curr = vj;
		ls.add(curr);
		while(preds.containsKey(curr)) {
			ls.add(preds.get(curr));
			curr = preds.get(curr);
		}
		Collections.reverse(ls);
		return ls;
	}
	
	//returns the optimal path by checking bfs then returning retrievePath
	public ArrayList<Vertex> shortestPath(Vertex vi, Vertex vj) {
		bfs(vi,vj);
		return retrievePath(vj);
	} // Implement this.
	
	public Set<Vertex> getVertices() {return Ve;} 
	public Set<Edge> getEdges() {return Ee;} 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExploredGraph eg = new ExploredGraph();
		// Test the vertex constructor: 
		Vertex v0 = eg.new Vertex("[[4,3,2,1],[],[]]");
		Vertex v1 = eg.new Vertex("[[],[],[4,3,2,1]]");
		eg.bfs(v0, v1);
		eg.idfs(v0, v1);
		System.out.println(eg.retrievePath(v1));
		System.out.println(eg.nedges());
		System.out.println(eg.nvertices());
		// Add your own tests here.
		// The autograder code will be used to test your basic functionality later.

	}
	
	/*
	 * holds the state of the Tower game
	 */
	class Vertex {
		ArrayList<Stack<Integer>> pegs; // Each vertex will hold a Towers-of-Hanoi state.
		// There will be 3 pegs in the standard version, but more if you do extra credit option A5E1.
		
		// Constructor that takes a string such as "[[4,3,2,1],[],[]]":
		public Vertex(String vString) {
			String[] parts = vString.split("\\],\\[");
			pegs = new ArrayList<Stack<Integer>>(3);
			for (int i=0; i<3;i++) {
				pegs.add(new Stack<Integer>());
				try {
					parts[i]=parts[i].replaceAll("\\[","");
					parts[i]=parts[i].replaceAll("\\]","");
					List<String> al = new ArrayList<String>(Arrays.asList(parts[i].split(",")));
					
					Iterator<String> it = al.iterator();
					while (it.hasNext()) {
						String item = it.next();
                        if (!item.equals("")) {
                                
                                pegs.get(i).push(Integer.parseInt(item));
                        }
					}
				}
				catch(NumberFormatException nfe) { nfe.printStackTrace(); }
			}		
		}
		
		
		//returns the game state as a string
		public String toString() {
			String ans = "[";
			for (int i=0; i<3; i++) {
			    ans += pegs.get(i).toString().replace(" ", "");
				if (i<2) { ans += ","; }
			}
			ans += "]";
			return ans;
		}
		
		//override the equals method to test if the strings of the vertexes are equal
		@Override
		public boolean equals(Object obj) {
			return toString().equals(obj.toString());
		}
		
		//Overrides the hashcode method to something new
		@Override
		public int hashCode() {
			int hash = 1;
			hash = hash * 13 + pegs.get(0).toString().hashCode();
			hash = hash * 17 + pegs.get(1).toString().hashCode();
			hash = hash * 31 + pegs.get(2).toString().hashCode();
			
			return hash;
		}
		
		
	}
	
	class Edge {
		Vertex endpoint1; // first vertex
		Vertex endpoint2; // second vertex
		
		//initializes the edge
		public Edge(Vertex vi, Vertex vj) {
			// Add whatever you need to here.
			endpoint1 = vi;
			endpoint2 = vj;
		}
		
		//returns the string representing the edge
		public String toString() {
			return "Edge from " + endpoint1.toString() + " to " + endpoint2.toString();
		}
		
		//returns endpoint1
		public Vertex getEndpoint1() {
			return endpoint1;
		}
		
		//returns endpoint2
		public Vertex getEndpoint2() {
			return endpoint2;
		}
	}
	
	//represents a change in the game state
	class Operator {
		private int i, j; // i and j represent the move of a disk from peg i to peg j

		//initializes the move
		public Operator(int i, int j) { // Constructor for operators.
			this.i = i;
			this.j = j;
		}

		//returns whether or not the move from i to j for Vertex v is acceptable or not
		public boolean precondition(Vertex v) {
			Stack<Integer> pegOne = v.pegs.get(i);
			Stack<Integer> pegTwo = v.pegs.get(j);
			if (pegOne.isEmpty()) {
				return false;
			} else if (pegTwo.isEmpty()) {
				return true;
			} else if (pegOne.peek() > pegTwo.peek()) {
				return false;
			} else {
				return true;
			}
		}

		//returns a new vertex after applying the operator transition
		public Vertex transition(Vertex v) {
			Vertex temp = new Vertex(v.toString());
			Stack<Integer> iStack = temp.pegs.get(i);
			Stack<Integer> jStack = temp.pegs.get(j);
			jStack.push(iStack.pop());
			return temp; 
		}

		//returns a string representing the operator move
		public String toString() {
			// TODO: Add code to return a string good enough
			// to distinguish different operators
			return "peg " + i + " to " + j;
		}
	}

}