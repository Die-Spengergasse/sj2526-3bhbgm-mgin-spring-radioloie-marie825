package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.*;
import at.spengergasse.spring_thymeleaf.entities.Reservierung;
import at.spengergasse.spring_thymeleaf.entities.ReservierungRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservierung")
public class ReservierungController
{
    private final ReservierungRepository reservierungrepository;
    private final PatientRepository patientrepository;
    private final DeviceRepository devicerepository;

    public ReservierungController(ReservierungRepository reservierungrepository, PatientRepository patientRepository, DeviceRepository deviceRepository) {
        this.reservierungrepository = reservierungrepository;
        this.patientrepository = patientRepository;
        this.devicerepository = deviceRepository;
    }

    @GetMapping("/list")
    public String reservierung(Model model, Integer deviceId) {
        if (deviceId != null) {
            model.addAttribute("reservierung", reservierungrepository.findByDeviceId(deviceId));
        } else {
            model.addAttribute("reservierung", reservierungrepository.findAll());
        }
        model.addAttribute("devices", devicerepository.findAll());
        return "reservierunglist";
    }

    @GetMapping("/add")
        public String addReservation(Model model)
        {
            model.addAttribute("reservierung",new Reservierung());
            model.addAttribute("patients",patientrepository.findAll());
            model.addAttribute("devices",devicerepository.findAll());
            return "add_reservierung";
        }

        @PostMapping("/add")
        public String addReservation(@ModelAttribute("reservierung") Reservierung reservierung)
        {
            reservierungrepository.save(reservierung);
            return "redirect:/reservierung/list";
        }

//        @PostMapping("/list")
//        public String addReservation(@ModelAttribute("reservierung") Reservierung reservierung)
//        {
//            reservierungrepository.save(reservierung);
//            return "redirect:/reservierung/list";
//        }
}