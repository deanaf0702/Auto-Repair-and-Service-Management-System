package AutoCenter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class ScanHelper {
	public static Scanner scanner = new Scanner(System.in);
	private static boolean wentCatch = false;
	public static int nextInt() 
	{
		//scanner.nextLine();
		int selection = 0;
		do {
			try {
				wentCatch = false;
				String s = scanner.nextLine();
				if(!s.isEmpty())
					selection = Integer.parseInt(s);
				else wentCatch = true;
			}catch(Exception e) {
				e.printStackTrace();
				wentCatch = true;
				//scanner.nextLine();
				//System.out.println("Try again: ");
			}
		}while(wentCatch);
		
		return selection;
	}
	public static String next() 
	{
		try {
			wentCatch = false;
			return scanner.next();
		}catch(Exception e) {
			e.printStackTrace();
			wentCatch = true;
		}
		return null;
	}
	public static Double nextDouble() 
	{
		//scanner.nextLine();
		do {
			try {
				wentCatch = false;
				return scanner.nextDouble();
			}catch(Exception e) {
				e.printStackTrace();
				wentCatch = true;
				scanner.nextLine();
				System.out.println("Try again: ");
			}
		}while(wentCatch);
		
		return 0.0;
	}
	public static String nextLine()
	{
		try {
			wentCatch = false;
			String stringInput = scanner.nextLine();
			return stringInput;
		}catch(Exception e) {
			e.printStackTrace();
			wentCatch = true;
			//scanner.nextLine();
			System.out.println("Try again: ");
		}
		
		return null;
	}
	public static Date nextDate()
	{
		//scanner.nextLine();
		do {
			try {
				wentCatch = false;
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				Date stringDate = formatter.parse(scanner.next());
				//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
				
				return (Date)stringDate;
				
			}catch(Exception e) {
				e.printStackTrace();
				wentCatch = true;
				scanner.nextLine();
				System.out.println("Try again: ");
			}
		}while(wentCatch);
		return null;
	}
	
}
