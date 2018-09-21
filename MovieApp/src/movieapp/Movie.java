package movieapp;

/*
Class: Movie.java
Description:The class represents a movie that 
            has specific attributes 


Author:  Shahd Sideek-s3660270
 */
public class Movie extends Item {

    public Movie(String id, String title, String genre, String description, double fee) {
        super(id, title, description, genre, fee);

    }

    @Override
    public String getDetails() {
        String spDetails = super.getDetails();
        String onLoan = "No", rentalPer = "", mType = "", heading = "Not Borrowed";
        String details = "";
        double fee = 0;
        boolean flag = false;
        int flagIndx = -1;
        if (isNewRelease) {
            rentalPer = "2 Days";
            mType = "New Release";

            fee = NEW_RELEASE_SURCHARGE;

        } else {

            rentalPer = "7 Days";
            mType = "Weekly";
            fee = WEEKLY_MOVIES_SURCHARGE;
        }
        details += "\n" + heading + "(" + mType + ")\n";
        details += "---------------------------------------------\n";
        String singleRecord = String.format("\n%-25s %s \n%-25s %s \n%-25s %s \n%-25s %s  \n%-25s %s %.2f \n%-25s %s \n\n%-25s %s \n%-25s %s ", ""
                + "ID : ", id, "Title : ", title, "Genre : ", genre, "Description : ", description, "Standard Fee :","$", fee, "On Loan : ", onLoan, "Movie Type : ", mType, "Rental Period : ", rentalPer);
        details += singleRecord;
        details += spDetails;

        return details;

    }

    @Override
    public String toString() {
        String toStr = super.toString();
        if (isNewRelease) {
            return toStr + ":" + NEW_RELEASE_SURCHARGE + ":NR:" + "Y";

        } else {
            return toStr + ":" + WEEKLY_MOVIES_SURCHARGE + ":WK:" + "n";
        }
    }

}
