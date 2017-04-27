/*
	Comp 282 
	Computing Convex Hull then checking too see if 
	test points are inside or outside of hull
	February 2017
	
	Driver1.java
*/
import java.util.Scanner;
public class Driver1 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Project 1: Boundaries");
		System.out.println("Loading points from input.txt...");
		System.out.println();
		C_Hull c = new C_Hull();
		c.openFile();
		c.readFile();
		c.computeHull(c.getArray());
		System.out.println();
		boolean flag = true;
		while(flag){
			System.out.println("Test Point: ");
			String points = scan.nextLine();
			flag =c.determineTestPoints(points);
			System.out.println();
		}
		scan.close();
	}
}
