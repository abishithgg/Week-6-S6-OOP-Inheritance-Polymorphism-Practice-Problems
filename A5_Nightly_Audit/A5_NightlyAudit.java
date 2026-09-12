package A5_Nightly_Audit;

class LibraryMember {

    private int borrowLimit;
    private int booksBorrowed;

    // Shared counter for all members
    private static int membersEnrolled = 0;

    // Final member number cannot be changed
    private final String memberNumber;

    // Constructor
    public LibraryMember(int borrowLimit) {

        if (borrowLimit <= 0) {
            throw new IllegalArgumentException(
                "Borrow limit must be positive"
            );
        }

        this.borrowLimit = borrowLimit;
        this.booksBorrowed = 0;

        // Increase counter for every new object
        membersEnrolled++;

        // Create unique member number
        memberNumber = "LIB-" + (100 + membersEnrolled);
    }

    // No-argument borrowBook()
    public void borrowBook() {

        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    // Overloaded borrowBook()
    public void borrowBook(String genre) {

        // Reuse the original method
        borrowBook();

        // Genre is recorded/accepted here
        System.out.println(
            "Genre: " + genre
        );
    }

    // Return borrowed book count
    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    // Return final member number
    public String getMemberNumber() {
        return memberNumber;
    }

    // Validate renewal code
    public static boolean isValidRenewalCode(
            String code) {

        // Must contain exactly 4 characters
        if (code == null || code.length() != 4) {
            return false;
        }

        // First character must be R
        if (code.charAt(0) != 'R') {
            return false;
        }

        // Characters 1 and 2 must be digits
        if (!Character.isDigit(code.charAt(1)) ||
            !Character.isDigit(code.charAt(2))) {

            return false;
        }

        // Last character must be uppercase
        if (!Character.isUpperCase(code.charAt(3))) {
            return false;
        }

        return true;
    }

    // Return total enrolled members
    public static int getMembersEnrolled() {
        return membersEnrolled;
    }
}


// FacultyMember is a subclass of LibraryMember
class FacultyMember extends LibraryMember {

    private String department;

    public FacultyMember(
            int borrowLimit,
            String department) {

        super(borrowLimit);

        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}


public class A5_NightlyAudit {

    static String processNightlyAudit(
            LibraryMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int faculty = 0;
        int regular = 0;

        for (LibraryMember member : members) {

            // Safely handle null
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            // Check whether member is FacultyMember
            if (member instanceof FacultyMember) {
                faculty++;
            } else {
                regular++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + faculty
                + " faculty | "
                + regular
                + " regular";
    }


    public static void main(String[] args) {

        // Create first member
        LibraryMember m1 =
                new LibraryMember(3);

        System.out.println(
            "Member number: "
            + m1.getMemberNumber()
        );

        System.out.println(
            "Members enrolled: "
            + LibraryMember.getMembersEnrolled()
        );


        // Renewal code validation
        System.out.println(
            LibraryMember.isValidRenewalCode("R12A")
        );

        System.out.println(
            LibraryMember.isValidRenewalCode("R1A")
        );

        System.out.println(
            LibraryMember.isValidRenewalCode("X12A")
        );


        // Method overloading
        m1.borrowBook();
        m1.borrowBook("Fiction");

        System.out.println(
            "Books borrowed: "
            + m1.getBooksBorrowed()
        );


        // Nightly audit
        LibraryMember[] members = {

            new FacultyMember(
                5,
                "Physics"
            ),

            null,

            new LibraryMember(3)
        };

        System.out.println(
            processNightlyAudit(members)
        );
    }
}
