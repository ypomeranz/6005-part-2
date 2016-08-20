package sat;

import static org.junit.Assert.*;

import org.junit.Test;

import sat.formula.Clause;
import sat.formula.Literal;
import sat.formula.PosLiteral;
import sat.formula.Formula;
import sat.env.*;

public class SATSolverTest {

    // make sure assertions are turned on!  
    // we don't want to run test cases without assertions too.
    // see the handout to find out how to turn them on.
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    public static void main(String[] args){
        test1();
        test2();
        test3();
        test4();
        test5();
    }
    
    public static void test1(){
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal na = a.getNegation();
        Literal nb = b.getNegation();
        Literal nc = c.getNegation();
        
        System.out.println("\nTest 1 on handout");
        System.out.println("Expected a must be true, b can be anything");
        Clause clause1 = make(a, nb);
        Clause clause2 = make(a, b);
        Formula f = new Formula(clause1);
        f = f.addClause(clause2);
        System.out.println(f.toString());
        Environment answer1 = SATSolver.solve(f);
        System.out.println("answer:");
        System.out.println(answer1.toString());
    }
    
    public static void test2(){
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal na = a.getNegation();
        Literal nb = b.getNegation();
        Literal nc = c.getNegation();
        
        System.out.println("\nTest 2 on handout");
        System.out.println("Expected 'null' ");
        Clause clause1 = make(a);
        Clause clause2 = make(b);
        Clause clause3 = make(nb);
        
        Formula f1 = new Formula(clause1);
        f1 = f1.addClause(clause2);
        Formula f2 = new Formula(clause1);
        f2 = f1.addClause(clause3);
        Formula f = f1.and(f2);
        Environment answer2 = SATSolver.solve(f);
        System.out.println("answer:");
        if (answer2==null){
            System.out.println("null");
        } else{
            System.out.println(answer2.toString());
        }
    }
    
    public static void test3(){
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal na = a.getNegation();
        Literal nb = b.getNegation();
        Literal nc = c.getNegation(); 
        
        System.out.println("\nTest 3 on handout");
        System.out.println("Expected:abc true");
        Clause clause1 = make(a);
        Clause clause2 = make(b);
        Formula f1 = new Formula(clause1);
        f1 = f1.addClause(clause2);
        Clause clause3 = make(nb, c);
        Formula f2 = new Formula(clause3);
        Formula f = f1.and(f2);
        Environment answer3 = SATSolver.solve(f);
        System.out.println("answer:");
        System.out.println(answer3.toString());
        
    }
    
    public static void test4(){
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal d = PosLiteral.make("d");
        Literal e = PosLiteral.make("e");
        
        Clause clause1 = make(a,b);
        Clause clause2 = make(c,d,e);
        Formula f = new Formula(clause1);
        f = f.addClause(clause2);
        f = f.not();
        System.out.println("\n test4:");
        System.out.println("Not ((a or b) and (c or d or e))");
        System.out.println("Two possible answers - either ab false or cde false");
        Environment answer4 = SATSolver.solve(f);
        System.out.println("answer:");
        System.out.println(answer4.toString());
    }
    
    public static void test5(){
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal d = PosLiteral.make("d");
        Literal e = PosLiteral.make("e");
        
        Clause clause1 = make(a,b);
        Clause clause2 = make(c,d,e);
        Formula f = new Formula(clause1);
        f = f.addClause(clause2);
        Environment answer5 = SATSolver.solve(f);
        System.out.println("\nTest5:");
        System.out.println("(a or b) and (c or d or e)");
        System.out.println("many possible answers - should have at least one from each group");
        System.out.println(answer5.toString());
    }
    
    private static Clause make(Literal... e) {
        Clause c = new Clause();
        for (int i = 0; i < e.length; ++i) {
            c = c.add(e[i]);
        }
        return c;
    }

    
}