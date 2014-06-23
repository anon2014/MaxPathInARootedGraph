
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class GenerateGraph {

	/**
	 * Generates a graph structure of nodes from the given input adjacency list
	 * @param rootLabel: label for the root node
	 * @param graph: Mapping between node labels representing the adjacency list for the graph
	 * @return the root node
	 */
	public Node GenerateGraphWithRoot(String rootLabel, Map<String, List<String>> graph) {

		if ((rootLabel == null) || (graph == null)) {
			System.out.println("Root is null or graph is null, check input");
			return null;
		}
		
		rootLabel = rootLabel.trim(); // trim leading and trailing whitespaces from root label
		
		if (rootLabel.isEmpty() || (rootLabel.length() <= 0)) {
			System.out.println("Root Label is an empty string");
			return null;
		}
		
		// Create set of nodes
		Set<Node> nodes = new HashSet<Node>();

		// Create root node with given label;
		Node rootNode = new Node(rootLabel);
		nodes.add(rootNode);
		
		// create nodes for given labels in the input and add to set of nodes
		for (String nodeLabel: graph.keySet()) {
			if (!nodeLabel.equalsIgnoreCase(rootLabel)) {
				nodes.add(new Node(nodeLabel));
			}
		}

		// Create edges between nodes
		for (Map.Entry<String, List<String>> entry: graph.entrySet()) {

			String nodeLabel = entry.getKey();
			try {
				// get the node with the given label
				Node node = getNode(nodes, nodeLabel);
				if (node != null) {
					List<String> nodeChildrenLabels = entry.getValue();
					for (String childLabel: nodeChildrenLabels) {
						//System.out.println("Edge: node " + nodeLabel + " --> node " + childLabel);

						try {
							Node childNode = getNode(nodes, childLabel);
							if (childNode == null) {
								// System.out.println("Found null node, creating new node with label: " + childLabel);

								childNode = new Node(childLabel);
								nodes.add(childNode);
							}
							node.addChildNode(childNode);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}	
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return rootNode;
	}
	
	/**
	 * Search for a node with a given label in the input set of nodes
	 * @param nodes: Set of nodes
	 * @param label: given label
	 * @return node with given label in the set (if found), otherwise null
	 */
	private Node getNode(Set<Node> nodes, String label) {
		// Iterate through the set of nodes to search for node with given label
		for (Node n: nodes) {
			if (n.getLabel().equalsIgnoreCase(label)) {
				return n;
			}
		}
		return null;
	}
	
}
