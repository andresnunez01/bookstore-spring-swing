package com.example.tienda_libros.servicio;

import com.example.tienda_libros.modelo.Libro;
import com.example.tienda_libros.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServicio implements ILibroServicio{

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Override
    public List<Libro> listarLibros() {
        List<Libro> libros = libroRepositorio.findAll();
        return libros;
    }

    @Override
    public Libro buscarLibro(Integer idLibro) {
        Libro libro = libroRepositorio.findById(idLibro).orElse(null);
        return libro;
    }

    @Override
    public void guardarLibro(Libro libro) {
        libroRepositorio.save(libro);

    }

    @Override
    public void eliminarLibro(Libro libro) {
        libroRepositorio.delete(libro);

    }
}
