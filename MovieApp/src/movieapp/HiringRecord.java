/*
Class: HiringRecord.java
Description:The class represents single hiring record
            for any type of item that can be hired 

Author:  Shahd Sideek-s3660270
*/
package movieapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class HiringRecord  {
	
	private String id;
	private double rentalFee;
	private double lateFee;
	DateTime borrowDate;
	DateTime returnDate;
	
	
	
	
 public HiringRecord(String id, double rentalFee, double lateFee, DateTime borrowDate, DateTime returnDate)
	{
	this.id=id;
	this.rentalFee = rentalFee;
	this.lateFee = lateFee;
	this.borrowDate = borrowDate;
	this.returnDate = returnDate;
	}
 
 
 
 
 public String getId() {    
	return id;
}
public double getRentalFee() {
	return rentalFee;
}

public double getLateFee() {
	return lateFee;
}

public DateTime getBorrowDate() {
	return borrowDate;
}
public DateTime getReturnDate() {
	return returnDate;
}



public double returnItem(DateTime returnDate, double lateFee) {
	
	int date = DateTime.diffDays(returnDate, this.returnDate);
	double totalFee = date * lateFee;
	
	if (lateFee < 0)
	{
	   System.out.println("late fee should not be less than 0");
	}
	
	
	
	return totalFee;
	 
 }

public String getDetails() {
	 
                String detailString=String.format("\n\t%-25s %s \n\t%-25s %s \n\t%-25s %s \n\t%-25s %.2f  \n\t%-25s %.2f \n\t%-25s %.2f  ", "Hire Id : " , id ,"Borrow Date : ",
                       borrowDate.getFormattedDate(),"Return Date",returnDate.getFormattedDate(),"Fee : ",rentalFee,"Late Fee : ",lateFee,"Total Fees : ",(rentalFee+lateFee));
   
	return detailString;
        
}
@Override
public String toString(){
   	return   id + " : " + borrowDate + " : " + returnDate + " : " + rentalFee
            + " : " + lateFee;
 
}

	
}
