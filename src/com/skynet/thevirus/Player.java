package com.skynet.thevirus;
import java.util.*;
import java.io.*;
import java.math.*;
import java.nio.file.NotDirectoryException;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    private int N; // Number total of node
    private int L; // Number total of Links
    private int E; // Number total of Exit Gateway
    // Array to stock links
    private Map<Integer, ArrayList<Integer>> links = new HashMap<>();
    private ArrayList<Integer> gateways = new ArrayList();

    // Constructor
    Player(int n, int l, int e){
    	System.err.println("Player construction N :" + n + " L:" + l + " E: " + e);
        this.N = n;
        this.L = l;
        this.E = e;
        
        // Initial Links
        for(int i = 0; i< N; i++){
           	links.put(i, new ArrayList<Integer>());                   	
        }        
    }

    public void addEdge(int N1, int N2){
        // set to map
        links.get(N1).add(N2);
        links.get(N2).add(N1);                	
    }
    
    public void addGateway(int EI){
    	gateways.add(EI);
    }
    
    /**
     * Cut a link
     * @param from
     * @param to
     */
    public void cut(int from, int to){
		// Cut SI and first linksSI
		System.err.println("Cut " +  from + " " + to);
		links.get(from).remove(to);
		links.get(to).remove(from);
		System.out.println(from + " " + to);    	
    }
    
    public void solve(final int SI){
		System.err.println("Begin Solve");
        Set<Integer> parents = new HashSet<>();
        int distance = 0;
        
        // Add all gateways
        parents.addAll(gateways);
        
        while(true && distance < 3){
	        Set<Integer> temps = new HashSet<Integer>();
	        
	        // Get all next level of parents
	        Iterator iterator = parents.iterator();
	        
	        // Increment distance 
	        distance++;
	        while(iterator.hasNext()){
	        	temps.addAll(links.get(iterator.next()));        	
	        }
	        
	    	// if found SI
	    	if(temps.contains(SI)){
	    		// Get all connect with SI link to parents
	    		ArrayList<Integer> linksSI = links.get(SI);
	    		linksSI.retainAll(parents);
	    		System.err.println("Solve " + linksSI);
	    		cut(SI, linksSI.get(0));
	    		System.err.println("End Solve");
	    		return;
	    	}
	    	parents = temps;
        }
        // If Skynet Agent is still far away from exit gateway 
        // Then cut node before SI arrive
        if(distance < 3){
        	Iterator<Integer> g = gateways.iterator();
        	while(g.hasNext()){
        		int gate = g.next();
        		ArrayList<Integer> l = links.get(gate);
        		if(l.size() > 0){
        			cut(gate, l.get(0));
        		}
        	}
        }
    }
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        
        Player player = new Player(N, L, E);
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            
            player.addEdge(N1, N2);
        }
        
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            player.addGateway(EI);
        }
                
        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            player.solve(SI);	
            // Example: 0 1 are the indices of the nodes you wish to sever the link between
           // System.out.println("0 1");
        }
    }
    
}