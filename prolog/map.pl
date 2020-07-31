map_build([], null).
map_build([], ).
map_build([[Key, Value] | T], node(null, RightR, Key, Value)) :-
	map_build(T, RightR).

% map_get(null, _, _) :- false.
map_get(node(_, _, _, Key, Value), Key, Value) :- !.
map_get(node(NLeft, NRight, NKey, _), Key, Value) :-
	Key < NKey,
	map_get(NLeft, Key, Value), !.
map_get(node(NLeft, NRight, NKey, _), Key, Value) :-
	Key > NKey,
	map_get(NRight, Key, Value).
	
zig()