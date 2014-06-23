
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Node {
	  private String label; 		// String label associated with the node
	  private List<Node> children; 	// list of children of the node 
  
	  /**
	   * Constructor: create a node with a label and empty children list
	   * @param name: String label for the node
	   */
	  public Node(String name) {  
	    label = name;
	    children = null;
	  }

	 /**
	  * Constructor: create a node with the given label and given children list
	  * @param name: String label for the node
	  * @param childrenList: list of children nodes 
	   */
	  public Node(String name, List<Node> childrenList) { 
	    label = name;
	    children = childrenList;
	  }

	  /**
	   * Adds a node to the list of children nodes
	   * @param childNode: node to be added as a child
	   */
	  public void addChildNode(Node childNode) {
	    if (children == null) {
	    	children = new ArrayList<Node>(Arrays.asList(new Node[] {childNode}));
	    } else {
	    	children.add(childNode);
	    }
	  }
	  
	  /**
	   * get the node label
	   * @return string label of the node
	   */
	  public String getLabel() {
		  return label;
	  }
	  
	  /**
	   * get the children nodes
	   * @return list of children nodes
	   */
	  public List<Node> getChildren() {
		  return children;
	  }
	  
}
