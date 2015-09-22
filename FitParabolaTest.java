/**
 * Evan O'Connor - eco2116
 * ParabolaFitTest.java
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FitParabolaTest {

	public static void main(String[] args) {
	
		if(args.length != 1) {
			throw new RuntimeException("ERROR: Need command line argument for input file.");
		}	
	
		// Create BufferedReader for input file (command line argument)
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(args[0]));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Could not identify input file from command line args.");
		}
	
		// Read input file data
		int m = 0;
		double[] inputVals = new double[3];
		try {
			m = Integer.parseInt(br.readLine());
			for (int i=0; i<3; i++) {
				inputVals[i] = Double.parseDouble(br.readLine());
			}
		} catch (NumberFormatException e) {
			System.out.println("ERROR: Inputs file not parsable.");
		} catch (IOException e) {
			System.out.println("ERROR: Encountered an IO Exception.");
		}
		
		// Generate perturbed test data (t_i, b_i) for i=1,...,m
		ArrayList<double[]> fitData = Matrix.offsetParabolicData(
				Matrix.generateParabolicData(inputVals, m));
		
		// Create a matrix to represent the product A transpose * A
		Matrix A = Matrix.generateA(fitData);
		Matrix coefficient = Matrix.multiply(Matrix.transpose(A), A);
		
		// Create a matrix to represent the vector A transpose * b
		Matrix b = new Matrix(m,1);
		for (int i=0; i<m; i++) {
			b.setValue(i, 0, fitData.get(i)[1]);
		}
		Matrix rhs = Matrix.multiply(Matrix.transpose(A), b);
		Matrix x = Matrix.solveSquare(coefficient, rhs);
		
		// Print comparisons of actual C,D,E values and those generated by the algorithm
		System.out.println("For m = "+m+", the parabolic fit genereated the following values:\n");
		System.out.println("C = "+x.getValue(0, 0)+" compared to the actual value of "+inputVals[0]);
		System.out.println("D = "+x.getValue(1, 0)+" compared to the actual value of "+inputVals[1]);
		System.out.println("E = "+x.getValue(2, 0)+" compared to the actual value of "+inputVals[2]);
	}

}