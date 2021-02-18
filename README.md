# Movie Catalogue Web Application

This is the frontend/backend repository for an online Movie Catalogue application. The frontend is made using Thymeleaf  whilst the backend is made using Spring Boot.
Users can register, add movies to their collection, search for a movie using the Online Movie Database to automatically fill in details about their movie, and edit/delete movies already added.

A live demo of this application can be found here: https://blooming-escarpment-55590.herokuapp.com/

## Unimplemented Functionality
Currently Websockets emit to every connected user, regardless of whether the user is viewing a specific game or not. Would like to change this to use the principal/username and emit only to those users.
Games are currently stored in-memory, could be made to persist instead.

## Installation

Rename the application-demo.properties to application.properties and change the properties to your own. Your user database will need the following tables:
**users**
* username (varchar)
* password (varchar, at least 68 chars for BCrypted strings)
* enabled (boolean/tinyint)

**roles**
* id (int)
* name (varchar)
Populate this with your required roles, only ROLE_USER is used in this application.

**users_roles**
* role_id (int)
* user_name (varchar)
Will need FK enforcement to above tables.

For Movies and their related tables, please refer to the entity classes for which tables you will need.

## Usage

Run MovieCatalogueApplication.java and navigate to http://localhost:8080.

## Credits

* Online Movie Database API: http://www.omdbapi.com/
