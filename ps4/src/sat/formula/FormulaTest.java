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
        test3();
    }
    
    public static void test1(){
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal na = a.getNegation();
        Literal nb = b.getNegation();
        Literal nc = c.getNegation();
        
        
        System.out.println("Test 1");
        System.out.println("Convert clause to Formula, then add formula. \n Expected (a or b) and (~a or c) ");
        Clause clause1 = make(a,b);
        Clause clause2 = make(na, c);
        Formula formula1 = new Formula(clause1);
        System.out.println(formula1.toString());
        formula1 = formula1.addClause(clause2);
        System.out.println(formula1.toString());
        System.out.println("Test 1 part b");
        System.out.println("Convert to not:");
        System.out.println("Expected - [(null),(~a~c),(~ba)(~b~c)");
        Formula formula2 = formula1.not();
        System.out.println(formula2.toString());
    }
    
    public static void test2(){
        System.out.println("(a and b) or c");
        System.out.println("expecting final result: [(a,c)(b,c)]");
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Clause clause1 = make(a);
        Clause clause2 = make(b);
        Clause clause3 = make(c);
        Formula f1 = new Formula(clause1);
        Formula f2 = new Formula(clause2);
        f1 = f1.and(f2);
        System.out.println("first, expecting a and b - if 'and' method works");
        System.out.println(f1.toString());
        f2 = new Formula(clause3);
        Formula testtwo = f1.or(f2);
        System.out.println(testtwo.toString());
    }
    
    public static void test3(){
        System.out.println("Test 3");
        System.out.println("(a,b) and (c,d,e)");
        System.out.println("with not");
        System.out.println("expected result: six clauses:");
        System.out.println("~a,~c  ~a ~d ~a~e  and the same for 'b' and the other three");
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