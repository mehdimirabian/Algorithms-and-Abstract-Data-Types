/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: ListTest.java
 * File Purpose: testing List.java
 *7/2/2015
 */

public class ListTest{
    static class Entry{
	int column;
	double value;
	Entry(int col, double val){
	    column = col;
	    value = val;
	}

	public boolean equals(Object object){
	    if (object == null || !(object instanceof Entry)) {
		return false;
	    }
	    return (((Entry)object).value == value);
	}

	public String toString(){
	    return "" + column;
	}
    }

    public static void main(String[] args){
	List A = new List();
	List C = new List();
	List B = new List();
      
	for(int i=1; i<=20; i++){
	    A.append(new Entry(i, (double)i));
	    C.append(new Entry(i, (double)i));
	    B.prepend(new Entry(i, (double)i));
	}
	System.out.println(A);
	System.out.println(B);
      
	for(A.moveFront(); A.index()>=0; A.moveNext()){
	    System.out.print(A.get()+" ");
	}
	System.out.println();
	for(B.moveBack(); B.index()>=0; B.movePrev()){
	    System.out.print(B.get()+" ");
	}

	System.out.println();
	
	System.out.println(A.equals(B));
	System.out.println(B.equals(C));
	System.out.println(C.equals(A));
	A.moveFront();
	for(int i = 0; i<6; i++) A.moveNext();
	A.insertBefore(-1);
	A.moveFront();
	for(int i = 0; i<16; i++) A.moveNext();
	A.insertAfter(-2);
	A.moveFront();
	for(int i = 0; i<11; i++) A.moveNext();
	A.delete();
	System.out.println(A);
	A.clear();
	System.out.println(A.length());
    }
}