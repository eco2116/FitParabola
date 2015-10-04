## Implementation of problem

My program begins by reading in the user's input from the input file.
Then, using this input, it generates evenly spaced data (t_i,b_i) for
i=1,...,m and samples the inputted parabola on evenly spaced points t_i.
This data is perturbed by a method in the Matrix class called
offsetParabolicData. This method adds a randomly generated number
between 0 and 1 to b_i for i=1,...,m. This perturbed data is stored
in an ArrayList of arrays of size 2 of doubles. Matrix A is generated
using a method generateA whose algorithm comes from p. 233 eqn. 10.

From here, we find x hat by first computing the coefficient matrix,
A transpose times A. This is done using the multiply and transpose
methods in my Matrix class. The right hand side vector is equal
to A transpose times b, where b is all of b_i for i=1,...,m.
This is done using the perturbed data and methods in the Matrix class.
Finally, knowing the layout of the coefficient and right hand side
matrices in this problem, we can solve (coefficient)(x hat)=(rhs) to find x hat,
the parabolic fit, with compondents C, D, and E, using Gaussian elimination
and back substitution.

Finally, the program compares the input values of C, D, and E used to
generate the non-perturbed data and the components of x hat from the parabolic fit.

 * Compile
  * javac Matrix.java
  * javac FitParabolaTest.java
 * Run test file with input file as the singular command line argument
  * java FitParabolaTest input

