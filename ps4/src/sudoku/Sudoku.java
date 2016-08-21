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
import sat.formula.*;

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
        this.occupies = new Variable[size][size][size*size];
        for (int i = 0; i < (dim*dim); i++){
            for (int j = 0; j < (dim*dim); j++){
                square[i][j]=0;
                for (int k=1; k <= size; k++)
                    occupies[i][j][k] = new Variable(Integer.toString(i)+Integer.toString(j)+Integer.toString(k));
                
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
        this.occupies = new Variable[dim*dim][dim*dim][size*size];
        
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                for (int k = 1; k <= size; k++){
                    occupies[i][j][k] = new Variable(Integer.toString(i)+Integer.toString(j)+Integer.toString(k));
                }
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

        //part 1 - variables in the starting grid have to be true
        Formula formula1 = new Formula();
        for (int i = 0; i < this.size; i++){
            for (int j = 0; j < this.size; j++){
                if (this.square[i][j] > 0){
                    formula1 = formula1.addClause(new Clause(PosLiteral.make(occupies[i][j][square[i][j]])));
                }
            }
        }
        
        //part 2 - at most one digit per square
        Formula formula2 = new Formula();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                
                for (int k = 1; k <= size; k++){
                    for (int kprime = 1; kprime <= size; kprime++){
                        if (k !=  kprime){
                            Clause tempclause = new Clause(NegLiteral.make(occupies[i][j][k]));
                            tempclause = tempclause.add(NegLiteral.make(occupies[i][j][kprime]));
                            formula2 = formula2.addClause(tempclause);
                        }
                    }
                }   
            }
        }
        
        //part 3 - each digit exactly once in each row
        //3 a - each digit at least once in each row
        Formula formula3a = new Formula();
        for (int i = 0; i < size; i++){
            for (int k = 1; k <= size; k++){
                Clause tempclause = new Clause();
                for (int j = 0; j < size; j++){
                    tempclause = tempclause.add(PosLiteral.make(occupies[i][j][k]));
                }
                formula3a = formula3a.addClause(tempclause);
            }
        }
        
        //3b - each digit no more than once in each row
        Formula formula3b = new Formula();
        for (int i = 0; i < size; i++){
            for (int k = 1; k <= size; k++){
                
                for (int j = 0; j < size; j++){
                    for (int jprime = 0; jprime < size; jprime++){
                        if (j != jprime){
                            Clause tempclause = new Clause();
                            tempclause = tempclause.add(NegLiteral.make(occupies[i][j][k]));
                            tempclause = tempclause.add(NegLiteral.make(occupies[i][jprime][k]));
                            formula3b = formula3b.addClause(tempclause);
                        }
                        
                    }
                }
            }
        }
        //part 4 - digit no more than once in each column
        Formula formula4a = new Formula();
        for (int j = 0; j < size; j++){
            for (int k = 1; k <= size; k++){
                Clause tempclause = new Clause();
                for (int i = 0; i < size; i++){
                    tempclause = tempclause.add(PosLiteral.make(occupies[i][j][k]));
                }
                formula4a = formula4a.addClause(tempclause);
            }
        }
        Formula formula4b = new Formula();
        for (int j = 0; j < size; j++){
            for (int k = 1; k <= size; k++){
                
                for (int i = 0; i < size; i++){
                    for (int iprime = 0; iprime < size; iprime++){
                        if (i != iprime){
                            Clause tempclause = new Clause();
                            tempclause = tempclause.add(NegLiteral.make(occupies[i][j][k]));
                            tempclause = tempclause.add(NegLiteral.make(occupies[iprime][j][k]));
                            formula4b = formula4b.addClause(tempclause);
                        }
                        
                    }
                }
            }
        }        
        
        //part 5 - each digit exactly once in each section
        
        //part 5a - every digit at least once in each section
        Formula formula5a = new Formula();
        
        for (int vblock = 0; vblock<dim; vblock++){
            for (int hblock = 0; hblock<dim; hblock++){
                for (int k = 1; k <= size; k++){
                    Clause tempclause = new Clause();
                    for (int i = 0; i<dim; i++){
                        for (int j = 0; j<dim; j++){
                            tempclause = tempclause.add(PosLiteral.make(occupies[(vblock*dim)+i][(hblock*dim)+j][k]));
                        }
                    }
                    formula5a = formula5a.addClause(tempclause);
                }
            }
        }
        
        //part 5b - every digit no more than once in each section
        Formula formula5b = new Formula();
        
        for (int vblock = 0; vblock<dim; vblock++){
            for (int hblock = 0; hblock<dim; hblock++){
                for (int k =1; k<= size; k++){
                    for (int i = 0; i<dim; i++){
                        for (int j = 0; j < dim; j++){
                            for (int iprime = 0; iprime < dim; iprime++){
                                for (int jprime = 0; jprime <dim; jprime++){
                                    if (iprime != i || jprime != j){
                                        Clause tempclause = new Clause();
                                        tempclause = tempclause.add
                                            (NegLiteral.make(occupies[(vblock*dim)+i][(hblock*dim)+j][k]));
                                        tempclause = tempclause.add
                                            (NegLiteral.make(occupies[(vblock*dim)+iprime][(hblock*dim)+jprime][k]));
                                        formula5b = formula5b.addClause(tempclause);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        Formula finalformula = new Formula();
        finalformula = finalformula.and(formula1);
        finalformula = finalformula.and(formula2);
        finalformula = finalformula.and(formula3a);
        finalformula = finalformula.and(formula3b);
        finalformula = finalformula.and(formula4a);
        finalformula = finalformula.and(formula4b);
        finalformula = finalformula.and(formula5a);
        finalformula = finalformula.and(formula5b);
        
        return finalformula;
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
