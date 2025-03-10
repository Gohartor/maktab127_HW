package HW12.Q1;


class Student {
    private int id;
    private String name;
    private int score;

    public Student(int id, String name, int score) throws InvalidScoreException {
        this.id = id;
        this.name = name;
        setScore(score);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) throws InvalidScoreException {
        if (score < 0 || score > 100) {
            throw new InvalidScoreException("score must be between 0 and 100");
        }
        this.score = score;
    }

    public String getGrade() {
        if (score >= 90){
            return "A";
        } else if (score >= 80){
            return "B";
        } else if (score >= 70){
            return "C";
        } else if (score >= 60){
            return "D";
        } else {
            return "F";
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Score: " + score + ", Grade: " + getGrade();
    }

}