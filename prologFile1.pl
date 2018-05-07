:- consult(prologFile).

strcmp(X,Y) :- X==Y.

strcmpN(X,Y) :- X\==Y.

isTunnel(X) :- tunnel(X,Z), strcmp(Z,yes), !.
isTunnel(X) :- tunnel(X,Z), strcmp(Z,no), !.

cmpr(X,Y) :- X >= Y, X =< Y+2.

canMoveFromTo(X,Y) :- nextTo(X,Y), belongsTo(Y,Z), railway(Z,K), strcmp(K,no),
			boundary(Z,L), strcmp(L,yes),
			natural(Z,M), strcmp(M,no),
			barrier(Z,N), strcmp(N,no),
			waterway(Z,P), strcmp(P,no),
			access(Z,Q), strcmp(Q,yes),
			highway(Z,T), strcmpN(T,steps),
			isTunnel(Z).

tollCheck(X,Y) :- toll(X,Z), strcmp(Z,yes), Y is 25, !.
tollCheck(X,Y) :- Y is 0.

buswayCheck(X,Y) :- busway(X,Z), strcmpN(Z,no), Y is 3, !.
buswayCheck(X,Y) :- Y is 0.

maxspeedCheck(X,Y) :- maxspeed(X,Z), (Z>=60 -> Y is 0 ; Y is 30).

lanesCheck(X,Y) :- lanes(X,Z), (Z>2 -> Y is 0).
lanesCheck(X,Y) :- lanes(X,Z), (Z>1 -> Y is 10).
lanesCheck(X,Y) :- Y is 20.

trafficCheck(X,Y) :- trafficInfo(X,K,L), strcmpN(L,no), clientHrs(client,H), cmpr(H,K), (strcmp(L,high) -> Y is 40), !. 
trafficCheck(X,Y) :- trafficInfo(X,K,L), strcmpN(L,no), clientHrs(client,H), cmpr(H,K),	(strcmp(L,medium) -> Y is 20), !.
trafficCheck(X,Y) :- trafficInfo(X,K,L), strcmpN(L,no), clientHrs(client,H), cmpr(H,K),	(strcmp(L,low) -> Y is 5), !.
trafficCheck(X,Y) :- Y is 0.

highwayCheck(X,Y) :- highway(X,Z), strcmp(Z,none), Y is 50, !.
highwayCheck(X,Y) :- highway(X,Z), strcmp(Z,footway), Y is 70, !.
highwayCheck(X,Y) :- highway(X,Z), strcmp(Z,pedestrian), Y is 100, !.
highwayCheck(X,Y) :- highway(X,Z), strcmp(Z,service), Y is 50, !.
highwayCheck(X,Y) :- Y is 0.

totalCost(X,Y) :- tollCheck(X,K), highwayCheck(X,U), buswayCheck(X,L), maxspeedCheck(X,M), lanesCheck(X,N), Y is (K+L+M+N+U).








