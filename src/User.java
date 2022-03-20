import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class User {

	private String name;
	private int userID;
	private double balance;
	private int previousTransactionAmount;
	private String previousTransactionAction;

	public User(String name) {
		this.name = name;
		this.userID = (int) (10000000 * Math.random());
	}

	public int getUserID() {
		return userID;
	}

	void checkBalance() {
		System.out.println("Checking the balance in your account:");
		System.out.println("User ID: " + this.userID);
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();
	}

	void deposit(int amount) throws IOException {
		if (amount > 0) {
			this.balance += amount;
			this.previousTransactionAmount = amount;
			this.previousTransactionAction = "Deposit";
			System.out.println();
			System.out.println("Deposited $" + amount);
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
			appendFile();
		} else {
			System.out.println();
			System.out.println("The money is invalid");
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
			System.out.println("How much would you like to deposit?");
			System.out.println();
		}
	}

	void withdrawl(int amount) throws IOException {
		if (this.balance == 0) {
			System.out.println();
			System.out.println("Your account doesn't have enough balace to withdraw money");
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
			System.out.println();
		} else if (amount > 0 && this.balance >= amount) {
			this.balance -= amount;
			this.previousTransactionAmount = -amount;
			this.previousTransactionAction = "Withdrawl";
			System.out.println();
			System.out.println("Withdrew $" + amount);
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
			System.out.println();
			appendFile();
		} else if (amount > 0) {
			System.out.println();
			System.out.println("Not enough balance");
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
			System.out.println("How much would you like to withdraw?");
		} else {
			System.out.println();
			System.out.println("The money is invalid");
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
			System.out.println("How much would you like to withdraw?");
			System.out.println();
		}
	}

	void getPreviousTransaction() {
		if (this.previousTransactionAmount > 0) {
			System.out.println();
			System.out.println("Deposited $" + previousTransactionAmount + ", your balance is " + this.balance);
			System.out.println();
		} else if (this.previousTransactionAmount < 0) {
			System.out.println();
			System.out.println("Withdrawn $" + -previousTransactionAmount + ", your balance is " + this.balance);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("No previous transactions.");
			System.out.println();
		}
	}

	void showMainScreen(int time) throws IOException {
		try (Scanner scanner = new Scanner(System.in)) {
			makeInitialFileFromGame();

			char userOption = '\0';

			if (time >= 0 && time <= 11) {
				System.out.println("Good morning, " + this.name.substring(0, this.name.indexOf(" ")) + "!");
			} else if (time < 18) {
				System.out.println("Good afternoon, " + this.name.substring(0, this.name.indexOf(" ")) + "!");
			} else {
				System.out.println("Good evening, " + this.name.substring(0, this.name.indexOf(" ")) + "!");
			}
			System.out.println("Your user ID is " + this.userID + ".");
			System.out.println("How can we help you today?");
			System.out.println();
			System.out.println("A. See my account balance");
			System.out.println();
			System.out.println("B. Deposit into my account");
			System.out.println();
			System.out.println("C. Withdraw from my account");
			System.out.println();
			System.out.println("D. See my previous transaction");
			System.out.println();
			System.out.println("E. Exit");

			while (userOption != 'E') {
				System.out.println();
				System.out.println();
				System.out.println("Which would you like to do?");
				System.out.println();

				userOption = Character.toUpperCase(scanner.next().charAt(0));

				switch (userOption) {
				case 'A':
					System.out.println();
					checkBalance();
					break;
				case 'B':
					System.out.println();
					System.out.println("How much would you like to deposit?");
					System.out.println();
					int depoAmount = 0;
					while (depoAmount <= 0) {
						depoAmount = scanner.nextInt();
						deposit(depoAmount);
					}
					break;
				case 'C':
					System.out.println();
					System.out.println("How much would you like to withdraw?");
					System.out.println();
					int withAmount = -1;
					int currentBalance = (int) this.balance;
					while (withAmount < 0 || (withAmount > currentBalance && currentBalance > 0)) {
						withAmount = scanner.nextInt();
						withdrawl(withAmount);
					}
					break;
				case 'D':
					getPreviousTransaction();
					break;
				case 'E':
					System.out.println();
					System.out.println("Thank you using our service! Have a wonderful day!");
					endFile();
					break;
				default:
					System.out.println();
					System.out.println("Error: invalid option, please enter A, B, C, D or E");
				}

			}
		}
	}

	public void makeInitialFileFromGame() throws IOException {
		FileWriter writer = new FileWriter("/Users/dongh/OneDrive/Desktop/history.txt");
		writer.write("ATM simulator history of the user " + this.name + "\n");
		writer.write("User ID of " + this.name + " is " + this.userID);
		writer.close();
	}

	public void appendFile() throws IOException {
		File file = new File("/Users/dongh/OneDrive/Desktop/history.txt");

		try (Scanner scan = new Scanner(file)) {
			String fileContent = "";

			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}

			FileWriter writer = new FileWriter("/Users/dongh/OneDrive/Desktop/history.txt");
			writer.append(fileContent + "\nTransaction: " + this.previousTransactionAction + " of "
					+ previousTransactionAmount);
			writer.close();
		}
	}

	public void endFile() throws IOException {
		File file = new File("/Users/dongh/OneDrive/Desktop/history.txt");

		try (Scanner scan = new Scanner(file)) {
			String fileContent = "";

			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}

			FileWriter writer = new FileWriter("/Users/dongh/OneDrive/Desktop/history.txt");
			writer.append(fileContent + "\n" + this.name + " finished using ATM service");
			writer.close();
		}
	}

}
