package a4;

import java.util.ArrayList;

public class CourseManager {
    /**
     * Maintains a record of all courses successfully registered.
     * It is guaranteed that students enrolled in a course must exist in students.
     */
    private ArrayList<Course> courses;

    /**
     * Maintains a record of all students successfully registered.
     * It is guaranteed that courses student enrolled in must exist in courses.
     */
    private ArrayList<Student> students;

    /**
     * Represent system open(true) or not(false).
     */
    private boolean ifOpen;

    public ArrayList<Student> getStudents() {
    }

    public ArrayList<Course> getCourses() {
    }

    public void setIfOpen(Boolean ifOpen) {
    }

    public boolean getIfOpen() {
    }

    /**
     * Register a course. Add a student object to students and set the courseManager
     * of the student object to this manager. It is guaranteed that all studentIDs are
     * unique.
     */
    public void addStudent(Student student) {
    }


    /**
     * Register a course. Add a course object to courses and set the courseManager
     * of the course object to this manager. It is guaranteed that all courseIDs are
     * unique.
     */
    public void addCourse(Course course) {

    }

    /**
     * Attempts to enroll a Student in a Course.
     * Enrollment will only be successful if the course exists, the student has not
     * already enrolled, the credits is greater than 0, and they have enough credits to
     * bid.
     * If successful, the student's credits will be reduced by the amount bid on the
     * course. The corresponding list in Student and Course should be updated.
     * Only available when ifOpen is true. Return false if ifOpen is false.
     *
     * @return boolean Returns true if student enroll this course successfully;
     * otherwise, it returns false.
     */
    public boolean enrollStudentInCourse(Student student, String courseId, int
            credits) {

    }

    /**
     * Modifies the number of credits a student has bid on an already enrolled
     * course.
     * The modification will only be successful if the course exists, the student is
     * currently enrolled in the course,and the new bid is within the student's
     * available credits. This can be used to increase or decrease the bid.
     * On a successful bid modification, the student's credits will be updated to
     * reflect the new bid amount. The corresponding list in Student and Course should
     * be updated.
     * Only available when ifOpen is true. Return false if ifOpen is false.
     *
     * @return boolean Returns true if the bid modification was successful;
     * otherwise, it returns false.
     */
    public boolean modifyStudentEnrollmentCredits(Student student, String courseId,
                                                  int credits) {

    }

    /**
     * Drops a student's enrollment from a specific course.
     * The drop will succeed only if the course exists, and the student is currently
     * enrolled in it.
     * If ifOpen is true, upon a successful drop, any credits the student had bid
     * for this course will be refunded in full. The corresponding list in Student and
     * Course should be updated.
     * Only available when ifOpen is true. Return false if ifOpen is false.
     *
     * @return boolean Returns true if the student successfully drops the course;
     * otherwise, it returns false.
     */
    public boolean dropStudentEnrollmentCourse(Student student, String courseId) {

    }

    /**
     * Completes the course registration process. Change ifOpen to false.
     * This method resolves which students get into each course based on their bids
     * and the course capacities. Students with higher bids are prioritized. The
     * corresponding list in Student and Course should be updated.
     * Only successStudents in class Course and successCourses in class Student need
     * to be updated.
     */
    public void finalizeEnrollments() {
    }

    /**
     * Retrieves a list of courses with associated credits for a given student.
     * Each String in the list includes the course ID and the points bid by the
     * student in enrollCourses, follow the format: "courseID: enrollmentCredits"
     * (without quotes).
     * Only available when ifOpen is true. Return null if ifOpen is false.
     *
     * @return A list of Strings, each representing the courses and the respective
     * credits the student enrolled.
     */
    public ArrayList<String> getEnrolledCoursesWithCredits(Student student) {
    }
}
