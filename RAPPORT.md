Valentin COCAUD - Maël NACCACHE - FIL A3# Projet Capitrain - Algorithme Hindley-Milner## IntroductionDans le cadre de notre projet d'initiation à la recherche, nous devions implémenter l'Algorithme d'inférence de type de Hindley-Milner pour du Lambda Calcul.L'avantage d'utiliser cet algorithme est sa rapidité et le fait qu'il permet de trouver le type le plus général d'une expression sans aucune annotation de type. De plus, il a été prouvé correcte[1].## Pourquoi l'inférence de type ?L'intérêt d'un algorithme d'inférence est multiple. Dans notre cas par exemple, il permet de s'assurer d'une certaine validité du code, sans que le code contienne la moindre annotation. Cela permet d'avoir un code plus clair et aéré. Mais l'inférence devient d'autant plus intéressante lorsqu'elle est justement mêlée à des annotations de type. En effet, l'algorithme pourra alors vérifier la concordance entre le type inféré et le type indiqué par le programmeur.Avoir un système de typage fort et statique de ce type est un atout de taille dans la détection de bug avant même que le code ne soit exécuté ou même compilé. Et c'est une bonne chose, car ce type d'algorithme est généralement très rapide et permet donc d'informer le développeur en temps réel, sans passer par une longue étape de compilation.Le dernier point important est que l'algorithme renvoie toujours le type le plus générique. Il pourra ensuite être appliqué partout en instanciant ce type au besoin. C'est le rôle du `let` dans notre petit langage.Par exemple, ici la fonction contenu dans la varibale `id` est générique et peux donc indiférment être appliqué à un nombre, un boolean ou une autre fonction.

```
let id = \x -> x
in 
	(\w -> \y -> \z -> z . w) . (id 3) . (id true) . (id (\a -> a)) 
```
Le type finale de l'expression sera `Int` et `id` sera utilisé avec le type `Int -> Int` puis le type `Bool -> Bool` et enfin le type `(t -> t) -> (t -> t)`.## Implémentation en JavaL'implémentation devant se faire en Java, nous avons adopté une architecture objet pour notre programme.Il est composé de deux arbres : un arbre syntaxique et d'un arbre de type. Chaque arbre part d'une classe mère (respectivement Expression et Type) qui permet de factoriser les fonctions communes à tous les objets enfants.L'algorithme se divise en 2 étapes distinctes : l'inférence de type puis l'unification des contraintes de type. L'inférence portant sur l'arbre syntaxique, il a été intégré au seindes classes de ce dernier à travers une interface. L'unification quant à elle ne dépend que de l'arbre de type, il a donc été intégré à celui-ci, également via une interface.Afin de factoriser le code au maximum et de faciliter les substitutions de type dans nos différents objets, nous avons créé une interface générique `Substituable<T>` qui est implémentée par tous les objets pouvant faire l'objet d'une substitution. Le paramètre `T` permet de choisir quel type sera renvoyé par la fonction de substitution.```javaType implements Substituable<Type> { ... }Scheme implements Substituable<Scheme> { ... }Substitution implements Substituable<Substitution> { ... }``` Dans la version mutable du projet (jointe à ce document), seules les `TFunction` (type fonction) et les `Scheme` sont mutables. En effet, rendre mutable un `TVariable` (variable de type) ou un `TConstructeur` (contracteur de type) n'aurait pas beaucoup de sens. Une autre solution pour rendre les `TVariable` mutable aurait été de créer une classe `Type` contenant un attribut `type` avec une énumération permettant de définir le type. Mais une telle architecture sort quelque peu du mode objet standard.

## Pourquoi la version mutable est un échèque ?

La version mutable ne fonctionne pas à cause de la fonction instantiate.

```java
public Type instantiate(Scheme scheme) {
    List<Type> freshVars = new LinkedList<>();
    scheme.variables().forEach(var -> freshVars.add(freshName()));

    return scheme.type().apply(new Substitution(scheme.variables(), freshVars));
}
```

On peux voir que l'on applique une substitution au type contenu dans le `Scheme` passé en paramètre. Dans la version imutable, cela renvoie une nouvelle fonction. Mais dans la version mutable, cela modifie le type contenu dans le Scheme. Le type sera donc le même la prochaine fois que l'on tentera une instantiation de ce scheme.

Pour palier ce problème, il faudrais inversé le controller de la méthode instantiate. C'est à dire créer un méthode instantiate dans `Scheme` et dans `Type`.

Dans `Scheme`, la méthode va créer une substitution pour remplacer les variables du forall par de nouvelle variable.

Dans `Type`, la méthode va appliquer cette substitution sur des copies des objets. Cela permettra d'effectivement "instantié" le type avec une nouvelle instance :

```java
public class Scheme extends Substitutable<Scheme> {
	public Type instantiate(TypeInferenceEnv env) {
	    List<Type> freshVars = new LinkedList<>();
	    variables().forEach(var -> freshVars.add(env.freshName()));
	
	    return type().instantiate(new Substitution(variables(), freshVars));
	}
}

public class Type extends Substitutable<Type> {
	public abstract Type instantiate(Substitution sub);
}

public class TFunction extends Type {
	@Override
    public Type instantiate(Substitution sub) {
        return function(left.instantiate(sub), right.instantiate(sub));
    }
}

public class TVariable extends Type {
	@Override
    public Type instantiate(Substitution sub) {
        return sub.variables().contains(this) ? sub.substituteOf(this) : this;
    }
}

public class  TConstructor extends Type {
	@Override
    public Type instantiate(Substitution sub) {
        return this;
    }
}    
```## Références[1] Damas, Luis; Milner, Robin (1982). Principal type-schemes for functional programs. 9th Symposium on Principles of programming languages (POPL'82). ACM. pp. 207–212.