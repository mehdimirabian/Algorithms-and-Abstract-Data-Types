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
		Object data;
		Node next;
		Node previous;

		// constructor for Node class
		Node(Object givenData) {
			data = givenData;
			next = null;
			previous = null;
		}

		Node(Object givenData, Node pre, Node nxt) {
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

	Object front() { // Returns front element. Pre: length()>0
		if (length() > 0) {
			return front.data;
		}
		return -1;
	}

	Object back() { // Returns back element. Pre: length()>0
		if (length() > 0) {
			return back.data;
		}
		return -1;
	}

	Object get() { // Returns cursor element. Pre: length()>0, index()>=0
		if (length() > 0 && index() >= 0)
			return cursor.data;
		return -1;
	}

	public boolean equals(Object L) { // Returns true if this List and L are the
										// same
		// integer
		// sequence. The cursor is ignored in both lists.
		Node myNode = front;
		Node givenNode = ((List) L).front;

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

	void prepend(Object data) { // Insert new element into this List. If List is
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

	void append(Object data) { // Insert new element into this List. If List is
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

	void insertBefore(Object data) { // Insert new element before cursor.
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

	void insertAfter(Object data) { // Inserts new element after cursor.
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

}
