package movieapp;

/*
Class: Game.java
Description:The class represents a movie that 
            has specific attributes 


Author:  Shahd Sideek-s3660270
 */
public class Game extends Item {

    private String platforms[];
    int diffDays;
    
    private boolean extended;

    public Game(String id, String title, String description, String genre, double fee, String platforms[], boolean extended) {
        super(id, title, description, genre, fee);

        this.platforms = platforms;
        this.extended = extended;

    }

    public String[] getPlatforms() {
        return platforms;
    }

    @Override
    public double returnItem(DateTime returnDate) {
        double lateFee = 0;

        diffDays = DateTime.diffDays(returnDate, startDate);
        lateFee = diffDays * 1;
        int DayPast7 = diffDays / 7;
        /*late fee for every 7 days past due date*/
        lateFee += (DayPast7 * 5);
        if (diffDays > 2) {
            int daysPastDueDate1 = (diffDays - 2);                 //days past from due date  
            lateFee = ((NEW_RELEASE_SURCHARGE * 50) / 100) * daysPastDueDate1;

        }
        /*providing 50% discount if the item is enabled 
        for extended hire
         */
        if (extended) {
            lateFee = (lateFee * 50) / 100;
        }
        return lateFee;

    }

    @Override
    public String getDetails() {
        String onLoan = "No";

        String spDetails = super.getDetails(); //To change body of generated methods, choose Tools | Templates.
        String details = "";
        // details += "\n" + heading + "(" + mType + ")\n";
        details += "---------------------------------------------\n";
        String platform = "";
        for (int i = 0; i < this.platforms.length; i++) {
            platform += platforms[i];
            if (i < platforms.length - 1) {

                platform += ",";
            }
        }
        if (extended) {
            onLoan = "Yes";
        }
        String singleRecord = String.format("\n%-25s %s \n%-25s %s \n%-25s %s \n%-25s %s  \n%-25s %.2f \n%-25s %s \n\n%-25s %s \n%-25s %s ", ""
                + "ID : ", id, "Title : ", title, "Genre : ", genre, "Description : ", description, "Standard Fee : $", fee, "Platforms : ", platform, "On Loan : ", onLoan, "Rental Period : ", diffDays);
        details += singleRecord;
        details += spDetails;

        return details;

    }

    @Override
    public String toString() {
        String platform = "";
        for (int i = 0; i < this.platforms.length; i++) {
            platform += platforms[i];
            if (i < platforms.length - 1) {

                platform += ",";
            }
        }
        String toStr = super.toString();
        if (extended) {
            return toStr + ":" + platform + ":" + STANDARD_FEE + ":" + "E";

        } else {
            return toStr + ":" + platform + ":" + STANDARD_FEE + ":" + "N";
        }
    }

}
