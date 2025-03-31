package com.iticbcn.pau.llibrary.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data  // Lombok: Genera getters, setters, toString, etc.
@Table(name = "books")
public class Llibre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_llibre")
    private int idLlibre;

    @Column(name = "titol", nullable = false)
    private String titol;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "editorial")
    private String editorial;

    @Column(name = "data_publicacio")
    private LocalDate dataPublicacio;  // Cambiado de String a LocalDate

    @Column(name = "tematica")
    private String tematica;

    @Column(name = "ISBN", unique = true, nullable = false)  // Mapea al campo UNIQUE NOT NULL
    private String isbn;
}