import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GradeManager {
    private ArrayList<Student> students = new ArrayList<>();
    private int nextId = 1;

    private static final String DATA_DIR  = System.getProperty("user.dir") + File.separator + "data";
    private static final String CSV_PATH  = DATA_DIR + File.separator + "grades.csv";

    public GradeManager() {
        createDataFolder();
        loadFromCSV();
    }

    private void createDataFolder() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) System.out.println("  Created data folder at: " + DATA_DIR);
        }
    }

    public void addStudent(String name, String subject, double score) {
        students.add(new Student(nextId++, name, subject, score));
        System.out.println("\n  Student \"" + name + "\" added successfully!");
        saveToCSV();
    }

    public boolean removeStudent(int id) {
        boolean removed = students.removeIf(s -> s.getId() == id);
        if (removed) saveToCSV();
        return removed;
    }

    public List<Student> getAllStudents() { return students; }

    public double getAverage() {
        if (students.isEmpty()) return 0;
        double sum = 0;
        for (Student s : students) sum += s.getScore();
        return sum / students.size();
    }

    public Student getHighest() {
        return students.stream().max(Comparator.comparingDouble(Student::getScore)).orElse(null);
    }

    public Student getLowest() {
        return students.stream().min(Comparator.comparingDouble(Student::getScore)).orElse(null);
    }

    public int getTotalStudents() { return students.size(); }
    public boolean isEmpty()      { return students.isEmpty(); }

    public Student findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    private void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_PATH))) {
            pw.println("ID,Name,Subject,Score,Grade");
            for (Student s : students) {
                pw.printf("%d,%s,%s,%.1f,%s%n",
                        s.getId(), s.getName(), s.getSubject(),
                        s.getScore(), s.getGrade());
            }
            System.out.println("  Saved to: " + CSV_PATH);
        } catch (IOException e) {
            System.out.println("  Warning: Could not save CSV — " + e.getMessage());
        }
    }

    private void loadFromCSV() {
        File file = new File(CSV_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            int maxId = 0;
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                int id       = Integer.parseInt(parts[0].trim());
                String name  = parts[1].trim();
                String subj  = parts[2].trim();
                double score = Double.parseDouble(parts[3].trim());
                students.add(new Student(id, name, subj, score));
                if (id > maxId) maxId = id;
            }
            nextId = maxId + 1;
            if (!students.isEmpty())
                System.out.println("  Loaded " + students.size() + " student(s) from saved data.");
        } catch (IOException e) {
            System.out.println("  Warning: Could not load CSV — " + e.getMessage());
        }
    }
}