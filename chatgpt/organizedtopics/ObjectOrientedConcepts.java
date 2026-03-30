package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Object-Oriented Concepts
 * Description: OOP models real entities using classes, encapsulation, and behavior methods.
 * Real-world usage: Bank account systems with controlled deposit and withdrawal actions.
 */
import java.util.ArrayList;
import java.util.List;

public class ObjectOrientedConcepts {

    // Problem 1: Bank Account Class (Basic)
    // Explanation: Model account balance with deposit and withdraw operations.
    // Approach: Store balance in class field and update through methods.
    // Time Complexity: O(1) per operation
    // Space Complexity: O(1)
    public static class BankAccountBasic {
        private int balance;

        public BankAccountBasic(int balance) {
            this.balance = balance;
        }

        public void deposit(int amount) {
            balance += amount;
        }

        public boolean withdraw(int amount) {
            if (amount > balance) {
                return false;
            }
            balance -= amount;
            return true;
        }

        public int getBalance() {
            return balance;
        }
    }

    // Problem 2: Bank Account with Validation (Encapsulation)
    // Explanation: Prevent invalid transactions with simple checks.
    // Approach: Allow only positive deposits and valid withdrawals.
    // Time Complexity: O(1) per operation
    // Space Complexity: O(1)
    public static class BankAccountValidated {
        private int balance;

        public BankAccountValidated(int balance) {
            this.balance = Math.max(0, balance);
        }

        public boolean deposit(int amount) {
            if (amount <= 0) {
                return false;
            }
            balance += amount;
            return true;
        }

        public boolean withdraw(int amount) {
            if (amount <= 0 || amount > balance) {
                return false;
            }
            balance -= amount;
            return true;
        }

        public int getBalance() {
            return balance;
        }
    }

    // Problem 3: Bank Account with Transaction History
    // Explanation: Track each deposit/withdraw action for auditing.
    // Approach: Append transaction messages into list after each operation.
    // Time Complexity: O(1) per operation
    // Space Complexity: O(t) for t transactions
    public static class BankAccountWithHistory {
        private int balance;
        private final List<String> history;

        public BankAccountWithHistory(int balance) {
            this.balance = Math.max(0, balance);
            this.history = new ArrayList<>();
        }

        public void deposit(int amount) {
            if (amount > 0) {
                balance += amount;
                history.add("Deposited " + amount);
            }
        }

        public boolean withdraw(int amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                history.add("Withdrew " + amount);
                return true;
            }
            history.add("Failed withdrawal " + amount);
            return false;
        }

        public int getBalance() {
            return balance;
        }

        public List<String> getHistory() {
            return new ArrayList<>(history);
        }
    }

    public static void main(String[] args) {
        BankAccountBasic basic = new BankAccountBasic(1000);
        basic.deposit(500);
        basic.withdraw(300);
        System.out.println("Basic Balance: " + basic.getBalance());

        BankAccountValidated validated = new BankAccountValidated(200);
        validated.deposit(100);
        validated.withdraw(50);
        System.out.println("Validated Balance: " + validated.getBalance());

        BankAccountWithHistory history = new BankAccountWithHistory(300);
        history.deposit(150);
        history.withdraw(100);
        System.out.println("History Balance: " + history.getBalance());
        System.out.println("History Log: " + history.getHistory());
    }
}
