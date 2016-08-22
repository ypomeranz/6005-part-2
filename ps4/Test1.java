import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.File;

public class Test1{
    public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException{

    BufferedReader buffer=new BufferedReader(new FileReader("samples/sudoku_4x4.txt"));
    String x = buffer.readLine();
    while (x != null){
        System.out.println(x);
        x = buffer.readLine();
    }
    }

}