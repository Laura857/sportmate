# Sportmate

> Projet annuel A5
>
> 4 développeurs :  Laura TRICHET - Sylvie YE - Maxime BONNET - Datis NASSARIAN
> 
> 3 designers : Magou KEITA - Mickael LAYME - Adrien MONRIBOT

Sportmate est une application mobile qui permet à un utilisateur de trouver des partenair(e)s 
pour pratiquer une activité sportive. L'utilisateur a ainsi la possibilité, une fois connecté de 
créer des "annonces" pour rechercher un(e) partenair(e), et de consulter l'ensemble des annonces
des autres utilisateurs via une carte.

Ce projet est découpé en deux : 
- une partie back en JAVA qui expose des apis (ce repo)
- une partie front en REACT NATIVE ([Repo git](https://github.com/Pyxize/iim-sportmate-front))

## Fiche technique
Sportmate est une application **SprintBoot** en **JAVA 17**, qui fonctionne avec **Maven**.

Ce projet possède une base de données en **PostgreSQL**. 

Afin de faire tourner les scripts SQL, l'outil de migration de base de données **Flyway** est utilisé.

## Lancer l'application local

``` bash
# Démarrer la base de données : lancer le docker compose
docker compose - up 
ou 
depuis le projet dans votre IDE

# Démarrer le project Spring : 
Dans votre IDE, ajouter une nouvelle configuration SpringBoot
Choisir la class main : com.example.sportmate.SportmateApplication
Choisri le SDK : Java 17
Choisir active profiles : local
Via votre IDE lancer le projet, il démarrera sur le port localhost:8080
```

## Documentation API :  
[Swagger qual](https://sportmate-develop.herokuapp.com/swagger-ui/index.html#/user-controller)
[Swagger prod](https://sportmate-master.herokuapp.com/swagger-ui/index.html#/user-controller)

## Environnements :

- [Sportmate local](http://localhost:8080)

- [Sportmate qual](https://sportmate-develop.herokuapp.com)

- [Sportmate prod](https://sportmate-master.herokuapp.com)

