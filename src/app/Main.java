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

public class Main {
    private static boolean DBG=true;
    public static void main(String[] args){
        AtomTerm cristof = AtomTerm.get("cristof");
        AtomTerm robert = AtomTerm.get("robert");
        AtomTerm functor = AtomTerm.get("frate"); 
        Term[] arguments = new Term[]{cristof, robert};
        CompoundTerm regula = new CompoundTerm(functor, arguments);

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
        if(DBG){
            System.out.println("BEFORE");
        }
        state.addClause(loader, regula);
        if(DBG){
            System.out.println("AFTER");
        }

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
            int rc = interpreter.execute(goal);
            System.out.println("Rc = " + rc);
            System.out.println("Variable = " + variable.dereference().toString());
            //interpreter.stop(goal);
        }catch(PrologException e){
            e.printStackTrace();
        }
    }
}
