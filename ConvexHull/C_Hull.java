/*
	Comp 282 
	Computing Convex Hull then checking to see if 
	test points are inside or outside of hull
	February 2017
	
	C_Hull.java
*/
import java.util.*;
import java.io.*;
public class C_Hull {
	private Scanner scan1 = new Scanner(System.in);
	private Scanner scan2 = new Scanner(System.in);
	private Coordinate[] coordinates;
	private ArrayList<Coordinate> convexHullPoints;
	private ArrayList<Coordinate> YPoints;

	public void openFile(){
		try{
			scan1= new Scanner(new File("input.txt"));
			scan2= new Scanner(new File("input.txt"));
		}
		catch(Exception e){
			System.out.println("Error: Could not find the file");
		}
	}
	
	public void readFile(){
		 int count=0;
		//This first while loop gets the size of the Coordinate array
		while(scan1.hasNextInt()){
			scan1.nextInt();
			scan1.nextInt();
			count++;//How big the array will be
		}
		coordinates = new Coordinate[count];
		int i =0;
		while(scan2.hasNext()){
			//This while loop puts the x y coordinates in the array
			coordinates[i]=new Coordinate();
			coordinates[i].x=scan2.nextInt();
			coordinates[i].y=scan2.nextInt();
			i++;			
		}
	}
	
	private boolean findCounterClockwise(Coordinate first, Coordinate second, Coordinate third)
    {
        int calc = (second.x - first.x) * (third.y - first.y) - (second.y - first.y) * (third.x - first.x);
        //if calc is a positive value then it's a left turn/counter clockwise 
         if (calc < 0)
             return true;
         return false;
    }
	
	public void computeHull(Coordinate[] coordinates){
		
		int numOfPoints = coordinates.length;
		
		//int array with size of how many coordinates there are
		int[] upcoming = new int [numOfPoints];
		
		for(int i=0;i<upcoming.length;i++){
			upcoming[i]=-1;//all indexes of this array will contain -1
		}
		
		int first= 0;
		
		int third = 1;
			for(int g =0; g<numOfPoints; g++){
				if(findCounterClockwise(coordinates[first],coordinates[g],coordinates[third])){
					third = g;//flag third with an index that constitutes a right turn
					//do this until third equal the index that constitutes the last right turn
				}
			}
				upcoming[first]=third;//The value of the first index of the array is going to equal the index of the last right turn
				first=third;//first equals the index number of the last right turn
		 while(first != 0){//Goes until you get back to zero
			third = (first+1) % numOfPoints;
			for(int g =0; g<numOfPoints; g++){
				if(findCounterClockwise(coordinates[first],coordinates[g],coordinates[third])){
					third = g;//flag third with an index that constitutes a right turn
				}
			}
				upcoming[first]=third;//flags each index with values from the hull
				first=third;
		}
		
		result(coordinates,upcoming);
	}

	public void result(Coordinate[] cords, int[] ups){
		
		convexHullPoints = new ArrayList<Coordinate>();//This will aid in sorting
		for(int i=0;i<ups.length;i++){
			if(ups[i]!=-1){
				convexHullPoints.add(cords[i]);//The actual convex hull points
			}
		}
		//Returns sorted Array
		sortArray(convexHullPoints);
		System.out.println("The points on the convex hull are: ");
		System.out.println();
		for(int i=0; i<convexHullPoints.size();i++){
			//Prints out the values of the Convex Hull
			System.out.println("("+convexHullPoints.get(i).x+", "+convexHullPoints.get(i).y+")");
		}	
	}

	public ArrayList<Coordinate> sortArray(ArrayList<Coordinate> unSortedPoints) {
		//Bubble sort through the points
		Coordinate temp;
		for(int i=0;i<unSortedPoints.size();i++){
			for(int j=1; j<(unSortedPoints.size()-i);j++){
				if(unSortedPoints.get(j-1).x>unSortedPoints.get(j).x){
				temp = unSortedPoints.get(j-1);
				Collections.swap(unSortedPoints, j, j-1);
				unSortedPoints.get(j).equals(temp);
				}
			}
		}
		//returns the points sorted by X value
		return unSortedPoints;
	}

	public void sortYValues(){
		/*Another bubble sort but for the y values
		It's a little unnecessary to sort these like this (ascending order)
		But the way I see it if its ever required to sort the array with Y values 
		ascending instead of by X values, then its easier to implement*/
		YPoints = new ArrayList<Coordinate>(convexHullPoints);
		Coordinate temp;
		for(int i=0;i<YPoints.size();i++){
			for(int j=1; j<(YPoints.size()-i);j++){
				if(YPoints.get(j-1).y>YPoints.get(j).y){
				temp = YPoints.get(j-1);
				Collections.swap(YPoints, j, j-1);
				YPoints.get(j).equals(temp);
				}
			}
		}
	}

	public boolean determineTestPoints(String points){
			
			if(points.equalsIgnoreCase("quit")){
				//Program terminates once this condition is satisfied
				return false;
			}

			String[] testPoints = points.split(" ");
			
			int x = Integer.parseInt(testPoints[0]);
			int y = Integer.parseInt(testPoints[1]);
			
			//Necessary to find the extreme Y values
			sortYValues();
			
			//If its greater than or less than the extreme X points of the Hull
			if(x<convexHullPoints.get(0).x||x>convexHullPoints.get(convexHullPoints.size()-1).x){
				System.out.println("Outside");
				return true;
			}
			//If its greater than or less than the extreme Y points of the Hull
			if(y<YPoints.get(0).y||y>YPoints.get(YPoints.size()-1).y){
				System.out.println("Outside");
				return true;
			}
			
			//If x+y is greater than or equal to the greatest y value or less than or equal to the least y value
			if(x+y>YPoints.get(YPoints.size()-1).y||x+y<YPoints.get(0).y){
				System.out.println("Outside");
				return true;
			}
			
			
			//All other cases will be inside the hull
			else
				System.out.println("Inside");
				return true;
	}

	public Coordinate[] getArray(){
		//getter method 
		return coordinates;
	}


}
