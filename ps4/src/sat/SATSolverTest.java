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
    }
    
    public static void test1(){
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();
    
    System.out.println("Test 1 on handout");
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
    
    private static Clause make(Literal... e) {
        Clause c = new Clause();
        for (int i = 0; i < e.length; ++i) {
            c = c.add(e[i]);
        }
        return c;
    }

    
}