package A3_Student_Fine;

import java.util.Arrays;

class LibraryMember {

    private String memberId;
    private int borrowLimit;

    // Array to store every fine
    private int[] fineHistory;

    private int fineCount;

    public LibraryMember(String memberId, int borrowLimit) {

        if (memberId == null || memberId.trim().length() < 4) {
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

        // Maximum 10 fines
        this.fineHistory = new int[10];
        this.fineCount = 0;
    }

    // Parent method
    protected void chargeFine(int amount) {

        if (fineCount < fineHistory.length) {
            fineHistory[fineCount] = amount;
            fineCount++;
        }
    }

    // Return defensive copy
    public int[] getFineHistory() {

        return Arrays.copyOf(
            fineHistory,
            fineCount
        );
    }

    // Calculate total fine
    public int getTotalFine() {

        int total = 0;

        for (int i = 0; i < fineCount; i++) {
            total += fineHistory[i];
        }

        return total;
    }
}


// StudentMember inherits from LibraryMember
class StudentMember extends LibraryMember {

    private String course;

    public StudentMember(
            String memberId,
            int borrowLimit,
            String course) {

        super(memberId, borrowLimit);
        this.course = course;
    }

    // Student gets 50% discount
    @Override
    protected void chargeFine(int amount) {

        // Pass half the fine to parent
        super.chargeFine(amount / 2);
    }
}


public class A3_StudentFine {

    public static void main(String[] args) {

        StudentMember s =
                new StudentMember(
                    "STU5",
                    3,
                    "CSE"
                );

        // Original fine = 100
        // Student pays only 50
        s.chargeFine(100);

        System.out.println(
            "Total fine: "
            + s.getTotalFine()
        );


        // Get fine history
        int[] history = s.getFineHistory();

        // Try to modify returned array
        history[0] = 999;

        // Internal array is still safe
        System.out.println(
            "Fine history: "
            + Arrays.toString(
                s.getFineHistory()
            )
        );
    }
}
