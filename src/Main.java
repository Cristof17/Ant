import gnu.prolog.vm.Environment;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.CompoundTerm;

public class Main {
    public static void main(String[] args){
        AtomTerm cristof = AtomTerm.get("cristof");
        AtomTerm robert = AtomTerm.get("robert");
        AtomTerm functor = AtomTerm.get("frate"); 
        Term[] arguments = new Term[]{cristof, robert};
        CompoundTerm regula = new CompoundTerm(functor, arguments);

        /*
         * Add the clause to the database. Steps:
         * -create an Environment object
         * -create a PrologTextLoaderState object and pass the Environment
         *      object to the constructor of PrologTextLoaderState
         * -create a PrologTextLoader object and pass the PrologTextLoaderState
         *      object to the constructor of PrologTextLoader
         * -call processClause on the PrologTextLoaderState object passing the
                PrologTextLoader object and the Term object to be added
         */
        
    }
}
