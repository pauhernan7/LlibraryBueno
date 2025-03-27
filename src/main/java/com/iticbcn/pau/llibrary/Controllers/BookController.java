package com.iticbcn.pau.llibrary.Controllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.iticbcn.pau.llibrary.Model.Llibre;
import com.iticbcn.pau.llibrary.Model.Usuaris;
import com.iticbcn.pau.llibrary.Repositories.RepoLlibre;

@Controller
@SessionAttributes("users")
public class BookController {

    @Autowired
    private RepoLlibre repoll; 

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
        model.addAttribute("llibres", repoll.getAllLlibres());
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
    public String inserir(@ModelAttribute("users") Usuaris users, 
                          @RequestParam(name = "idLlibre") String idLlibre,
                          @RequestParam(name = "titol") String titol,  
                          @RequestParam(name = "autor") String autor,
                          @RequestParam(name = "editorial") String editorial,  
                          @RequestParam(name = "datapublicacio") String datapublicacio,
                          @RequestParam(name = "tematica") String tematica,
                          Model model) {

        if (idLlibre == null || !idLlibre.matches("\\d+")) {
            model.addAttribute("message", "La id de llibre ha de ser un nombre enter");
            model.addAttribute("llibreErr", true);
            return "inserir";
        }

        int idL = Integer.parseInt(idLlibre);
        repoll.InsertaLlibre(new Llibre(idL, titol, autor, editorial, datapublicacio, tematica));
        model.addAttribute("llibres", repoll.getAllLlibres());
        return "consulta";
    }

    @PostMapping("/cercaid")
    public String cercaId(@ModelAttribute("users") Usuaris users,
                          @RequestParam(name = "idLlibre", required = false) String idLlibre, 
                          Model model) {
        
        try {
            int idLlib = Integer.parseInt(idLlibre);
            Llibre llibre = repoll.getLlibreID(idLlib);
            if (llibre != null) {
                model.addAttribute("llibre", llibre);
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

    @ModelAttribute("users")
    public Usuaris getDefaultUser() {
        return new Usuaris(); 
    }
}
