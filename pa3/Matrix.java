/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: Matrix.java
 * File Purpose: ADT
 *7/22/2015
 */

public class Matrix {

	private class Slot {
		int col;
		double val;

		// Slot Constructor
		Slot(int column, double value) {
			col = column;
			val = value;
		}

		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof Slot)) {
				System.out.println("incoract type");
				return false;
			}
			return ((Slot) obj).val == val && ((Slot) obj).col == col;
		}

		public String toString() {
			return "(" + col + ", " + val + ")";
		}

	}

	private List[] matrix;
	private int NNZ;

	// Constructor
	Matrix(int n) { // Makes a new n x n zero Matrix. pre: n>=1
		if (n < 1) {
			System.out.println("Please enter a valid matrix size");
			return;
		}
		matrix = new List[n];
		for (int i = 0; i < n; i++)
			matrix[i] = new List();
		NNZ = 0;
	}

	int getSize() { // Returns n, the number of rows and columns of this Matrix
		return matrix.length;
	}

	// Returns the number of non-zero entries in this Matrix
	public int getNNZ() {
		return NNZ;
	}

	// overrides Object's equals() method
	public boolean equals(Object x) {
		// checks for correct parameters
		if (x == null || !(x instanceof Matrix)) {
			System.out.println("Matrix:equals: incorrect parameters");
			return false;
		}
		Matrix temp = (Matrix) x;
		// checks for equal lengths
		if (temp.getSize() != matrix.length) {
			return false;
		}
		// compares list entries
		for (int i = 0; i < matrix.length; i++) {
			if (!(matrix[i].equals(temp.matrix[i]))) {
				return false;
			}
		}
		return true;
	}

	// sets this Matrix to the zero state
	public void makeZero() {
		// clears all the lists
		for (int i = 0; i < matrix.length; i++) {
			matrix[i].clear();
		}
		NNZ = 0;
	}

	// returns a new Matrix having the same entries as this Matrix
	// it is equivalent to scalarMult(1);
	public Matrix copy() {
		// if scalar is 0 then everything is 0
		// iterates over the rows
		Matrix copy = new Matrix(matrix.length);
		for (int row = 0; row < matrix.length; row++) {
			List old = matrix[row];
			List fresh = copy.matrix[row];
			for (old.moveFront(); old.index() >= 0; old.moveNext()) {
				Object object = old.get();
				// checks to make sure objects in list are of type Entry
				if (object == null || !(object instanceof Slot)) {
					System.out.println("Matrix:copy: Not correct type");
					return null;
				}
				Slot ij = (Slot) object;

				// copies over the column and value
				Slot ijcopy = new Slot(ij.col, ij.val);
				fresh.append(ijcopy);
			}
		}
		// number of non-zero entries will be the same
		copy.NNZ = NNZ;
		return copy;
	}

	// changes ith row, jth column of this Matrix to x
	public void changeEntry(int i, int j, double x) {
		if ((i + j) < 2 || (i + j) > (2 * matrix.length)) {
			System.out
					.println("Matrix: changeEntry: Indexes are out of bounds");
			return;
		}
		List row = matrix[i - 1];
		if (row == null)
			System.out.println("Matrix:ChangeEntry: row is Null");
		Slot ij = null;
		// iterates over columns of "row" (list)
		for (row.moveFront(); row.index() >= 0; row.moveNext()) {
			Object object = row.get();
			// checks to make sure objects in list are of type Entry
			if (object == null || !(object instanceof Slot)) {
				System.out.println("Matrix:changeEntry: Not correct types");
				return;
			}
			ij = (Slot) object;
			if (ij.col < j)
				continue;
			break;
		}

		// if the index doesn't exist and x isn't 0
		if (row.index() == -1 && x != 0) {
			row.append(new Slot(j, x));
			NNZ++;
		} else if (ij != null && ij.col == j) {
			// if new value is 0 and the node exists elminate it
			// and decrement NNZ otherwise add it
			if (x == 0) {
				row.delete();
				NNZ--;
			} else {
				ij.val = x;
			}
		} else {
			if (x != 0) {
				row.insertBefore(new Slot(j, x));
				NNZ++;
			}
		}

	}

	// returns the scalar product of this Matrix and x
	public Matrix scalarMult(double x) {
		// if scalar is 0 then everything is 0
		if (x == 0)
			this.makeZero();
		// iterates over the rows
		Matrix copy = new Matrix(matrix.length);
		for (int row = 0; row < matrix.length; row++) {
			List old = matrix[row];
			List fresh = copy.matrix[row];
			for (old.moveFront(); old.index() >= 0; old.moveNext()) {
				Object object = old.get();
				// checks to make sure objects in list are of type Entry
				if (object == null || !(object instanceof Slot)) {
					System.out.println("Matrix:copy: Not correct type");
					return null;
				}
				Slot ij = (Slot) object;

				// copies over the column and value * scalar
				Slot ijcopy = new Slot(ij.col, (ij.val) * x);
				fresh.append(ijcopy);
			}
		}
		// number of non-zero entries will be the same
		copy.NNZ = NNZ;
		return copy;
	}

	// returns a new Matrix that is the sum of this Matrix with M
	// It first checks if the Matrix passed is the same matrix.
	// If it is, simply return the scalarMult(2) reducing to theta(n + a)
	// Otherwise add up columns from least to greatest. This is done using
	// a variation of the merging algorithm from merge sort. By iterating
	// over each row (n times) and accessing each element in both lists
	// (a + b) times, the order of this function is theta(n + a + b).
	public Matrix add(Matrix M) {
		if (matrix.length != M.matrix.length) {
			System.out.println("Matrix:add: different matrix sizes");
			return null;
		}
		// if it is the exact same matrix then just mutiply it by 2.
		// Improves efficiency for such cases
		// to theta(n+a) instead of theta(n+a+b);
		if (M == this)
			return this.scalarMult(2);
		Matrix copy = new Matrix(matrix.length);
		// for loop that iterates over rows
		for (int row = 0; row < matrix.length; row++) {
			List one = matrix[row];
			List two = M.matrix[row];
			List sum = copy.matrix[row];

			// this algorithm does addition much like
			// the "merging" process in merge sort
			one.moveFront();
			two.moveFront();
			// iterates through each list
			while (one.index() >= 0 && two.index() >= 0) {
				Slot r = (Slot) (one.get());
				Slot l = (Slot) (two.get());
				copy.NNZ++;
				if (r.col == l.col) {
					if (r.val + l.val != 0) {
						sum.append(new Slot(r.col, r.val + l.val));
					} else {
						NNZ--; // decrement of 0 result
					}
					one.moveNext();
					two.moveNext();
				} else if (r.col < l.col) {
					sum.append(new Slot(r.col, r.val));
					one.moveNext();
				} else {
					sum.append(new Slot(l.col, l.val));
					two.moveNext();
				}
			}

			// if both cursors are OFF then exit the loop
			if (one.index() == two.index())
				continue;
			// if either cursor is still ON then append the rest
			List rest = (one.index() >= 0) ? one : two;
			while (rest.index() >= 0) {
				Slot fin = (Slot) (rest.get());
				copy.NNZ++;
				sum.append(new Slot(fin.col, fin.val));
				rest.moveNext();
			}

		}
		return copy;
	}

	// returns a new Matrix that is the difference of this Matrix with M
	// It first checks if the Matrix passed is the same matrix.
	// If it is, simply return the scalarMult(2) reducing to theta(n + a)
	// Otherwise subtract the columns from least to greatest. This is done using
	// a variation of the merging algorithm from merge sort. By iterating
	// over each row (n times) and accessing each element in both lists
	// (a + b) times, the order of this function is theta(n + a + b).
	public Matrix sub(Matrix M) {
		if (matrix.length != M.matrix.length) {
			System.out.println("Matrix:sub: different matrix sizes");
			return null;
		}
		// if it is the exact same matrix then return the 0 matrix
		if (M == this)
			return new Matrix(matrix.length);

		Matrix copy = new Matrix(matrix.length);
		// for loop that iterates over rows
		for (int row = 0; row < matrix.length; row++) {
			List one = matrix[row];
			List two = M.matrix[row];
			List sum = copy.matrix[row];

			// this algorithm does subtraction much like
			// the "merging" process in merge sort
			// the values in two will be subtracted
			// the values in one will be added
			one.moveFront();
			two.moveFront();
			while (one.index() >= 0 && two.index() >= 0) {
				Slot r = (Slot) (one.get());
				Slot l = (Slot) (two.get());
				copy.NNZ++;
				if (r.col == l.col) {
					if (r.val - l.val != 0) {
						sum.append(new Slot(r.col, r.val - l.val));
					} else {
						NNZ--; // decrement if 0 result
					}
					one.moveNext();
					two.moveNext();
				} else if (r.col < l.col) {
					sum.append(new Slot(r.col, r.val));
					one.moveNext();
				} else {
					sum.append(new Slot(l.col, 0 - l.val));
					two.moveNext();
				}
			}

			// if both cursors are OFF then exit the loop
			if (one.index() == two.index())
				continue;
			// if either cursor is still ON then append the rest
			List rest = (one.index() >= 0) ? one : two;
			int sign = (one.index() >= 0) ? 1 : -1;
			while (rest.index() >= 0) {
				Slot fin = (Slot) (rest.get());
				copy.NNZ++;
				sum.append(new Slot(fin.col, sign * fin.val));
				rest.moveNext();
			}

		}
		return copy;
	}

	// returns the transpose of the matrix
	public Matrix transpose() {
		Matrix copy = new Matrix(matrix.length);
		// iterates over current matrix rows
		for (int i = 0; i < matrix.length; i++) {
			List row = matrix[i];
			for (row.moveFront(); row.index() >= 0; row.moveNext()) {
				Slot entry = (Slot) (row.get());
				// converts column indexes into row indexes
				int line = entry.col - 1;
				copy.matrix[line].append(new Slot(i + 1, entry.val));
			}
		}
		copy.NNZ = NNZ;
		return copy;
	}

	// returns a new Matrix that is the product of this matrix with M
	public Matrix mult(Matrix prod) {
		if (matrix.length != prod.matrix.length) {
			System.out.println("Matrix:mult: invalid size parameter");
			return null;
		}

		Matrix copy = new Matrix(matrix.length);
		// if either matrices are the 0 matrix just return a 0 matrix
		if (NNZ == 0 || prod.NNZ == 0)
			return copy;
		// transposes a matrix to carry row to row vector multiplication
		Matrix trans = prod.transpose();
		for (int i = 0; i < matrix.length; i++) {
			// if the list length is 0 skip
			if (matrix[i].length() == 0)
				continue;
			for (int j = 0; j < matrix.length; j++) {
				if (trans.matrix[j].length() == 0)
					continue;
				double result = dot(matrix[i], trans.matrix[j]);
				if (result != 0) {
					copy.matrix[i].append(new Slot(j + 1, result));
					copy.NNZ++;
				}
			}
		}
		return copy;

	}

	// executes a vector dot product on two lists
	private static double dot(List P, List Q) {
		double result = 0;
		P.moveFront();
		Q.moveFront();
		while (P.index() >= 0 && Q.index() >= 0) {
			Slot p = (Slot) (P.get());
			Slot q = (Slot) (Q.get());
			if (p.col == q.col) {
				result += p.val * q.val;
				P.moveNext();
				Q.moveNext();
			} else if (p.col < q.col) {
				P.moveNext();
			} else {
				Q.moveNext();
			}
		}
		return result;
	}

	// to string method for Matrix
	public String toString() {
		String ret = "";
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i].length() > 0) {
				ret += (i + 1) + ": " + matrix[i] + "\n";
			}

		}
		return ret;
	}

}
