package week2;

public class LinkedList {
	Node first;
	
	public LinkedList() {
		
	}
	
	public void add(int number) {
		Node n = new Node();
		n.data = number;
		
		// The list is empty
		if (first == null) {
			first = n;
		}
		// The list is not empty
		else {
			Node current = first;
			while (current.next != null) {
				current = current.next;
			}
			current.next = n;
		}
	}
	
	public void print() {
		Node current = first;
		while (current != null) {
			System.out.println(current.data);
			current = current.next;
		}
	}
	
	public int get(int index) {
		Node current = first;
		for (int i = 0 ; i < index ; i++) {
			current = current.next;
		}
		return current.data;	
	}
	
	// Says whether it contains 
	public boolean contains(int data) {
		Node current = first;
		while (current != null) {
			if (current.data == data) {
				return true;
			}
			else {
				current = current.next;
			}
		}
		return false;
	}
	
	// Returns how many nodes are in the list.
	public int size() {
		int count = 0;
		
		Node current = first;
		while (current != null) {
			count = count + 1;
			current = current.next;
		}
		
		return count;
	}
	
	// Hard!
	public void reverse() {
		
	}
	
	// Medium
	public int lastNumber() {
		if (first == null) {
			return 0;
		}
		
		Node current = first;
		while (current.next != null) {
			current = current.next;
		}
		return current.data;
	}
	
	// Easy
	public int sum() {
		int total = 0;
		
		Node current = first;
		while (current != null) {
			total = total + current.data;
			current = current.next;
		}
		
		return total;
	}
}
