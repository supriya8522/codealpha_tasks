import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GradeManager {

    private ArrayList<Student> students = new ArrayList<>();
    private int nextId = 1;

    private static final String FILE_NAME = "students.csv";

    public GradeManager() {
        loadFromFile();
    }

    public void addStudent(String name, String subject, double score) {
        students.add(new Student(nextId++, name, subject, score));

        saveToFile();

        System.out.println("\n  Student \"" + name + "\" added successfully!");
    }

    public boolean removeStudent(int id) {

        boolean removed = students.removeIf(s -> s.getId() == id);

        if (removed) {
            saveToFile();
        }

        return removed;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public double getAverage() {

        if (students.isEmpty())
            return 0;

        double sum = 0;

        for (Student s : students) {
            sum += s.getScore();
        }

        return sum / students.size();
    }

    public Student getHighest() {

        return students.stream()
                .max(Comparator.comparingDouble(Student::getScore))
                .orElse(null);
    }

    public Student getLowest() {

        return students.stream()
                .min(Comparator.comparingDouble(Student::getScore))
                .orElse(null);
    }

    public int getTotalStudents() {
        return students.size();
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    public Student findById(int id) {

        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void saveToFile() {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Student s : students) {

                writer.println(
                        s.getId() + "," +
                        s.getName() + "," +
                        s.getSubject() + "," +
                        s.getScore()
                );
            }

        } catch (IOException e) {

            System.out.println("Error saving data.");
        }
    }

    private void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String subject = parts[2];
                double score = Double.parseDouble(parts[3]);

                students.add(new Student(id, name, subject, score));

                if (id >= nextId) {
                    nextId = id + 1;
                }
            }

        } catch (IOException e) {

            System.out.println("Error loading data.");
        }
    }
}