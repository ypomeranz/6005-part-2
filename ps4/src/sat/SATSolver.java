package sat;

import immutable.ImList;
import immutable.EmptyImList;
import sat.env.*;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     * 
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        Formula x = formula;
        Environment env = new Environment();
        return solve(x.getClauses(), env);
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     * 
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        Clause x = new Clause();
        Boolean allnull = true;
        Environment newenv = env;
        
        //Step 1: Create a new list that reflects this environment
        //Along the way - check if the list
        ImList<Clause> clauzlist = new EmptyImList<Clause>();
        for (Clause clause : clauses){
            x = new Clause(); 
            for (Literal l : clause){
                
                Bool lstatus = l.eval(env);
                if (lstatus == Bool.TRUE){
                    x = null;
                    break;
                } else if (lstatus == Bool.UNDEFINED){
                    x = x.add(l);
                }
                //a key thing here works by ommission - if the literal status is false, it won't be added to the new clause
                
            }
            //check to see if it's an empty clause - if there exists even one, we fail and return null
            //also check to see if everything is null - if one is not null, then we won't return yet...
            if (x != null && x.isEmpty()){
                return null;
            } else if (x != null && allnull==true){
                allnull = false;
            }
            //for simplicity - our new list of clauses will contain only non-null clauses 
            if (x != null){
                System.out.println(x.toString());
                clauzlist = clauzlist.add(x);
                System.out.println(clauzlist.toString());
            }
        }
        
        //if we've gone through every clause, and in the new environment all are null
        //then we have a solution - return current environment
        if (allnull == true){
            return env;
        }
        
        //if we've gotten to this point we still have clauses left
        //so we change the environment by setting the status of a variable
        //best case scenario - there's a clause with only one literal
        //otherwise remember the smallest clause
        System.out.println("printing clauzlist");
        System.out.println(clauzlist.toString());
        int smallest = -1;
        Clause smallestclauz = new Clause();
        for (Clause clause : clauzlist){
            if (clause.size()==1){
                Literal l = clause.chooseLiteral();
                if (l.toString().charAt(0)!='~'){
                    //Writing a print statement here because I'm not sure how to use getClass
                    System.out.println("class is posliteral");
                    newenv = env.putTrue(l.getVariable());
                } else if (l.toString().charAt(0)=='~'){
                    System.out.println("class is negliteral");
                    newenv = env.putFalse(l.getVariable());
                }
                return solve(clauzlist, newenv);
            } else if (smallest == -1){
                smallest = clause.size();
                smallestclauz = clause;
            } else if (clause.size() < smallest){
                smallest = clause.size();
                smallestclauz = clause;
            }
        }
        //at this point we've gone through the clauzlist and have a smallest clause that isn't of size 1
        //so pick a random literal - set to true, then recurse
        System.out.println(smallestclauz.toString());
        Literal a = smallestclauz.chooseLiteral();
        Boolean littruth = false;
        if (a.toString().charAt(0)=='~'){
            newenv = env.putFalse(a.getVariable());
            littruth = false;
        } else {
            newenv = env.putTrue(a.getVariable());
            littruth = true;
        }
        Environment asettotrue = solve(clauzlist, newenv);
        if (asettotrue != null){
            return asettotrue;
        } else{
            if (littruth == false){
                newenv = env.putTrue(a.getVariable());
            } else {
                newenv = env.putFalse(a.getVariable());
            }
            return solve(clauzlist, newenv);
        }
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     * 
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {
        // TODO: implement this.
        throw new RuntimeException("not yet implemented.");
    }
    


}
