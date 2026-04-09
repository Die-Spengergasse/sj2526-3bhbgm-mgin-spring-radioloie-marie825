package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.Patient;
import at.spengergasse.spring_thymeleaf.entities.PatientRepository;
import at.spengergasse.spring_thymeleaf.entities.Reservierung;
import at.spengergasse.spring_thymeleaf.entities.ReservierungRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservierung")
public class ReservierungController
{
    private final ReservierungRepository reservierungrepository;

    public ReservierungController(ReservierungRepository reservierungrepository) {
        this.reservierungrepository = reservierungrepository;
    }

    @GetMapping("/list")
    public String reservierung(Model model)
    {
        model.addAttribute("reservierung",reservierungrepository.findAll());
            return "reservierunglist";
        }
    @GetMapping("/add")
        public String addReservation(Model model)
        {
            model.addAttribute("reservierung",new Reservierung());
            return "add_reservierung";
        }

        @PostMapping("/list")
        public String addReservation(@ModelAttribute("reservierung") Reservierung reservierung)
        {
            reservierungrepository.save(reservierung);
            return "redirect:/reservierung/list";
        }
}