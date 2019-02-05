%variable is visible outside the if condition branches
check(var, true).
haha(var1, var2).
printeaza(X) :- write(X).

printceva(Var) :- write(Var).
testf(Ceva) :- (check(var, true) -> check(X, Y), write(true); write(false)),
		write(X),
 		write(Y).
