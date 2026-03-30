package dsa.handbook;

/*
==================================================
📌 PROBLEM: Bank Account (OOP)
==================================================

Imagine this real scenario:
Real bank accounts must reject invalid money operations.

You are given:
Input operations: open, deposit, withdraw.
Output balance and success flag.

👉 Your task:
Keep account state valid after every operation.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Let’s reason before coding:
open=1000, deposit 500 => 1500, withdraw 1200 => 300, withdraw 400 => fail and stay 300.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

A beginner may try this:
Public balance mutation bypasses rules like insufficient funds.

Why it fails:
It misses the key invariant of this specific problem.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Step1 balance=1000
Step2 deposit(500)->1500
Step3 withdraw(1200)->success,true,balance300
Step4 withdraw(400)->false,balance300.

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Timeline: 1000 -> 1500 -> 300 -> 300.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

Validation-based O(1) methods for every operation.

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

negative opening, zero/negative deposit, over-withdraw.

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

calling withdraw twice in print changes state unintentionally.

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interviewer expects encapsulation + validation + clear method contracts.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Clear variable names
- Separate logic into methods
- Comments explain WHY, not WHAT

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Key trick: private balance and guarded methods only.

==================================================
*/
public class BankAccount {
    private final String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double openingBalance) {
        if (openingBalance < 0) throw new IllegalArgumentException("Opening balance cannot be negative");
        this.accountHolder = accountHolder;
        this.balance = openingBalance;
    }

    // Deposit operation.
    // Time: O(1), Space: O(1)
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive");
        balance += amount;
    }

    // Withdraw operation with insufficient-funds check.
    // Time: O(1), Space: O(1)
    public boolean withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw amount must be positive");
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    public double getBalance() { return balance; }

    @Override
    public String toString() {
        return "BankAccount{holder='" + accountHolder + "', balance=" + balance + "}";
    }

    public static void main(String[] args) {
        BankAccount acc = new BankAccount("Alex", 1000);

        System.out.println("Input: create account holder=Alex, opening=1000");
        System.out.println("Expected: balance=1000.0 | Actual: " + acc.getBalance());

        acc.deposit(500);
        System.out.println("Input: deposit(500)");
        System.out.println("Expected: balance=1500.0 | Actual: " + acc.getBalance());

        boolean withdraw1 = acc.withdraw(1200);
        System.out.println("Input: withdraw(1200)");
        System.out.println("Expected: success=true, balance=300.0 | Actual: success=" + withdraw1 + ", balance=" + acc.getBalance());

        boolean withdraw2 = acc.withdraw(400);
        System.out.println("Input: withdraw(400)");
        System.out.println("Expected: success=false, balance=300.0 | Actual: success=" + withdraw2 + ", balance=" + acc.getBalance());

        System.out.println("Final Account Snapshot: " + acc);
    }
}



