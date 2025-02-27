/**
 * A partial implementation of a hash table using chaining (binary search trees)
 *
 *  @author COSC 311, Fall '24
 *  @version (10-29-24)
 *  
 *  Note: You must add methods get, and remove to this class. Also, you must 
 *        replace the call to the preOrder method with a call to the levelOrder method
 *        which you will add to the class BST. The method named 'hash' must be changed
 *        to satisfy the project requirement.
 *   
 */

package sellP5Package;

import java.util.Scanner;

@SuppressWarnings ("unchecked")
public class Hashing <K extends Comparable <K>, V>{
	private final int SIZE= 37;
	private BST<Pair<K,V>> [] table;
	
	// constructor 
	public Hashing () {
		//table = (BST<Pair<K,V>> [])new Object [SIZE];  //*** this doesn't work!
		table = new BST[SIZE];
	}

	// hash the key using division  (replace this hash function whit the one given)
	public int hash(K key) {
		int intKey = (Integer) key; // Cast key to Integer
	    return ((intKey * intKey) >>> 10) % 37;
	}
	
	// add a (key,value) pair into the hash table, make sure this method works!
	public V put (K key , V value) {
		int index = hash(key);
		if (index < 0) index += table.length;
		if (table[index] == null) table[index] = new BST<Pair<K,V>> ();
		Pair <K,V> item = new Pair<>(key,value);
		Pair<K,V> other = table[index].find(item);
		if (other == null) {   // item isn't in the table
			table[index].add(item);
			return null;
		}
		// an item with the given key is in the table
		table[index].replace(other,item);
		return other.getValue();
	}	
	
	// print the hash table using preorder traversal of BSTs
	public void print(Scanner keyboard) {
		
		int startIndex=0;
		int endIndex=36;
		System.out.println("Input the starting index (press Enter to use default: 0): ");
	    String startInput = keyboard.nextLine(); // Read input as a string
	    if (!startInput.isEmpty()) {
	        try {
	            startIndex = Integer.parseInt(startInput); // Convert to integer
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Using default value: 0");
	            startIndex = 0;
	        }
	    }

	    System.out.println("Input the ending index (press Enter to use default: 36): ");
	    String endInput = keyboard.nextLine(); // Read input as a string
	    if (!endInput.isEmpty()) {
	        try {
	            endIndex = Integer.parseInt(endInput); // Convert to integer
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Using default value: 36");
	            endIndex = 36;
	        }
	    }
		while (startIndex>endIndex || startIndex<0 || endIndex>36)
		{
			System.out.println("Start index is greater than end index OR values were out of bounds (0-36)");
			System.out.println("Input the starting index (press Enter to use default: 0): ");
		    startInput = keyboard.nextLine(); // Read input as a string
		    if (!startInput.isEmpty()) {
		        try {
		            startIndex = Integer.parseInt(startInput); // Convert to integer
		        } catch (NumberFormatException e) {
		            System.out.println("Invalid input. Using default value: 0");
		            startIndex = 0;
		        }
		    }

		    System.out.println("Input the ending index (press Enter to use default: 36): ");
		    endInput = keyboard.nextLine(); // Read input as a string
		    if (!endInput.isEmpty()) {
		        try {
		            endIndex = Integer.parseInt(endInput); // Convert to integer
		        } catch (NumberFormatException e) {
		            System.out.println("Invalid input. Using default value: 36");
		            endIndex = 36;
		        }
		    }
		}
		for (int i=startIndex;i<=endIndex;i++)
			if (table[i] != null) {
				System.out.print(i + ": ");
				table[i].preorder();
			}
	}
	
	public void print() {
		for (int i=0;i<SIZE;i++)
			if (table[i] != null) {
				System.out.print(i + ": ");
				table[i].preorder();
			}
	}
	
	public BST<Pair<K,V>> [] getTable()
	{
		return table;
	}

}

