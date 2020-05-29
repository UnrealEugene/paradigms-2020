prime(X) :- X >= 2, \+ composite(X).
least_divisor(X, X) :- prime(X). 

add_composite_(X, D) :- composite(X), !.
add_composite_(X, D) :-
	\+ composite(X),
	assert(composite(X)),
	assert(least_divisor(X, D)).
mark_as_composite_(X, L, R) :- L > R, !.
mark_as_composite_(X, L, R) :-
	F is X * L,
	add_composite_(F, X),
	NL is L + 1,
	mark_as_composite_(X, NL, R).
try_mark_(X, MAX_N) :- composite(X), !.
try_mark_(X, MAX_N) :- 
	prime(X),
	R is MAX_N // X, 
	mark_as_composite_(X, X, R).
handle_(X, MAX_N) :- X_ is X * X, X_ > MAX_N, !.
handle_(X, MAX_N) :- 
	X_ is X * X, 
	X_ =< MAX_N,
	try_mark_(X, MAX_N), 
	X1 is X + 1, 
	handle_(X1, MAX_N).
init(MAX_N) :- handle_(2, MAX_N), !.

sorted([X, Y | T]) :- X =< Y, sorted([Y | T]).
sorted([X]).
sorted([]).

reduce(F, S, [], S).
reduce(F, S, [H | T], R) :- G =.. [F, S, H, GR], call(G), reduce(F, GR, T, R).
reduce(F, [H | T], R) :- reduce(F, H, T, R).

mul(A, B, R) :- R is A * B.

prime_divisors(1, []).
prime_divisors(N, D) :- list(D), !, sorted(D), reduce(mul, D, N).
prime_divisors(N, [LD | ND]) :- number(N), !, least_divisor(N, LD), NN is N // LD, prime_divisors(NN, ND).