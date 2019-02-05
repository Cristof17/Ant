%test_head(+List, -Head).
test_head([H|T], H).
%test_tail(+List, -Tail).
test_tail([H|T], T).
%create(+Head, +Tail, -List)
create(H, T, [H|T]).
