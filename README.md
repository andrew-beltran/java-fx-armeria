Este proyecto fue realizado en mi primer año del ciclo 'DAW - Desarrollo de aplicaciones web' 
como trabajo final de la asignatura de programación.
# Armería fx
Con esta aplicación podrás mantener un registro de diferentes armas y sus atributos
(calidad, peso, coste, etc.).

## Guia de uso
Al iniciar el proyecto mostrará ventana de la App, mostrará a la izquierda una 
lista de las armas almacenadas y a la derecha veremos los atributos del arma seleccionada(si la hay).

![alt text](https://github.com/andrew-beltran/java-fx-armeria/blob/master/docs/images/inicio.JPG "initiation")

En la barra superior hay diferentes opciones(filtrar, nueva arma, guardar, cargar e importar fav).
Al seleccionar una de las armas disponibles tendrás otras 2 opciones(fav, y eliminar).

![alt text](https://github.com/andrew-beltran/java-fx-armeria/blob/master/docs/images/opciones.JPG "options")

### Lista opciones
* Filtrar: nos permitirá buscar entre las diferentes armas según sus atributos.
![alt text](https://github.com/andrew-beltran/java-fx-armeria/blob/master/docs/images/filtrar.JPG "example filter")

* Nueva arma: podremos crear un arma nueva con sus respectivos atributos.
![alt text](https://github.com/andrew-beltran/java-fx-armeria/blob/master/docs/images/nueva%20arma.JPG "example new weapon")
* Guardar: almacenatemos los datos modificados en un fichero '.xml' para una posterior carga.
* Cargar: cargaremos os datos almacenados actualmente en un fichero '.xml'.
* Importar fav: cargaremos las armas marcadas como 'favoritas', estas se hayan almacenadas en una base de datos.
### Arma seleccionada
* Fav: marca como 'favorita' el arma seleccionada actualmente.
* Eliminar: elimina el arma seleccionada de la armería.
    
El fichero donde se almacenan los usuarios es un xml.

Incluye una versión por consola, esta versión en vez de guardar los datos en un '.xml' los 
guarda en un fichero '.txt' y no tiene opción de marcar favoritos(guardar en base de datos).

Se ha usado XAMPP para la base de datos, el IDE usado ha sido NetBeans 8.2 con JDK 1.8, destacar también el uso de Maven 3.6.3.

### Crear la base de datos y tabla en MySql:
Base de datos
```
CREATE DATABASE armero;
```
Tabla 'armas'
```
CREATE TABLE IF NOT EXISTS Armas (
  nombre VARCHAR(20) NOT NULL, 
  calidad VARCHAR(20), 
  categoria VARCHAR(20), 
  tipoDanho VARCHAR(20), 
  peso DOUBLE, 
  coste DOUBLE, 
  CONSTRAINT armas_pk PRIMARY KEY (nombre)
);
```
