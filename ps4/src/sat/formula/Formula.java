/**
 * Author: dnj, Hank Huang
 * Date: March 7, 2009
 * 6.005 Elements of Software Construction
 * (c) 2007-2009, MIT 6.005 Staff
 */
 /*
 Formula = ImList<Clause>
 Clause = ImList<Literal>
 Literal = PosLiteral | NegLiteral
 */
package sat.formula;

import immutable.ImList;

import immutable.*;

import java.util.Iterator;

import sat.env.Variable;

/**
 * Formula represents an immutable boolean formula in
 * conjunctive normal form, intended to be solved by a
 * SAT solver.
 */
public class Formula {
    private final ImList<Clause> clauses;
    // Rep invariant:
    //      clauses != null
    //      clauses contains no null elements (ensured by spec of ImList)
    //
    // Note: although a formula is intended to be a set,  
    // the list may include duplicate clauses without any problems. 
    // The cost of ensuring that the list has no duplicates is not worth paying.
    //
    //    
    //    Abstraction function:
    //        The list of clauses c1,c2,...,cn represents 
    //        the boolean formula (c1 and c2 and ... and cn)
    //        
    //        For example, if the list contains the two clauses (a,b) and (!c,d), then the
    //        corresponding formula is (a or b) and (!c or d).

    void checkRep() {
        assert this.clauses != null : "SATProblem, Rep invariant: clauses non-null";
    }

    /**
     * Create a new problem for solving that contains no clauses (that is the
     * vacuously true problem)
     * 
     * @return the true problem
     */
    public Formula() {

       this.clauses = new EmptyImList<Clause>();
    }
    
    public Formula(ImList<Clause> clauses){
        this.clauses=clauses;
    }

    /**
     * Create a new problem for solving that contains a single clause with a
     * single literal
     * 
     * @return the problem with a single clause containing the literal l
     */
    public Formula(Variable l) {
        clauses = new NonEmptyImList<Clause>(new Clause(PosLiteral.make(l)));
    }

    /**
     * Create a new problem for solving that contains a single clause
     * 
     * @return the problem with a single clause c
     */
    public Formula(Clause c) {
        clauses = new NonEmptyImList<Clause>(c);
    }

    /**
     * Add a clause to this problem
     * 
     * @return a new problem with the clauses of this, but c added
     */
    public Formula addClause(Clause c) {
        Formula newForm = new Formula(clauses.add(c));
        return newForm;
        
    }

    /**
     * Get the clauses of the formula.
     * 
     * @return list of clauses
     */
    public ImList<Clause> getClauses() {
        return clauses;
    }

    /**
     * Iterator over clauses
     * 
     * @return an iterator that yields each clause of this in some arbitrary
     *         order
     */
    public Iterator<Clause> iterator() {
        // TODO: implement this.
        throw new RuntimeException("not yet implemented.");
    }

    /**
     * @return a new problem corresponding to the conjunction of this and p
     */
    public Formula and(Formula p) {
        
        ImListIterator<Clause> x = new ImListIterator<Clause>(p.clauses);
        Formula y = this;
        while (x.hasNext()){
            y = y.addClause(x.next());
        }
        return y;
    }

    /**
     * @return a new problem corresponding to the disjunction of this and p
     */
    public Formula or(Formula p) {
        // TODO: implement this.
        // Hint: you'll need to use the distributive law to preserve conjunctive normal form, i.e.:
        //   to do (a & b) .or (c & d),
        //   you'll need to make (a | b) & (a | c) & (b | c) & (b | d)    
        //Yoni - this example is wrong - should be a | d not a | b - I think?
        /* pseudocode:
            new empty formula
            for clause in formula a
                for clause in formula b
                    add clause with elements of clause a and clause b to new empty formula
            return new formula
        */
        //Note to Self: remember to allow for possibility of a null result
        Formula x = new Formula();
        ImListIterator<Clause> formulaA = new ImListIterator<Clause>(this.clauses);
        while (formulaA.hasNext()){
            ImListIterator<Clause> formulaB = new ImListIterator<Clause>(p.clauses);
            while (formulaB.hasNext()){
                Clause joinedClause = formulaA.next().merge(formulaB.next());
                if (joinedClause==null){
                    return null;
                } else {
                    x = x.addClause(joinedClause);    
                }
                
            }
        }
        return x;
    }

    /**
     * @return a new problem corresponding to the negation of this
     */
    public Formula not() {
        // TODO: implement this.
        // Hint: you'll need to apply DeMorgan's Laws (http://en.wikipedia.org/wiki/De_Morgan's_laws)
        // to move the negation down to the literals, and the distributive law to preserve 
        // conjunctive normal form, i.e.:
        //   if you start with (a | b) & c,
        //   you'll need to make !((a | b) & c) 
        //                       => (!a & !b) | !c            (moving negation down to the literals)
        //                       => (!a | !c) & (!b | !c)    (conjunctive normal form)
        throw new RuntimeException("not yet implemented.");
    }

    /**
     * 
     * @return number of clauses in this
     */
    public int getSize() {
        // TODO: implement this.
        throw new RuntimeException("not yet implemented.");
    }

    /**
     * @return string representation of this formula
     */
    public String toString() {
        String result = "Problem[";
        for (Clause c : clauses)
            result += "\n" + c;
        return result + "]";
    }
}
