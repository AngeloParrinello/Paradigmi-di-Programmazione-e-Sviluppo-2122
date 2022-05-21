% a . --> atomo
% 10 --> numero
% a(10) . --> termine composto
% X --> variabile

% all (+T, +L) relates a list with a template for all its elements
% es: all (1, [1,1,1])
% es: all (p(_), [p(1),p(2),p(3)])
all (_, []) .
all (H, [H|T]) :- all (H, T).

% all (1, [1,1,1]) -->  yes. Solution: all(1,[1,1,1])
% all (p(_), [p(1),p(2),p(3)]) --> no. Perchè? 
% all (p(_), [p(1),p(2),p(3)]) ma poi p(_) cosa diventa? p(1) ! Perchè al primo giro la variabile unifica con la prima ma poi il valore che si ottiene nella variabile sporca la variabile stessa diventa p(1)
% all (p(1), [p(2), p(3)])
% dobbiamo unificare il tutto cercando di non sporcare la variabile. Come? Con la copy_term

all (_, []) .
% all (H, [H2|T]) :- copy_term(H, HC), HC=H2, all (H, T). oppure...
all (H, [H2|T]) :- copy_term(H, H2), all (H, T). 

%-------------BINARY TREES--------------

% search ( tree ( tree ( nil , 10 , nil ) , 20 , tree ( tree ( nil , 30 , nil ) , 40 , nil ) ) , E ) .
% search( +T, +E) .
search ( tree (_, E, _), E) .
search ( tree(L, _, _), E) :- search (L,E) .
search ( tree(_, _, R), E) :- search (R,E).
% attenzione che qui l'ordine delle soluzioni cambia a seconda dell'ordine dei predicati	

% leaves (+ Tree , - ListLeaves ), returns the list of leaves
leaves ( nil , []) . % handling void tree
leaves ( tree ( nil , E , nil ) , [ E ]) :- !. % handling a leaf
leaves ( tree (L , _ , R ) , O ) :- % general case
	leaves (L , OL ) , % OL are leaves on left
	leaves (R , OR ) , % OR are leaves on right
	append ( OL , OR , O ) . % O appends the two

% leftlist (+ Tree , - List ), returns the left - most branch as a list
leftlist ( nil , []) .
leftlist ( tree ( nil , E , _ ) , [ E ]) :- !.
leftlist ( tree (T , E , _ ) , [ E | L ]) :- leftlist (T , L ) .

%-----------------N-ARY TREES---------
% searchN (+ Tree ,? Elem ), search Elem in Tree
searchN ( tree (E , _ ) , E ) .
searchN ( tree (_ , L ) ,E ) :- member (T , L ) , search (T , E ) .

% searchV (+ Tree ,? Elem ), search Elem in Tree
searchV (T , E) :- T =.. [ tree , E | _ ].
searchV (T , E) :- T =.. [ tree , _ | L], member ( T2 , L) , searchV ( T2 , E).

%----------------DINAMICALLY LISTS------------
% factorial (+N, -Out ,? Cache )
% cache is a partial list of factorials [1 ,1 ,2 ,6 ,24|_]
factorial (N , Out , Cache ) :- factorial (N , Out , Cache , 0) .

factorial (N , Res , [ Res | _ ] , N ) :- ! , nonvar ( Res ) .

factorial (N , Out , [H , V | T ] , I ) :-
	var ( V ) , ! , I2 is I + 1 , V is H * I2 ,
	factorial (N , Out , [ V | T ] , I2 ) .

factorial (N , Out , [ _ , V | T ] , I ) :-
	I2 is I + 1 , factorial (N , Out , [ V | T ] , I2 ) .


%#############################PART1####################################

% dropAny (? Elem ,? List ,? OutList )
% Drops any occurrence of element

dropAny (X , [X | T], T) .
dropAny (X , [H | Xs ], [H | L ]) :- dropAny (X , Xs , L ) .

% dropFirst (? Elem ,? List ,? OutList )
% Drops first occurrence of element
% 									dropFirst(10, [10, 20, 10, 30, 10], L)
%										/														\
% 						Yes. L / [20,10,30,10] 			dropFirst (10, [20, 10, 30, 10], L') con L/[10|L']
%																									\
%																									....									
% dato che trova la soluzione a sx, lui taglia tutti i rami alla sua destra (risale di un livello e taglia tutti quelli a dx).
% Ricordati che è depth-first la ricerca

dropFirst (X , [X | T], T) :- ! .
dropFirst (X , [H | Xs ], [H | L ]) :- dropFirst (X , Xs , L ) .

% 									dropXXX (10, [10, 20, 10, 30, 10], L)
%										/														\
% 						Yes. L / [20, 10, 30, 10] 			dropXXX (10, [20, 10, 30, 10], L') con L/[10|L']
%																									\
%																							dropXXX (10, [10, 30, 10], L'') con L'/[10, 20|L'']
%																							/																	\
%																	Yes. L / [10, 20, 30, 10]										dropXXX (10, [30, 10], L''') con L''/[10, 20, 10|L''']
%																																									\
%																																								dropXXX (10, [10], L'''') con L'''/[10, 20, 10, 30|L'''']
%																																										/																	\
%																																									Yes. L / [10, 20, 10, 30]					no


% 									dropLast (10, [10, 20, 10, 30, 10], L)
%																				\
% 									dropLast (10, [20, 10, 30, 10], L') con L/[10|L']
%																					\
%														dropLast (10, [10, 30, 10], L'') con L'/[10, 20|L'']
%																								\
%																			dropLast (10, [30, 10], L''') con L''/[10, 20, 10|L''']
%																									\
%																						dropLast (10, [10], L'''') con L'''/[10, 20, 10, 30|L'''']
%																							/																	\
%																					Yes. L / [10, 20, 10, 30]					  no
% lascio intatto l'albero  ma elimino solo alla fine quando la lista L è vuota perchè vuol dire che sono in fondo
% questa soluzione non funziona in tutti i casi ... quella dopo sì
% dropLast (X , [X], []) :- !.
% dropLast (X , [H | Xs ], [H | L ]) :- dropLast (X , Xs , L ).

% Altra idea, specchiare l'albero e cambiare l'ordine dei predicati 
dropLast_2 (X , [H | Xs ], [H | L ]) :- dropLast(X , Xs , L ), ! .
dropLast_2 (X , [X | T], T) .

% dropAll: drop all occurrences, returning a single list as result

dropAll_2 (X, Li, Lo) :- dropAll_2 (X, Li, Lo, Lo) .
dropAll_2 (X, [], _, []) .
dropAll_2 (X, [X|Xs], _, T) :- !, dropAll_2 (X, Xs, Lo, T) . 
dropAll_2 (X, [H|Xs], _, [H|L]) :- dropAll_2 (X, Xs, Lo, L) .

dropAll (X, [], []) .
dropAll (X, [X | Xs], L) :- dropAll (X, Xs, L), ! .
dropAll (X, [H | Xs], [H | L]) :- dropAll (X, Xs, L) .


%#############################PART2####################################

% fromList (+ List ,- Graph )

fromList ([ _ ] ,[]) .
fromList ([ H1 , H2 |T ] ,[ e(H1 , H2 ) |L ]) :- fromList ([ H2 |T] ,L) .

% fromCircList (+ List ,- Graph )

% which implementation ?

fromCircList ([], []).
fromCircList ([H1] ,[e(H1, H1)]) .
fromCircList ([ H1 , H2 |T ] ,[ e(H1 , H2 ) |L ]) :- fromCircList ([ H2 |T] ,L, H1) .
fromCircList ([H1], [ e(H1, H) ], H) .
fromCircList ([ H1 , H2 |T ], [ e(H1 , H2 ) |L ], H) :- fromCircList ([ H2 |T] ,L, H) .

% inDegree (+ Graph , +Node , -Deg)

% Deg is the number of edges leading into Node

inDegree ( G, N, D) :- inDegree (G, N, D, 0) .
inDegree ([], N, D, D) .
inDegree ( [e(H1, N)|T], N, D, L ) :- L2 is L + 1, !, inDegree (T, N, D, L2) .
inDegree ( [_|T], N, D, L ) :- inDegree (T, N, D, L) .

% dropNode (+ Graph , +Node , -OutGraph )

% drop all edges starting and leaving from a Node
% use dropAll defined in 1.1??
% testando dropNode([e(1,2),  e(1,3), e(2,3)],1,L). con la dropNode che contiene dropAll si vede che torna L / [e(1,3),e(2,3)]
% e testando ancora dropNode([e(1,2), e(1,2), e(1,3), e(1,2), e(2,3)],1,L). si vede che torna L / [e(1,3),e(2,3)]. Perchè? perche la variabile X viene sporcata!
% serve il copy_term perchè lui al primo giro fa diventare X = e(1,2) e prova ad andare a confrontare e(1,2) con il resto degli elementi ma X rimarrà sempre e(1,2)
% con il copy_term X rimane 1,_ senza definire quell'_
% Infatti se si prova a lanciare dropNode([e(1,3), e(1,2), e(1,3), e(1,2), e(2,3)],1,L). (con la dropNode rotta che ha dropAll) ritornerà e(1,2), e(1,2), e(2,3) perchè dopo il primo giro X unifica e 
% diventa e(1,3) senza possibiltà di confrontare gli altri elementi

dropAllCopy (X, [], []) .
dropAllCopy (X, [X2 | Xs], L) :- copy_term(X, X2), dropAllCopy (X, Xs, L), ! .
dropAllCopy (X, [H | Xs], [H | L]) :- dropAllCopy (X, Xs, L) .

% dropNode (G, N, OG) :- dropAll (e(N ,_),G, G2), dropAll (e(_ ,N ), G2, OG) . --> NO È ROTTO!

dropNode (G, N, OG) :- dropAllCopy (e(N ,_),G, G2), dropAllCopy (e(_ ,N ), G2, OG) .

% dropNode([e(1,2),e(1,3),e(2,3)],1,[e(2,3)]).

% reaching (+ Graph , +Node , -List )

% all the nodes that can be reached in 1 step from Node
% possibly use findall , looking for e(Node ,_) combined
% with member (? Elem ,? List )

% reaching([e(1,2),e(1,3),e(2,3)],1,L). -> L/[2,3]
% reaching([e(1,2),e(1,2),e(2,3)],1,L). -> L/[2,2]).

% findall(e(1,X), member(X,[1,2,3,4]), Bag)

reaching(G, N, L) :- findall(H2, member(e(N, H2), G), L).

% anypath (+ Graph , +Node1 , +Node2 , -ListPath )

% a path from Node1 to Node2
% if there are many path , they are showed 1-by -1

% es: anypath([e(1,2),e(1,3),e(2,3)],1,3,L).
% L/[e(1,2),e(2,3)] --> L/[e(1,3)]

anypath (G, N1, N2, L) :- anypath (G, N1, N2, L, G) .
anypath ([ e(N1, N3) | T], N1, N2, [e(N1, N3)|L], G) :- anypath (G, N3, N2, L, G) . % caso in cui ho N1 e un altro nodo N3 allora devo andare da N3 a N2 e ricontrollo l'intera lista però!
anypath ([ e(N3, N4) | T], N1, N2, L, G) :- anypath (T, N1, N2, L, G) . % caso in cui non ho nulla di interessante
anypath ([ e(N1, N2) | T], N1, N2, [e(N1,N2)], G) .

% anypath([e(1,3),e(2,5),e(3,8),e(1,8),e(8,7),e(1,7)], 1, M, O)

% anypath ([ e(N3, N2) | T], N1, N2, [e(N3, N2)|L], G) :- anypath (G, N1, N3, L, G) . % caso in cui ho N2 che si raggiunge tramite N3 allora devo andare da N1 a N3 e ricontrollo l'intera lista però!

% allreaching (+ Graph , +Node , -List )

% all the nodes that can be reached from Node
% Suppose the graph is NOT circular !
% Use findall and anyPath !

% es: allreaching([e(1,2),e(2,3),e(3,5)],1,[2,3,5]).

allreaching(Graph, Node, List) :- anypath(Graph, Node, M, L), !, findall(H2, member(e(H1, H2), L), List) .



%#############################PART3####################################

% table = [cell(0,1,x), cell(1,1,o), ...]
% player x or o
% win(x), win(o), nothing even

% findAllMoves(+Table, +Player, -MovesList).
% return a list with all moves that mades a player.
findAllMoves (T, P, ML) :- findall(cell(X, Y, P), member(cell(X, Y,P), T ), ML) .
















