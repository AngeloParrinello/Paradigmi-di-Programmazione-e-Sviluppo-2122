sum (zero, N, N) .
sum (s(N), M, s(R)) :- sum (N, M, R) .

% only cases that are true
greater (s(N), zero) .
greater (s(N), s(M)) :- greater(N, M) . 

% Prolog doesn't require a complex data structure for multiple output --> two argument at the end is enough!
% the input is the first member and the last two are the output. 
% es: prevnext(s(s(zero)), P, S) ---> P/s(zero) S/s(s(s(zero)))
prevnext(s(N), N, s(s(N))) .

% an iterable return a result at each call (so, in lazy-mode in Prolog).
% f: I1 x I2 x ... x In -> Iterable[O] --> p(I1, I2, .., In, O).
interval (N1, N2, N1) .
% l'elemento E è compreso nell'intervallo N1 e N2 SE E è compreso tra il successivo di N1 e N2
% e inoltre N2 è più grande di N1 (sennò non si fermerebbe mai).
% costruito per iterare ma per verificare se un numero è compreso nell'intervallo.
interval (N1, N2, E) :- greater(N2, N1), interval (s(N1), N2, E) .

% List in Prolog
% cons(10, cons(20, cons(30, nil))) --> cons/2, nil/0

%first exercise with List: element -> if E is an element of list L
element (E, cons(E, _)) .
element (E, cons(_, L)) :- element (E, L) .

% find(L, E) .
find ([H|_], H) .
find ([_|T], E) :- find(T, E) .

% position (L, N, E) .
position ([H|T], zero, H) .
position ([H|T], s(N), E) :- position(T, N, E) .

% similar to fold-left this functions ...
% join (L1, L2, L)
join ([], L, L) .
join ([H|T], L, [H|O]) :-  join(T, L, O) . % O is my result

% if we call...
% join([a, b, c], [d, e, f], [a, b, c, d, e, f]) --> then recursion...
% join([b, c], [d, e, f], O=[b, c, d, e, f]) --> the result is H|O!

% Prolog is magic, but at what level of magic?
% FULLY RELATIONAL: se di fatto non è vero che ha degli input o degli output, ma considerando una variabile
% messa come OUTPUT in un punto qualunque degli argomenti e lui considerando gli altri ci restituisce ci enumera
% tutte queste soluzioni. Quindi non c'è veramente una input o un output.
% es: position andrebbe usata normalmente così: position([10, 20, 30], zero, 10) --> yes
% ma dato che è fully-relational possa usarla anche così:
% -> position([10, 20, 30], zero, E) --> (mi sto chiedendo cosa c'è in posizione 0) E/10 (è come la get sulle liste)
% -> position([10, 20, 30], N, 10) --> (mi sto chiedendo in che posizione è il 10, simile ad una indexOf) N/zero
% -> position([10, 20, 30], N, E) --> (mi iteri tutti gli elementi e mi restituisci la loro posizione, simile ad una zip sull'indice) N/zero E/10, N/s(zero) E/20, N/s(s(zero)) E/30
% -> position(L, N, 10) --> mi dici tutte le liste che hanno un 10? ne genera infinte...
% RICORDA: i built-in number NON sono fully relational

% =================================================1=====================================================

% LAB 09/05/2022
% PART 1
% search (Elem , List )

search (X, [X|_]) .
search (X, [_|Xs]) :- search (X, Xs) .
% search(a,[a,b,c]). --> Yes
% search(a,[c,d,e]). --> No
% search(X,[a,b,c]). --> yes. X / a Solution: search(a,[a,b,c]) yes. X / b Solution: search(b,[a,b,c]) yes. X / c Solution: search(c,[a,b,c]) Genera tutte le possibili soluzioni VERE alla ricerca
% search(a,X). --> yes. X / [a|_1406] Solution: search(a,[a|_1406]) ... (all'infinito). Genera tutte le possibili liste che contengono il carattere "a"
% search(a,[X,b,Y,Z]). --> yes. X / a Solution: search(a,[a,b,Y,Z]) yes. Y / a Solution: search(a,[X,b,a,Z]) yes. Z / a Solution: search(a,[X,b,Y,a]). Genera tutte le possibili soluzioni VERE alla ricerca (rispettando la forma della lista in input)
% search(X,Y). yes. Y / [X|_1566] Solution: search(X,[X|_1566]) Genera tutte le infinite soluzioni ad un carattere qualunque in una lista qualunque

% search2 (Elem , List )
% looks for two consecutive occurrences of Elem

search2 (X, [X, X|_]) .
search2 (X, [_| Xs]) :- search2 (X, Xs) .
% search2(a,[b,c,a,a,d,e,a,a,g,h]). --> yes. Solution: search2(a,[b,c,a,a,d,e,a,a,g,h]) yes. Solution: search2(a,[b,c,a,a,d,e,a,a,g,h])
% search2(a,[b,c,a,a,a,d,e]). --> yes. Solution: search2(a,[b,c,a,a,a,d,e]) yes. Solution: search2(a,[b,c,a,a,a,d,e]) (ci sta perchè ci sono tre a consecutivi quindi si possono vedere come due possibili a consecutive
% search2(X,[b,c,a,a,d,d,e]).--> Cerca una possibile lettere che sia ripetuta due volte nella lista. yes. X / a Solution: search2(a,[b,c,a,a,d,d,e]) yes. X / d Solution: search2(d,[b,c,a,a,d,d,e])
% search2(a,L). --> Genera tutte le liste in cui la "a" è ripetuta ... quindi infinite volte yes
% search2(a,[_,_,a,_,a,_]). --> Genera tutti i possibili casi in questa lista particolare in cui ci sono due "a" consecutive

search_two (X, [X, _, X|_]) .
search_two (X, [_| Xs]) :- search_two(X, Xs) .

% search_two(a,[b,c,a,a,d,e]). ? no
% search_two(a,[b,c,a,d,a,d,e]). ? yes

% search_anytwo (Elem , List )
% looks for any Elem that occurs two times , anywhere
% TODO WITH SEARCH ONLY

search_anytwo (X, [X, _, X|_]) .
search_anytwo (X, [X, X, _|_]) .
search_anytwo (X, [_|Xs]) :- search_anytwo(X, Xs) .


% search_anytwo(a,[b,c,a,a,d,e]). ? yes
% search_anytwo(a,[b,c,a,d,e,a,d,e]). ? yes

% =================================================2=====================================================

% PART 2

% size (List , Size )
% Size will contain the number of elements in List

size ([], 0) .
size ([ _ | Xs ], N) :- size (Xs, N2), N is N2 + 1 .


% Check whether it works! --> Yes, it works.
% Can it allow for a fully relational behaviour? Yes, it is. The predicate has fully relation behaviour. 

% size (List , Size )
% Size will contain the number of elements in List , written using notation zero , s( zero ), s(s( zero ))..
size_pean ([], zero) .
size_pean ([ _ | Xs ], s(N)) :- size_pean (Xs, N) . % --> WORKS
% size_pean ([ _ | Xs ], N) :- size_pean (Xs, s(N)) . % --> NOT WORKS
% why? size_pean ([ _ | Xs ], N) :- size_pean (Xs, s(N)) . doesn't works? Because i am trying to "predict" the future. I'm asking to predicate that is true if its successive number size_pean it's true ...
% it's obviously impossible! Each recursive predicate depends only by its precedent recursive-case.
% Can it allow a fully relational behaviour? Yes, it can.

% sum(List , Sum )
% ?- sum ([1 ,2 ,3] , X ).
% yes .
% X /6
sum_list ([], 0) .
sum_list ([H|T], N2) :- sum_list(T, N), N2 is N + H .

% average (List , Average )
% it uses average (List ,Count ,Sum , Average )
% first line: input from user that call a sub-average predicate with count and sum that starts from zero and variable A that rapresent the average.
average ( List ,A) :- average ( List ,0 ,0 , A) .
% second line: base case. Calculate the average from count and sum.
average ([], C, S, A) :- A is S/C .
% third line: recursive case. Whatever the list is increase of one the count and sum last two number. After that re-call average predicate and re-matching with something (if it fit).
average ([ X|Xs ], C, S, A) :-
	C2 is C + 1,
	S2 is S + X,
	average (Xs, C2, S2, A) .
% Sequence of resolvent/goals
% >> average([3,4,3],A) [call to sub-average predicate]
% >> average([3,4,3],0,0,A)
% >> average([4,3],1,3,A)
% >> average([3],2,7,A)
% >> average([],3,10,A) ? A=3.3333
% Note: this is a tail recursion!!!

% max(List , Max )
% Max is the biggest element in List
% Suppose the list has at least one element
% Do you need an extra argument?
% >> first develop: max(List,Max,TempMax)
% >> where TempMax is the maximum found so far (il massimo trovato fino ad ora) (initially it is the first number in the list.)
max ([H|T], A) :- max (T, A, H) .
max ([], A, Mt) :- A is Mt.
max ([ H|T ], A, Mt) :- H > Mt, Mt2 is H, max (T, A, Mt2) .
max ([ H|T ], A, Mt) :- H < Mt, max (T, A, Mt) .

% max(List ,Max , Min )
% Max is the biggest element in List
% Min is the smallest element in List
% Suppose the list has at least one element
% Realise this yourself!
% >> by properly changing max
% >> note you have a predicate with “2 outputs”
% max_min (List, Max, MaxTemp, Min, MinTemp) .
max_min ([H|T], Ma, Mi) :- max_min (T, Ma, Mi, H, H) .
max_min ([], Ma, Mi, MaT, MiT) :- Ma is MaT, Mi is MiT .
max_min ([H|T], Ma, Mi, MaT, MiT) :- H > MaT, MaT2 is H, max_min (T, Ma, Mi, MaT2, MiT) .
max_min ([H|T], Ma, Mi, MaT, MiT) :- H < MiT, MiT2 is H, max_min (T, Ma, Mi, MaT, MiT2) .
max_min ([H|T], Ma, Mi, MaT, MiT) :- H < MaT, H > MiT, max_min (T, Ma, Mi, MaT, MiT) .


% =================================================3=====================================================

% same (List1 , List2 )
% are the two lists exactly the same ?

same ([] ,[]) .
same ([ X | Xs ] ,[ X| Ys ]) :- same ( Xs , Ys ).

% same ([10], [10]) . --> yes. Solution: same([10],[10]) OK, i expected that result
% same ([10],  N) . --> yes. N / [10] Solution: same([10],[10]) OK, i expected that result
% same (M,  [10, 20, 30]) . --> yes. M / [10,20,30] Solution: same([10,20,30],[10,20,30]) OK, i expected that result
% same (M, N) . --> yes. M / [] N / [] Solution: same([],[]) yes. M / [_7374] N / [_7374] Solution: same([_7374],[_7374]) and so ... OK, i expected that result

% all_bigger (List1 , List2 )
% all elements in List1 are bigger than those in List2 , 1 by 1
% example : all_bigger ([10 ,20 ,30 ,40] ,[9 ,19 ,29 ,39]) . --> Yes
all_bigger (X, []) .
all_bigger ([H|T], [H2|T2]) :- H > H2, all_bigger(T, T2) .

% sublist (List1 , List2 )
% List1 should contain elements all also in List2
% example : sublist ([1 ,2] ,[5 ,3 ,2 ,1]).
% do a recursion on List1, each time just use search of exercise 1.1!
sublist ([], X) .
sublist ([H|T], X) :- search (H, X), sublist (T, X) .


% =================================================4=====================================================

% seq(N, List )
% example : seq (5 ,[0 ,0 ,0 ,0 ,0]).

seq (0 ,[]) .
seq (N ,[0| T ]) :- N2 is N - 1, seq ( N2 ,T ) .

% Is it fully relational? No, it isn't

% seqR (N, List )
% example : seqR (4 ,[4 ,3 ,2 ,1 ,0]).

seqR (0, [0]) .
seqR (N, [N|T]) :- N2 is N - 1, seqR (N2, T) .

% seqR2 (N, List )
% example : seqR2 (4 ,[0 ,1 ,2 ,3 ,4]).

seqR2 (N, List) :- seqR2 (N, List, 0) .
seqR2 (N, [], N2) :- N2 is N + 1 .
seqR2 (N, [H|T], M) :- H =:= M, M2 is M + 1, seqR2(N, T, M2) .


% =================================================5=====================================================

% last predicate. It takes two input (list and last element to add/control) and returnes one output (the list with the element in last position).
% last (?InputList, ?Element, ?OutputList).
% es : last([1,2,3],5,[1,2,3,5]).
% es : last([1,2,3],N,[1,2,3,5]).
% es: last([1,2,3],5,N).
% es: last(N,5,[1, 2, 3, 5]).
% es: last(N,5,M).
%es : last(N,X,M).
% It is fully relational
last ([], N, N) .
last ([E], N, [E, N]) .
last ([H|T], N, [H|O]) :- last(T, N, O) .

% map_plus_one predicate. 
% Maps each element of a list into another element of other list plus one.
% map_plus_one (+InputList, -Outputlist).
% es: map_plus_one([0, 1], [1, 2]).
% es: map_plus_one([0, 1], N). --> yes. N / [1,2] Solution: map_plus_one([0,1],[1,2])
map_plus_one ([], []) .
map_plus_one ([H|T], [H2|T2]) :- H2 is H+1, map_plus_one(T, T2) .

% add_tail (+InputList, +Element, -OutputList)
% Add the given element to the end of the list.
% es: add_tail ([1, 2, 3], 4, N). --> yes. N / [1,2,3,4] Solution: add_tail([1,2,3],4,[1,2,3,4])
% es: add_tail ([1, 2, 3], 4, [1, 2, 3, 4]).
add_tail ([],X,[X]) .
add_tail ([H|T], X, [H|L]) :- add_tail(T,X,L).

% filter (+InputList, -OutputList)
% filter the element greater than zero from an input list.
% es: filter ([1, 2, 3, -1, 0], [1, 2, 3]).
% es: filter ([1, 2, 3, -1, 0], N). --> yes. N / [1,2,3] Solution: filter([1,2,3,-1,0],[1,2,3])
filter (L, S) :- filter (L, [], S) .
filter ([], S, S) .
filter ([H|T], Xs, S) :- H > 0, add_tail (Xs, H, Xso), filter(T, Xso, S).
filter ([H|T], Xs, S) :- filter (T, Xs, S) .

% case with cut predicate (to start practicing with the cut predicate)
filter_cut (L, S) :- filter_cut (L, [], S) .
filter_cut ([], S, S) .
filter_cut ([H|T], Xs, S) :- H > 0, add_tail (Xs, H, Xso), !, filter_cut(T, Xso, S).
filter_cut ([H|T], Xs, S) :- filter_cut (T, Xs, S) .










