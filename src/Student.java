public class Student {
    private int id;
    private String name;
    private String subject;
    private double score;

    public Student(int id, String name, String subject, double score) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.score = score;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSubject() { return subject; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public String getGrade() {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return String.format("%-4d %-20s %-15s %-8.1f %s",
                id, name, subject, score, getGrade());
    }
}
