import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathFinder {

	  /**
	   * find the maximum path length in the structure/graph and print
	   * the node label and the length of the path
	   * @param root: node
	   * @return maximum path length (type int)
	   * 			possible values of return value are:
	   * 			>= 0 (valid case of max path length of 0 or more in structure/graph)
	   * 			-1 (indicating a loop in the structure/graph)
	   * 			-2 (otherwise, for example indicating a null value passed for root)
	   */
	  public static int findMaxPathLength(Node root) {
		  int maxPathLength = -2; // initialize the max path length to -2 (return this in case of an error)
		  
		  if (root != null) {
			  try {
				  // create the list of children nodes based on their distance from root
				  ArrayList<ArrayList<Node>> pathList = findPaths(root);
				    
				  if (pathList == null) {
					  return maxPathLength;
				  }
				  
				  if (pathList.isEmpty()) { // pathList is cleared when a loop is found
				      // System.out.println("Structure has loops in it.");
				      return -1; // here return value -1 is used to indicate a loop in the graph
				  }
			
				  // the maximum path length is the maximum index on which a child node is stored in pathList
				  maxPathLength = pathList.size() - 1;

				  //  get the first node that is at the largest distance from root
				  Node mostDistantNode = pathList.get(maxPathLength).get(0);

				  // print the maximum path length and the most distant node from root
				  System.out.println("Maximum path length from root node " + root.getLabel() + 
						  			" to most distant node " + mostDistantNode.getLabel() + " is " + maxPathLength);
				  
			  } catch (Exception ex) {
				  ex.printStackTrace();
				  System.out.println("findMaxPathLength encountered an exception " + ex.toString());
			  }
			  
		  } 
		    
		  return maxPathLength;
	  }

	  /**
	   * Function to construct a list of list of nodes at each distance/length from the root 
	   * for example, nodes that are ith distance away from root are stored at the ith index of the list
	   * @param root: node
	   * @return list of list of children nodes of the root: 
	   * 		  list of all nodes that are i hops away from root is stored at index i in the list
	   * 
	   */
	  private static ArrayList<ArrayList<Node>> findPaths(Node root) {
		  try {
			  /* construct a list of list "pathList" such that all the nodes at distance i from 
			   * root node will get stored in a list at index i in the pathList
			   */
			  ArrayList<ArrayList<Node>> pathList = new ArrayList<ArrayList<Node>>();
			  
			  // add the root node at index 0, indicating that it is at distance 0 from root
			  pathList.add(0, new ArrayList<Node>(Arrays.asList(new Node[] {root})));
			  
			  // add children nodes in pathList at respective positions based on their 
			  // distance from the root node
			  boolean hasLoops = addRootChildren(root, pathList, 1);
			  
			  // if structure has loops, clear the pathList to indicate that
			  if (hasLoops) {
				  System.out.println("The graph has loops");
			      pathList.clear();
			  } else {
				  System.out.println("The graph does not have loops");
			  }
			    
			  return pathList;
			  
		  } catch (Exception ex) {
			  System.out.println("findPaths encountered an exception: " + ex.toString());
			  return null;
		  }
	  }

	  /**
	   * Add child nodes in a list at the index position indicating the 
	   * distance of the node from the root node
	   * The algorithm is to traverse the graph structure in a depth-first manner and visit
	   * nodes on the current path and add them to an indexed structure based on their 
	   * distance from the root. 
	   * A list of visited nodes on a path is maintained to identify loops.
	   * @param node: current node 
	   * @param childList: list of child nodes of node
	   * @param length: distance of the node from the root
	   * @return boolean value indicating a presence/absence of loop
	   */
	  private static boolean addRootChildren(Node node, ArrayList<ArrayList<Node>> childList, int length) throws Exception {
		  try { 
			  List<Node> nodeChildren = node.getChildren();
			  if (nodeChildren != null) { // iterate through all the children of the current node
				  for (Node childNode :  nodeChildren) {
					  if (node == childNode) {
						  return true;
					  }
					  ArrayList<Node> visitedNodes = new ArrayList<Node>(Arrays.asList(new Node[] {node,  childNode}));

					  // add the current child node to the childList
					  addNodeAtIndex(childNode, childList, length);

					  // add children of the current child node on this path to the list
					  try {
				    	  if (addChildren(childNode, childList, length + 1, visitedNodes)) {
				    		  return true;
				    	  }
					  } catch (Exception ex) {
						  System.out.println("addRootChildren encountered an exception: " + ex.toString());
						  throw ex;
					  }
				  }
			  }
			  return false;
			  
		  } catch(Exception ex1) {
			  System.out.println("addRootChildren encountered an exception: " + ex1.toString());
			  throw ex1;
		  }
	  }
	  
	  /**
	   * Auxiliary method to add child nodes in a list at the index position 
	   * indicating the distance of the node from the root node
	   * @param node: current node 
	   * @param childList: list of child nodes of node
	   * @param length: distance of the current node from the root
	   * @param visitedNodes: list of nodes that have already been visited on this path from root
	   * @return boolean value indicating a presence/absence of loop
	   */
	  private static boolean addChildren(Node node, ArrayList<ArrayList<Node>> childList, 
			  								int length, ArrayList<Node> visitedNodes) throws Exception{
		  try {
			  List<Node> nodeChildren = node.getChildren();
			  if (nodeChildren != null) { // if node has children
				  /* 
				   * Check for loops early on by checking whether any of the 
				   * children node has already been visited on this path
				   */
				  for (Node childNode :  nodeChildren) {
					  if (visitedNodes.contains(childNode)) {
						  return true; // loop found
					  }
				  }
				  
				  // iterate through all the children of the current node and add to children list
				  for (Node childNode :  nodeChildren) {
					  // add the child to the childList
					  addNodeAtIndex(childNode, childList, length);
					  
					  // add the child node to list of visited nodes
				      visitedNodes.add(childNode);
					  
				      // recurse through the children of the child node
				      return addChildren(childNode, childList, length + 1, visitedNodes);
				  }
		    } 
			return false; // no loops found
			  
		  } catch (Exception ex) {
			  System.out.println("Exception encountered in addChildren: " + ex.toString());
			  throw ex;
		  }
	  }

	  /**
	   * Add the current node to the list at the index position in the childList
	   * @param node
	   * @param childList
	   * @param index
	   */
	  private static void addNodeAtIndex(Node node, ArrayList<ArrayList<Node>> childList, int index ) {
  	  try {
  		  childList.get(index).add(node);    
	      } catch (Exception ex) { 
	    	  // Exception occurs in case the list is uninitialized
	    	  // Initialize the childList when visiting the first child of the node
	    	  childList.add(index, new ArrayList<Node>(Arrays.asList(new Node[] {node})));
	      }
	  }

}
