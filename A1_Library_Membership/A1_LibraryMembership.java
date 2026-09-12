package A1_Library_Membership;

class LibraryMember {

    private String memberId;
    private int borrowLimit;
    private int booksBorrowed;

    // Constructor with validation
    public LibraryMember(String memberId, int borrowLimit) {

        if (memberId == null ||
            memberId.trim().length() < 4) {

            throw new IllegalArgumentException(
                "Member ID must have at least 4 characters"
            );
        }

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
}


// StudentMember inherits from LibraryMember
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
}


public class A1_LibraryMembership {

    // Enroll many members and count rejected IDs
    static String enrollBatch(
            String[] memberIds,
            int borrowLimit) {

        int enrolled = 0;
        int rejected = 0;

        for (String id : memberIds) {

            try {
                // Validation happens inside constructor
                new LibraryMember(id, borrowLimit);
                enrolled++;

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Enrolled: " + enrolled
                + " | Rejected: " + rejected;
    }


    public static void main(String[] args) {

        // Test StudentMember
        StudentMember s =
                new StudentMember("STU10", 3, "CSE");

        s.borrowBook();
        s.borrowBook();

        System.out.println(
            "Books borrowed: "
            + s.getBooksBorrowed()
        );


        // Test batch enrollment
        String[] memberIds = {
            "STU1",
            "LB1",
            "STU2",
            " ",
            "STU3"
        };

        System.out.println(
            enrollBatch(memberIds, 3)
        );


        // Test invalid member ID
        try {

            new LibraryMember("LB1", 3);

        } catch (IllegalArgumentException e) {

            System.out.println(
                "Construction rejected"
            );
        }
    }
}
