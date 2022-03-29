import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		// get time of the day
		int time = getHourOfDay();

		// Asking user if they are a new customer or an existing customer
		System.out.println("Would you like to start a new account or use an existing account? [new/existing]");
		System.out.println();
		Scanner scanner = new Scanner(System.in);
		String input = "";

		// Ensuring that desired input is received from user
		while (true) {
			input = scanner.nextLine().toLowerCase();
			if (input.equals("new") || input.equals("existing")) {
				break;
			} else {
				System.out.println();
				System.out.println("Please answer with 'new' or 'existing' without any spaces");
				System.out.println();
			}
		}

		// if the user is new make a new account and start simulation
		if (input.equals("new")) {
			User newUser = new User();
			newUser.showMainScreen(time);
			// otherwise ask user their name
		} else {
			System.out.println();
			System.out.println("What is your name?");
			System.out.println();
			String name;
			// ensuring that the name is in the right format
			while (true) {
				name = scanner.nextLine();
				if (!name.contains(" ")) {
					System.out.println();
					System.out.println("Please enter your first name and last name");
					System.out.println();
				} else if (name.length() <= 30) {
					break;
				} else {
					System.out.println();
					System.out.println("Your name is too long.");
					System.out.println();
				}
			}
			String nameNoSpace = getNameWithoutSpace(name);

			try {
				// extract information from the user file simulate the account.
				User existingUser = new User(name, readIDFromFile(nameNoSpace), readBalanceFromFile(nameNoSpace),
						readNumTransactionFromFile(nameNoSpace), readPasswordFromFile(nameNoSpace));
				existingUser.showMainScreen(time);
			} catch (java.io.FileNotFoundException e) {
				// if the user doesn't exist, create a new account
				System.out.println();
				System.out.println("File does not exist for user ");
				System.out.println("A new account will be created");
				System.out.println("_________________________________________________________________________________");
				User newUser = new User();
				newUser.showMainScreen(time);
			}

		}

	}

	// returns the name without spaces to create a file
	private static String getNameWithoutSpace(String name) {
		return name.replaceAll("\\s+", "");
	}

	// Returning a real time for a real life like interactions
	public static int getHourOfDay() {

		Date currentDate = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("kk");
		return Integer.valueOf(timeFormat.format(currentDate));
	}

	// Extracting user ID from file
	public static int readIDFromFile(String name) throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + name + ".txt");
		try (Scanner scan = new Scanner(file)) {

			String text = scan.nextLine();
			text = scan.nextLine();
			int userID = Integer.parseInt(text.replaceAll("[\\D]", ""));
			return userID;
		}
	}

	// Extracting number of account balance from file
	public static double readBalanceFromFile(String name) throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + name + ".txt");
		try (Scanner scan = new Scanner(file)) {

			Double balance = 0.0;
			String text = "";
			while (scan.hasNextLine()) {
				text = scan.nextLine();
			}
			balance = (double) Integer.parseInt(text.replaceAll("[\\D]", "")) * 0.1;

			return balance;
		}
	}

	// Extracting number of transactions from file
	public static int readNumTransactionFromFile(String name) throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + name + ".txt");
		try (Scanner scan = new Scanner(file)) {

			String text = "";
			int counter = 0;
			while (scan.hasNextLine()) {
				text = scan.nextLine();
				if (text.contains("Transaction")) {
					counter++;
				}
			}

			return counter;
		}
	}

	// Extracting password from the file
	public static String readPasswordFromFile(String name) throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + name + ".txt");
		try (Scanner scan = new Scanner(file)) {

			String password = scan.nextLine();
			password = scan.nextLine();
			password = scan.nextLine();
			password = password.substring(10, password.length());
			return password;
		}
	}
}
