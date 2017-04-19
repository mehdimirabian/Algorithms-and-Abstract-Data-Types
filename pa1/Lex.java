/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: Lex.java
 * File Purpose: Main Class
 *7/2/2015
 */

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class Lex {

	//opens the file
	static Scanner open(String filename) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (IOException except) {
			System.err.println("Invalid filename");
			exit(1);
		}
		return scan;
	}

	// counts the lines
	static int lineCount(Scanner file) {
		int index = 0;
		for (; file.hasNextLine(); file.nextLine())
			index++;
		return index;
	}

	// reads each line and stores it in a String array
	static String[] read(Scanner file, int number) {
		String[] str = new String[number];
		for (int i = 0; file.hasNextLine(); i++) {
			str[i] = file.nextLine();
		}
		return str;
	}

	// insertion sort
	static List insertionSort(String[] file) {
	int i;
		List L = new List();
		// puts in the first element into the list
		if (file.length > 0)
			L.append(0);
		// the sorting
		for (int j = 1; j < file.length; j++) {
			String temp = file[j];
			int index = j - 1;
			// move cursor to current position
			L.moveFront();
			for(i=0; i<index; i++){
				L.moveNext();
			}
			
			while (index > -1 && temp.compareTo(file[L.get()]) < 1) {
				index--;
				L.movePrev();
			}

			// if cursor is null, then prepend
			// otherwise insert after it
			if (L.index() == -1) {
				L.prepend(j);
			} else {
				L.insertAfter(j);
			}
		}

		return L;
	}

	public static void main(String[] args) {
		// checking runtime arguments
		if (args.length != 2) {
			System.err.println("Invalid inputs");
			exit(1);
		}

		
		Scanner input = open(args[0]);
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
		int linecount = lineCount(input);
		input = open(args[0]);
		String[] array = read(input, linecount);
		List list = insertionSort(array);
		for (list.moveFront(); list.index() >= 0; list.moveNext()) {
			writer.println(array[list.get()]);
		}

		writer.close();

	}

}
