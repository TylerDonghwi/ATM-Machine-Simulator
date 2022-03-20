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
	private int numTransaction;
	private boolean hasHistory;
	private String password;

	public User(String name) {
		this.name = name;
		this.userID = (int) (10000000 * Math.random());
		hasHistory = false;
	}

	public User(String name, int userID, double balance, int numTransaction, String password) {
		this.name = name;
		this.userID = userID;
		this.balance = balance;
		this.numTransaction = numTransaction;
		this.hasHistory = true;
		this.password = password;
	}

	public int getNumTransaction() {
		return this.numTransaction;
	}

	public void setPassword() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String password = scanner.nextLine();
			if (password.length() >= 8 && password.length() < 16) {
				this.password = password;
				break;
			} else {
				System.out.println();
				System.out.println("Make sure your password is between 8 to 15 digits long");
				System.out.println();
			}
		}
	}

	public void enterPassword() {
		Scanner scanner = new Scanner(System.in);
		int counter = 0;
		while (true) {
			String password = scanner.nextLine();
			if (password.equals(this.password)) {
				break;
			} else {
				System.out.println();
				System.out.println("wrong password");
				System.out.println();
			}
		}
	}

	void checkBalance() {
		System.out.println("Checking the balance in your account:");
		System.out.println("User ID: " + this.userID);
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();
	}

	void deposit(int amount) throws IOException {
		this.balance += amount;
		this.previousTransactionAmount = amount;
		this.previousTransactionAction = "Deposit";
		System.out.println();
		System.out.println("Deposited $" + amount);
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();
		this.numTransaction++;
		appendFile();

	}

	void withdrawl(int amount) throws IOException {
		this.balance -= amount;
		this.previousTransactionAmount = -amount;
		this.previousTransactionAction = "Withdrawl";
		System.out.println();
		System.out.println("Withdrew $" + amount);
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();
		this.numTransaction++;
		appendFile();
	}

	void getPreviousTransaction() {
		if (this.previousTransactionAmount > 0) {
			System.out.println();
			System.out.println("Deposited $" + previousTransactionAmount);
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
		} else if (this.previousTransactionAmount < 0) {
			System.out.println();
			System.out.println("Withdrawn $" + -previousTransactionAmount);
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("No previous transactions.");
			System.out.println();
		}
	}

	void showMainScreen(int time) throws IOException {
		try (Scanner scanner = new Scanner(System.in)) {
			char userOption = '\0';

			if (time >= 0 && time <= 11) {
				System.out.println();
				System.out.println("Good morning, " + this.name.substring(0, this.name.indexOf(" ")) + "!");
			} else if (time < 18) {
				System.out.println();
				System.out.println("Good afternoon, " + this.name.substring(0, this.name.indexOf(" ")) + "!");
			} else {
				System.out.println();
				System.out.println("Good evening, " + this.name.substring(0, this.name.indexOf(" ")) + "!");
			}
			System.out.println("Your user ID is " + this.userID + ".");

			if (this.hasHistory == false) {
				System.out.println();
				System.out.println(
						"This is your first time here, please set your password.\nYour password should be between 8 to 15 digits long.");
				System.out.println();
				setPassword();
				System.out.println();
				System.out.println("Your new password is set");
				System.out.println();
			} else {
				System.out.println();
				System.out.println("Welcome back, please enter your pass word to access your bank account.");
				System.out.println();
				enterPassword();
				System.out.println();
			}

			if (this.hasHistory == false) {
				makeInitialFileFromGame();
			} else {
				continueWriting();
			}
			System.out.println("________________________________________________________");
			System.out.println();
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
				System.out.println("________________________________________________________");
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
					int depoAmount = 0;
					while (true) {
						try {
							System.out.println();
							System.out.println("How much would you like to deposit?");
							System.out.println();
							depoAmount = Integer.parseInt(scanner.next());
							if (depoAmount > 0) {
								deposit(depoAmount);
								break;
							} else if (depoAmount == 0) {
								System.out.println();
								System.out.println("You can not deposit $0, please try again with a valid value");
								System.out.printf("Balance: $%.2f", this.balance);
								System.out.println();
							} else {
								System.out.println();
								System.out
										.println("Money can not be negative, please try again with a positive value.");
								System.out.println();
							}
						} catch (NumberFormatException e) {
							System.out.println();
							System.out.println("Invalid format of money, try again.");
							System.out.println();
						}
					}
					break;
				case 'C':
					int withAmount = -1;
					while (true) {
						try {
							if (this.balance == 0) {
								System.out.println();
								System.out.println("Your account doesn't have enough balace to withdraw money");
								System.out.printf("Balance: $%.2f", this.balance);
								System.out.println();
								break;
							}

							System.out.println();
							System.out.println("How much would you like to withdraw?");
							System.out.println();
							withAmount = Integer.parseInt(scanner.next());

							if (withAmount > 0 && this.balance >= withAmount) {
								withdrawl(withAmount);
								break;
							} else if (withAmount > 0) {
								System.out.println();
								System.out.println("Not enough balance");
								System.out.printf("Balance: $%.2f", this.balance);
								System.out.println();
							} else if (withAmount == 0) {
								System.out.println();
								System.out.println("You can not withdraw $0, please try again with a valid value");
								System.out.printf("Balance: $%.2f", this.balance);
								System.out.println();
							} else {
								System.out.println();
								System.out
										.println("Money can not be negative, please try again with a positive value.");
								System.out.printf("Balance: $%.2f", this.balance);
								System.out.println();
							}
						} catch (NumberFormatException e) {
							System.out.println();
							System.out.println("Invalid format of money, try again.");
						}
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
		writer.write("\nThe user's pin " + this.name + " is " + this.password + ".");
		writer.close();
	}

	private void continueWriting() throws IOException {
		File file = new File("/Users/dongh/OneDrive/Desktop/history.txt");
		String fileContent = "";

		try (Scanner scan = new Scanner(file)) {
			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}
		}

		FileWriter writer = new FileWriter("/Users/dongh/OneDrive/Desktop/history.txt");
		writer.append(fileContent + "\nUser " + this.name + " used the ATM simulator again\n");
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
			writer.append(fileContent + "\nTransaction " + this.numTransaction + ": " + this.previousTransactionAction
					+ " of $" + Math.abs(previousTransactionAmount) + ", balance is $" + this.balance);
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
			writer.append(fileContent + "\n" + this.name + " finished using ATM service.\nThe final balance is $"
					+ this.balance);
			writer.close();
		}
	}

}
