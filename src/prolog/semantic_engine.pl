%The check function is checked using predefined rules
%means(+Element, +Element, +Element).
%antrenare
means(sensor1, move1, semantic1). //YAW-PITCH-ROLL,GYROX,GYROY,GYROZ
means(sensor1, move2, semantic1).
means(sensor1, move3, semantic1).
means(sensor1, move4, semantic1).
means(sensor1, move5, semantic2).
means(sensor1, move6, semantic2).
means(sensor1, move7, semantic2).
means(sensor1, move8, semantic2).
means(sensor1, move9, semantic3).
means(sensor1, move10, semantic3).
means(sensor1, move11, semantic3).
means(sensor1, move12, semantic3).

%caninfer(+Element, +List).
%function for checking if the context can still be modified given the
%combination of move and current context.
%If there is not rule matching for a given move and a given context, then
%the current context is not modifiable anymore
caninfer(X, []). %you can modify a null context
caninfer(move1, [semantic1]). %you can modify a single value context
caninfer(move2, [semantic1]). %you can modify a single value context

%append(+List, +Elem, -AppendedList)
append([], X, [X]).
append([H|T], Elem, Out) :- append(T, Elem, Out2), Out = [H|Out2].
%append(+CurrentList, +NewList, -ResultList).
appendlist(List, [], List).
appendlist(List, [H|T], Out):- append(List, H, Ret), appendlist(Ret, T, Out).

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
infer([semantic1], semantic1, [semantic1, semantic2]).
infer(Context, Semantic, NewContext) :- append(Context, Semantic, NewContext).

%Clear the given context for a new one
%clear(+Element, +List, -OutList).
clear(Semantic, Context, NewContext) :- NewContext = [].

engine([], [], History).
engine([H|T], Context, History) :- write("Move is:"),write(H), write("\t\t\tContext is:"), write(Context), write("\t\t\tHistory is:"), write(History),(
					 caninfer(H, Context) -> write(" Infer\t"),
					 means(sensor1, H, Semantic), infer(Context, Semantic, NewContext), !, NewHistory = History; write(" Clear \t"),
					 appendlist(History, Context, NewHistory), NewContext = []
			     ),
				write("\n"),
				engine(T, NewContext, NewHistory).
engine([], Context, History) :- write("Move is:[]"), write("\t\t\tContext is:"), write(Context), write("\t\t\tHistory is:"), write(History).
