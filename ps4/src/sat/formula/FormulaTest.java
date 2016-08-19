package sat.formula;

import static org.junit.Assert.*;
import org.junit.Test;

public class FormulaTest {    


    // make sure assertions are turned on!  
    // we don't want to run test cases without assertions too.
    // see the handout to find out how to turn them on.
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    public static void main(String[] args){
        testFormula();
    }
    
    public static void testFormula(){
        
        test1();
        test2();
    }
    
    public static void test1(){
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal na = a.getNegation();
        Literal nb = b.getNegation();
        Literal nc = c.getNegation();
        
        Clause clause1 = make(a,b);
        Clause clause2 = make(na, c);
        Formula formula1 = new Formula(clause1);
        System.out.println(formula1.toString());
        formula1 = formula1.addClause(clause2);
        System.out.println(formula1.toString());
        Formula formula2 = formula1.not();
        System.out.println(formula2.toString());
    }
    
    public static void test2(){
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal d = PosLiteral.make("d");
        
        Clause clause1 = make(a,b);
        Clause clause2 = make(c,d);
        Formula f = new Formula(clause1);
        f = f.addClause(clause2);
        f = f.not();
        System.out.println(f.toString());
        
        
    }
    
    

    
    
    // Helper function for constructing a clause.  Takes
    // a variable number of arguments, e.g.
    //  clause(a, b, c) will make the clause (a or b or c)
    // @param e,...   literals in the clause
    // @return clause containing e,...
    private static Clause make(Literal... e) {
        Clause c = new Clause();
        for (int i = 0; i < e.length; ++i) {
            c = c.add(e[i]);
        }
        return c;
    }
}