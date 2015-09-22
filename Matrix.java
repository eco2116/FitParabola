/**
 * Evan O'Connor - eco2116
 * Matrix.java
 */

import java.util.ArrayList;

public class Matrix {

	private int row;
	private int col;
	private double[][] data;
	
	public Matrix(Matrix duplicate) {
		this.row = duplicate.row;
		this.col = duplicate.col;
		this.data = duplicate.data;			
	}
	
	public Matrix(int m, int n) {
		setRow(m);
		setCol(n);
		data = new double[m][n];
	}
	
	// Returns the product of two matrices
	public static Matrix multiply(Matrix A, Matrix B) {

		if (A.col != B.row) 
			throw new RuntimeException("Illegal matrix dimensions.");
		
		Matrix C = new Matrix(A.row, B.col); 
        for (int i = 0; i < C.row; i++)
            for (int j = 0; j < C.col; j++)
                for (int k = 0; k < A.col; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
	}
	
	// Solves Ax=b where A is square and invertible
	// Uses Gaussian elimination with partial pivoting and back substitution
    public static Matrix solveSquare(Matrix A, Matrix b) {
    	
        if (A.row != A.col || b.row != A.col || b.col != 1)
            throw new RuntimeException("ERROR: Argument matrices incorrect size.");

        Matrix A_copy = new Matrix(A);
        Matrix b_copy = new Matrix(b);

        // Gaussian Elimination with partial pivoting
        for (int i = 0; i < A.col; i++) {
            int track = i;
            for (int j = i + 1; j < A.col; j++)
                if (Math.abs(A.data[j][i]) > Math.abs(A.data[track][i]))
                    track = j;
            
            A.rowSwap(i, track);
            b.rowSwap(i, track);
            
            for (int j = i + 1; j < A.col; j++)
                b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];
            
            for (int j = i + 1; j < A.col; j++) {
                double m = A.data[j][i] / A.data[i][i];
                for (int k = i+1; k < A.col; k++) {
                    A.data[j][k] -= A.data[i][k] * m;
                }
                A.data[j][i] = 0;
            }
        }
        
        // Back Substitution
        Matrix x = new Matrix(A.col, 1);
        for (int j = A.col - 1; j >= 0; j--) {
            double z = 0;
            for (int k = j + 1; k < A.col; k++)
                z += A.data[j][k] * x.data[k][0];
            x.data[j][0] = (b.data[j][0] - z) / A.data[j][j];
        }
        return x;
    }
    
    private void rowSwap(int i, int j) {
    	Matrix swap = this;
		double[] temp = swap.data[i];
		swap.data[i] = swap.data[j];
		swap.data[j] = temp;
    }
	
	// Returns the (m x 3) matrix A for the parabolic fit 
	public static Matrix generateA(ArrayList<double[]> data) {
		
		Matrix A = new Matrix(data.size(), 3);
		for (int i=0; i<A.row; i++) {
			
			// Fill in columns for A such that column 1 contains all ones,
			// column 2 contains t_i, and column 3 contains (t_i)^2 for i=1,...,m
			A.setValue(i, 0, 1);
			A.setValue(i, 1, data.get(i)[0]);
			A.setValue(i, 2, data.get(i)[0]*data.get(i)[0]);
		}
		return A;
	}
	
	// Generates data (t_i, b_i) for i=1,...,m with evenly spaced t_i
	// and b_i generated from C + D*t_i + E*(t_i)^2
	public static ArrayList<double[]> generateParabolicData(double[] CDE, int m) {
		ArrayList<double[]> dataPairs = new ArrayList<double[]>();
		
		for (int i=0; i<m; i++) {
			double[] pair = new double[2];
			pair[0] = i;
			// Parabolic equation
			pair[1] = (CDE[0] + CDE[1]*i + CDE[2]*i*i);
			dataPairs.add(pair);
		}
		return dataPairs;
	}
	
	// Perturbs parabolic data by adding a random offset generated
	// by Math.random() between 0 and 1 to all b_i for i=1,...,m
	public static ArrayList<double[]> offsetParabolicData(ArrayList<double[]> data) {
		for (double[] pair : data) {
			pair[1] += Math.random();
		}
		return data;
	}
	
	// Returns the transpose of a given matrix
	public static Matrix transpose(Matrix m) {
		Matrix t = new Matrix(m.col, m.row);
		double[][] tData = new double[t.row][t.col];
		for (int i=0; i<t.row; i++) {
			for (int j=0; j<t.col; j++) {
				// Swap row and column indices
				tData[i][j] = m.data[j][i];
			}
		}
		t.setData(tData);
		return t;
	}
	
	public void setData(double[][] d) {
		this.data = d;
	}
	
	public String toString() {
		String printMatrix = "";
		for (int i=0; i<row; i++) {
			printMatrix += "[ ";
			for (int j=0; j<col; j++) {
				printMatrix += (data[i][j]+" ");
			}
			printMatrix += "]\n";
		}
		
		return printMatrix;
	}
	// Getter and setter for a given matrix elements
	public void setValue(int i, int j, double e) {
		data[i][j] = e;
	}
	
	public double getValue(int i, int j) {
		return data[i][j];
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

