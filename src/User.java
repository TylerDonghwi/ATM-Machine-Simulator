import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class User {

	private String name;
	private int userID;
	private double balance;
	private double previousTransactionAmount;
	private String previousTransactionAction;
	private int numTransaction;
	private boolean hasHistory;
	private String password;

	// new account will have no history with a randomly generated userID.
	public User() {
		this.userID = (int) ((Math.random() * (99999999 - 10000000)) + 10000000);
	}

	// existing account will have instances extracted from the existing file
	public User(String name, int userID, double balance, int numTransaction, String password) {
		this.name = name;
		this.userID = userID;
		this.balance = balance;
		this.numTransaction = numTransaction;
		this.password = password;
		this.hasHistory = true;
	}

	// for a new account, setting a name.
	public void setName() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String name = scanner.nextLine();
			if (!name.contains(" ")) {
				System.out.println();
				System.out.println("Please enter your first name and last name");
				System.out.println();
			} else if (name.length() <= 30) {
				this.name = name;
				break;
			} else {
				System.out.println();
				System.out.println("Your name is too long.");
				System.out.println();
			}
		}
	}

	// returns the name without spaces to create a file
	private String getNameWithoutSpace() {
		return this.name.replaceAll("\\s+", "");
	}

	// for a new account, setting a password
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

	// for an existing account, entering the password and making sure that it is
	// correct
	public void enterPassword() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String password = scanner.nextLine();
			if (password.equals(this.password)) {
				break;
			} else {
				System.out.println();
				System.out.println("wrong password, try again");
				System.out.println();
			}
		}
	}

	// checking the account balance
	void checkBalance() {
		System.out.println("Checking the balance in your account:");
		System.out.println("User ID: " + this.userID);
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();
	}

	// making a deposit
	void deposit(double depoAmount) throws IOException {
		this.balance += depoAmount;
		this.balance = Math.floor(this.balance * 100.0) / 100.0;
		this.previousTransactionAmount = depoAmount;
		this.previousTransactionAction = "Deposit";
		this.numTransaction++;
		System.out.println();
		System.out.println("Deposited $" + depoAmount);
		giveInterest();
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();
		appendFile();

	}

	// making a withdrawl
	void withdrawl(double withAmount) throws IOException {
		this.balance -= withAmount;
		this.balance = Math.floor(this.balance * 100.0) / 100.0;
		this.previousTransactionAmount = -withAmount;
		this.previousTransactionAction = "Withdrawl";
		this.numTransaction++;
		System.out.println();
		System.out.println("Withdrew $" + withAmount);
		giveInterest();
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();

		appendFile();
	}

	// making a transfer
	void transfer(double transferAmount, String name) throws IOException {
		this.balance -= transferAmount;
		this.balance = Math.floor(this.balance * 100.0) / 100.0;
		this.previousTransactionAmount = -transferAmount;
		this.previousTransactionAction = "Transfer";
		this.numTransaction++;
		System.out.println();
		System.out.println("Transfered $" + transferAmount + " to " + name);
		giveInterest();
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();

		appendFile(name);
	}

	// getting the previous transaction
	void getPreviousTransaction() {
		if (this.previousTransactionAmount > 0) {
			System.out.println();
			System.out.println("Deposited $" + previousTransactionAmount);
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
		} else if (this.previousTransactionAction == null) {
			System.out.println();
			System.out.println("No previous transactions.");
			System.out.println();
		} else if (this.previousTransactionAction.equals("Withdrawl")) {
			System.out.println();
			System.out.println("Withdrawn $" + -previousTransactionAmount);
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
		} else if (this.previousTransactionAction.equals("Transfer")) {
			System.out.println();
			System.out.println("Transfered $" + -previousTransactionAmount);
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("No previous transactions.");
			System.out.println();
		}
	}

	void giveInterest() {
		if (this.numTransaction % 5 == 0) {
			this.balance *= 1.01;
			this.balance = Math.floor(this.balance * 100.0) / 100.0;
			System.out.println(
					"This is your " + this.numTransaction + "th transaction! 1% interest is added to your account!");
		}
	}

	// main method that gets run
	void showMainScreen(int time) throws IOException {
		try (Scanner scanner = new Scanner(System.in)) {
			char userOption = '\0';

			if (this.hasHistory == false) {
				System.out.println();
				System.out.println("This is your first time here, to make a new account, please enter your full name.");
				System.out.println();
				setName();
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
				System.out.println();
				System.out.println("Please set your password.\nYour password should be between 8 to 15 digits long.");
				System.out.println();
				setPassword();
				System.out.println();
				System.out.println("Your new password is set as " + this.password);
				System.out.println();
			} else {
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
				System.out.println();
				System.out.println("Your user ID is " + this.userID + ".");
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

			while (userOption != 'F') {
				System.out
						.println("__________________________________________________________________________________");
				System.out.println();
				System.out.println("Which would you like to do?");
				System.out.println();
				System.out.println("A. See my account balance");
				System.out.println("B. Deposit into my account");
				System.out.println("C. Withdraw from my account");
				System.out.println("D. Transfer money into another account");
				System.out.println("E. See my previous transaction");
				System.out.println("F. Exit");
				System.out
						.println("__________________________________________________________________________________");
				System.out.println();

				userOption = Character.toUpperCase(scanner.next().charAt(0));

				switch (userOption) {
				case 'A':
					System.out.println();
					checkBalance();
					break;
				case 'B':
					double depoAmount = 0;
					while (true) {
						try {
							System.out.println();
							System.out.println("How much would you like to deposit?");
							System.out.println();
							depoAmount = Double.parseDouble(scanner.next());
							if (depoAmount > 0) {

								deposit(Math.floor(depoAmount * 100.0) / 100.0);
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
					double withAmount = -1;
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
							withAmount = Double.parseDouble(scanner.next());

							if (withAmount > 0 && this.balance >= withAmount) {
								withdrawl(Math.floor(withAmount * 100.0) / 100.0);
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

					double transferAmount = -1;

					if (this.balance == 0) {
						System.out.println();
						System.out.println("Your account doesn't have enough balace to transfer money");
						System.out.printf("Balance: $%.2f", this.balance);
						System.out.println();
						break;
					}
					// Ask the user who they are going to transfer the money to (make sure that the
					// name format is correct)
					System.out.println();
					System.out.println("Who's account would you like to transfer?");
					String name;
					int userId2 = 0;
					String nameNoSpace;
					boolean a = true;

					while (true) {
						name = scanner.nextLine();
						if (!name.contains(" ")) {
							System.out.println("Please enter their first name and last name");
							System.out.println();
						} else if (name.length() <= 30) {
							break;
						} else {
							System.out.println();
							System.out.println("Name is too long.");
							System.out.println();
						}
					}
					nameNoSpace = name.replaceAll("\\s", "");
					try {
						userId2 = readIDFromFile(nameNoSpace);
					} catch (java.io.FileNotFoundException e) {
						// if the user doesn't exist, create a new account
						System.out.println();
						System.out.println("Account does not exist for user");
						System.out.println();
						a = false;
					}

					if (name.toLowerCase().equals(this.name.toLowerCase())) {
						System.out.println();
						System.out.println("You can't transfer to yourself");
						System.out.println();
						a = false;
					}

					while (a) {
						try {
							System.out.println();
							System.out.println("What is their User ID?");
							System.out.println();
							int userId1 = scanner.nextInt();
							if (userId1 == userId2) {
								break;
							} else if (userId1 > 99999999) {
								System.out.println();
								System.out.println("Your input is too long.");
								System.out.println();
							} else {
								System.out.println();
								System.out.println("The User ID does not match, please try again");
								System.out.println();
							}
						} catch (NumberFormatException e) {
							System.out.println();
							System.out.println("Invalid format of User ID, try again.");
						}
					}

					while (a) {
						System.out.println();
						System.out.println("How much would you like to transfer?");
						System.out.println();
						transferAmount = Double.parseDouble(scanner.next());
						if (transferAmount > 0 && this.balance >= transferAmount) {
							transfer(Math.floor(transferAmount * 100.0) / 100.0, name);

							double balance = readBalanceFromFile(nameNoSpace);
							balance += transferAmount;

							appendTransFile(nameNoSpace, transferAmount, balance);

							break;
						} else if (transferAmount > 0) {
							System.out.println();
							System.out.println("Not enough balance");
							System.out.printf("Balance: $%.2f", this.balance);
							System.out.println();
						} else if (transferAmount == 0) {
							System.out.println();
							System.out.println("You can not transfer $0, please try again with a valid value");
							System.out.printf("Balance: $%.2f", this.balance);
							System.out.println();
						} else {
							System.out.println();
							System.out.println("Money can not be negative, please try again with a positive value.");
							System.out.printf("Balance: $%.2f", this.balance);
							System.out.println();
						}
					}

					break;
				case 'E':
					getPreviousTransaction();
					break;
				case 'F':
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

	// creating the initial file from the game
	public void makeInitialFileFromGame() throws IOException {
		FileWriter writer = new FileWriter(
				"/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");
		writer.write("ATM simulator history of the user " + this.name + ":\n");
		writer.write("User ID: " + this.userID);
		writer.write("\nPassword: " + this.password);
		writer.write("\nBalance: $" + this.balance + "\n\n");
		writer.close();
	}

	// if the user chooses to play from the existing account, the file will append
	// from existing file
	private void continueWriting() throws IOException {
		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");
		String fileContent = "";

		try (Scanner scan = new Scanner(file)) {
			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}
		}

		FileWriter writer = new FileWriter(
				"/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");
		writer.append(fileContent + "\nUser used the ATM simulator again, Balance: $" + this.balance + "\n");
		writer.close();
	}

	// every action (deposits and withdrawls) will be appended into the text file
	public void appendFile() throws IOException {
		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");

		try (Scanner scan = new Scanner(file)) {
			String fileContent = "";

			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}

			FileWriter writer = new FileWriter(
					"/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");
			if (this.numTransaction % 5 == 0) {
				writer.append(fileContent + "Transaction " + this.numTransaction + ": " + this.previousTransactionAction
						+ " of $" + Math.abs(previousTransactionAmount) + " + 1% interest, Balance: $" + this.balance);
			} else {
				writer.append(fileContent + "Transaction " + this.numTransaction + ": " + this.previousTransactionAction
						+ " of $" + Math.abs(previousTransactionAmount) + ", Balance: $" + this.balance);
			}

			writer.close();
		}
	}

	public void appendFile(String name) throws IOException {
		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");

		try (Scanner scan = new Scanner(file)) {
			String fileContent = "";

			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}

			FileWriter writer = new FileWriter(
					"/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");
			if (this.numTransaction % 5 == 0) {
				writer.append(fileContent + "Transaction " + this.numTransaction + ": " + this.previousTransactionAction
						+ " of $" + Math.abs(previousTransactionAmount) + " to " + name + ", + 1% interest, Balance: $"
						+ this.balance);
			} else {
				writer.append(fileContent + "Transaction " + this.numTransaction + ": " + this.previousTransactionAction
						+ " of $" + Math.abs(previousTransactionAmount) + " to " + name + ", Balance: $"
						+ this.balance);
			}

			writer.close();
		}
	}

	public void appendTransFile(String name, Double transferAmount, Double balance) throws IOException {
		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + name + ".txt");

		try (Scanner scan = new Scanner(file)) {
			String fileContent = "";

			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}

			FileWriter writer = new FileWriter("/Users/dongh/OneDrive/Desktop/ATM game file/history" + name + ".txt");
			writer.append(
					fileContent + "\nTransfer of $" + transferAmount + " from " + this.name + ", Balance: $" + balance);

			writer.close();
		}
	}

	// when the user exits with an e, it will close the action
	public void endFile() throws IOException {
		File file = new File("/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");
		try (Scanner scan = new Scanner(file)) {
			String fileContent = "";

			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}

			FileWriter writer = new FileWriter(
					"/Users/dongh/OneDrive/Desktop/ATM game file/history" + getNameWithoutSpace() + ".txt");
			writer.append(fileContent + "Finished using the ATM service. Balance: $" + this.balance);
			writer.close();
		}
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
			text = text.substring(text.indexOf("Balance:"), text.length());
			text = text.substring(10, text.length());
			balance = Double.parseDouble(text);

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
