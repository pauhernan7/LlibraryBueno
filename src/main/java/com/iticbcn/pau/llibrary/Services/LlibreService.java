package com.iticbcn.pau.llibrary.Services;

import com.iticbcn.pau.llibrary.Model.Llibre;
import com.iticbcn.pau.llibrary.Repositories.LlibreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LlibreService {

    @Autowired
    private LlibreRepository llibreRepository;

    // Método para guardar un libro con validación de ISBN
    public Llibre saveLlibre(Llibre llibre) {
        validateISBN(llibre.getIsbn());
        checkDuplicateISBN(llibre.getIsbn());
        return llibreRepository.save(llibre);
    }

    // Método para validar formato ISBN (10 o 13 dígitos numéricos)
    private void validateISBN(String isbn) {
        if (isbn == null || !isbn.matches("^\\d{10}(\\d{3})?$")) {
            throw new IllegalArgumentException("Format d'ISBN invàlid: ha de tenir 10 o 13 dígits numèrics");
        }
    }

    // Método para verificar ISBN duplicado
    private void checkDuplicateISBN(String isbn) {
        if (llibreRepository.findByIsbn(isbn).isPresent()) {
            throw new IllegalArgumentException("L'ISBN ja existeix a la base de dades");
        }
    }

    // Métodos básicos CRUD
    public List<Llibre> getAllLlibres() {
        return llibreRepository.findAll();
    }

    public Optional<Llibre> findByIdLlibre(int id) {
        return llibreRepository.findById(id);
    }

    public Optional<Llibre> findByIsbn(String isbn) {
        return llibreRepository.findByIsbn(isbn);
    }

    // Métodos adicionales requeridos por la actividad
    public Set<Llibre> findByTitol(String titol) {
        return llibreRepository.findByTitol(titol);
    }

    public Set<Llibre> findByTitolAndEditorial(String titol, String editorial) {
        return llibreRepository.findByTitolAndEditorial(titol, editorial);
    }

    // Método para validar ISBN (público para uso del controlador)
    public boolean isValidISBN(String isbn) {
        try {
            validateISBN(isbn);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}