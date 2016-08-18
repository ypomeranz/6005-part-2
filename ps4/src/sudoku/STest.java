package sudoku;

import java.io.*;
    
public class STest{
    
    public static void main(String[] args) throws IOException, Sudoku.ParseException{
        testSudoku();
    }

    public static void testSudoku() throws IOException, Sudoku.ParseException{
        Sudoku firstTest = Sudoku.fromFile(2, "../samples/sudoku_4x4.txt");
        System.out.println(firstTest.toString());
        System.out.println("Second test");
        Sudoku secondTest = Sudoku.fromFile(3, "../samples/sudoku_hard2.txt");
        System.out.println(secondTest.toString());
    }


}