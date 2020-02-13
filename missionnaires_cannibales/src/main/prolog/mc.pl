main :-
	E0 = 3/3/g/0/0,
	E1 = 0/0/d/3/3,
	C0 = [E0],
	chemin(E0, E1, C0, C1),
	writeln(C1).

chemin(E0, E1, C0, C1) :-
	legal(E0-E1), !,
	C1 = [E1 | C0].
chemin(E0, E1, C0, C1) :-
	legal(E0-E),
	not(member(E, C0)),
	C = [E | C0],
	chemin(E, E1, C, C1).

legal(MG/CG/g/MD/CD-MG1/CG1/d/MD1/CD1) :-
between(0, 2, DeltaM),
between(0, 2, DeltaC),
Delta is DeltaM + DeltaC,
member(Delta, [1, 2]),
MG1 is MG - DeltaM,
CG1 is CG - DeltaC,
MD1 is MD + DeltaM,
CD1 is CD + DeltaC,
valide(MG1),
valide(CG1),
valide(MD1),
valide(CD1),
safe(MG1, CG1),
safe(MD1, CD1).
legal(MG/CG/d/MD/CD-MG1/CG1/g/MD1/CD1) :-
legal(MD/CD/g/MG/CG-MD1/CD1/d/MG1/CG1). % symétrie droite/gauche

valide(N) :-
0 =< N,
N =< 3.

safe(M, C) :-
M >= C, !.
safe(M, _) :-
M = 0.