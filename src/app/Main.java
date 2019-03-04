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
import gnu.prolog.term.JavaObjectTerm;

public class Main {

        private static boolean DBG=false;
        public static void main(String[] args){
        //simple predicate frate(cristof, robert)

        AtomTerm cristof = AtomTerm.get("cristof");
        AtomTerm robert = AtomTerm.get("robert");
        AtomTerm frate = AtomTerm.get("frate"); 
        Term[] arguments = new Term[]{cristof, robert};
        CompoundTerm regula = new CompoundTerm(frate, arguments);

        //simple predicate frate(cristof, cric);
        AtomTerm frate2 = AtomTerm.get("frate");
        AtomTerm cristof2 = AtomTerm.get("cristof");
        AtomTerm cric = AtomTerm.get("cric");
        Term[] arguments3 = new Term[]{cristof2, cric};
        CompoundTerm regula3 = new CompoundTerm(frate2, arguments3);

        //simple list [1, 2, 3]
        AtomTerm listFunctor = AtomTerm.get(".");
        AtomTerm arg1 = AtomTerm.get("1");
        AtomTerm arg2 = AtomTerm.get("2");
        AtomTerm arg3 = AtomTerm.get("3");
        Term[] listArgs = new Term[]{arg1, arg2, arg3};
        CompoundTerm list = new CompoundTerm(listFunctor, listArgs);

        //predicate with list casa(3, [camera1, camera2, camera3])
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
        //simple rule iubeste(X) :- frumoasa(X), draguta(X).
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
        Term[] headTailArgs = new Term[]{headTerm, tailTerm};
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
        //rule with JavaObjectTerm
    	MyObject obj1 = new MyObject(1);
    	JavaObjectTerm java = new JavaObjectTerm(obj1);
    	AtomTerm javaTerm = AtomTerm.get("java");
    	Term[] javaRuleArgs = new Term[]{java};
    	CompoundTerm javaPredicate = new CompoundTerm(javaTerm, javaRuleArgs);

	    //JavaObjects
    	//add simple java object

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
        AtomTerm file = AtomTerm.get("rules.pl");
        env.ensureLoaded(file);
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
	    state.addClause(loader, javaPredicate);
		if (DBG){
			System.out.println("add: " + regula.toString());
			System.out.println("add: " + regula3.toString());
			System.out.println("add: " + list.toString());
			System.out.println("add: " + ruleWithList.toString());
			System.out.println("add: " + bodyPredicate1.toString());
			System.out.println("add: " + bodyPredicate2.toString());
			System.out.println("add: " + simpleRule.toString());
			System.out.println("add: " + usaTerm.toString());
			System.out.println("add: " + usa2Term.toString());
			System.out.println("add: " + headTailClause.toString());
			System.out.println("add: " + javaPredicate.toString());
		}
        //get
        //call state.getModule().getDefinedPredicate(tag) where tag is a CompundTerm.tag object
        //the call returns a predicate which contains the tag. If the predicate is different than
        //null it means it was defined and you can get the clauses that contain the predicate name
        //by calling state.geModule().getClauses() which returns a List<Term> where the clauses
        //that contain the predicate reside
        remove(state, regula);
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
		//simple predicate
        AtomTerm goalFunctor = AtomTerm.get("frate");
        AtomTerm first = AtomTerm.get("cristof");
        VariableTerm variable = new VariableTerm("X");
        Term[] goalArguments = new Term[]{first, variable};
        CompoundTerm goalTerm = new CompoundTerm(goalFunctor, goalArguments);
        Interpreter interpreter = env.createInterpreter();
        Goal goal = interpreter.prepareGoal(goalTerm);
		if (DBG){
			System.out.println("query: simple predicate: frate(cristof, X)");
		}
        try {
            int rc = 0;
            do{
                rc = interpreter.execute(goal);
                System.out.println("rc = " + rc + " X = " + variable.dereference().toString());
                if (rc == 1)
                    break;
            }while(rc > -1);
        }catch(PrologException e){
            e.printStackTrace();
        }


        //simple list
        AtomTerm listGoalFunctor = AtomTerm.get(".");
        AtomTerm listGoalArg1 = AtomTerm.get("1");
        AtomTerm listGoalArg2 = AtomTerm.get("2");
        AtomTerm listGoalArg3 = AtomTerm.get("3");
        VariableTerm listVariable = new VariableTerm("X");
        Term listGoalArgs[] = new Term[]{listGoalArg1, listVariable, listGoalArg3};
        Term listGoalTerm = new CompoundTerm(listGoalFunctor, listGoalArgs);
        Goal listGoal = interpreter.prepareGoal(listGoalTerm);
		if(DBG){
			System.out.println("query: simple list: [1, X, 3]");
		}
        try {
            int rc = interpreter.execute(listGoal);
            System.out.println("rc = " + rc + " X = " + listVariable.dereference().toString()); 
        }catch(PrologException e){
            e.printStackTrace(); }


		//predicate with list casa(3, Y)
        AtomTerm ruleWithListGoalFunctor = AtomTerm.get("casa");
        AtomTerm ruleWithListGoalArg1 = AtomTerm.get("3");
        VariableTerm ruleWithListGoalVariableTerm = new VariableTerm("Y");
        Term[] ruleWithListGoalTermArgs = new Term[]{ruleWithListGoalArg1, ruleWithListGoalVariableTerm};
        CompoundTerm ruleWithListGoalTerm = new CompoundTerm(ruleWithListGoalFunctor, ruleWithListGoalTermArgs);
        Goal ruleWithListGoal = interpreter.prepareGoal(ruleWithListGoalTerm);
		if (DBG){
			System.out.println("query: rule with list: casa(3, Y)");
		}
        try {
            int rc = interpreter.execute(ruleWithListGoal);
            System.out.println("rc = " + rc + " Y = " + ruleWithListGoalVariableTerm.dereference().toString());
        }catch(PrologException e){
            e.printStackTrace();
        }


        //simple clause query
        AtomTerm simpleRuleGoalFunctor = AtomTerm.get("iubeste");
        VariableTerm simpleRuleVariable = new VariableTerm("Y");
        Term simpleRuleGoalTermArgs[] = new Term[]{simpleRuleVariable};
        CompoundTerm simpleRuleGoalTerm = new CompoundTerm(simpleRuleGoalFunctor, simpleRuleGoalTermArgs);
        Goal simpleRuleGoal = interpreter.prepareGoal(simpleRuleGoalTerm);
		if (DBG){
			System.out.println("query: simple rule: iubeste(Y)");
		}
        try{
            int rc = interpreter.execute(simpleRuleGoal);
            System.out.println("rc = " + rc + " Y = " + simpleRuleVariable.dereference().toString());
        }catch(PrologException e){
            e.printStackTrace();
        }

	    //clause with list with [H|T]
        AtomTerm predicateGoalTermFunctor = AtomTerm.get("predicate");
        VariableTerm headGoalTerm = new VariableTerm("H");
        VariableTerm tailGoalTerm = new VariableTerm("T");
        Term[] predicateGoalArgs = new Term[]{headGoalTerm, tailGoalTerm};
        AtomTerm headTailPredicateGoalTermFunctor = AtomTerm.get(".");
        CompoundTerm headTailPredicateGoalTerm = new CompoundTerm(headTailPredicateGoalTermFunctor, predicateGoalArgs);
        Term[] predicateGoalTermArgs = new Term[]{headTailPredicateGoalTerm};
        CompoundTerm predicateGoalTerm = new CompoundTerm(predicateGoalTermFunctor, predicateGoalTermArgs);
        Goal headTailListGoal = interpreter.prepareGoal(predicateGoalTerm);
		if (DBG){
			System.out.println("query: list with [H|T]: predicate([H|T])");
		}
        try {
            int rc = 0;
            while (rc == 0){
                rc = interpreter.execute(headTailListGoal); 
                System.out.println("rc =" + rc + "[H="+headGoalTerm.dereference().toString()+"|T="+tailGoalTerm.dereference().toString()+"]");
            }
        }catch (PrologException e){
            e.printStackTrace();
        }

        AtomTerm javaGoalPredicate = AtomTerm.get("java");
        VariableTerm javaGoalVariable = new VariableTerm("X");
        Term[] javaGoalArgs = new Term[]{javaGoalVariable};
        CompoundTerm javaGoalTerm = new CompoundTerm(javaGoalPredicate, javaGoalArgs);
        Goal javaGoal = interpreter.prepareGoal(javaGoalTerm);
        if (DBG){
            System.out.println("query = java object java(Y)");
        }
        try {
            int rc = interpreter.execute(javaGoal);
            System.out.println("rc = " + rc + " Y = " + javaGoalVariable.dereference().toString());
            boolean equal = javaGoalVariable.dereference().equals(java);
            System.out.println("JavaGoalVariable class type = " + equal);
            
        }catch(PrologException e){
            e.printStackTrace();
        }

        AtomTerm filePredicateTerm = AtomTerm.get("means");
        AtomTerm filePredicateTermFirstArgument = AtomTerm.get("move1");
        AtomTerm filePredicateTermSecondArgument = AtomTerm.get("semantic1");
        Term filePredicateTermArgs[] = new Term[]{filePredicateTermFirstArgument, filePredicateTermSecondArgument};
        CompoundTerm fromFilePredicateTerm = new CompoundTerm(filePredicateTerm, filePredicateTermArgs);
        try {
            Goal filePredicateTermGoal = interpreter.prepareGoal(fromFilePredicateTerm);
            int rc = interpreter.execute(filePredicateTermGoal);
            System.out.println("filePredicateTermGoal Rc = " + rc);
        }catch(PrologException e){
            e.printStackTrace();
        }
            
        }

        static void addPredicate(PrologTextLoaderState state
                    ,PrologTextLoader loader
                    ,String functor
                    ,String...arguments) throws Exception{
            if (state == null){
                throw new NullPointerException("State is null");
            }
            if (loader == null){
                throw new NullPointerException("Loader is null");
            }
            if(functor == null){
                throw new NullPointerException("Functor is null");
            }
            if (functor.equals("")){
                throw new Exception("Functor equlas empty string");
            }
            if (arguments == null){
                throw new NullPointerException("Arguments are null");
            }
            if (arguments.length == 0){
                throw new Exception("Arguments list is emtpy");
            }
            int i = 0;
            int argumentsLength = arguments.length;
            Term[] ruleArguments = new Term[argumentsLength];
            for (i = 0; i < argumentsLength; ++i){
                ruleArguments[i] = AtomTerm.get(arguments[i]);
            }
            AtomTerm rulePredicate = AtomTerm.get(functor);
            CompoundTerm rule = new CompoundTerm(rulePredicate, ruleArguments);
            state.addClause(loader, rule);
        }

        static void addPredicateWithArgsAsList(PrologTextLoaderState state,
                    PrologTextLoader loader
                    ,String functor
                    ,String...listArguments) throws Exception{
            if (state == null){
                throw new NullPointerException("State is null");
            }
            if (loader == null){
                throw new NullPointerException("Loader is null");
            }
            if(functor == null){
                throw new NullPointerException("Functor is null");
            }
            if (functor.equals("")){
                throw new Exception("Functor equlas empty string");
            }
            if (listArguments == null){
                throw new NullPointerException("listArguments are null");
            }
            if (listArguments.length == 0){
                throw new Exception("listArguments is emtpy");
            }
            int i = 0;
            int listArgumentsLength = listArguments.length;
            Term[] listArgumentsTerms = new Term[listArgumentsLength];
            for (i = 0; i < listArgumentsLength; ++i){
                listArgumentsTerms[i] = AtomTerm.get(listArguments[i]);	
            }
            AtomTerm rulePredicate = AtomTerm.get(functor);
            CompoundTerm rule = new CompoundTerm(rulePredicate, listArgumentsTerms);
            state.addClause(loader, rule);
        }

        static void addPredicate(PrologTextLoaderState state
                ,PrologTextLoader loader
                ,String functor
                ,String[] nonListArguments
                ,String...listArguments) throws Exception {
            if (state == null){
                throw new NullPointerException("State is null");
            }
            if (loader == null){
                throw new NullPointerException("Loader is null");
            }
            if(functor == null){
                throw new NullPointerException("Functor is null");
            }
            if (functor.equals("")){
                throw new Exception("Functor equlas empty string");
            }
            if (nonListArguments == null){
                throw new NullPointerException("nonListArguments are null");
            }
            if (nonListArguments.length == 0){
                throw new Exception("nonListArguments is emtpy");
            }
            if (listArguments == null){
                throw new NullPointerException("listArguments are null");
            }
            if (listArguments.length == 0){
                throw new Exception("listArguments is emtpy");
            }
            int i = 0;
            int nonListArgumentsLength = nonListArguments.length;	
            int listArgumentsLength = listArguments.length;
            Term[] listArgumentsTerms = new Term[listArgumentsLength];
            for (i = 0; i < listArguments.length; ++i){
                listArgumentsTerms[i] = AtomTerm.get(listArguments[i]);	
            }
            AtomTerm listPredicate = AtomTerm.get(".");
            CompoundTerm listTerm = new CompoundTerm(listPredicate, listArgumentsTerms);
            Term[] nonListArgumentsTerms = new Term[nonListArgumentsLength + 1];
            //+1 is for the list term passed as argument
            for (i = 0; i < nonListArgumentsLength; ++i){
                nonListArgumentsTerms[i] = AtomTerm.get(nonListArguments[i]);
            }
            AtomTerm rulePredicate = AtomTerm.get(functor);
            int lastPosition = nonListArguments.length-1;
            nonListArgumentsTerms[lastPosition] = listTerm;
            CompoundTerm rule = new CompoundTerm(rulePredicate, nonListArgumentsTerms);
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
                        //System.out.println(realClause);
                        if (realClause.compareTo(regula) == 0){
                            //o iau din lista de clause a predicatului si o sterg
                            predicate.removeClause(clause);
                    if (DBG){
                        System.out.println("remove: " + regula.toString());
                    }
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
                    //System.out.println(compoundClause);
                    Term firstArg = compoundClause.args[0];//the tag is :-
                    if (firstArg instanceof CompoundTerm){
                        CompoundTerm realClause = (CompoundTerm)firstArg;
                        //System.out.println(realClause);
                        if (realClause.compareTo(regula) == 0){
                            //o iau din lista de clause a predicatului si o sterg
                            if (DBG){
                                System.out.print("update : " + regula.toString());
                            }
                            realClause.args[1] = AtomTerm.get("geam");
                            if (DBG){
                                System.out.println(" to " + realClause.toString());
                            } 
                        }
                    }
                }
            }
        }
}
