package nauka.angielski;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("serial")
public class Menu implements Serializable
{

static Scanner scan = new Scanner(System.in);
	
	public static int showMenu()
	{
		int input = 0;
		boolean isNotDigit = true;
				
        System.out.println("1. Wylosuj rozdzia³ do powtórki");
        System.out.println("2. Dodaj rozdzia³ do powtórki");
        System.out.println("3. Poka¿ powtórzone rozdzia³y");
        System.out.println("4. Poka¿ rozdzia³y do powtórzenia");
        System.out.println("0. Zakoñcz program");
        
        while(isNotDigit)
        {
	        try 
	        {
	        	input = scan.nextInt();
	        	isNotDigit = false;
			} catch (InputMismatchException e) 
	        {
				System.out.println("To nie jest liczba, spróbuj jeszcze raz");				
				isNotDigit = true;
				scan.nextLine();
			}	        
        }        
        return input;       
	}
	
	public static void switchCaseMenu() throws IOException, ClassNotFoundException
	{
		LinkedList<Integer> notLearnedChapters = new LinkedList<Integer>();	
		LinkedList<Integer> learnedChapters = new LinkedList<Integer>();
		String fileName = createFile();
		
		readFromFile(notLearnedChapters,learnedChapters,fileName);
		
		int inputChoice = showMenu();
		
		
		while(inputChoice != 0)
		{
			switch (inputChoice) 
			{			
			case 1:				
				randomChapter(notLearnedChapters, learnedChapters);
				break;
			case 2:
				addLearningChapter(notLearnedChapters);			
				break;
			case 3:
				toShowLearnedChapters(learnedChapters);
				break;		
			case 4:
				toShowNotLearnedChapters(notLearnedChapters);		
				break;		
			default:
				System.out.println("Nieprawid³owy wybór, spróbuj jeszcze raz");
			}
			System.out.println("\nWciœnij Enter, aby kontynuowaæ...");
			System.in.read();
			inputChoice = showMenu();
		}		
		scan.close();
		saveToFile(notLearnedChapters, learnedChapters,fileName);
	}

	public static String createFile() throws IOException {
		String fileName = "dane.txt";
		File file = new File(fileName);
		if(!file.exists()) 
		{
            file.createNewFile();
        }
		return fileName;
	}

	public static void toShowNotLearnedChapters(LinkedList<Integer> notLearnedChapters) 
	{		
		if (notLearnedChapters.size()>0) 
		{
			System.out.println("Rozdzia³y do powtórzenia to; ");
			for (int i = 0; i < notLearnedChapters.size(); i++) 
			{
				 System.out.print(notLearnedChapters.get(i) + ", ");
			}
		}
		else
		{
			System.out.println("Brak rozdzia³ów do powtórzenia");
		}
	}

	public static void addLearningChapter(LinkedList<Integer> notLearnedChapters) 
	{
		try 
		{
			System.out.println("Podaj numer rozdzia³u");
			int inputNumber = scan.nextInt();
			boolean chapterExists = false;
			for (int i = 0; i < notLearnedChapters.size(); i++) 
			{
				if (notLearnedChapters.get(i)==inputNumber) 
				{
					chapterExists = true;
					break;
				} else
				{
					chapterExists = false;
				}
			}
			if (chapterExists) 
			{
				System.out.println("Rozdzia³ ju¿ istnieje");
			} else 
			{
				notLearnedChapters.add(inputNumber);
				System.out.println("Rozdzia³ "+ inputNumber + " zosta³ dodany");
			}						
		} 
			catch (Exception e) 
			{
			System.out.println("To nie jest liczba, spróbuj jeszcze raz");		
			}
	}

	public static void randomChapter(LinkedList<Integer> notLearnedChapters, LinkedList<Integer> learnedChapters) 
	{
		try
		{
			Random random = new Random();	
			int chapterNumber;
			chapterNumber = random.nextInt(notLearnedChapters.size()-1)+1;
			System.out.println("Wylosowany rozdzia³ na dziœ to " + notLearnedChapters.get(chapterNumber));
			learnedChapters.add(notLearnedChapters.get(chapterNumber));
			notLearnedChapters.remove(chapterNumber);
		} 
		catch(IllegalArgumentException e)
		{			
			try
			{
				System.out.println("Wylosowany rozdzia³ na dziœ to " + notLearnedChapters.get(0));
				learnedChapters.add(notLearnedChapters.get(0));
				notLearnedChapters.remove(0);
			} 
			catch(IndexOutOfBoundsException f)
			{
				System.out.println("Brak materia³u do nauki");
			}			
		}		
	}
	
	public static void toShowLearnedChapters(LinkedList<Integer> learnedChapters) 
	{
		if (learnedChapters.size()>0) 
		{
			System.out.println("Nauczone rozdzia³y to; ");
			for (int i = 0; i < learnedChapters.size(); i++) 
			{
				System.out.print(learnedChapters.get(i) + ", ");
			}
		}
		else
		{
			System.out.println("Brak nauczonych roz... do nauki gnoju :P");
		}		
	}
	
	public static void saveToFile(LinkedList<Integer> notLearnedChapters, LinkedList<Integer> learnedChapters, String fileName) throws FileNotFoundException, IOException
	{
		FileWriter save = new FileWriter(fileName);
		BufferedWriter write = new BufferedWriter(save);
		
		writeNotLearnedChapters(notLearnedChapters, write);
		write.newLine();
		writeLearnedChapters(learnedChapters, write);
		
		write.close();
		save.close();
	}

	public static void writeLearnedChapters(LinkedList<Integer> learnedChapters, BufferedWriter write) throws IOException 
	{
		for (int i = 0; i < learnedChapters.size(); i++) 
		{
			write.write(learnedChapters.get(i).toString() + ",");
		}
	}

	public static void writeNotLearnedChapters(LinkedList<Integer> notLearnedChapters, BufferedWriter write) throws IOException 
	{
		writeLearnedChapters(notLearnedChapters, write);
	}
	
	public static void readFromFile(LinkedList<Integer> notLearnedChapters, LinkedList<Integer> learnedChapters, String fileName) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		FileReader readFromFile = new FileReader(fileName);
		BufferedReader readLine = new BufferedReader(readFromFile);
		
		try 
		{
			String holderNotLearned = readLine.readLine().toString();
			String notLearned[] = holderNotLearned.split(",");
			readNotLearnedChapters(notLearnedChapters, notLearned);	
		} 	
			catch (NullPointerException e) {}
		try 
		{
			String holderLearned = readLine.readLine().toString();
			String learned[] = holderLearned.split(",");
			readLearnedChapters(learnedChapters, learned);
		} 
			catch (NullPointerException g) {}															
		readLine.close();
		readFromFile.close();		
	}

	public static void readLearnedChapters(LinkedList<Integer> learnedChapters, String[] learned) {
		for (int i = 0; i < learned.length; i++) 
		{
			learnedChapters.add(Integer.parseInt(learned[i]));			
		}
	}

	public static void readNotLearnedChapters(LinkedList<Integer> notLearnedChapters, String[] notLearned) {
		readLearnedChapters(notLearnedChapters, notLearned);
	}
}

