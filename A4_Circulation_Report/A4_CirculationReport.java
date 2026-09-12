package A4_Circulation_Report;

class LibraryMember {

    private String memberId;
    private int borrowLimit;
    private int booksBorrowed;

    // Constructor
    public LibraryMember(String memberId, int borrowLimit) {

        // Member ID must contain at least 4 characters
        if (memberId == null || memberId.trim().length() < 4) {
            throw new IllegalArgumentException(
                "Member ID must have at least 4 characters"
            );
        }

        // Borrow limit must be positive
        if (borrowLimit <= 0) {
            throw new IllegalArgumentException(
                "Borrow limit must be positive"
            );
        }

        this.memberId = memberId;
        this.borrowLimit = borrowLimit;
        this.booksBorrowed = 0;
    }

    // Borrow one book
    public void borrowBook() {

        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    // Return number of borrowed books
    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    // Display information
    public String displayInfo() {

        return "General | Books: "
                + booksBorrowed;
    }
}


// StudentMember inherits LibraryMember
class StudentMember extends LibraryMember {

    private String course;

    public StudentMember(
            String memberId,
            int borrowLimit,
            String course) {

        // Call parent constructor
        super(memberId, borrowLimit);

        this.course = course;
    }

    // Getter for course
    public String getCourse() {
        return course;
    }

    // Override displayInfo()
    @Override
    public String displayInfo() {

        return "Student | Course: "
                + course
                + " | Books: "
                + getBooksBorrowed();
    }
}


public class A4_CirculationReport {

    // Build report for all members
    static String batchPrint(
            LibraryMember[] members) {

        // StringBuilder for the complete report
        StringBuilder report =
                new StringBuilder();

        for (LibraryMember member : members) {

            // Polymorphic method call
            report.append(
                member.displayInfo()
            );

            // Check before downcasting
            if (member instanceof StudentMember) {

                // Safe downcasting
                StudentMember student =
                        (StudentMember) member;

                report.append(
                    " [Course via downcast: "
                    + student.getCourse()
                    + "]"
                );
            }

            // Separator
            report.append(" | ");
        }

        return report.toString();
    }


    public static void main(String[] args) {

        // Create mixed memberships
        // Using 4-character IDs because
        // the constructor requires at least 4 characters
        LibraryMember[] members = {

            new LibraryMember(
                "LIB5",
                3
            ),

            new StudentMember(
                "STU6",
                3,
                "ECE"
            )
        };

        // Print complete report
        System.out.println(
            batchPrint(members)
        );


        // Example of safe downcasting
        LibraryMember plain =
                new LibraryMember(
                    "LIB6",
                    3
                );

        if (plain instanceof StudentMember) {

            StudentMember student =
                    (StudentMember) plain;

            System.out.println(
                student.getCourse()
            );

        } else {

            System.out.println(
                "Downcast avoided: not a StudentMember"
            );
        }
    }
}
