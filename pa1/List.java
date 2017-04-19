/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: List.java
 * File Purpose: ADT
 *7/2/2015
 */
public class List {

	// private inner Node class that defines a Node inside the list
	private class Node {
		int data;
		Node next;
		Node previous;

		// constructor for Node class
		Node(int givenData) {
			data = givenData;
			next = null;
			previous = null;
		}

		Node(int givenData, Node pre, Node nxt) {
			data = givenData;
			previous = pre;
			next = nxt;
		}

		public String toString() {
			return "" + data;
		}
	}

	// class List fields
	private Node front;
	private Node back;
	private Node cursor;
	private int length;

	// private int index;

	// class List Constructor
	List() {
		front = null;
		back = null;
		cursor = null;
		length = 0;
		// index = -1;
	}

	// Access functions
	int length() { // Returns the number of elements in this List.
		return length;

	}

	int index() { // If cursor is defined, returns the index of the cursor
					// element,
		// otherwise returns -1.
		int index = -1;
		if (cursor == null)
			return index;
		for (Node tmp = front; tmp != null; tmp = tmp.next) {
			index++;
			if (tmp == cursor)
				break;
		}
		return index;
	}

	int front() { // Returns front element. Pre: length()>0
		if (length() > 0) {
			return front.data;
		}
		return -1;
	}

	int back() { // Returns back element. Pre: length()>0
		if (length() > 0) {
			return back.data;
		}
		return -1;
	}

	int get() { // Returns cursor element. Pre: length()>0, index()>=0
		if (length() > 0 && index() >= 0)
			return cursor.data;
		return -1;
	}

	boolean equals(List L) { // Returns true if this List and L are the same
								// integer
		// sequence. The cursor is ignored in both lists.
		Node myNode = front;
		Node givenNode = L.front;

		while (myNode != null && givenNode != null) {
			if (myNode.data != givenNode.data) {
				return false;
			}
			myNode = myNode.next;
			givenNode = givenNode.next;
		}
		if (myNode != null || givenNode != null) {
			return false;
		}

		else {
			return true;
		}
	}

	// Manipulation procedures
	void clear() { // Resets this List to its original empty state.
		cursor = null;
		front = null;
		back = null;
		length = 0;

	}

	void moveFront() { // If List is non-empty, places the cursor under the
						// front element,
		// otherwise does nothing.
		if (length > 0) {
			cursor = front;
		}
	}

	void moveBack() { // If List is non-empty, places the cursor under the back
						// element,
		// otherwise does nothing.
		if (length > 0) {
			cursor = back;
		}
	}

	void movePrev() { // If cursor is defined and not at front, moves cursor one
						// step toward
		// front of this List, if cursor is defined and at front, cursor becomes
		// undefined, if cursor is undefined does nothing.
		int index = index();
		index = index();
		if (cursor != null) {
			if (cursor != front) {
				cursor = cursor.previous;
			} else if (cursor == front) {
				cursor = null;
			}
		}
	}

	void moveNext() { // If cursor is defined and not at back, moves cursor one
						// step toward
		// back of this List, if cursor is defined and at back, cursor becomes
		// undefined, if cursor is undefined does nothing.
		int index = index();
		if (cursor != null) {
			if (cursor != back) {
				cursor = cursor.next;
			} else if (cursor == back) {
				cursor = null;
			}
		}
	}

	void prepend(int data) { // Insert new element into this List. If List is
								// non-empty,
		// insertion takes place before front element.
		length++;
		Node N = new Node(data, null, front);
		if (front != null) {
			front.previous = N;
		} else {
			back = N;
		}
		front = N;
	}

	void append(int data) { // Insert new element into this List. If List is
							// non-empty,
		// insertion takes place after back element.
		length++;
		Node N = new Node(data, back, null);
		if (back != null) {
			back.next = N;
		} else {
			front = N;
		}
		back = N;
	}

	void insertBefore(int data) { // Insert new element before cursor.
		// Pre: length()>0, index()>=0
		length++;
		if (index() >= 0 && length() >= 0) {
			Node N = new Node(data, cursor.previous, cursor);
			if (cursor.previous != null) {
				cursor.previous.next = N;
			} else {
				front = N;
			}

			cursor.previous = N;
		}
	}

	void insertAfter(int data) { // Inserts new element after cursor.
		// Pre: length()>0, index()>=0
		length++;
		if (index() >= 0 && length() >= 0) {
			Node N = new Node(data, cursor, cursor.next);
			if (cursor.next != null) {
				cursor.next.previous = N;
			} else {
				back = N;
			}

			cursor.next = N;
		}
	}

	void deleteFront() { // Deletes the front element. Pre: length()>0
		length--;
		if (length() > 0) {
			if (cursor == front)
				cursor = null;
			front = front.next;
			front.previous = null;
		}

	}

	void deleteBack() { // Deletes the back element. Pre: length()>0
		length--;
		if (length() > 0) {
			if (cursor == back)
				cursor = null;
			back = back.previous;
			back.next = null;
		}

	}

	void delete() // Deletes cursor element, making
	{
		if (cursor == front) {
			deleteFront();
		} else if (cursor == back) {
			deleteBack();
		}

		if (length() > 0 && index() > 0) {
			length--;
			cursor.next.previous = cursor.previous;
			cursor.previous.next = cursor.next;
			cursor = null;
		}

	}

	// Other methods
	public String toString() { // Overrides Object's toString method. Returns a
								// String
		// representation of this List consisting of a space
		// separated sequence of integers, with front on left.
		String str = "";
		Node temp = front;
		while (temp != null) {
			str += temp + " ";
			temp = temp.next;
		}
		return str;
	}

	List copy() { // Returns a new List representing the same integer sequence
					// as this
		// List. The cursor in the new list is undefined, regardless of the
		// state of the cursor in this List. This List is unchanged.
		List replicate = new List();
		List copy = new List();
		for (Node N = front; N != null; N = N.next) {
			copy.append(N.data);
		}
		return copy;
	}

	List concat(List L) { // Returns a new List which is the concatenation of
		// this list followed by L. The cursor in the new List
		// is undefined, regardless of the states of the cursors
		// in this List and L. The states of this List and L are
		// unchanged.
		List linked = copy();
		for (Node N = L.front; N != null; N = N.next) {
			linked.append(N.data);
		}
		return linked;
	}

	
	
	public void moveTo(int i) {
		if (i >= 0 && i <= length() - 1) {
			cursor = front;
			for (; i > 0; i--)
				cursor = cursor.next;
		} else {
			cursor = null;
		}
	}

}
