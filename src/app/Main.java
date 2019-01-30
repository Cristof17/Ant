import gnu.prolog.vm.Environment;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.database.PrologTextLoader;
import gnu.prolog.database.PrologTextLoaderState;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologException;

public class Main {
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
         * -create a PrologTextLoaderState object and pass the Environment
         *      object to the constructor of PrologTextLoaderState
         * -create a PrologTextLoader object and pass the PrologTextLoaderState
         *      object to the constructor of PrologTextLoader and a null
         *      reference to replace the Term object required by the constructor
         *      which should be converted to Term to pass the compiler
         *      "ambigous"
         * -call addClause on the PrologTextLoaderState object passing the
                PrologTextLoader object and the Term object to be added
         */
        Environment env = new Environment();
        PrologTextLoaderState state = new PrologTextLoaderState(env);
        PrologTextLoader loader = new PrologTextLoader(state, (Term)null);
        state.addClause(loader, regula);

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
        AtomTerm second = AtomTerm.get("robert");
        Term[] goalArguments = new Term[]{first, second};
        CompoundTerm goalTerm = new CompoundTerm(goalFunctor, goalArguments);
        Interpreter interpreter = env.createInterpreter();
        Goal goal = interpreter.prepareGoal(goalTerm);
        try {
            int rc = interpreter.execute(goal);
            System.out.println("Rc = " + rc);
            interpreter.stop(goal);
        }catch(PrologException e){
            e.printStackTrace();
        }
    }
}
