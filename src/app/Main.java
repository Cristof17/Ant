import gnu.prolog.vm.Environment;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.database.PrologTextLoader;
import gnu.prolog.database.PrologTextLoaderState;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologException;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.vm.TermConstants;

public class Main {
    private static boolean DBG=true;
    public static void main(String[] args){
        //predicate
        AtomTerm cristof = AtomTerm.get("cristof");
        AtomTerm robert = AtomTerm.get("robert");
        AtomTerm frate = AtomTerm.get("frate"); 
        Term[] arguments = new Term[]{cristof, robert};
        CompoundTerm regula = new CompoundTerm(frate, arguments);

        //predicate
        AtomTerm frate2 = AtomTerm.get("frate");
        AtomTerm cristof2 = AtomTerm.get("cristof");
        AtomTerm cric = AtomTerm.get("cric");
        Term[] arguments3 = new Term[]{cristof2, cric};
        CompoundTerm regula3 = new CompoundTerm(frate2, arguments3);

        //list
        AtomTerm listFunctor = AtomTerm.get(".");
        AtomTerm arg1 = AtomTerm.get("1");
        AtomTerm arg2 = AtomTerm.get("2");
        AtomTerm arg3 = AtomTerm.get("3");
        Term[] listArgs = new Term[]{arg1, arg2, arg3};
        CompoundTerm list = new CompoundTerm(listFunctor, listArgs);

        //predicate with list
        AtomTerm casa = AtomTerm.get("casa");
        AtomTerm numarCamere = AtomTerm.get("3");
        AtomTerm listFunctor2 = AtomTerm.get(".");
        AtomTerm ruleWithListArg1 = AtomTerm.get("camera1");
        AtomTerm ruleWithListArg2 = AtomTerm.get("camera2");
        AtomTerm ruleWithListArg3 = AtomTerm.get("camera3");
        Term ruleListArgs[] = new Term[]{ruleWithListArg1, ruleWithListArg2, ruleWithListArg3};
        CompoundTerm list2 = new CompoundTerm(listFunctor2, ruleListArgs);
        Term[] ruleArgs = new Term[]{numarCamere, list2};
        CompoundTerm ruleWithList = new CompoundTerm(casa, ruleArgs);

        //add a simple rule
        //create the rule
        AtomTerm headFunctor = AtomTerm.get("iubeste");
        AtomTerm gigel = AtomTerm.get("gigel");
        VariableTerm headVariable = new VariableTerm("X");
        Term[] headArgs = new Term[]{headVariable};
        CompoundTerm head = new CompoundTerm(headFunctor, headArgs);
        AtomTerm bodyFunctor1 = AtomTerm.get("frumoasa");
        Term bodyTerms1[] = new Term[]{headVariable};
        CompoundTerm body1 = new CompoundTerm(bodyFunctor1, bodyTerms1);
        AtomTerm bodyFunctor2 = AtomTerm.get("draguta");
        Term bodyTerms2[] = new Term[]{headVariable};
        CompoundTerm body2 = new CompoundTerm(bodyFunctor1, bodyTerms1);
        Term bodyTerms[] = new Term[]{body1, body2};
        CompoundTerm body = new CompoundTerm(TermConstants.conjunctionTag, bodyTerms);
        Term[] simpleRuleArgs = new Term[]{head, body};
        CompoundTerm simpleRule = new CompoundTerm(TermConstants.clauseTag, simpleRuleArgs);

        //add basic predicates
        Term bodyPredicateArgs1[] = new Term[]{gigel};
        CompoundTerm bodyPredicate1 = new CompoundTerm(bodyFunctor1, bodyPredicateArgs1);
        Term bodyPredicateArgs2[] = new Term[]{gigel};
        CompoundTerm bodyPredicate2 = new CompoundTerm(bodyFunctor2, bodyPredicateArgs2);


        //add a simple list for head and tail query
        //exist(usa);
        //exist(usa2);
        //predicate([H|T]) :- exist(H), exist(T).
        AtomTerm predicate = AtomTerm.get("predicate");
        VariableTerm headTerm = new VariableTerm("H");
        VariableTerm tailTerm = new VariableTerm("T");
        AtomTerm headTailElement = AtomTerm.get(headTerm.toString()+"|"+tailTerm.toString());
        Term[] headTailArgs = new Term[]{headTailElement};
        AtomTerm headTailFunctor = AtomTerm.get("."); 
        CompoundTerm headTailList = new CompoundTerm(headTailFunctor, headTailArgs);
        Term[] headTailPredicateArgs = new Term[]{headTailList};
        CompoundTerm headTailTerm = new CompoundTerm(predicate, headTailPredicateArgs);
        AtomTerm existTerm = AtomTerm.get("exist");
        AtomTerm usa = AtomTerm.get("usa");
        AtomTerm usa2 = AtomTerm.get("usa2");
        Term[] usaArgs = new Term[]{usa};
        Term[] usa2Args = new Term[]{usa2};
        CompoundTerm usaTerm = new CompoundTerm(existTerm, usaArgs);
        CompoundTerm usa2Term = new CompoundTerm(existTerm, usa2Args);
        Term[] ruleFirstTermArgs = new Term[]{headTerm};
        CompoundTerm ruleFirstTerm = new CompoundTerm(existTerm, ruleFirstTermArgs);
        Term[] ruleSecondTermArgs = new Term[]{tailTerm};
        CompoundTerm ruleSecondTerm = new CompoundTerm(existTerm, ruleSecondTermArgs);
        Term[] headTailClauseTermArgs = new Term[]{ruleFirstTerm, ruleSecondTerm};
        CompoundTerm headTailClauseTerm = new CompoundTerm(TermConstants.conjunctionTag, headTailClauseTermArgs);
        Term[] headTailClauseArgs = new Term[]{headTailTerm, headTailClauseTerm};
        CompoundTerm headTailClause = new CompoundTerm (TermConstants.clauseTag, headTailClauseArgs);

        /* ADD THE CLAUSES TO THE DATABASE
         *
         * Steps:
         * -create an Environment object with empty constructor
         * -get a PrologTextLoaderState object from the environment using
         *      getPrologTextLoaderState();
         * -create a PrologTextLoader object and pass the PrologTextLoaderState
         *      object to the constructor of PrologTextLoader and a null
         *      reference to replace the Term object required by the constructor
         *      which should be converted to Term to pass the compiler
         *      "ambigous"
         * -call addClause on the PrologTextLoaderState object passing the
                PrologTextLoader object and the Term object to be added
         */
        Environment env = new Environment();
        PrologTextLoaderState state = env.getPrologTextLoaderState();
        PrologTextLoader loader = new PrologTextLoader(state, (Term)null);
        //add
        state.addClause(loader, regula);
        state.addClause(loader, regula3);
        state.addClause(loader, list);
        state.addClause(loader, ruleWithList);
        //simple rule add
        //add the preidcates first and then add the rule
        state.addClause(loader, bodyPredicate1);
        state.addClause(loader, bodyPredicate2);
        state.addClause(loader, simpleRule);
        //head tail term rule
        state.addClause(loader, usaTerm);
        state.addClause(loader, usa2Term);
        state.addClause(loader, headTailClause);
        //get
        //call state.getModule().getDefinedPredicate(tag) where tag is a CompundTerm.tag object
        //the call returns a predicate which contains the tag. If the predicate is different than
        //null it means it was defined and you can get the clauses that contain the predicate name
        //by calling state.geModule().getClauses() which returns a List<Term> where the clauses
        //that contain the predicate reside

        //remove(state, regula);
        update(state, regula3);
        
        /*
         * RUN QUERYS
         * Steps:
         * -create Term a term object representing the goal
         * -create an Interpreter using env.createInterpreter();
         * -call env.runInitialization(interpreter);
         * -call interpreter.prepareGoal(goalTerm) which returns a Goal object;
         * -call interpreter.executeGoal(goalObject);
         * -at each point after executeGoal you can do a dereference on a variableTerm in
            the goal
         * -call interpreter.stop(goalObject);
         */
        AtomTerm goalFunctor = AtomTerm.get("frate");
        AtomTerm first = AtomTerm.get("cristof");
        VariableTerm variable = new VariableTerm("X");
        Term[] goalArguments = new Term[]{first, variable};
        CompoundTerm goalTerm = new CompoundTerm(goalFunctor, goalArguments);
        Interpreter interpreter = env.createInterpreter();
        Goal goal = interpreter.prepareGoal(goalTerm);
        try {
            int rc = 0;
            do{
                System.out.println("[MAIN]: Execut in main");
                rc = interpreter.execute(goal);
                System.out.println("rc = " + rc + " variable = " + variable.dereference().toString());
                if (rc == 1)
                    break;
            }while(rc > -1);
        }catch(PrologException e){
            e.printStackTrace();
        }


        //get the parameter of a list in prolog
        AtomTerm listGoalFunctor = AtomTerm.get(".");
        AtomTerm listGoalArg1 = AtomTerm.get("1");
        AtomTerm listGoalArg2 = AtomTerm.get("2");
        AtomTerm listGoalArg3 = AtomTerm.get("3");
        VariableTerm listVariable = new VariableTerm("X");
        Term listGoalArgs[] = new Term[]{listGoalArg1, listVariable, listGoalArg3};
        Term listGoalTerm = new CompoundTerm(listGoalFunctor, listGoalArgs);
        Goal listGoal = interpreter.prepareGoal(listGoalTerm);
        try {
            int rc = interpreter.execute(listGoal);
            System.out.println("rc = " + rc + " variable = " + listVariable.dereference().toString()); 
        }catch(PrologException e){
            e.printStackTrace();
        }

        AtomTerm ruleWithListGoalFunctor = AtomTerm.get("casa");
        AtomTerm ruleWithListGoalArg1 = AtomTerm.get("3");
        VariableTerm ruleWithListGoalVariableTerm = new VariableTerm("Y");
        Term[] ruleWithListGoalTermArgs = new Term[]{ruleWithListGoalArg1, ruleWithListGoalVariableTerm};
        CompoundTerm ruleWithListGoalTerm = new CompoundTerm(ruleWithListGoalFunctor, ruleWithListGoalTermArgs);
        Goal ruleWithListGoal = interpreter.prepareGoal(ruleWithListGoalTerm);
        try {
            int rc = interpreter.execute(ruleWithListGoal);
            System.out.println("rc = " + rc + " variable = " + ruleWithListGoalVariableTerm.dereference().toString());
        }catch(PrologException e){
            e.printStackTrace();
        }


        //query the simple rule
        AtomTerm simpleRuleGoalFunctor = AtomTerm.get("iubeste");
        AtomTerm marcel = AtomTerm.get("marcel");
        AtomTerm gigelGoal = AtomTerm.get("gigel");
        VariableTerm simpleRuleVariable = new VariableTerm("Y");
        Term simpleRuleGoalTermArgs[] = new Term[]{simpleRuleVariable};
        CompoundTerm simpleRuleGoalTerm = new CompoundTerm(simpleRuleGoalFunctor, simpleRuleGoalTermArgs);
        Goal simpleRuleGoal = interpreter.prepareGoal(simpleRuleGoalTerm);
        try{
            int rc = interpreter.execute(simpleRuleGoal);
            System.out.println("simpleRule rc = " + rc + " variable = " + simpleRuleVariable.dereference().toString());
        }catch(PrologException e){
            e.printStackTrace();
        }

        //query head tail rule
        AtomTerm headTailGoalTermPredicate = AtomTerm.get("predicate");
        VariableTerm headTailGoalArgTermListArg1 = new VariableTerm("X");
        AtomTerm headTailGoalArgTermListArg2 = AtomTerm.get("cricric");
        Term[] headTailGoalArgTermListArgs = new Term[]{headTailGoalArgTermListArg1, headTailGoalArgTermListArg2};
        AtomTerm headTailGoalArgTermListFunctor = AtomTerm.get(".");
        CompoundTerm headTailGoalArgTerm = new CompoundTerm(headTailGoalArgTermListFunctor, headTailGoalArgTermListArgs);
        Term[] headTailGoalTermArgs = new Term[]{headTailGoalArgTerm};
        CompoundTerm headTailGoalTerm = new CompoundTerm(headTailGoalTermPredicate, headTailGoalTermArgs);
        Goal headTailGoal = interpreter.prepareGoal(headTailGoalTerm);
        try {
            int rc = interpreter.execute(headTailGoal);
            System.out.println("rc = " + rc);
        }catch(PrologException e){
            e.printStackTrace();
        }
        
    }

   static void remove(PrologTextLoaderState state, CompoundTerm regula){
        //remove
        //cannot remove simply by calling removeClause with the clause defined by us
        //because the clause that we are passing to remove is not the same object as
        //the clause which has been introduced because in the source code there is a call
        //Predicate.prepareClause(clause) which creates a new clause based on our initial
        //clause. What we can do is iterate throught all the clauses returned by
        //getClauses and see which one equals our clause so that we can remove it
        gnu.prolog.database.Predicate predicate = state.getModule().getDefinedPredicate(regula.tag);
        java.util.List<Term> clauses = predicate.getClauses();
        java.util.Iterator<Term> it = clauses.iterator();
        while (it.hasNext()){
            Term clause = it.next();
            if (clause instanceof CompoundTerm){
                CompoundTerm compoundClause = (CompoundTerm)clause;
                Term firstArg = compoundClause.args[0];//the tag is :-
                if (firstArg instanceof CompoundTerm){
                    CompoundTerm realClause = (CompoundTerm)firstArg;
                    System.out.println(realClause);
                    if (realClause.compareTo(regula) == 0){
                        //o iau din lista de clause a predicatului si o sterg
                        predicate.removeClause(clause);
                    }
                }
            }
        }
    }

    static void update(PrologTextLoaderState state, CompoundTerm regula){
        //update de facut metoda speciala pentru el
        //to update a clause at the beginning we need to 
        //get the clause from the clauses list returned by the predicate
        //then update the specific item, either functor or arguments by using atomterm.get()
        gnu.prolog.database.Predicate predicate2 = state.getModule().getDefinedPredicate(regula.tag);
        java.util.List<Term> clauses2 = predicate2.getClauses();
        java.util.Iterator<Term> it2 = clauses2.iterator();
        while (it2.hasNext()){
            Term clause = it2.next();
            if (clause instanceof CompoundTerm){
                CompoundTerm compoundClause = (CompoundTerm)clause;
                System.out.println(compoundClause);
                Term firstArg = compoundClause.args[0];//the tag is :-
                if (firstArg instanceof CompoundTerm){
                    CompoundTerm realClause = (CompoundTerm)firstArg;
                    System.out.println(realClause);
                    AtomTerm atomTerm = (AtomTerm)realClause.args[1];
                    if (realClause.compareTo(regula) == 0){
                        //o iau din lista de clause a predicatului si o sterg
                        realClause.args[1] = AtomTerm.get("geam");
                    }
                }
            }
        }
    }
}
