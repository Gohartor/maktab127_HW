package HW12.Q1;

import java.util.Comparator;

class StudentScoreComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return s1.getScore() - s2.getScore();
        // ***** 01 *****
//        if (s1.getScore() > s2.getScore()) {
//            return +1;
//        } else if (s1.getScore() == s2.getScore()) {
//            return 0;
//        } else {
//            return -1;
//        }

        // ***** 02 *****
//        return Integer.compare(s1.getScore(), s2.getScore());  nozoli mishe
//        return Student.compare(s2.getScore(), s1.getScore());
    }

}
