import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class scanner {

	public static void main(String[] args) throws IOException {
		
    	double FNum = 0;														//
		double LNum = 0;														// VARIABLE INITIALIZATION
		String csvName = "";													//
		String savePath = "C:\\Users\\asche\\Desktop\\Optics Lab\\"				//
				+ "Experiment 2 - Guassian Optics\\Data Analysis\\";			//
		String text = "";														//
		int lineNumber;															//
		String line;															//
		double i;

		System.out.println("========== GAUSSIAN BEAM SCANNER ==========");		//
		Scanner userinput = new Scanner (System.in);							// USER INPUT AND VALUE SETTING
		System.out.print("New .txt File Name:  ");								//
		csvName = userinput.nextLine();											//
		System.out.print("First sample number:  ");								//
		FNum = userinput.nextDouble();											//
		System.out.print("Last sample number:  ");								//
		LNum = userinput.nextDouble();											//
		System.out.println();
		
		File file = new File(savePath + csvName +  ".csv");        				// creates a new file with the user input name
		FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);     	// 
		BufferedWriter out = new BufferedWriter(writer);						// allows us to write to this file
			
		for(i = FNum; i < LNum+0.01; i += 0.01, FNum += 0.01) 					// checks for every file name with 2 decimal place precision
		{
			
			FNum = round(FNum, 2);												// rounds file name to avoid floating point weirdness
			
			String FNumS = String.format("%.2f", FNum);							//makes sure the txt file name uses 2 decimal points 
																				//**change this if file names differ from 2 decimal points**
			String fileName = (FNumS +".txt");									//string variable to hold the name of a .txt file.
			File fileToRead = new File(fileName);							 	//opens file we are reading

			if (fileToRead.exists())											//skips files that do not exist
			{
				
				BufferedReader in = new BufferedReader (new FileReader(fileName));// reader for file
				line = in.readLine();	
				
				while ((line = in.readLine()) != null)							//starts reading the lines in the .csv until there is a null line
				{
					for(lineNumber = 1; lineNumber < 13; lineNumber++)			//adjust these to read different lines
					{
						
						in.readLine();											// discards the lines that we are not interested in
							
					}
					
					for(lineNumber = 13; lineNumber < 113; lineNumber++)			//adjust these to read different lines
					{
						
						text = in.readLine();									//reads the data we're interested in
						out.append(FNumS + "," + Double.parseDouble(FNumS)/10
								+ "," + Double.parseDouble(FNumS)*25.4/10 +
								"," + " " + "," + text);									//prints to the csv 
						out.newLine();
						out.flush();		
						
					}
					
					in.readLine();
					in.readLine();												//skips rest of lines we don't care about
					in.readLine();
					in.readLine();
					out.newLine();

					 
				}
			
				in.close();
				
			}
			

			 
		}
					
		System.out.println();
		if (FNum == LNum)
		{
			
			out.close();
			userinput.close();
			System.exit(1);
			
		}
			
	}
    
    
    private static double round (double value, int precision) 
    {
    	
        int scale = (int) Math.pow(10, precision);					//method for rounding the double
        return (double) Math.round(value * scale) / scale;			//could not use math.round due to floating point weirdness in Java
        
    }
}
