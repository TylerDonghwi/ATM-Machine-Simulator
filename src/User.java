import java.util.Scanner;

public class User {

	private String name;
	private int userID;
	private double balance;
	private int previousTransaction;

	public User(String name) {
		this.name = name;
		this.userID = (int) ((int) 10000000 * Math.random());
	}

	void checkBalance() {
		System.out.println("Checking the balance in your account:");
		System.out.println("User ID: " + this.userID);
		System.out.printf("Balance: $%.2f", this.balance);
		System.out.println();
	}

	void deposit(int amount) {
		if (amount > 0) {
			this.balance += amount;
			this.previousTransaction = amount;
			System.out.println("Deposited $" + amount);
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
		} else {
			System.out.println("The money is invalid");
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
		}
	}

	void withdrawl(int amount) {
		if (amount > 0 && this.balance > amount) {
			this.balance -= amount;
			this.previousTransaction = -amount;
			System.out.println("Withdrew $" + amount);
			System.out.printf("Balance: $%.2f", this.balance);
		} else if (amount > 0) {
			System.out.println("Not enough balance");
			System.out.printf("Balance: $%.2f", this.balance);
		} else {
			System.out.println("The money is invalid");
			System.out.printf("Balance: $%.2f", this.balance);
			System.out.println();
		}
	}

	void getPreviousTransaction() {
		if (this.previousTransaction > 0) {
			System.out.println("Deposited $" + previousTransaction);
			System.out.println();
		} else if (this.previousTransaction < 0) {
			System.out.println("Withdrawn $" + -previousTransaction);
			System.out.println();
		} else {
			System.out.println("No previous transactions.");
			System.out.println();
		}
	}

	void showMainScreen() {
		try (Scanner scanner = new Scanner(System.in)) {
			char userOption = '\0';
			System.out.println("Kia ora, " + this.name + "!");
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
				System.out.println("Which would you like to do?");

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
					int depoAmount = scanner.nextInt();
					deposit(depoAmount);
					break;
				case 'C':
					System.out.println("How much would you like to withdraw?");
					System.out.println();
					int withAmount = scanner.nextInt();
					withdrawl(withAmount);
					break;
				case 'D':
					getPreviousTransaction();
					break;
				case 'E':
					System.out.println("Thank you using our service! Have a wonderful day!");
					break;
				default:
					System.out.println("Error: invalid option, please enter A, B, C, D or E");
				}

			}
		}
	}
}
