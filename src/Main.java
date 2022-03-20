import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		User account1 = new User("Tyler Kim");
		int time = getHourOfDay();

		// User account1 = new User(readNameFromFile(), readIDFromFile(),
		// readBalanceFromFile(),
		// readNumTransactionFromFile());
		account1.showMainScreen(time);
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
}
