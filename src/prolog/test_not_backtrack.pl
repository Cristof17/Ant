this(a, b).
this(c, d).

%the ! after a clause, makes prolog not backtrack;
iuhuu(X, Y) :- this(X, Y), !.

