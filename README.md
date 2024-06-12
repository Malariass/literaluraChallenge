# literaluraChallenge
Descripcion:
    El desarroollo de esta aplicacion corresponde al desafio de programacion para construir un catalogo de libro segun algunos criterios.

Funcionalidades:
      Se conforma de un Menu Principal: 1 - Libro por Título: Realiza consulta a API de Consulta Externa de Libros y guarda la consulta en un BBDD propia, link API externa: https://gutendex.com/ 

      Luego realiza una serie de búsqueda personalizada en nuestra BBDD con el historial del consultas a la API Externa Gutendex, donde nos da diferentes opciones de búsqueda:
       2 - Lista de libros consultados
       3 - Lista de libros consultados por Autor
       4 - Lista de Autores vivos en determinado año
       5 - Lista de libros por Idioma

      La búsqueda por Idioma desplegará un sub Menu de busqueda con idiomas a consultar.

Una consideración importante, en la documentación https://gutendex.com/, en la opción Search que dice: Use this to search author names and book titles with given words. They must be separated by a space (i.e. %20 in URL-encoded format) and are case-insensitive. For example, /books?search=dickens%20great includes Great Expectations by Charles Dickens, en la consulta hay que agregar un "/" luego de books y quedaria asi: /books/?search=dickens%20great.
