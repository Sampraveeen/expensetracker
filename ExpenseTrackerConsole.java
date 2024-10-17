import java.util.Scanner;

public class ExpenseTrackerConsole {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Personal Expense Tracker ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Show Summary");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline

                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();

                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();

                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();

                    Expense expense = new Expense(amount, category, date, description);
                    expenseManager.addExpense(expense);
                    break;

                case 2:
                    expenseManager.viewExpenses();
                    break;

                case 3:
                    System.out.print("Enter the expense number to delete: ");
                    int index = scanner.nextInt() - 1;
                    expenseManager.deleteExpense(index);
                    break;

                case 4:
                    expenseManager.showSummary();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
