% Group number 34
% Christian George Sanger - 947777
% Chinnapat Jongthep - 1909148
% Kacper Lisikiewicz - 1911309

parent(mary,georgeVI).
parent(mary,henry).
parent(mary,george).
parent(georgeV,georgeVI).
parent(georgeV,henry).
parent(georgeV,george).
parent(elizabeth,elizabethII).
parent(georgeVI,elizabethII).
parent(alice,richard).
parent(henry,richard).
parent(elizabethII,charles).
parent(elizabethII,andrew).
parent(elizabethII,anne).
parent(elizabethII,edward).
parent(philip,charles).
parent(philip,andrew).
parent(philip,anne).
parent(philip,edward).
parent(diana,william).
parent(diana,harry).
parent(charles,william).
parent(charles,harry).
parent(sarah,beatrice).
parent(sarah,eugenie).
parent(andrew,beatrice).
parent(andrew,eugenie).
parent(anne,peter).
parent(anne,zara).
parent(mark,peter).
parent(mark,zara).
parent(kate,georgejun).
parent(kate,charlotte).
parent(kate,louis).
parent(william,georgejun).
parent(william,charlotte).
parent(william,louis).
parent(meghan,archie).
parent(harry,archie).


the_royal_females([mary,elizabeth,elizabethII,alice,anne,diana,sarah,beatrice,zara,eugenie,charlotte,kate,meghan]).
the_royal_males([georgeV,georgeVI,george,philip,charles,andrew,edward,richard,henry, william,harry,peter,georgejun,mark,louis,archie]).


% Helper predicates
female(X) :- 
    the_royal_females(F), 
    member(X,F).
male(X) :- 
    the_royal_males(M), 
    member(X, M).

% Question 1

%(1)
the_royal_family(X) :- 
    the_royal_females(F), 
    the_royal_males(M), 
    append(F, M, X).

%(2)
mother(M, C) :- 
    female(M), 
    parent(M, C).

%(3)
grandma(G, C) :- 
    mother(G, M), 
    parent(M, C).

%(4)
hasChild(X) :- 
    parent(X,_).

%(5)
ancestor(X, Y) :- 
    parent(X,Y).
ancestor(X, Y) :- 
    parent(X, Z), 
    ancestor(Z, Y).

%(6)
sibling(X, Y) :- 
    parent(Z, X), 
    parent(Z, Y), 
    X \= Y.

%(7)
sister(X, Y) :- 
    sibling(X, Y), 
    female(X).

%(8)
% Who is the grandChild of GeorgeV?
grandParent(X, Y) :- 
    parent(X, Z), 
    parent(Z, Y).

% grandParent(georgeV, X).

% X = elizabethII ? ;
% X = richard ? ;

%(9)
% Who has a child (one or more)?
% parent(X,_).

% X = mary ? ;
% X = georgeV ? ;
% X = elizabeth ? ;
% X = georgeVI ? ;
% X = alice ? ;
% X = henry ? ;
% X = elizabethII ? ;
% X = philip ? ;
% X = diana ? ;
% X = charles ? ;
% X = sarah ? ;
% X = andrew ? ;
% X = anne ? ;
% X = mark ? ;
% X = kate ? ;
% X = william ? ;
% X = meghan ? ;
% X = harry

%(10)
% Who is an ancestor of Archie?
% ancestor(X, archie).

% X = harry ?
% X  = mary ? ;
% X = georgeV ? ;
% X = elizabeth ? ;
% X = georgeVI ? l
% X = elizabethII ? ;
% X = philip ? ;
% X = diana ? ;
% X = charles ? ;

%(11)
% Who is a cousin of Eugenine?
cousin(X, Y) :- 
    parent(A, X), 
    parent(B, Y), 
    sibling(A, B).
% cousin(eugenine, X).

% X = william ? ;
% X = harry ? ;
% X = peter ? ;
% X = zara ? ;

%(12)
% Who has a cousin who is grandma?
has_cousin_who_is_grandma(X) :- 
    cousin(X, Y), 
    grandma(Y, _).
% has_cousin_who_is_grandma(X).

% X = richard ? ;

%(13)
% Who has a brother who is a grandfather?
brother_who_is_a_grandfather(X) :- 
    sibling(X, Y), 
    male(Y), 
    grandParent(Y, _).
% has_cousin_who_is_grandma(X).

% X = henry ? ;
% X = george ? ;
% X = anne ? ;
% X = edward ? ;
% X = andrew ? ;


%  Question 2

train(swansea, cardiff, [3,5,8,15,17,18,19,20,23],1,[4,5,6,7,10,14,18,22,23],2).
train(cardiff, manchester, [7,11,16],4,[8,14,19],5).
train(cardiff, bristol, [3,5,7,11,15,18,19,20],2,[5,6,7,10,14,16,18,22],2).
train(manchester, bristol, [5,6,7,8,11,15,18,19,20],4,[5,6,7,10,14,16,18,22],5).
train(manchester, swansea, [7,11,16],5,[8,14,19],6).
train(manchester, london, [6,7,11,16],4,[7,8,14,19],5).
train(cardiff, london, [5,6,7,11,18,19,20],3,[8,9,17,18,19,20,21],3).
train(london, brussels, [6,7,8,11,13,17,18,20],5,[9,11,13,16,17,18,19,23],5).
train(london, paris, [7,11,13,17,18,20],5,[9,11,13,16,18,20],6).
train(paris, brussels, [7,11,17],4,[9,13,19],3).
train(paris, munich, [7,11,13,17,22],8,[5,9,13,19,23],7).
train(munich, vienna, [8,9,11,13,17,19],6,[9,10,12,16,18,23],5).
train(vienna, venice, [5,7,8,10,13,16,12,23],8,[2,4,7,9,12,20,21,23],9).
train(venice, paris, [4,11,20],11,[9,12,21],10).

city([swansea, cardiff, manchester, london, bristol,
       brussels, paris, munich, vienna, venice]).

direct(X, Y, Deps, Dur) :- % Valid if a direct train from X to Y
    train(X,Y, Deps, Dur,_,_);
    train(Y,X, _   ,   _,Deps, Dur).

% Check that Dep and Arr are a valid amount of time to wait in a city
waiting(Arr, Dep) :- 
    (   Arr < Dep,
        Arr + 4 >= Dep
    );
    % allows vaild times if split over midnight.
    (   ShiftArr is (Arr + 12) mod 24,
        ShiftDep is (Dep + 12) mod 24,
        ShiftArr < ShiftDep,
        ShiftArr + 4 >= ShiftDep
    ).


% Base case of a direct line between X and Y
con(X, Dep, [], Arr, Y, _) :-
    direct(X, Y, Deps, Dur),
    member(Dep, Deps),
    Arr is (Dep + Dur) mod 24.

% Recussive case where a intermediate Z city is visited
con(X, Dep, V, Arr, Y, Cs) :-
    % that a direct line doesn't connect X and Y
    \+ (direct(X, Y, _, _)),
    delete(Cs, X, CnoX),
    % Some Z which has a direct line from X
    direct(X, Z, XtoZDeps, XtoZDur),
    % Z that hasn't been visited yet.
    member(Z, CnoX),
    member(Dep, XtoZDeps),
    % time that we arrive at Z
    ZArr is (Dep + XtoZDur) mod 24,
    % check that there is a connection between Z and Y
    con(Z, ZDep, V1, Arr, Y, CnoX),
    % check that the time wating at Z is valid
    waiting(ZArr, ZDep),
    % add the stop at Z to list of vias
    append([via(ZArr, Z, ZDep)], V1,  V).

connection(X, Dep, V, Arr, Y) :-
    city(Cs),
    con(X, Dep, V, Arr, Y, Cs).
