package myQAGRTP;

import java.util.ArrayList;

import myQAGRTP.ChangedObjectsSelect2;


public class AnotherClass {
    public static void main(String[] args) {
        ChangedObjectsSelect2.runCodes();

        // Use the changedObjects list for further computations
        ArrayList<Integer> changedObjects = ChangedObjectsSelect2.getChangedObjects(); // Assuming a getter method is added
        ArrayList<Integer> changedObjects1 = new ArrayList<>();
	// ... perform calculations with changedObjects
   	  changedObjects1.add(1); changedObjects1.add(3); changedObjects1.add(5);
	 changedObjects1.add(7); changedObjects1.add(9);
	 System.out.println(changedObjects1);
        System.out.println(changedObjects);
    }
}