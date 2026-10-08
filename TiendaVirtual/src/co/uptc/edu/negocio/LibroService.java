package co.uptc.edu.negocio;
import java.util.ArrayList;
public class LibroService {
    // LISTA DE LIBROS
    private ArrayList<Libro> libros;
    // CONSTRUCTOR
    public LibroService() {
        libros = new ArrayList<Libro>();}
    // CREAR
    public boolean registrar(Libro libro) {
        if (libro == null) {
            return false;}
        if (buscarPorId(libro.getIdLibro()) != null) {
            return false;}
        if (libro.getTitulo().isEmpty()) {
            return false;}
        if (libro.getPrecio() <= 0) {
            return false;}
        if (libro.getStock() < 0) {
            return false;}
        libros.add(libro);
        return true;}
    // CONSULTAR
    public ArrayList<Libro> consultar() {
        return libros;}
    // BUSCAR
    public Libro buscarPorId(int id) {
        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i);
            if (libro.getIdLibro() == id) {
                return libro;}
        }
        return null;}
    // ACTUALIZAR
    public boolean actualizar(Libro libroNuevo) {
        Libro libro = buscarPorId(
                libroNuevo.getIdLibro()
        );
        if (libro == null) {
            return false;}
        if (libroNuevo.getTitulo().isEmpty()) {
            return false;}
        if (libroNuevo.getPrecio() <= 0) {
            return false;}
        if (libroNuevo.getStock() < 0) {
            return false;}
        libro.setTitulo(
                libroNuevo.getTitulo()
        );
        libro.setAutor(
                libroNuevo.getAutor()
        );
        libro.setCategoria(
                libroNuevo.getCategoria()
        );
        libro.setEditorial(
                libroNuevo.getEditorial()
        );
        libro.setAnoPublicacion(
                libroNuevo.getAnoPublicacion()
        );
        libro.setFormato(
                libroNuevo.getFormato()
        );
        libro.setPrecio(
                libroNuevo.getPrecio()
        );
        libro.setStock(
                libroNuevo.getStock()
        );
        return true;}
    // ELIMINAR
    public boolean eliminar(int id) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIdLibro() == id) {
                libros.remove(i);
                return true;}
        }
        return false;
    }
}