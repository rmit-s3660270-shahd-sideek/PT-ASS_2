package movieapp;

import static movieapp.Movie.NEW_RELEASE_SURCHARGE;

/*
Class: Item.java
Description:The class represents a item  that 
            has specific attributes and few function that
            are common for all ietms that extends this class

Author:  Shahd Sideek-s3660270
 */
public class Item {

    public boolean isNewRelease;
    public static final double NEW_RELEASE_SURCHARGE = 5;
    public static final double WEEKLY_MOVIES_SURCHARGE = 3;
    double STANDARD_FEE = 20;
    protected String id;
    protected String title;
    protected String description;
    protected String genre;
    protected double fee;
    protected HiringRecord[] hireHistory = new HiringRecord[10];
    protected DateTime startDate;
    public static int hiredRecord = 0;
    protected HiringRecord currentlyBorrowed;

    public Item(String id, String title, String description, String genre, double fee) {

        String newId = "M_" + id;

        this.id = newId;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.fee = fee;

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public double getFee() {
        return fee;
    }

    public HiringRecord[] getHireHistory() {
        return hireHistory;
    }

    public HiringRecord getCurrentlyBorrowed() {
        return currentlyBorrowed;
    }

    public double borrow(String memberId, int advanceDays) throws BorrowException {

        boolean found = false;
        double fees = 0;
        //check if a movie is in the records

//        for (int ind = 0; ind < hiredRecord; ind++) {
//
//            if (hireHistory[ind].getId().equals(id)) {
//                found = true;
//
//            }
//        }

        if (!found) {

            startDate = new DateTime(advanceDays);
            if (isNewRelease) {

                DateTime rDate = new DateTime(2);
                currentlyBorrowed = new HiringRecord(memberId + startDate.getEightDigitDate(), NEW_RELEASE_SURCHARGE, ((NEW_RELEASE_SURCHARGE * 50) / 100), startDate, rDate);
                hireHistory[hiredRecord] = currentlyBorrowed;
                fees = NEW_RELEASE_SURCHARGE;

            } else {
                DateTime rDate = new DateTime(7);
                currentlyBorrowed = new HiringRecord(memberId + startDate.getEightDigitDate(), WEEKLY_MOVIES_SURCHARGE, ((WEEKLY_MOVIES_SURCHARGE * 50) / 100), startDate, rDate);
                hireHistory[hiredRecord] = currentlyBorrowed;
                fees = WEEKLY_MOVIES_SURCHARGE;
            }
            hiredRecord++;
            // numOfRecords++;

        } else {
            throw new BorrowException("Error!The item is currently on loan");

        }
        return fees;
    }

    public double returnItem(DateTime returnDate) {
        double lateFee = 0;

        int diffDays = DateTime.diffDays(returnDate, startDate);
        if (isNewRelease) {
            if (diffDays > 2) {
                int daysPastDueDate1 = (diffDays - 2);                 //days past from due date  
                lateFee = ((NEW_RELEASE_SURCHARGE * 50) / 100) * daysPastDueDate1;

            }
            //   totalFee = NEW_RELEASE_SURCHARGE + lateFee;
        } else {
            if (diffDays > 7) {
                int daysPastDueDate2 = (diffDays - 7);
                lateFee = ((WEEKLY_MOVIES_SURCHARGE * 50) / 100) * daysPastDueDate2;

            }

        }
        return lateFee;
    }

    public String getDetails() {

        String onLoan = "No", rentalPer = "", mType = "", heading = "Not Borrowed";
        String details = "";
        double fee = 0;
        boolean flag = false;
        int flagIndx = -1;
        if (hireHistory != null) {
            for (int ind = 0; ind < hiredRecord; ind++) {
                if(   hireHistory[ind]!=null &&   !hireHistory[ind].getId().isEmpty()){
                if (id.equals("M_" + (hireHistory[ind].getId().subSequence(2, 5)))) {
                    flagIndx = ind;
                    flag = true;
                    onLoan = "Yes";
                    heading = "Borrowed";

                }
                }
            }
            details += "\n\tBORROWING RECORD";
            details += "\n\t---------------------------------------------";
            if (flag) {

                details += hireHistory[flagIndx].getDetails();

            } else {

                details += "\n\tNONE";
            }
            details += "\n\t---------------------------------------------";
        }
        return details;
    }

    public String toString() {

        return id + ":" + title + ":" + description + ":" + genre;

    }

}
