package HW12.Q1;

import java.util.Comparator;

class StudentScoreComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
//        return Integer.compare(s1.getScore(), s2.getScore());  nozoli mishe
        return Integer.compare(s2.getScore(), s1.getScore());
    }

}
