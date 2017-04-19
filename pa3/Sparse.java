/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: Sparse.java
 * File Purpose: Main Program
 *7/22/2015
 */

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

public class Sparse {
	
	static Scanner openFileCheck(String filename) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (IOException except) {
			System.err.println("Invalid filename");
			exit(1);
		}
		return scan;
	}

	
	static int integerInput(Scanner file) {
		int length = 0;
		if (file.hasNextInt()) {
			length = file.nextInt();
		} else {
			System.out
					.println("Invalid file input format: needs to have correct integer order");
			exit(1);
		}
		return length;
	}

	
	static void print(Scanner file, PrintWriter writer, String args) {
		int length = integerInput(file);
		Matrix A = new Matrix(length);
		Matrix B = new Matrix(length);
		int Arow = integerInput(file);
		int Brow = integerInput(file);
		file.nextLine(); 
		for (; Arow > 0; Arow--) {
			A.changeEntry(integerInput(file), integerInput(file), file.nextDouble());
		}
		writer.println("A has " + A.getNNZ() + " non-zero entries:");
		writer.println(A);

		// matrix B
		file.nextLine();
		for (; Brow > 0; Brow--) {
			B.changeEntry(integerInput(file), integerInput(file), file.nextDouble());
		}
		writer.println("B has " + B.getNNZ() + " non-zero entries:");
		writer.println(B);

		//all input implementations
		Matrix ScalarA = A.scalarMult(1.5);
		writer.println("(1.5)*A =");
		writer.println(ScalarA);

		Matrix AplusB = A.add(B);
		writer.println("A+B =");
		writer.println(AplusB);

		Matrix AplusA = A.add(A);
		writer.println("A+A =");
		writer.println(AplusA);

		Matrix BminusA = B.sub(A);
		writer.println("B-A =");
		writer.println(BminusA);

		Matrix AminusA = A.sub(A);
		writer.println("A-A =");
		writer.println(AminusA);

		Matrix Atranspose = A.transpose();
		writer.println("Transpose(A) =");
		writer.println(Atranspose);

		Matrix AmultB = A.mult(B);
		writer.println("A*B =");
		writer.println(AmultB);

		Matrix BmultB = B.mult(B);
		writer.println("B*B =");
		writer.println(BmultB);

	}

	public static void main(String[] args) {
		// checks arguments
		if (args.length != 2) {
			System.err.println("Invalid inputs");
			exit(1);
		}
		Scanner input = openFileCheck(args[0]);

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(args[1]));
		} catch (FileNotFoundException excep) {
			System.err.println("Could not create the file");
			exit(1);
		} catch (IOException excep) {
			System.err.println("Could not create the file");
			exit(1);
		}
		print(input, writer, args[0]);
		writer.close();
	}

}