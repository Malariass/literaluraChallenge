package com.alura.challenge_literatura.principal;

import com.alura.challenge_literatura.model.Autor;
import com.alura.challenge_literatura.model.ResultadoDatos;
import com.alura.challenge_literatura.model.DatosLibro;
import com.alura.challenge_literatura.model.Libro;
import com.alura.challenge_literatura.repository.AutorRepository;
import com.alura.challenge_literatura.repository.LibroRepository;
import com.alura.challenge_literatura.service.ConsumoAPI;
import com.alura.challenge_literatura.service.ConvierteDatos;


import java.io.IOException;
import java.util.*;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<Libro> libros = new ArrayList<>();
    private List<Autor> autores;
    private String idioma;

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        try {
            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                
                | GENERAL | INDICAR OPCION
                
                    1 - Libro por Título
                    2 - Lista de libros consultados
                    3 - Lista de libros consultados por Autor
                    4 - Lista de Autores vivos en determinado año
                    5 - Lista de libros por Idioma

                    (0) - Salir
                    """;

                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroPorNombre();
                        break;
                    case 2:
                        buscarLibrosConsultados();
                        break;
                    case 3:
                        buscarLibrosConsultadosPorAutor();
                        break;
                    case 4:
                        buscarLibroAnioAutor();
                        break;
                    case 5:
                        buscarPorIdioma();
                        break;

                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error, opcion invalida " + e.getMessage());
        }


    }


    private void buscarLibroPorNombre() {
        System.out.println("Escribe el nombre del Libro que deseas buscar:");
        String usuarioLibro = teclado.nextLine();
        String url = URL_BASE + "?search=" + usuarioLibro.replace(" ", "%20");
        System.out.println("Consultando API con URL: " + url);

        var json = consumoApi.obtenerDatos(url);
        System.out.println("JSON recibido: " + json);

        var datosJason = conversor.obtenerDatos(json, ResultadoDatos.class);
        System.out.println("Convirtiendo json a objeto ResultadoDatos: " + datosJason);


        DatosLibro datosLibro = datosJason.resultados().get(0);
        System.out.println("Objeto lista de ResultadoDatos tomo los datos del primer libro: " + datosJason);

        Libro libro = new Libro(datosLibro);
        Autor autor = new Autor().obtenerPrimerAutor(datosLibro);
        System.out.println("Libro obtenido: " + libro);
        System.out.println("Autor obtenido: " + autor);


        guardarLibroConAutor(libro, autor);
        System.out.println("Libro y Autor procesado y guardado en mi BBDD");

    }

    private void guardarLibroConAutor(Libro libro, Autor autor){
        System.out.println("Verificando que el autor en la BBDD... ");
        Optional<Autor> autorBuscado = autorRepository.findByNombreContains(autor.getNombre());

        //Valida si existe el autor en la  BBDD, en un caso devuelve el autor en la BBDD o en el otro caso crea el autor
        if(autorBuscado.isPresent()){
            System.out.println("El Autor ya existe.");
            libro.setAutor(autorBuscado.get());
        } else {
            System.out.println("Nuevo autor.");
            autorRepository.save(autor);
            libro.setAutor(autor);
        }

        //guarda el libro
        try {
            libroRepository.save(libro);
        } catch (Exception e) {

            System.out.println("Ocurrió un error al guardar el libro: " + e.getMessage());
        }
    }

    private void buscarLibrosConsultados() {
        System.out.println("Buscando Libros consultados...");

        libros = libroRepository.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getNombreAutor))
                .forEach(System.out::println);

    }

    private void buscarLibrosConsultadosPorAutor() {

        try {
            System.out.println("Escribe el nombre del autor que deseas buscar:");
            String nombreAutor = teclado.nextLine();

            // valido el ingreso! me tarde media tarde para que funcione!!!!!
            String camelString = camelFrase(nombreAutor);
            Optional<Autor> autorBuscado = autorRepository.findByNombreContains(camelString);

            if(autorBuscado.isPresent()) {
                libros = libroRepository.buscarPorAutor(camelString);
                System.out.println("****************");
                System.out.println("Listado de Libros del autor " + autorBuscado.get().getNombre().toUpperCase() );
                libros.forEach(l -> System.out.println("TITULO: " + l.getTitulo() + " IDIOMA: " + l.getIdioma()));
            } else {
                System.out.println("No se encontró el nombre en consultas previas a la BBDD.");
            }
        } catch (NoSuchElementException  e) {
            System.out.println("Error: El autor no está presente en la BBDD: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }

    }

    public static String camelFrase(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }

        StringBuilder result = new StringBuilder(string.length());
        String[] words = string.split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        // Elimina el espacio adicional al final
        return result.toString().trim();
    }

    private void buscarLibroAnioAutor() {

        try {

            System.out.println("Consulta Autores vivos desde: ");
            Integer anioDesde = teclado.nextInt();
            teclado.nextLine();

            System.out.println("Consulta Autores vivos hasta: ");
            Integer anioHasta = teclado.nextInt();
            teclado.nextLine();

            if (anioDesde > anioHasta) {
                System.out.println("Consulta inválida, el año de inicio es mayor al año final de busqueda");
            } else {
                autores = autorRepository.busquedaAutorFechaNacimientoMuerte(anioDesde,anioHasta);
                System.out.println(autores);

                autores.stream()
                        .sorted(Comparator.comparing(Autor::getNombre))
                        .forEach( a -> System.out.println("Autor: " + a.getNombre() + " (" + a.getanioDeNacimiento() + " - " + a.getanioDeMuerte()+")"));
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error, entrada invalida " + e.getMessage());
        }

    }

    private void buscarPorIdioma() {
        try {int opcion = -1;
            while (opcion != 0) {
                String menu2 = """
                
                | GENERAL | INDICAR OPCION DE IDIOMA
                
                        1 - español (es)
                        2 - portugues (pt)
                        3 - inglés (in)
                        4 - francés (fr)
                        5 - otros (ingrese el codigo valido de idioma, ej: fl filandés)

                    (0) - Salir
                    """;

                System.out.println(menu2);
                System.out.print("Ingrese una opción: ");
                opcion = teclado.nextInt();
                teclado.nextLine(); // Consume the newline

                switch (opcion) {
                    case 1:
                        idioma = "es";
                        break;
                    case 2:
                        idioma = "pt";
                        break;
                    case 3:
                        idioma = "en";
                        break;
                    case 4:
                        idioma = "fr";
                        break;
                    case 5:
                        System.out.print("Ingrese el codigo valido de idioma, ej: fl filandés: ");
                        idioma = teclado.nextLine();
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida, intente nuevamente.");
                        continue; // Vuelve al inicio del bucle
                }

                if (opcion != 0) {
                    List<Libro> libros = libroRepository.findByIdiomaContains(idioma);

                    if (libros.isEmpty()) {
                        System.out.println("No se encontraron registros para el idioma: " + idioma);
                    } else {
                        libros.stream()
                                .sorted(Comparator.comparing(Libro::getNombreAutor))
                                .forEach(l -> System.out.println("TITULO: " + l.getTitulo() + " Autor: " + l.getNombreAutor() + " IDIOMA: " + l.getIdioma() + " Descargas: " + l.getDescargas()));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error, opcion invalida " + e.getMessage());
            teclado.nextLine(); // Limpia el buffer de entrada
        }
    }

}
