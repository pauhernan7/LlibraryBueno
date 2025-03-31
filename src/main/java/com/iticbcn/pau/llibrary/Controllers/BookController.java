package com.iticbcn.pau.llibrary.Controllers;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.iticbcn.pau.llibrary.Model.Llibre;
import com.iticbcn.pau.llibrary.Model.Usuaris;
import com.iticbcn.pau.llibrary.Services.LlibreService;

@Controller
@SessionAttributes("users")
public class BookController {

    @Autowired
    private LlibreService llibreService;

    @GetMapping("/")
    public String iniciar(Model model) {
        return "login";
    }

    @PostMapping("/index")
    public String login(@ModelAttribute("users") Usuaris users, Model model) {
        model.addAttribute("users", users);
        if (users.getUsuari().equals("pau") && users.getPassword().equals("hola")) {
            return "index";
        } else {
            return "login";
        }        
    }

    @GetMapping("/index")
    public String index(@ModelAttribute("users") Usuaris users, Model model) {
        return "index";
    }

    @GetMapping("/consulta") 
    public String consulta(@ModelAttribute("users") Usuaris users, Model model) {
        model.addAttribute("llibres", llibreService.getAllLlibres());
        return "consulta";
    }

    @GetMapping("/inserir") 
    public String inputInserir(@ModelAttribute("users") Usuaris users, Model model) {
        return "inserir";
    }

    @GetMapping("/cercaid")
    public String inputCerca(@ModelAttribute("users") Usuaris users, Model model) {
        model.addAttribute("llibreErr", true);
        model.addAttribute("message", "");
        model.addAttribute("llibre", new Llibre());
        return "cercaid";
    }

    @PostMapping("/inserir")
    public String inserir(
        @ModelAttribute("users") Usuaris users,
        @RequestParam(name = "titol") String titol,
        @RequestParam(name = "autor") String autor,
        @RequestParam(name = "editorial") String editorial,
        @RequestParam(name = "datapublicacio") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate datapublicacio,
        @RequestParam(name = "tematica") String tematica,
        @RequestParam(name = "isbn") String isbn,
        Model model
    ) {
        try {
            Llibre nouLlibre = new Llibre();
            nouLlibre.setTitol(titol);
            nouLlibre.setAutor(autor);
            nouLlibre.setEditorial(editorial);
            nouLlibre.setDataPublicacio(datapublicacio);
            nouLlibre.setTematica(tematica);
            nouLlibre.setIsbn(isbn);

            llibreService.saveLlibre(nouLlibre);
            return "redirect:/consulta";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "inserir";
        }
    }

    @PostMapping("/cercaid")
    public String cercaId(
        @ModelAttribute("users") Usuaris users,
        @RequestParam(name = "idLlibre") String idLlibre,
        Model model
    ) {
        try {
            int idLlib = Integer.parseInt(idLlibre);
            Optional<Llibre> llibre = llibreService.findByIdLlibre(idLlib);
            
            if (llibre.isPresent()) {
                model.addAttribute("llibre", llibre.get());
                model.addAttribute("llibreErr", false);
            } else {
                model.addAttribute("message", "No hi ha cap llibre amb aquesta id");
                model.addAttribute("llibreErr", true);
            }
        } catch (NumberFormatException e) {
            model.addAttribute("message", "La id de llibre ha de ser un nombre enter");
            model.addAttribute("llibreErr", true);
        }
        return "cercaid";
    }

    @PostMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDuplicateISBN(DataIntegrityViolationException e, Model model) {
        model.addAttribute("errorMessage", "L'ISBN ja existeix a la base de dades");
        return "inserir";
    }

    @ModelAttribute("users")
    public Usuaris getDefaultUser() {
        return new Usuaris();
    }
}