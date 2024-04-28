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

    public CourseManager() {
        ifOpen = true;
        courses = new ArrayList<>();
        students = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setIfOpen(Boolean ifOpen) {
        this.ifOpen = ifOpen;
    }

    public boolean getIfOpen() {
        return ifOpen;
    }

    /**
     * Register a course. Add a student object to students and set the courseManager
     * of the student object to this manager. It is guaranteed that all studentIDs are
     * unique.
     */
    public void addStudent(Student student) {
        students.add(student);
        student.setCourseManager(this);
    }


    /**
     * Register a course. Add a course object to courses and set the courseManager
     * of the course object to this manager. It is guaranteed that all courseIDs are
     * unique.
     */
    public void addCourse(Course course) {
        courses.add(course);
        course.setCourseManager(this);
    }

    /**
     * Attempts to enroll a Student in a Course.
     * <p>
     * If successful, the student's credits will be reduced by the amount bid on the
     * course. The corresponding list in Student and Course should be updated.
     * Only available when ifOpen is true. Return false if ifOpen is false.
     *
     * @return boolean Returns true if student enroll this course successfully;
     * otherwise, it returns false.
     */
    public boolean enrollStudentInCourse(Student student, String courseId, int
            credits) {
        var course = getCourse(courseId);
        if (!ifOpen || failedToEnroll(student, course, credits)) {
            return false;
        }
        student.setCredits(student.getCredits() - credits);
        student.getEnrollCourses().add(course);
        course.getEnrollStudent().add(student);
        course.getCredits().add(credits);
        return true;
    }

    /**
     * Enrollment will only be successful if the course exists, the student has not
     * already enrolled, the credits is greater than 0, and they have enough credits to
     * bid.
     */
    private boolean failedToEnroll(Student student, Course course, int credits) {
        if (course == null) {
            return true;
        }
        if (credits <= 0 || credits > student.getCredits()) {
            return true;
        }
        var enrollStudents = course.getEnrollStudent();
        for (var enrollStudent : enrollStudents) {
            if (enrollStudent.equals(student)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get course by ID.
     */
    private Course getCourse(String courseId) {
        for (var course : courses) {
            if (course.getCourseID().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Modifies the number of credits a student has bid on an already enrolled
     * course.
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
        var course = getCourse(courseId);
        if (!ifOpen || failedToModify(student, course, credits)) {
            return false;
        }

        int index = course.getEnrollStudent().indexOf(student);
        var courseCredits = course.getCredits();
        int prevCredits = courseCredits.get(index);

        student.setCredits(student.getCredits() + prevCredits - credits);
        courseCredits.set(index, credits);

        return true;
    }

    /**
     * The modification will only be successful if the course exists, the student is
     * currently enrolled in the course,and the new bid is within the student's
     * available credits. This can be used to increase or decrease the bid.
     */
    private boolean failedToModify(Student student, Course course, int credits) {
        if (course == null) {
            return true;
        }
        // Never enrolled.
        if (!course.getEnrollStudent().contains(student)) {
            return true;
        }

        int index = course.getEnrollStudent().indexOf(student);
        int prevCredits = course.getCredits().get(index);
        return student.getCredits() + prevCredits - credits < 0;
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
        var course = getCourse(courseId);
        if (!ifOpen || failToDrop(student, course)) {
            return false;
        }

        int index = course.getEnrollStudent().indexOf(student);
        int credits = course.getCredits().get(index);

        student.setCredits(student.getCredits() + credits);
        student.getEnrollCourses().remove(course);
        course.getEnrollStudent().remove(student);
        course.getCredits().remove(index);

        return true;
    }

    private boolean failToDrop(Student student, Course course) {
        return course == null || !course.getEnrollStudent().contains(student);
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
        ifOpen = false;
        for (var course : courses) {
            var enrollStudent = course.getEnrollStudent();
            var credits = course.getCredits();

            var temp = new ArrayList<>(enrollStudent);
            enrollStudent.sort((s1, s2) -> {
                int n1 = temp.indexOf(s1);
                int n2 = temp.indexOf(s2);
                return credits.get(n2) - credits.get(n1);
            });
            credits.sort((c1, c2) -> c2 - c1);

            var successStudents = course.getSuccessStudents();
            int maxCap = course.getMaxCapacity();
            for (int i = 0; i < Math.min(maxCap, enrollStudent.size()); i++) {
                successStudents.add(enrollStudent.get(i));
            }
            if (maxCap < enrollStudent.size()) {
                int lastCredit = credits.get(maxCap);
                int end = successStudents.size() - 1;
                while (!successStudents.isEmpty() && credits.get(end) == lastCredit) {
                    successStudents.remove(end);
                    end -= 1;
                }
            }

            for (var student : successStudents) {
                student.getSuccessCourses().add(course);
            }
        }
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
        if (!ifOpen) {
            return null;
        }
        var enrollCourses = student.getEnrollCourses();
        var result = new ArrayList<String>();
        for (var course : enrollCourses) {
            int index = course.getEnrollStudent().indexOf(student);
            int credit = course.getCredits().get(index);
            String s = String.format("%s: %d", course.getCourseID(), credit);
            result.add(s);
        }
        return result;
    }
}
