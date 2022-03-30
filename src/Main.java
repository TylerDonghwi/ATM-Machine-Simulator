import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
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
				User existingUser = new User(name, User.readIDFromFile(nameNoSpace),
						User.readBalanceFromFile(nameNoSpace), User.readNumTransactionFromFile(nameNoSpace),
						User.readPasswordFromFile(nameNoSpace));
				existingUser.showMainScreen(time);
			} catch (java.io.FileNotFoundException e) {
				// if the user doesn't exist, create a new account
				System.out.println();
				System.out.println("File does not exist for user ");
				System.out.println("A new account will be created");
				System.out
						.println("__________________________________________________________________________________");
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

}
