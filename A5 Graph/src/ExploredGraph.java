import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
 * @author your name(s) here.
 * Extra Credit Options Implemented, if any:  (mention them here.)
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
	
	public ExploredGraph() {
		initialize();
	}

	public void initialize() {
		// Implement this
		Ve = new LinkedHashSet<Vertex>();
		Ee = new LinkedHashSet<Edge>();
	}
	
	public int nvertices() {
		return Ve.size();
	}
	
	public int nedges() {
		return Ee.size();
	}    
	
	// Implement this. (Iterative Depth-First Search)
	/*
	 * 
	 * Procedure IDFS(v0, g):
Set count = 0.
Let OPEN = [v0]; Let CLOSED = []
Set Pred(v0) = null;
While OPEN is not empty:
   v = OPEN.removeFirst()
   Set Label(v) = count; count += 1.
   S = successors(v);
   For s in S:  
      if s in OPEN or s in CLOSED, continue.
      else insert s into OPEN at the front.
              Set Pred(s) = v.
   Insert v into CLOSED
	 */
	public void idfs(Vertex vi, Vertex vj) {
		search(vi, vj, "idfs");
	} 
	
	// Implement this. (Breadth-First Search)
	//order to test 0,1 -> 0,2 -> 1,0 -> 1,2 -> 2,0 -> 2,1
	public void bfs(Vertex vi, Vertex vj) {
		search(vi, vj, "bfs");
	}
	
	public void search(Vertex vi, Vertex vj, String type) {
		int count = 0;
		LinkedList<Vertex> open = new LinkedList<Vertex>();
		LinkedList<Vertex> closed = new LinkedList<Vertex>();
		open.add(vi);
		vi.PRED = null;
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
					s.PRED = v;
					Edge temp = new Edge(v, s);
					Ee.add(temp);
				}
				if (s.equals(vj)) {
					open.clear();
				}
			}
			closed.add(v);
			Ve.add(v);
		}
	}
	
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
			boolean test = arr[i].precondition(v);
			if (test) {
				ret.add(arr[i].transition(v));
			}
		}
		return ret;
		
	}
	
	public ArrayList<Vertex> retrievePath(Vertex vi) {
		return null;
	}
	
	public ArrayList<Vertex> shortestPath(Vertex vi, Vertex vj) {
		return null;
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
		System.out.println(v0);
		// Add your own tests here.
		// The autograder code will be used to test your basic functionality later.

	}
	
	class Vertex {
		ArrayList<Stack<Integer>> pegs; // Each vertex will hold a Towers-of-Hanoi state.
		Vertex PRED;
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
					System.out.println("ArrayList al is: "+al);
					Iterator<String> it = al.iterator();
					while (it.hasNext()) {
						String item = it.next();
                        if (!item.equals("")) {
                                System.out.println("item is: "+item);
                                pegs.get(i).push(Integer.parseInt(item));
                        }
					}
				}
				catch(NumberFormatException nfe) { nfe.printStackTrace(); }
			}		
		}
		public String toString() {
			String ans = "[";
			for (int i=0; i<3; i++) {
			    ans += pegs.get(i).toString().replace(" ", "");
				if (i<2) { ans += ","; }
			}
			ans += "]";
			return ans;
		}
	}
	
	class Edge {
		Vertex endpoint1;
		Vertex endpoint2;
		
		public Edge(Vertex vi, Vertex vj) {
			// Add whatever you need to here.
			endpoint1 = vi;
			endpoint2 = vj;
		}
		
		public String toString() {
			return "Edge from " + endpoint1.toString() + " to " + endpoint2.toString();
		}
		
		public Vertex getEndpoint1() {
			return endpoint1;
		}
		
		public Vertex getEndpoint2() {
			return endpoint2;
		}
	}
	
	class Operator {
		private int i, j;

		public Operator(int i, int j) { // Constructor for operators.
			this.i = i;
			this.j = j;
		}

		public boolean precondition(Vertex v) {
			Integer iVar = v.pegs.get(i).peek();
			Integer jVar = v.pegs.get(j).peek();		
			if (iVar > jVar || iVar == null) {
				return false;
			} else {
				return true;
			}
		}

		public Vertex transition(Vertex v) {
			Stack<Integer> iStack = v.pegs.get(i);
			Stack<Integer> jStack = v.pegs.get(j);
			jStack.push(iStack.pop());
			return v; 
		}

		public String toString() {
			// TODO: Add code to return a string good enough
			// to distinguish different operators
			return "peg " + i + " to " + j;
		}
	}

}