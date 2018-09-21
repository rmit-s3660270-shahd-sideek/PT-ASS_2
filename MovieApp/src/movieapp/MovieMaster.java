/*
Class: MovieMaster.java
Description:The class reperesents the main class
            that handle all the tasks including getting 
            items details  and adding them to colletion
            Also resposible for borrowing and returning the item

Author:  Shahd Sideek-s3660270
 */
package movieapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieMaster {

    double STANDARD_GAME_FEE = 20;
    Scanner input = new Scanner(System.in);
    private Item[] item = new Item[50];
    SimpleDateFormat dfmt = new SimpleDateFormat("dd/MM/yyyy");
    private int itemCount = 0;

    private double calculateFee(boolean isNewRelease) {
        if (isNewRelease) {
            return 5;
        } else {
            return 3;

        }
    }

    public static void menu() {
        System.out.println("\n*** Movie Master System Menu ***");
        System.out.println("Add Item          " + "A");
        System.out.println("Borrow Item       " + "B");
        System.out.println("Return Item       " + "C");
        System.out.println("Display details   " + "D");
        System.out.println("Seed Data         " + "E");
        System.out.println("Exit Program      " + "X");
        System.out.print("Enter selection :");
    }

    public void addItem() throws IdException {
        Scanner input = new Scanner(System.in);
        String movieId = null;
        boolean found;


          System.out.printf("%-25s", "Enter id:");
        movieId = input.nextLine();
        found = false;
        if (movieId.length() != 3) {
            throw new IdException("Error - the id  " + movieId + " is invalid. Please enter a 3 digit id.");

        } else {
            if (itemCount >= 0) {
                for (int ind = 0; ind < itemCount; ind++) {
                    String mId = item[ind].getId();

                    if (movieId.equals(mId.substring(2, mId.length()))) {
                        found = true;
                    }
                }
            }

            if (found == false) {
                
                System.out.printf("%-25s", "Enter title:");
                String movieTitle = input.nextLine();
               
               System.out.printf("%-25s", "Enter genre:");
                String movieGenre = input.nextLine();
                System.out.printf("%-25s", "Enter description:");
                String movieDesc = input.nextLine();
                System.out.printf("%-25s", "Movie or Game (M/G)?");
               
                String movieOrGame = input.nextLine();

                if (movieOrGame.equals("G")) {

                    System.out.printf("%-25s", "Enter Game Platforms: ");
                    String platforms = input.nextLine();
                    String plat[] = platforms.split(",");

                    Game newGame = new Game(movieId, movieTitle, movieGenre, movieDesc, STANDARD_GAME_FEE, plat, false);
                    item[itemCount] = newGame;
                    System.out.println("New Game added successfully for the game entitled " + newGame.getTitle());
                    itemCount++;
                } else if (movieOrGame.equals("M")) {
                    String release;
                    while (true) {
                   
                        System.out.printf("%-25s", "Is new release (Y/N):");

                        if (!(release = input.nextLine()).isEmpty()) {
                            if (release.equals("Y") || release.equals("N")) {
                                break;
                            } else if (release == null) {
                                System.out.println("\tExiting to main menu");
                            } else {
                                System.out.println("\tError: You must enter 'Y' or 'N' ");
                            }
                        } else {
                            System.out.println("\tExiting to main menu.");
                            break;
                        }
                    }
                    boolean newRelease = false;
                    if (release.equals("Y")) {
                        newRelease = true;
                    }
                    Movie newMovie = new Movie(movieId, movieTitle, movieGenre, movieDesc, calculateFee(newRelease));
                    item[itemCount] = newMovie;
                    System.out.println("New Movie added successfully for the movie entitled " + newMovie.getTitle());
                    itemCount++;
                } else {
                    System.out.println("Invalid choice");
                }

            } else {
                throw new IdException("Error - Movie ID " + movieId + " already exists in the system! ");

            }

        }

    }

    public void borrowItem() throws IdException, BorrowException {
        Scanner input = new Scanner(System.in);

        System.out.format("%-25s", "Enter id: ");
        String movieId = input.nextLine();
        int index = -1;
        boolean found = false;
        for (int ind = 0; ind < itemCount; ind++) {
            if (item[ind].getId().equals("M_" + movieId)) {
                found = true;
                index = ind;
                break;
            }
        }
        if (found != true) {
            throw new IdException("Error - the item with id number: " + movieId + " not found");

        } else {

            if (item[index].getCurrentlyBorrowed() != null && (item[index].getCurrentlyBorrowed().getId().equals("M_" + movieId))) {

                throw new BorrowException("Error:The item with id " + movieId + " is  currently on loan");

            } else {

                System.out.format("%-25s", "Enter member id: ");
                String memberId = input.nextLine();
                System.out.format("%-25s", "Advance Borrow(days): ");
                int advanceDays = Integer.parseInt(input.nextLine());
                if (item[index].getClass().getName().equalsIgnoreCase("movieapp.Game")) {
                    System.out.format("%-25s", "Is Extended Hire(Y/N)?: ");
                    String isEx = input.nextLine();
                } else {
                    System.out.println("name is" + item[index].getClass().getName());
                }
                String mId = item[index].getId() + "_" + memberId + "_";
                if (item[index].getClass().getName().equals("movieapp.Movie")) {

                    if (item[index].isNewRelease) {

                        System.out.println("The item " + item[index].getTitle() + " Costs $" + item[index].NEW_RELEASE_SURCHARGE + " and is due on:" + new DateTime(2 + advanceDays).getFormattedDate());
                    } else {
                        System.out.println("The item " + item[index].getTitle() + " Costs $" + item[index].WEEKLY_MOVIES_SURCHARGE + " and is due on:" + new DateTime(7 + advanceDays).getFormattedDate());
                    }
                } else {
                    System.out.println("The item " + item[index].getTitle() + " Costs $" + item[index].STANDARD_FEE + " and is due on:" + new DateTime(advanceDays).getFormattedDate());

                }

                double fees = item[index].borrow(mId, advanceDays);

            }
        }

    }

    public void returnItem() throws BorrowException {

        Scanner input = new Scanner(System.in);
        System.out.format("%-25s", "Enter id: ");
        String movieId = input.nextLine();

        boolean found = false;
        for (int ind = 0; ind < itemCount; ind++) {
            if (item[ind].getId().equals("M_" + movieId)) {

                System.out.println(item[ind].getCurrentlyBorrowed().getId().substring(2, 5));
                if (item[ind].getCurrentlyBorrowed() != null && (item[ind].getCurrentlyBorrowed().getId().substring(2, 5).equals(movieId))) {

                    System.out.format("%-25s", "Enter number of days on loan: ");
                    int numOfLoanDays = Integer.parseInt(input.nextLine());

                    double fees = item[ind].returnItem(new DateTime(numOfLoanDays));
                    System.out.printf("The total fee payable is  $%.2f", fees);

                } else {
                    throw new BorrowException("Error:The item with id " + movieId + " is  currently on loan");

                }
                found = true;
                break;
            }

        }
        if (found == false) {
            System.out.println("Error - the item with id number: " + movieId + " not found");

        }

    }

    public void displayMovieInfo() {
        for (int ind = 0; ind < itemCount; ind++) {
            System.out.println(item[ind].getDetails());

        }
    }

    public void seedData() {
        /*instansiate movie objects with hard coded values
            weekly movies
         */
        //movie that has not been borrowed
        try {
            Movie movie1 = new Movie("AIW", "Avengers Infinity War", "Action", " Infinity War is a 2018 American...", calculateFee(false));
            //movie that has been borrowed
            Movie movie2 = new Movie("JWF", "Jurassic World:Fallen Kingdom", "Action", " The film is the sequel to Jurassic World (2015) ...", calculateFee(false));

            movie2.borrow(movie2.getId() + "_" + "RRI" + "_", 0);
            Movie movie3 = new Movie("BLP", "Black Panther", "Action", "Black Panther is a 2018 American...", calculateFee(false));

            movie3.borrow(movie3.getId() + "_" + "RRP" + "_", 0);
            movie3.returnItem(new DateTime(5));    //returnrd 5 days later
            Movie movie4 = new Movie("ICD", "Incredibles 2", "Action", "Incredibles 2r is a 2018 American...", calculateFee(false));

            movie4.borrow(movie4.getId() + "_" + "RRR" + "_", 0);
            movie4.returnItem(new DateTime(10));    //returnrd 10 days later

            Movie movie5 = new Movie("DPL", "Deadpool 2", "Action", " Deadpool 2 is a 2018 American...", calculateFee(false));
            movie5.borrow(movie5.getId() + "_" + "WWR" + "_", 0);
            movie5.returnItem(new DateTime(10));    //returnrd 10 days later
            //borrowded by another memeber and not returned yet

            movie5.borrow(movie5.getId() + "_" + "PPR" + "_", 0);

            /*New Release movies*/
            Movie movie6 = new Movie("RPO", "Ready Player One", "Action", " Ready Player One is a 2018 American...", calculateFee(true));
            Movie movie7 = new Movie("PRU", "Pacific Rim Uprising", "Action", " Ready Player One is a 2018 American...", calculateFee(true));
            movie2.borrow(movie7.getId() + "_" + "RTR" + "_", 0);

            Movie movie8 = new Movie("AQP", "A Quiet Place", "Horror", " A Quiet Place is a 2018 American horror film directed by...", calculateFee(true));
            movie8.borrow(movie8.getId() + "_" + "RIR" + "_", 0);
            movie8.returnItem(new DateTime(1));    //returnrd 10 days later

            Movie movie9 = new Movie("MIF", "Mission Impossible Fallout", "Action", " Mission Impossible is a 2018 American...", calculateFee(true));
            movie9.borrow(movie9.getId() + "_" + "ROL" + "_", 0);
            movie9.returnItem(new DateTime(3));    //returnrd 10 days later

            Movie movie10 = new Movie("WIT", "A Wrinkle in Time", "Adventure", " A Wrinkle in Time is  fantasy adventure film directed by ...", calculateFee(true));
            movie10.borrow(movie10.getId() + "_" + "MNJ" + "_", 0);
            movie10.returnItem(new DateTime(3));    //returnrd 10 days later
            movie9.borrow(movie10.getId() + "_" + "ANO" + "_", 0);

//checks if the list is empty
            if (item[0] == null) {
                //add movie objects to array
                item[0] = movie1;
                item[1] = movie2;
                item[2] = movie3;
                item[3] = movie4;
                item[4] = movie5;
                item[5] = movie6;
                item[6] = movie7;
                item[7] = movie8;
                item[8] = movie9;
                item[9] = movie10;

                System.out.println("10 movies added to Collection");
                /*
                pre-populating teh collection with additional four games  
                 */
                String[] Platfoms1 = {"PS4", "Xbox 60"};
                Game game1 = new Game("IGA", "Injustice Gods Amoong Us", "Fighting", "What if our greatest heroes became...", STANDARD_GAME_FEE, Platfoms1, true);
                Game game2 = new Game("MCG", "Mortal Combat Game", "Fighting", "What if our greatest heroes became...", STANDARD_GAME_FEE, Platfoms1, true);
               // game2.borrow("RIC", 0);
                Game game3 = new Game("SF4", "StreetFighter4", "Fighting", "What if our greatest heroes became...", STANDARD_GAME_FEE, Platfoms1, true);
               // game3.borrow("RIC", 0);
                game3.returnItem(new DateTime(19));    //returned 19 days later
                Game game4 = new Game("MVC", "Marvel Vs Campcom", "Fighting", "What if our greatest heroes became...", STANDARD_GAME_FEE, Platfoms1, true);
               // game4.borrow("RMP", 0);
               // game4.returnItem(new DateTime(32));    //returned 32 days later
                item[10] = game1;
                item[11] = game2;
                item[12] = game3;
                item[13] = game4;
                itemCount = 14;

            } else {
                System.out.println("Error!Can not seed data,Collection already has movies");
            }
        } catch (BorrowException bEx) {
            System.out.println(bEx.getMessage());
        }

    }

    public void exitProgram() {
        System.exit(0);
    }

    public void runMenu() {
        readData();
        readHireHistory();
        String choice;
        do {
            menu();

            choice = input.next();
            String stringChoice = choice.toLowerCase();
            switch (stringChoice) {
                case "a": {

                    try {
                        addItem();
                    } catch (IdException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
                case "b":
                    try {
                        borrowItem();
                    } catch (IdException idExcp) {
System.out.println(idExcp.getMessage());
                    } catch (BorrowException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "c": {
                    try {
                        returnItem();
                    } catch (BorrowException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
                case "d":

                    displayMovieInfo();
                    break;
                case "e":

                    seedData();
                    break;
                case "x":
                    writeData();
                    writeHistory();
                    exitProgram();
                    break;

                default:
                    System.out.println("Invalid Option");

            }
        } while (choice != "x");
    }

    /*
    method to write all objects into the file
     */
    private void writeData() {

        try {
            File mainFile = new File("mainfile.txt");

            if (!mainFile.exists()) {
                mainFile = new File("mainfile_backup.txt");
            }
            PrintWriter pWriter = new PrintWriter(mainFile);

            //writig the hiring history to the file 
            for (int ind = 0; ind < itemCount; ind++) {

                pWriter.write("\n" + item[ind].getId() + "\t" + item[ind].getTitle() + "\t" + item[ind].getGenre() + "\t" + item[ind].getDescription() + "\t" + item[ind].getFee());

            }

            pWriter.close();
        } catch (Exception excp) {
            // System.out.println(excp.getMessage());
        } finally {

        }

    }

    private void writeHistory() {
        try {

            File historyFile = new File("hirehistory.txt");

            PrintWriter historyWriter = new PrintWriter(historyFile);
            //writig the hiring history to the file 
            for (int ind = 0; ind < itemCount; ind++) {
                HiringRecord[] hireHist = item[ind].getHireHistory();
                for (int i = 0; i < item[ind].hiredRecord; i++) {
                    historyWriter.write(hireHist[i].getId() + "\t" + hireHist[i].getRentalFee() + "\t" + hireHist[i].getLateFee() + "\t" + hireHist[i].getBorrowDate() + "\t" + hireHist[i].getReturnDate());

                }

            }
            historyWriter.close();

        } catch (Exception excp) {
            System.out.println(excp.getMessage());
        }
    }

    /*
    method to read all objects from the file
     */
    private void readData() {

        try {

            File mainFile = new File("mainfile.txt");

            if (!mainFile.exists()) {
                System.out.println("The data is loaded from backup file");
                mainFile = new File("mainfile_backup.txt");
            }

            BufferedReader bReader = new BufferedReader(new FileReader(mainFile));
            String oneItem = "";
            while ((oneItem = bReader.readLine()) != null) {
                String[] item1 = oneItem.split("\t");

                this.item[itemCount] = new Item(item1[0].substring(2, 5), item1[1], item1[2], item1[3], Double.parseDouble(item1[4]));
                itemCount++;

            }
            System.out.println("Id is" + item[0].getId());

            bReader.close();
        } catch (Exception excp) {
            System.out.println(excp.getMessage());

        } finally {

        }

    }

    private void readHireHistory() {

        try {

            File mainFile = new File("hirehistory.txt");

            BufferedReader bReader = new BufferedReader(new FileReader(mainFile));
            String oneItem = "";
            int histIndx = 0;
            while ((oneItem = bReader.readLine()) != null) {
                String[] item1 = oneItem.split("\t");

                this.item[itemCount].hireHistory[histIndx] = new HiringRecord(item1[0], Double.parseDouble(item1[1]), Double.parseDouble(item1[2]), (DateTime) ((Object) item1[3]), (DateTime) ((Object) item1[4]));

                itemCount++;

            }
            System.out.println("Id is" + item[0].getId());

            bReader.close();
        } catch (Exception excp) {
            System.out.println(excp.getMessage());

        } finally {

        }

    }

    
}
