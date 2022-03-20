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

		User account1 = new User();

		User account2 = new User(readNameFromFile(), readIDFromFile(), readBalanceFromFile(),
				readNumTransactionFromFile(), readPasswordFromFile());

		System.out.println("Would you like to start a new account or use an existing account? [y/n]");
		System.out.println("Answering y will create a new account and n will use the existing file.");
		System.out.println();
		Scanner scanner = new Scanner(System.in);
		char yesNo = '\0';
		while (true) {
			yesNo = scanner.nextLine().charAt(0);
			if (yesNo == 'y' || yesNo == 'n') {
				break;
			} else {
				System.out.println();
				System.out.println("Please answer with 'y' or 'n'");
				System.out.println();
			}
		}

		if (yesNo == 'y') {
			account1.showMainScreen(time);
		} else {
			account2.showMainScreen(time);
		}
	}

	public static int getHourOfDay() {

		Date currentDate = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("kk");
		return Integer.valueOf(timeFormat.format(currentDate));
	}

	public static String readNameFromFile() throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/history.txt");
		try (Scanner scan = new Scanner(file)) {

			String name = scan.nextLine();
			name = name.substring(34, name.length());
			return name;
		}
	}

	public static int readIDFromFile() throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/history.txt");
		try (Scanner scan = new Scanner(file)) {

			String text = scan.nextLine();
			text = scan.nextLine();
			int userID = Integer.parseInt(text.replaceAll("[\\D]", ""));
			return userID;
		}
	}

	public static double readBalanceFromFile() throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/history.txt");
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

	public static int readNumTransactionFromFile() throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/history.txt");
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

	public static String readPasswordFromFile() throws FileNotFoundException {

		File file = new File("/Users/dongh/OneDrive/Desktop/history.txt");
		try (Scanner scan = new Scanner(file)) {

			String password = scan.nextLine();
			password = scan.nextLine();
			password = scan.nextLine();
			password = password.substring(33, password.length());
			return password;
		}
	}
}
