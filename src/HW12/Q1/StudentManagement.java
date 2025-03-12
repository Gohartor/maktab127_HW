package HW12.Q1;

import java.util.*;

class StudentManagement {
    private final Map<Integer, Student> students = new HashMap<>();


    public void addStudent(int id, String name, int score) throws InvalidScoreException {
        students.put(id, new Student(id, name, score));
    }

    public void removeStudent(int id) {
        students.remove(id);
    }


    public void displayStudents() {
        for (Student student : students.values()) {
            System.out.println(student);
        }
    }


    public void updateScore(int id, int newScore) throws InvalidScoreException {
        if (!students.containsKey(id)) {
            System.out.println("student not found!");
        } else {
            students.get(id).setScore(newScore);
        }
    }


    public double calculateAverage() {
        if (students.isEmpty()) {
            return 0;
        } else {
            int sum = 0;
            for (Student s : students.values()) {
                sum += s.getScore();
            }
            return (double) sum / students.size();
        }
    }


    public List<Student> filterByGrade(String grade) {

        List<Student> filter = new ArrayList<>();

        for (Student s : students.values()) {
            if (s.getGrade().equals(grade)) {
                filter.add(s);
            }
        }
        return filter;
    }


    public List<Student> findTopStudents() {

        List<Student> sortedList = new ArrayList<>(students.values());

        /*
        ***** 01) interface comparator *****
        sortedList.sort(new StudentScoreComparator());
        */

        /*
        ***** 02) lambda simple *****
        sortedList.sort((o1, o2) -> o1.getScore() - o2.getScore());
        */

        // ***** 03) lambda *****
        //sortedList.sort(Comparator.comparingInt(Student::getScore));  soodi mishe
        sortedList.sort(Comparator.comparingInt(Student::getScore).reversed());


        /*
        ***** 04) lambda old java
        sortedList.sort(Comparator.comparingInt(s -> s.getScore()));
        */


        /*
        ***** 05) anonymous class for interface *****
        sortedList.sort(new Comparator<>() {
            @Override
            public int compare(Student o1, Student o2) {
                return 0;
            }
        });
        */

        return sortedList.subList(0, 2);
    }



}