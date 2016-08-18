/**
 * Author: dnj, Hank Huang
 * Date: March 7, 2009
 * 6.005 Elements of Software Construction
 * (c) 2007-2009, MIT 6.005 Staff
 */
package sudoku;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import sat.env.Environment;
import sat.env.Variable;
import sat.formula.Formula;

/**
 * Sudoku is an immutable abstract datatype representing instances of Sudoku.
 * Each object is a partially completed Sudoku puzzle.
 */
public class Sudoku {
    // dimension: standard puzzle has dim 3
    private final int dim;
    // number of rows and columns: standard puzzle has size 9
    private final int size;
    // known values: square[i][j] represents the square in the ith row and jth
    // column,
    // contains -1 if the digit is not present, else i>=0 to represent the digit
    // i+1
    // (digits are indexed from 0 and not 1 so that we can take the number k
    // from square[i][j] and
    // use it to index into occupies[i][j][k])
    private final int[][] square;
    // occupies [i,j,k] means that kth symbol occupies entry in row i, column j
    private final Variable[][][] occupies;

    // Rep invariant
    // TODO: write your rep invariant here
    private void checkRep() {
        // TODO: implement this.
        throw new RuntimeException("not yet implemented.");
    }

    /**
     * create an empty Sudoku puzzle of dimension dim.
     * 
     * @param dim
     *            size of one block of the puzzle. For example, new Sudoku(3)
     *            makes a standard Sudoku puzzle with a 9x9 grid.
     */
    public Sudoku(int dim) {
        // TODO: implement this.
        this.dim = dim;
        this.size = dim*dim;
        this.square = new int[dim*dim][dim*dim];
        this.occupies = new Variable[size][size][size+1];
        for (int i = 0; i < (dim*dim); i++){
            for (int j = 0; j < (dim*dim); j++){
                square[i][j]=0;
                occupies[i][j][0] = new Variable(Integer.toString(i)+","+Integer.toString(j)+",0");
            }
        }
    }

    /**
     * create Sudoku puzzle
     * 
     * @param square
     *            digits or blanks of the Sudoku grid. square[i][j] represents
     *            the square in the ith row and jth column, contains 0 for a
     *            blank, else i to represent the digit i. So { { 0, 0, 0, 1 }, {
     *            2, 3, 0, 4 }, { 0, 0, 0, 3 }, { 4, 1, 0, 2 } } represents the
     *            dimension-2 Sudoku grid: 
     *            
     *            ...1 
     *            23.4 
     *            ...3
     *            41.2
     * 
     * @param dim
     *            dimension of puzzle Requires that dim*dim == square.length ==
     *            square[i].length for 0<=i<dim.
     */
    public Sudoku(int dim, int[][] square) {
        this.dim = dim;
        this.size = dim*dim;
        this.square = square;
        this.occupies = new Variable[dim*dim][dim*dim][dim*dim+1];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                // System.out.print("i: ");
                // System.out.println(i);
                // System.out.print("j: ");
                // System.out.println(j);
                // System.out.println("k: ");
                // System.out.println(square[i][j]);
                occupies[i][j][square[i][j]] = new Variable(Integer.toString(i)+","+Integer.toString(j)+","+Integer.toString(square[i][j]));
                
            }
        }
    }

    /**
     * Reads in a file containing a Sudoku puzzle.
     * 
     * @param dim
     *            Dimension of puzzle. Requires: at most dim of 3, because
     *            otherwise need different file format
     * @param filename
     *            of file containing puzzle. The file should contain one line
     *            per row, with each square in the row represented by a digit,
     *            if known, and a period otherwise. With dimension dim, the file
     *            should contain dim*dim rows, and each row should contain
     *            dim*dim characters.
     * @return Sudoku object corresponding to file contents
     * @throws IOException
     *             if file reading encounters an error
     * @throws ParseException
     *             if file has error in its format
     */
    public static Sudoku fromFile(int dim, String filename) throws IOException,
            ParseException {
        BufferedReader buffer=new BufferedReader(new FileReader(filename));
        String x = buffer.readLine();
        int i = 0;
        int[][] square = new int[dim*dim][dim*dim];
        while (x != null){
            for (int j = 0; j< x.length(); j++){
                if (x.charAt(j)=='.'){
                    square[i][j]=0;
                } else {
                    square[i][j]=Character.getNumericValue(x.charAt(j));
                }
            } 
            x = buffer.readLine();
            i++;
        }
        //System.out.println("got here");
        //System.out.println(java.util.Arrays.toString(square));
        return new Sudoku(dim, square);
        
    }

    /**
     * Exception used for signaling grammatical errors in Sudoku puzzle files
     */
    @SuppressWarnings("serial")
    public static class ParseException extends Exception {
        public ParseException(String msg) {
            super(msg);
        }
    }

    /**
     * Produce readable string representation of this Sukoku grid, e.g. for a 4
     * x 4 sudoku problem: 
     *   12.4 
     *   3412 
     *   2.43 
     *   4321
     * 
     * @return a string corresponding to this grid
     */
    public String toString() {
        String newstring = "";
        for (int x = 0; x < size; x++){
           if (x!=0){
               newstring = newstring + "\n";
           }
           for (int y = 0; y < size; y++){
               if (square[x][y] == 0){
                   newstring = newstring + ('.');
               } else {
                   newstring = newstring+(Integer.toString(square[x][y]));
               }
           }
       }
       
        return newstring;   
    }

    /**
     * @return a SAT problem corresponding to the puzzle, using variables with
     *         names of the form occupies(i,j,k) to indicate that the kth symbol
     *         occupies the entry in row i, column j
     */
    public Formula getProblem() {

        // TODO: implement this.
        throw new RuntimeException("not yet implemented.");
    }

    /**
     * Interpret the solved SAT problem as a filled-in grid.
     * 
     * @param e
     *            Assignment of variables to values that solves this puzzle.
     *            Requires that e came from a solution to this.getProblem().
     * @return a new Sudoku grid containing the solution to the puzzle, with no
     *         blank entries.
     */
    public Sudoku interpretSolution(Environment e) {

        // TODO: implement this.
        throw new RuntimeException("not yet implemented.");
    }

}
