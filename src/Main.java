import java.util.Scanner;

public class Main {
    private static GradeManager manager = new GradeManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ReportPrinter.printHeader();

        boolean running = true;
        while (running) {
            ReportPrinter.printMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addStudentFlow();
                    break;
                case "2":
                    ReportPrinter.printAllStudents(manager.getAllStudents());
                    break;
                case "3":
                    ReportPrinter.printSummaryReport(manager);
                    break;
                case "4":
                    removeStudentFlow();
                    break;
                case "5":
                    running = false;
                    System.out.println("\n  Goodbye! Exiting Student Grade Tracker.\n");
                    break;
                default:
                    System.out.println("\n  Invalid choice. Please enter 1-5.");
            }
        }

        scanner.close();
    }

    private static void addStudentFlow() {
        System.out.println("\n  -- ADD STUDENT --");

        System.out.print("  Enter student name    : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("  Name cannot be empty."); return; }

        System.out.print("  Enter subject         : ");
        String subject = scanner.nextLine().trim();
        if (subject.isEmpty()) { System.out.println("  Subject cannot be empty."); return; }

        double score = -1;
        while (score < 0 || score > 100) {
            System.out.print("  Enter score (0-100)   : ");
            String scoreInput = scanner.nextLine().trim();
            try {
                score = Double.parseDouble(scoreInput);
                if (score < 0 || score > 100) {
                    System.out.println("  Score must be between 0 and 100. Try again.");
                    score = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Invalid number. Try again.");
            }
        }

        manager.addStudent(name, subject, score);
    }

    private static void removeStudentFlow() {
        if (manager.isEmpty()) {
            System.out.println("\n  No students to remove.");
            return;
        }

        ReportPrinter.printAllStudents(manager.getAllStudents());
        System.out.print("  Enter student ID to remove: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Student found = manager.findById(id);
            if (found == null) {
                System.out.println("  No student found with ID " + id + ".");
            } else {
                boolean removed = manager.removeStudent(id);
                if (removed)
                    System.out.println("  Student \"" + found.getName() + "\" removed successfully.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  Invalid ID entered.");
        }
    }
}
