import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShotProb {
	
	
	//initializes all the integers count for "Makes" and "Attempts" at each section of the court
			static int longDistanceAttempts = 0;
			static int longDistanceMakes = 0;
			static int rightWingAttempts = 0;
			static int rightWingMakes = 0;
			static int keyAttempts = 0;
			static int keyMakes = 0;
			static int leftWingAttempts = 0;
			static int leftWingMakes = 0;
			static int rightCornerAttempts = 0;
			static int rightCornerMakes = 0;
			static int leftCornerAttempts = 0;
			static int leftCornerMakes = 0;
			static int paintAttempts = 0;
			static int paintMakes = 0;
			static int behindBoardAttempts = 0;
			static int behindBoardMakes = 0;
			static int rightLaneAttempts = 0;
			static int rightLaneMakes = 0;
			static int leftLaneAttempts = 0;
			static int leftLaneMakes = 0;
	
public static void main(String[] args) {
	
	//Runs method for updating makes, attempts counts on a master "merged" file of all the nba game files
	UpdateStats("merged.csv");
	
	//initialize probability variables and computes probability by (makes/attempts)
    double longDistanceProb = 0.0;
    double rightWingProb = 0.0;
    double leftWingProb = 0.0;
    double keyProb = 0.0;
    double rightCornerProb = 0.0;
    double leftCornerProb = 0.0;
    double paintProb = 0.0;
    double behindBoardProb = 0.0;
    double rightLaneProb = 0.0;
    double leftLaneProb = 0.0;
	if (longDistanceAttempts != 0) longDistanceProb = (double)longDistanceMakes/longDistanceAttempts; 
	if (rightWingAttempts != 0) rightWingProb = (double)rightWingMakes/rightWingAttempts; 
	if (leftWingAttempts != 0) leftWingProb = (double)leftWingMakes/leftWingAttempts;
	if (keyAttempts != 0) keyProb = (double)keyMakes/keyAttempts;
	if (rightCornerAttempts != 0) rightCornerProb = (double)rightCornerMakes/rightCornerAttempts;
	if (leftCornerAttempts != 0) leftCornerProb = (double)leftCornerMakes/leftCornerAttempts;
	if (paintAttempts != 0) paintProb = (double)paintMakes/paintAttempts;
	if (behindBoardAttempts != 0) behindBoardProb = (double)behindBoardMakes/behindBoardAttempts;
	if (rightLaneAttempts != 0) rightLaneProb = (double)rightLaneMakes/rightLaneAttempts;
	if (leftLaneAttempts != 0) leftLaneProb = (double)leftLaneMakes/leftLaneAttempts;
	
	//prints report of all the different probabilities
	System.out.println("Probability of making a long distance shot: " + longDistanceProb);
	System.out.println("Probability of making a shot from the right corner: " + rightCornerProb);
	System.out.println("Probability of making a shot from the left corner: " + leftCornerProb);
	System.out.println("Probability of making a shot from the right wing: " + rightWingProb);
	System.out.println("Probability of making a shot from the left wing: " + leftWingProb);
	System.out.println("Probability of making a shot from the paint: " + paintProb);
	System.out.println("Probability of making a shot from the key: " + keyProb);
	System.out.println("Probability of making a shot from the right lane: " + rightLaneProb);
	System.out.println("Probability of making a shot from the left lane: " + leftLaneProb);
	System.out.println("Probability of making a shot from behind the back board: " + behindBoardProb);
	
}

//method for reading files and updating make, attempts counts
public static void UpdateStats(String s) {
		
	//create array lists to store x coordinate, y coordinate, and whether the shot was a make or miss 3 array lists
	//with corresponding indices. created a buffered reader to parse through csv data files
		ArrayList<Integer> xlist = new ArrayList<Integer>();
		ArrayList<Integer> ylist = new ArrayList<Integer>();
		ArrayList<String> outcomelist = new ArrayList<String>();
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";
		
		
		
		try {
			
			br = new BufferedReader(new FileReader(s));
			while ((line = br.readLine()) != null) {
				
				//every field in a row of the csv file is stored into a separate element of an array list
				//every column, i.e. outcome, x coordinate, and y coordinate with have the same index in each row
				//and can there for be reference later
				List<String> items = Arrays.asList(line.split(csvSplitBy));
				
				if (items.size() > 31 && items.get(27).equals("made")) {
					xlist.add(Integer.parseInt(items.get(30)));
					ylist.add(Integer.parseInt(items.get(31)));
					outcomelist.add("made");
				}
				if (items.size() > 31 && items.get(27).equals("missed")) {
					xlist.add(Integer.parseInt(items.get(30)));
					ylist.add(Integer.parseInt(items.get(31)));
					outcomelist.add("missed");
				}
			}	
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//finds which zone the x,y coordinates are in for every corresponding x,y and outcome in the 3 array lists
		//every entry updates attempt count, and if outcome list has "made" stored it will update the makes count for that zone
		for (int i = 0; i < xlist.size(); i++) {
			if ((xlist.get(i) >= 0 && xlist.get(i) <= 50) && ylist.get(i) > 35) {
				longDistanceAttempts++;
				if (outcomelist.get(i).equals("made")) {
					longDistanceMakes++;
				}
			}
			if ((xlist.get(i) >= 0 && xlist.get(i) <= 17) && (ylist.get(i) >= 15 && ylist.get(i) <= 35)) {
				rightWingAttempts++;
				if (outcomelist.get(i).equals("made")) {
					rightWingMakes++;
				}
			}
			if ((xlist.get(i) >= 17 && xlist.get(i) <= 33) && (ylist.get(i) >= 15 && ylist.get(i) <= 35)) {
				keyAttempts++;
				if (outcomelist.get(i).equals("made")) {
					keyMakes++;
				}
			}
			if ((xlist.get(i) >= 33 && xlist.get(i) <= 50) && (ylist.get(i) >= 15 && ylist.get(i) <= 35)) {
				leftWingAttempts++;
				if (outcomelist.get(i).equals("made")) {
					leftWingMakes++;
				}
			}
			if ((xlist.get(i) >= 0 && xlist.get(i) <= 3) && (ylist.get(i) >= 0 && ylist.get(i) <= 15)) {
				rightCornerAttempts++;
				if (outcomelist.get(i).equals("made")) {
					rightCornerMakes++;
				}
			}
			if ((xlist.get(i) >= 47 && xlist.get(i) <= 50) && (ylist.get(i) >= 0 && ylist.get(i) <= 15)) {
				leftCornerAttempts++;
				if (outcomelist.get(i).equals("made")) {
					leftCornerMakes++;
				}
			}
			if ((xlist.get(i) >= 17 && xlist.get(i) <= 33) && (ylist.get(i) >= 2 && ylist.get(i) <= 15)) {
				paintAttempts++;
				if (outcomelist.get(i).equals("made")) {
					paintMakes++;
				}
			}
			if ((xlist.get(i) >= 17 && xlist.get(i) <= 33) && (ylist.get(i) >= 0 && ylist.get(i) <= 2)) {
				behindBoardAttempts++;
				if (outcomelist.get(i).equals("made")) {
					behindBoardMakes++;
				}
			}
			if ((xlist.get(i) >= 3 && xlist.get(i) <= 17) && (ylist.get(i) >= 0 && ylist.get(i) <= 15)) {
				rightLaneAttempts++;
				if (outcomelist.get(i).equals("made")) {
					rightLaneMakes++;
				}
			}
			if ((xlist.get(i) >= 33 && xlist.get(i) <= 47) && (ylist.get(i) >= 0 && ylist.get(i) <= 15)) {
				leftLaneAttempts++;
				if (outcomelist.get(i).equals("made")) {
					leftLaneMakes++;
				}
			}
		}
}
}
