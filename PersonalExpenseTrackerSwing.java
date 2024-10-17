import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private static final String DATA_FILE = "expenses.txt";
    private static Map<String, List<Expense>> expenses = new HashMap<>();

    public static void main(String[] args) {
        loadExpenses();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Personal Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. View Summary");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    deleteExpense(scanner);
                    break;
                case 4:
                    viewSummary();
                    break;
                case 5:
                    saveExpenses();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter expense description: ");
        String description = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter category (e.g., Food, Transport, Entertainment): ");
        String category = scanner.nextLine();

        Expense expense = new Expense(description, amount, category);
        expenses.computeIfAbsent(category, k -> new ArrayList<>()).add(expense);

        System.out.println("Expense added successfully.");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        for (String category : expenses.keySet()) {
            System.out.println("\nCategory: " + category);
            for (Expense expense : expenses.get(category)) {
                System.out.println(expense);
            }
        }
    }

    private static void deleteExpense(Scanner scanner) {
        System.out.print("Enter the category of the expense to delete: ");
        String category = scanner.nextLine();

        List<Expense> categoryExpenses = expenses.get(category);
        if (categoryExpenses == null || categoryExpenses.isEmpty()) {
            System.out.println("No expenses found in this category.");
            return;
        }

        System.out.println("\nCategory: " + category);
        for (int i = 0; i < categoryExpenses.size(); i++) {
            System.out.println((i + 1) + ". " + categoryExpenses.get(i));
        }

        System.out.print("Enter the number of the expense to delete: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < categoryExpenses.size()) {
            categoryExpenses.remove(index);
            if (categoryExpenses.isEmpty()) {
                expenses.remove(category);
            }
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void viewSummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        double total = 0;
        System.out.println("\n--- Expense Summary ---");
        for (String category : expenses.keySet()) {
            double categoryTotal = expenses.get(category).stream().mapToDouble(Expense::getAmount).sum();
            total += categoryTotal;
            System.out.println("Category: " + category + " - Total: $" + categoryTotal);
        }
        System.out.println("Total Spending: $" + total);
    }

    private static void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (String category : expenses.keySet()) {
                for (Expense expense : expenses.get(category)) {
                    writer.println(category + "|" + expense.getDescription() + "|" + expense.getAmount());
                }
            }
            System.out.println("Expenses saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String category = parts[0];
                String description = parts[1];
                double amount = Double.parseDouble(parts[2]);

                Expense expense = new Expense(description, amount, category);
                expenses.computeIfAbsent(category, k -> new ArrayList<>()).add(expense);
            }
        } catch (IOException e) {
            System.out.println("No existing expense data found. Starting fresh.");
        }
    }
}

class Expense {
    private String description;
    private double amount;
    private String category;

    public Expense(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Description: " + description + ", Amount: $" + amount;
    }
}

