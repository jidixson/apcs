import java.util.ArrayList;

public class Node {
	ArrayList <String> letters;
	ArrayList <Node> nodes;
	
	public Node() {
		letters = new ArrayList <String> (); 
		nodes = new ArrayList <Node> ();
	}
	
	public void add(String letter, Node node) {
		letters.add(letter);
		nodes.add(node);
	}
	
	public boolean contains(String letter) {
		return letters.contains(letter);
	}
	
	public Node get(String letter) {
		return nodes.get(letters.indexOf(letter));
	}
}
