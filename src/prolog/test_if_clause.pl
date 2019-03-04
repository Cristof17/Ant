exist(A).
exist(B).
regula(X) :- (exist(X) -> true; false).
