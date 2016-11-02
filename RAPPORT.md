# Projet Capitrain - Algorithme Hindley-Milner
## Cocaud Valentin, Maël NACCACHE - FIL A3

# Introduction
Dans le cadre de nôtre projet d'initiation à la recherche, nous devions implémenter l'Algorithme de Hindley-Milner pour du Lambda Calcul.
L'Algorithme de Hindley-Milner est un algorithme d'inférence de type, permettant de trouver le type le plus générale d'une expression.

L'avantage d'utiliser cet algorithme est multiple :
- il est prouvé correcte [1]
- Il trouve le type le plus générale d'un expression
- Il est rapide
- Il permet de ce passer des annotations de type

Nôs tâche étaient donc :
1 - Implémenter un AST pour le Lambda Calcul
2 - Implémenter une version immutable de l'algorithme
3 - Implémenter une version mutable de l'algorithme

[1] Damas, Luis; Milner, Robin (1982). Principal type-schemes for functional programs. 9th Symposium on Principles of programming languages (POPL'82). ACM. pp. 207–212.