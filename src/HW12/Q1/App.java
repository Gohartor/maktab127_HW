package HW12.Q1;

public class App {
    public static void main(String[] args) throws InvalidScoreException {

        StudentManagement students = new StudentManagement();

        students.addStudent(1, "hasan", 95);
        students.addStudent(2, "ali", 88);
        students.addStudent(3, "mohammad", 76);
        students.addStudent(4, "naqi", 60);
        students.addStudent(5, "taqi", 55);
        students.addStudent(6, "hoshang", 42);

        System.out.println("--------------------------------");
        System.out.println("all students: ");
        students.displayStudents();

        students.removeStudent(6);
        System.out.println("--------------------------------");
        System.out.println("all students after removing: ");
        students.displayStudents();


        students.updateScore(1,100);
        System.out.println("--------------------------------");
        System.out.println("all students after updating: ");
        students.displayStudents();



        System.out.println("--------------------------------");
        System.out.println("average: " + students.calculateAverage());



        System.out.println("--------------------------------");
        System.out.println("2 top Students:");
        for (Student student : students.findTopStudents()) {
            System.out.println(student);
        }


        System.out.println("--------------------------------");
        System.out.println("students with grade 'B' : ");
        for (Student student : students.filterByGrade("B")) {
            System.out.println(student);
        }
    }
}
