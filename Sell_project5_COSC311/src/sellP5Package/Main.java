/*Programmer:         Joshua Sell         
Course:             COSC 311, F'24      
Project:            5               
Due Date:           12/2/2024

*
*
*/


package sellP5Package;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class Main {
//Starts the program
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		menuDisplay(null, null);
	}
	
	//creating the random access file
	public static RandomAccessFile createRAF(Scanner keyboard)
	{
		try {
			String inputFile;
			String outputFile;
			System.out.print("Enter the name of the input file: ");
			inputFile=keyboard.nextLine();
			File file = new File(inputFile); // Text file with student data
			//check if the file actually exists
			while (!file.exists())
	        {
	        	System.out.print("Input file does not exist. Please re-enter input file name: ");
	        	inputFile=keyboard.nextLine();
	        	file=new File(inputFile);
	        }
			//create scanner to read from file
	        Scanner fileScanner = new Scanner(file);
			System.out.print("Enter the name of the output file: ");
			outputFile=keyboard.nextLine();
			
			File outputRAFFile = new File(outputFile);
		    if (outputRAFFile.exists()) {
		        if (outputRAFFile.delete()) {
		            System.out.println("Existing random access file '" + outputFile + "' deleted.");
		        }
		    }
	        
	        //create a Random Access File to write student data
	        RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile, "rw"); // Create or open the file "students.dat"

	        // read and write each student record
	        while (fileScanner.hasNextLine()) { // Ensure we don't exceed 10 lines
	            // Create a new Student object
	            Student student = new Student();

	            // Read data from the text file into the Student object
	            student.readFromTextFile(fileScanner);

	            // Write the Student object data to the random access file
	            student.writeToFile(randomAccessFile);
	        }

	        // Close the Random Access File and Scanner
	        
	        

	        System.out.println("Student data successfully written to random access file " + outputFile +".");
	        return randomAccessFile;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	//displays the raf
	public static void displayRAF(RandomAccessFile raf, Scanner keyboard, boolean firstRun)
	{
		try 
		{
			//always display first five with this boolean
			if (firstRun)
			{
				raf.seek(0);				
				firstRun=false;
				displayNextFive(raf, keyboard);
			}
			else
			{
				char selection;
				System.out.println("\nEnter N (for next 5 records), A (for all remaining records), M(for main menu):");
				selection=keyboard.next().charAt(0);
				keyboard.nextLine();
				while (selection != 'N' && selection != 'n' && selection != 'A' && selection != 'a' && selection != 'M' && selection != 'm')
				{
					System.out.print("Invalid selection. Enter N (for next 5 records), A (for all remaining records), M(for main menu): ");
					selection=keyboard.next().charAt(0);
					keyboard.nextLine();
				}
				switch (selection)
				{
				//accept both capital and lowercase
					case 'N':
					case 'n':
						displayNextFive(raf, keyboard);
						break;
					case 'A':
					case 'a':
						displayRemainingEntries(raf, keyboard);
						break;
					case 'M':
					case 'm':
						return;
				}
			}
	    } catch (IOException e) {
	        e.printStackTrace(); // Print error details if there is an IOException
	    }
	}
	//the main driver method
	public static void menuDisplay(RandomAccessFile raf, Hashing<Integer, Integer> hashTable)
	{
		int selection;
		Scanner keyboard = new Scanner(System.in);
		while (true)
		{
			System.out.println("\nMenu\n"
					+ "====\n"
					+ "1: Make a random-access file\n"
					+ "2: Display a random-access file\n"
					+ "3: Build the Index\n"
					+ "4: Display the Index\n"
					+ "5: Retrieve a Record\n"
					+ "6: Modify a Record\n"
					+ "7: Add a Record\n"
					+ "8: Delete a record\n"
					+ "9: Quit Program \n\n"
					+ "Enter your selection: ");
			while (!keyboard.hasNextInt())
			{
				keyboard.nextLine();
				System.out.println("Incorrect input format. Please enter an integer: ");
			}
			selection=keyboard.nextInt();
			keyboard.nextLine();
			switch (selection) {
		        case 1:
		            raf=createRAF(keyboard);
		            break;
		        case 2:
		        	if (raf==null)
		        	{
		        		System.out.println("Create a Random Access File first!");
		        		break;
		        	}
		        	raf=fetchRAF(keyboard);
		            displayRAF(raf, keyboard, true);
		            break;
		        case 3:
		        	if (raf==null)
		        	{
		        		System.out.println("Create a Random Access File first!");
		        		break;
		        	}
		        	hashTable=createHashing(raf);
		            break;
		        case 4:
		        	if (raf==null)
		        	{
		        		System.out.println("Create a Random Access File first!");
		        		break;
		        	}
		        	if (hashTable==null)
		        	{
		        		System.out.println("Please create the hash table first.");
		        		break;
		        	}
		        	displayHashTable(hashTable, raf, keyboard);
		        	break;
		        case 5:
		        	if (raf==null)
		        	{
		        		System.out.println("Create a Random Access File first!");
		        		break;
		        	}
		        	if (hashTable==null)
		        	{
		        		System.out.println("Please create the hash table first.");
		        		break;
		        	}
		        	retrieveHashTableEntry(hashTable, raf, keyboard);
		        	
		        	break;
		        case 6:
		        	if (raf==null)
		        	{
		        		System.out.println("Create a Random Access File first!");
		        		break;
		        	}
		        	if (hashTable==null)
		        	{
		        		System.out.println("Please create the hash table first.");
		        		break;
		        	}
		        	modifyHashTableEntry(raf, hashTable, keyboard);
		        	break;
		        case 7:
		        	if (raf==null)
		        	{
		        		System.out.println("Create a Random Access File first!");
		        		break;
		        	}
		        	if (hashTable==null)
		        	{
		        		System.out.println("Please create the hash table first.");
		        		break;
		        	}
		        	addHashTableRecord(hashTable, keyboard, raf);
		        	break;
		        case 8:
		        	if (raf==null)
		        	{
		        		System.out.println("Create a Random Access File first!");
		        		break;
		        	}
		        	if (hashTable==null)
		        	{
		        		System.out.println("Please create the binary search tree first.");
		        		break;
		        	}
		        	deleteHashTableRecord(hashTable, raf, keyboard);
		        	break;
		        	
		        case 9:
		        	System.out.println("\nBye!");
		        	if (hashTable==null)
		        	{
		        		System.out.println("Please create the hash table first.");
		        		break;
		        	}
		        	keyboard.close();
		        	hashTable.print();
		        	return;
		        default:
		            System.out.println("Invalid selection. Please choose a valid option: ");
		            selection=keyboard.nextInt();
		            keyboard.nextLine();
		            break;
				}
			}
	}
	//displays remaining entries of raf
	public static void displayRemainingEntries(RandomAccessFile raf, Scanner keyboard) throws IOException
	{
		try {
			String fName;
	        String lName;
	        int id;
	        double GPA;
			while (raf.getFilePointer()<raf.length())
			{
				StringBuilder fNameBuilder=new StringBuilder(20);
	        	StringBuilder lNameBuilder=new StringBuilder(20);
	        	for (int stringPos=0; stringPos<20; stringPos++)
	        	{
	        		char ch = raf.readChar(); // Read a character once	                
	                fNameBuilder.append(ch); // Append the character to the StringBuilder
	        	}
	        	fName=fNameBuilder.toString().trim();
	        	for (int stringPos=0; stringPos<20; stringPos++)
	        	{
	        		char ch = raf.readChar(); // Read a character once
	                lNameBuilder.append(ch); // Append the character to the StringBuilder
	        	}
	        	lName=lNameBuilder.toString().trim();
	        	id=raf.readInt();
	        	GPA=raf.readDouble();
	        	if (fName.equals("deleted") || lName.equals("deleted")) {
	                continue; // Skip the current iteration and do not print the line
	            }
	            System.out.println(String.format("%-20s", fName) + String.format("%-20s", lName) + String.format("%-10s", id) + String.format("%-10s", GPA));
			}
			displayRAF(raf, keyboard, false);
		}
		catch (IOException e) {
	        e.printStackTrace(); // Print error details if there is an IOException
	        }
	}
	//displays next five entries of raf
	public static void displayNextFive(RandomAccessFile raf, Scanner keyboard) throws IOException
	{
		try {
	        // Read and display the contents of the random access file, only the next five
	        String fName;
	        String lName;
	        int id;
	        double GPA;
	        for (int i = 0; i < 5; i++) { // Read each line until EOF
	        	if (raf.getFilePointer()<raf.length())
	        	{
	        		//two stringbuilders for first name and last name to compile from the file
		        	StringBuilder fNameBuilder=new StringBuilder(20);
		        	StringBuilder lNameBuilder=new StringBuilder(20);
		        	for (int stringPos=0; stringPos<20; stringPos++)
		        	{
		        		char ch = raf.readChar(); // Read a character once	                
		                fNameBuilder.append(ch); // Append the character to the StringBuilder
		        	}
		        	fName=fNameBuilder.toString().trim();
		        	for (int stringPos=0; stringPos<20; stringPos++)
		        	{
		        		char ch = raf.readChar(); // Read a character once
		                lNameBuilder.append(ch); // Append the character to the StringBuilder
		        	}
		        	lName=lNameBuilder.toString().trim();
		        	id=raf.readInt();
		        	GPA=raf.readDouble();
		        	if (fName.equals("deleted") || lName.equals("deleted")) {
		        		i--;
		                continue; // Skip the current iteration and do not print the line
		            }
		            System.out.println(String.format("%-20s", fName) + String.format("%-20s", lName) + String.format("%-10s", id) + String.format("%-10s", GPA));
	        	}
	        	else
	        	{
	        		break;
	        	}
	        }
	        displayRAF(raf, keyboard, false);
		}
	    catch (IOException e) {
		        e.printStackTrace(); // Print error details if there is an IOException
		}
	}
	//fetches the raf
	public static RandomAccessFile fetchRAF(Scanner keyboard)
	{
		RandomAccessFile randomAccessFile = null;
		try {
			System.out.print("Input the name of the existing Random Access File: ");
	        String fileName = keyboard.nextLine();  // Store the user input in a variable
	
	        //Open the Random Access File for reading
	        randomAccessFile = new RandomAccessFile(fileName, "rw");
	        while (randomAccessFile.length()==0)
	        {
	        	System.out.print("Incorrect or empty file. Input the name of the existing Random Access File: ");
		        fileName = keyboard.nextLine();  // Store the user input in a variable
		
		        //Open the Random Access File for reading
		        randomAccessFile = new RandomAccessFile(fileName, "rw");
	        }
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        return randomAccessFile;
	}
	//creates the hash table
	public static Hashing<Integer, Integer> createHashing(RandomAccessFile raf)
	{
		try {
			Hashing<Integer, Integer> hashTable=new Hashing<>();
			int curPosition=80;
			int curIndex=0;
			while (curPosition<raf.length())
			{
				raf.seek(curPosition);
				hashTable.put(raf.readInt(), curIndex);
				curPosition=curPosition+92;
				curIndex++;
			}
			System.out.println("Hash Table created.");
			return hashTable;
		}
		catch (IOException e)
		{
			System.out.println("Hash table creation failed.");
			return null;
		}
	}
	//displays the hash table
	public static void displayHashTable(Hashing<Integer, Integer> h, RandomAccessFile raf, Scanner keyboard)
	{
		h.print(keyboard);
	}
	//retrieves a specific entry from the hash table
	public static void retrieveHashTableEntry(Hashing<Integer, Integer> h, RandomAccessFile raf, Scanner keyboard)
	{
		try {
		System.out.println("Enter the id of the student you'd like to find: ");
		while (!keyboard.hasNextInt())
		{
			System.out.println("Incorrect entry type. Please enter an integer: ");
			keyboard.nextLine();
		}
		Integer key = keyboard.nextInt();
		Integer hashedKey = ((key * key) >>> 10) % 37;
		Boolean found=false;
		int address=0;
		if (hashedKey >= 0 && hashedKey<37 && h.getTable()[hashedKey] != null) {
				QueueSLL<Pair<Integer, Integer>> queue = h.getTable()[hashedKey].toQueueLevelOrder();
				while (!queue.empty())
				{
					if (queue.peek().getKey().equals(key))
					{
						address=queue.peek().getValue();
						found=true;
					}
					queue.remove();
				}
				if (found==true)
				{
					String fName;
					String lName;
					int id;
					double GPA;
					raf.seek(address*92);
					StringBuilder fNameBuilder=new StringBuilder(20);
		        	StringBuilder lNameBuilder=new StringBuilder(20);
		        	for (int stringPos=0; stringPos<20; stringPos++)
		        	{
		        		char ch = raf.readChar(); // Read a character once	                
		                fNameBuilder.append(ch); // Append the character to the StringBuilder
		        	}
		        	//trim out the extra spaces
		        	fName=fNameBuilder.toString().trim();
		        	for (int stringPos=0; stringPos<20; stringPos++)
		        	{
		        		char ch = raf.readChar(); // Read a character once
		                lNameBuilder.append(ch); // Append the character to the StringBuilder
		        	}
		        	lName=lNameBuilder.toString().trim();
		        	id=raf.readInt();
		        	GPA=raf.readDouble();
		        	if (fName.equals("deleted") || lName.equals("deleted")) {
		                System.out.println("Entry has been deleted."); // Skip the current iteration and do not print the line
		            }
		        	else
		        	{
		        		System.out.println(String.format("%-20s", fName) + String.format("%-20s", lName) + String.format("%-10s", id) + String.format("%-10s\n", GPA));
		        	}
				}
				else
				{
					System.out.println("Not found.");
				}
				
			}
		else
		{
			System.out.println("Not found.");
		}
		}
		catch (IOException e)
		{
			
		}
	}
	//modifies an entry in the hash table
	public static void modifyHashTableEntry(RandomAccessFile raf, Hashing<Integer, Integer> h, Scanner keyboard)
	{
		try
		{
			System.out.println("Enter the student ID of the record you'd like to modify: ");
			while (!keyboard.hasNextInt())
			{
				System.out.println("Incorrect entry type. Please enter an integer: ");
				keyboard.nextLine();
			}
			Integer key = keyboard.nextInt();
			Integer hashedKey = ((key * key) >>> 10) % 37;
			int selection;
			if (h.getTable()[hashedKey]!=null)
				{
				QueueSLL<Pair<Integer, Integer>> queue = h.getTable()[hashedKey].toQueueLevelOrder();
				while (!queue.empty() && !queue.peek().getKey().equals(key))
				{
					queue.remove();
				}
				if (!queue.empty() && queue.peek().getKey().equals(key))
				{
					while (true) {
						System.out.println("1- Change the first name: \n"
								+ "2- Change the last name: \n"
								+ "3- Change GPA: \n"
								+ "4- Done: \n"
								+ "Enter your choice: ");
						while (true) {
						    // Check if the next input is an integer
							if (keyboard.hasNextInt()) {
								selection = keyboard.nextInt();
				
						        // Check if the integer is within the desired range
								if (selection >= 1 && selection <= 4) {
									keyboard.nextLine();
									break; // Exit the loop if a valid integer is entered
									}
								else {
						            System.out.println("Invalid choice. Please enter a number between 1 and 5.");
						        }
						    } 
							else {
						        System.out.println("Invalid input type detected. Please enter a number.");
						        keyboard.next(); // Consume the invalid input to avoid an infinite loop
						    }
				
						    // Consume any newline characters left in the buffer
						    keyboard.nextLine();
						}
						switch (selection)
						{
						//using +80 and +40 and other addition functions to skip to the specific spot in the file
							case 1:
								System.out.println("Enter new first name: ");
								raf.seek(92*queue.peek().getValue());
								raf.writeChars(String.format("%-20s", keyboard.nextLine()));
								break;
							case 2:
								System.out.println("Enter new last name: ");
								raf.seek((92*queue.peek().getValue())+40);
								raf.writeChars(String.format("%-20s", keyboard.nextLine()));
								break;
							case 3:
								System.out.println("Enter new GPA: ");
								raf.seek((92*queue.peek().getValue())+84);
								while (!keyboard.hasNextDouble())
								{
									keyboard.next();
									System.out.print("Invalid input type. Please re-enter: ");
								}
								raf.writeDouble(keyboard.nextDouble());
								keyboard.nextLine();
								break;
							case 4:
								return;
						}
					}
				}
				else
				{
					System.out.println("Search failed.");
				}
			}
			
		}
		catch (IOException e)
		{
			
		}
	}
	//adds an entry in the hash table
	public static void addHashTableRecord(Hashing<Integer, Integer> h, Scanner keyboard, RandomAccessFile raf)
	{
		try {
			
			System.out.println("Enter the id of the record you'd like to add: ");
			while (!keyboard.hasNextInt())
			{
				keyboard.nextLine();
				System.out.println("Incorrect entry type. Please enter an integer: ");
			}
			Integer id = keyboard.nextInt();
			Integer hashedKey = ((id * id) >>> 10) % 37;
			if (hashedKey>36 || hashedKey<0)
			{
				System.out.println("Out of bounds key.");
				return;
			}
			if (h.getTable()[hashedKey]!=null)
			{
				QueueSLL<Pair<Integer, Integer>> queue = h.getTable()[hashedKey].toQueueLevelOrder();
				while (!queue.empty() && queue.peek().getValue()!=id)
				{
					queue.remove();
				}
				if (!queue.empty())
				{
					System.out.println("Record already exists.");
				}
				else if (queue.empty())
				{
					keyboard.nextLine();
					String fName;
					String lName;
					double GPA;
					System.out.println("Insert First Name: ");
					fName=keyboard.nextLine();
					
					System.out.println("Insert Last Name: ");
					lName=keyboard.nextLine();
					System.out.println("Insert GPA: ");
					//check that a double is being given
					while (!keyboard.hasNextDouble())
					{
						keyboard.next();
						System.out.println("No double detected. Please re-enter GPA: ");
					}
					GPA=keyboard.nextDouble();
					keyboard.nextLine();
					Student newStudent = new Student();
					newStudent.setData(fName, lName, id, GPA);
					raf.seek(raf.length());
					newStudent.writeToFile(raf);
					int counter=(int) ((raf.length()-1)/96);
					Pair<Integer, Integer> tempPair=new Pair<>(id, counter);
					h.getTable()[hashedKey].add(tempPair);
					System.out.println("File successfully added.");
				}
			}
			else
			{
				keyboard.nextLine();
				String fName;
				String lName;
				double GPA;
				System.out.println("Insert First Name: ");
				fName=keyboard.nextLine();
				System.out.println("Insert Last Name: ");
				lName=keyboard.nextLine();
				System.out.println("Insert GPA: ");
				//check that a double is being given
				while (!keyboard.hasNextDouble())
				{
					keyboard.next();
					System.out.println("No double detected. Please re-enter GPA: ");
				}
				GPA=keyboard.nextDouble();
				keyboard.nextLine();
				Student newStudent = new Student();
				newStudent.setData(fName, lName, id, GPA);
				raf.seek(raf.length());
				newStudent.writeToFile(raf);
				int counter=(int) ((raf.length()-1)/96);
				h.put(id, counter);
				System.out.println("File successfully added.");
			}
		}
			
			//add input checking
		catch (IOException e)
		{
			
		}

	}
	//deletes an entry from the hash table
	public static void deleteHashTableRecord(Hashing<Integer, Integer> h, RandomAccessFile raf, Scanner keyboard)
	{
		try {
			String replacementText=String.format("%-46s", "deleted");
			int selection;
			System.out.println("Input the ID of the record you'd like to delete: ");
			while (!keyboard.hasNextInt())
			{
				keyboard.nextLine();
				System.out.print("Incorrect entry type. Please enter an integer: ");
			}
			selection=keyboard.nextInt();
			Integer hashedKey = ((selection * selection) >>> 10) % 37;
			if (h.getTable()[hashedKey]!=null && hashedKey >= 0 && hashedKey <= 36)
			{
				QueueSLL<Pair<Integer, Integer>> queue = h.getTable()[hashedKey].toQueueLevelOrder();
				while (!queue.empty() && !queue.peek().getKey().equals(selection))
				{
					queue.remove();
				}
				if (!queue.empty() && queue.peek().getKey().equals(selection))
				{
					raf.seek(92 * queue.peek().getValue());
					StringBuilder currentText=new StringBuilder();
					for (int i = 0; i < 46; i++)
					{
						currentText.append(raf.readChar());
					}
					if (currentText.toString().trim().equals("deleted"))
					{
						System.out.println("The entry has already been deleted.");
					}
					else
					{
						//overwrite the line with writeBytes
						raf.seek(92*queue.peek().getValue());
						raf.writeChars(replacementText);
						h.getTable()[hashedKey].delete(queue.peek());
						System.out.print("The record has successfully been deleted.");
					}
				}
				else
				{
					System.out.println("ID not found.");
				}
				}
			else
			{
				System.out.println("Deletion unsuccessful.");
			}
		}
		catch (IOException e)
		{
			
		}
	}
	//creates the binary search tree
	
}
