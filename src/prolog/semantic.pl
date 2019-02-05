%means(+Element, +Element).
means(move1, semantic1).
means(move2, semantic1).
means(move3, semantic1).
means(move4, semantic1).
means(move5, semantic2).
means(move6, semantic2).
means(move7, semantic2).
means(move8, semantic2).
means(move9, semantic3).
means(move10, semantic3).
means(move11, semantic3).
means(move12, semantic3).

%each list element should have the same "semantic" in the database
%same(+List, +Element).
same([], Semantic).
same([H|T], Semantic) :- means(H, Semantic1), Semantic = Semantic1, same(T, Semantic).


%check(+Element, +List).
check(Move, List) :- means(Move, Semantic), 
		 	same(List, Semantic).

%append(+List, +Elem, -AppendedList)
append([], X, [X]).
append([H|T], Elem, Out) :- append(T, Elem, Out2), Out = [H|Out2].

%This example shows just an appending rule, 
%But here is the spot where inference rules can be set so that when
%the new semantic comes in, the newcontext to be something defined as an axiom, 
%not a concatenation of a current context and an element
%concatenation is just an example of a simple inference rule
%rule can be set for any semantic that comes along. Here is an example

%This rule basically means that when semantic1 is generated when the context list
%is [semantic1, semantic3], the new context should become [semantic1, semantic2, semantic4].
%These rules need to be defined manually, entered as axioms in the database
rule(semantic1, [semantic1, semantic3], [semantic1, semantic2, semantic4]).

%infer(+List, +Element, -Appended List)
infer(Context, Semantic, NewContext) :- append(Context, Semantic, NewContext).

%Clear the given context for a new one
%clear(+Element, +List, -OutList).
clear(Semantic, Context, NewContext) :- NewContext = [].

engine([], []).
engine([H|T], Context) :- write(H), write(Context),(
					 check(H, Context) ->
					 means(H, Semantic), infer(Context, Semantic, NewContext), write(NewContext) ;
					 NewContext = [], write(NewContext)
			     ),
				engine(T, NewContext).
