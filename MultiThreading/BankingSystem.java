import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private double balance;
    private Lock lock = new ReentrantLock();

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.printf("Deposited: %.2f | New Balance: %.2f%n", amount, balance);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.printf("Withdrew: %.2f | New Balance: %.2f%n", amount, balance);
            } else {
                System.out.printf("Insufficient funds for withdrawal: %.2f | Current Balance: %.2f%n", amount, balance);
            }
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        return balance;
    }
}

class TransactionThread extends Thread {
    private BankAccount account;
    private boolean isDeposit;
    private double amount;

    public TransactionThread(BankAccount account, boolean isDeposit, double amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    public TransactionThread(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        if (isDeposit) {
            account.deposit(amount);
        } else {
            account.withdraw(amount);
        }
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0); // Initial balance of $1000

        // Create threads for deposits and withdrawals
        TransactionThread t1 = new TransactionThread(account, true, 200.0); // Deposit $200
        TransactionThread t2 = new TransactionThread(account, false, 150.0); // Withdraw $150
        TransactionThread t3 = new TransactionThread(account, false, 500.0); // Withdraw $500
        TransactionThread t4 = new TransactionThread(account, true, 300.0); // Deposit $300
        TransactionThread t5 = new TransactionThread(account, false, 200.0); // Withdraw $200

        // Start threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
        }

        // Final balance
        System.out.printf("Final Balance: %.2f%n", account.getBalance());
    }
}
