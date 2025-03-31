package com.iticbcn.pau.llibrary.Repositories;

import com.iticbcn.pau.llibrary.Model.Llibre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Set;  

public interface LlibreRepository extends JpaRepository<Llibre, Integer> {
    Optional<Llibre> findByIsbn(String isbn);
    Set<Llibre> findByTitol(String titol);
    Set<Llibre> findByTitolAndEditorial(String titol, String editorial);
}