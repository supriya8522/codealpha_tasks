import java.util.List;

public class ReportPrinter {

    private static final String BORDER = "=".repeat(65);
    private static final String DIVIDER = "-".repeat(65);

    public static void printHeader() {
        System.out.println("\n" + BORDER);
        System.out.println("         STUDENT GRADE TRACKER");
        System.out.println(BORDER);
    }

    public static void printMenu() {
        System.out.println("\n" + DIVIDER);
        System.out.println("  MENU");
        System.out.println(DIVIDER);
        System.out.println("  1. Add Student");
        System.out.println("  2. View All Students");
        System.out.println("  3. View Summary Report");
        System.out.println("  4. Remove Student");
        System.out.println("  5. Exit");
        System.out.println(DIVIDER);
        System.out.print("  Enter your choice: ");
    }

    public static void printAllStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("\n  No students found.");
            return;
        }
        System.out.println("\n" + BORDER);
        System.out.printf("  %-4s %-20s %-15s %-8s %s%n", "ID", "Name", "Subject", "Score", "Grade");
        System.out.println(DIVIDER);
        for (Student s : students) {
            System.out.println("  " + s);
        }
        System.out.println(BORDER);
    }

    public static void printSummaryReport(GradeManager manager) {
        System.out.println("\n" + BORDER);
        System.out.println("  SUMMARY REPORT");
        System.out.println(DIVIDER);

        if (manager.isEmpty()) {
            System.out.println("  No data available yet.");
            System.out.println(BORDER);
            return;
        }

        System.out.printf("  Total Students : %d%n", manager.getTotalStudents());
        System.out.printf("  Average Score  : %.2f%n", manager.getAverage());

        Student high = manager.getHighest();
        Student low  = manager.getLowest();

        System.out.printf("  Highest Score  : %.1f  (%s — %s)%n",
                high.getScore(), high.getName(), high.getSubject());
        System.out.printf("  Lowest  Score  : %.1f  (%s — %s)%n",
                low.getScore(), low.getName(), low.getSubject());

        System.out.println(DIVIDER);
        System.out.println("  Grade Distribution:");
        System.out.println(DIVIDER);

        int[] counts = new int[5]; // A B C D F
        for (Student s : manager.getAllStudents()) {
            switch (s.getGrade()) {
                case "A": counts[0]++; break;
                case "B": counts[1]++; break;
                case "C": counts[2]++; break;
                case "D": counts[3]++; break;
                case "F": counts[4]++; break;
            }
        }
        String[] labels = {"A (90-100)", "B (80-89)", "C (70-79)", "D (60-69)", "F (0-59)"};
        for (int i = 0; i < labels.length; i++) {
            String bar = "#".repeat(counts[i] * 3);
            System.out.printf("  %-12s | %-20s (%d)%n", labels[i], bar, counts[i]);
        }

        System.out.println(BORDER);
    }
}
