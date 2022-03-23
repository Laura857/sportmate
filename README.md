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

## Les différents web service : 

- Authentification
  - Inscription
   ```
  curl --location --request GET 'localhost:8080/api/level' \
  --header 'Content-Type: text/plain' \
  --data-raw '{
       "hobbies": [
          "Livre",
          "Musique"
       ],
       "login": {
           "email": "laura@hotmail.fr",
           "password": "Laura14!"
       },
       "sports": [
            {
              "level": "Confirmé",
              "name": "Natation"
            }
       ],
       "user": {
           "birthday": "2002-02-24",
           "firstName": "Laura",
           "genre": "FEMME",
           "lastName": "Lolo",
           "mobilePhone": "0606060606",
           "profilePicture": "file:///Users/laura/Library/Developer/CoreSimulator/Devices/962D0532-942A-48BD-BA29-4DD521A75E87/data/Containers/Data/Application/539725C3-8AFA-4123-B505-DCE68348B981/Library/Caches/ImagePicker/5C89157B-7C93-43D4-AAB6-38AC20BA4DEF.jpg"
       }
  }'
  ```

  - Connexion
  ```
  curl --location --request POST 'localhost:8080/api/login' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "email": "sylvie@gmail.com",
    "password": "mdp"
  }'
  ```
- Activités
  - Créer une activité
  ```
    curl --location --request POST 'localhost:8080/api/activity' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoic3lsdmllQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NDQ2MDY4OTIsImV4cCI6MTY0NDYwNzQ5Mn0.3WUxJH4objgdlvyo6WOWilQojoK2xFqgLTUefN8QI55FXGEIBlRsA9TVSap07VtxFZiHeNYo9rypChyVlHUcQQ' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "isEvent": false,
    "activityName": "Course à pied",
    "activityDate": "2021-10-10",
    "address": "1 rue vinci",
    "longitude": "09989888990223",
    "latitude": "80989080980908",
    "participant": 1,
    "sport": "Natation",
    "activityLevel": "Débutant"
  }'
    ```
  
  - Modifier une activité
  ```
    curl --location --request PUT 'localhost:8080/api/activity/32' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoic3lsdmllQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NDU3MDYyNjAsImV4cCI6MTY0NTcwNjg2MH0.bPMR30mm0tKeHNud4YdPka8oBHf_-gEE9_wBwOhp0CVIQRzD3suUgjXLohO2pNY24-UUDy7a9h1wyt1zf4bfnA' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "isEvent": false,
    "activityName": "Piscine",
    "activityDate": "2021-10-10",
    "address": "1 rue vinci",
    "longitude": "09989888990223",
    "latitude": "80989080980908",
    "participant": 1,
    "sport": "Natation",
    "activityLevel": "Débutant"
    }'
    ```
  - Supprimer une activité
  ```
  curl --location --request DELETE 'localhost:8080/api/activity/1' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoic3lsdmllQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NDU3MDYyNjAsImV4cCI6MTY0NTcwNjg2MH0.bPMR30mm0tKeHNud4YdPka8oBHf_-gEE9_wBwOhp0CVIQRzD3suUgjXLohO2pNY24-UUDy7a9h1wyt1zf4bfnA' \
  ```
  - Retourner une activité par id
  ```
    curl --location --request GET 'localhost:8080/api/activity/1' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoic3lsdmllQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NDgwMjg3NTMsImV4cCI6MTY0ODAyOTM1M30.0RZ9d4FdDYoEOHxuPIfmZaGqUkJ3C_iKlv0Dnn40h76swSrkxXb_Gyu9hJZ6rTsau7c2Sx232q_3G37XsxtVtA' \
    ```
  - Retourner toutes les activités
  ```
  curl --location --request GET 'localhost:8080/api/activity/all' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoic3lsdmllQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NDgwMjg3NTMsImV4cCI6MTY0ODAyOTM1M30.0RZ9d4FdDYoEOHxuPIfmZaGqUkJ3C_iKlv0Dnn40h76swSrkxXb_Gyu9hJZ6rTsau7c2Sx232q_3G37XsxtVtA' \
  ```
  - Retourner toues les activités d'un utilisateur
  ```
  curl --location --request GET 'localhost:8080/api/activity/user' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrS--header 'Cookie: JSESSIONID=1A46EBD52A28D651CF1DC0F6417E49F1'
  ```
- Sports
  - Retourner tous les sports
  ```
   curl --location --request GET 'localhost:8080/api/sport' \
  ```
- Niveaux
  - Retourner tous les niveaux
  ```
   curl --location --request GET 'localhost:8080/api/level' \
  ```
    

## Environnements :

- [Sportmate local](http://localhost:8080)

- [Sportmate qual](https://sportmate-develop.herokuapp.com)

- [Sportmate prod](https://sportmate-master.herokuapp.com)

