package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.Device;
import at.spengergasse.spring_thymeleaf.entities.DeviceRepository;
import at.spengergasse.spring_thymeleaf.entities.Patient;
import at.spengergasse.spring_thymeleaf.entities.PatientRepository;
import at.spengergasse.spring_thymeleaf.entities.Reservierung;
import at.spengergasse.spring_thymeleaf.entities.ReservierungRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/reservierung")
public class ReservierungController {
    private final ReservierungRepository reservierungrepository;
    private final PatientRepository patientrepository;
    private final DeviceRepository devicerepository;

    public ReservierungController(ReservierungRepository reservierungrepository, PatientRepository patientRepository, DeviceRepository deviceRepository) {
        this.reservierungrepository = reservierungrepository;
        this.patientrepository = patientRepository;
        this.devicerepository = deviceRepository;
    }

    @GetMapping("/list")
    public String reservierung(Model model, @RequestParam(required = false) Integer deviceId) {
        if (deviceId != null) {
            model.addAttribute("reservierung", reservierungrepository.findByDeviceId(deviceId));
        } else {
            model.addAttribute("reservierung", reservierungrepository.findAll());
        }
        model.addAttribute("devices", devicerepository.findAll());
        return "reservierunglist";
    }

    @GetMapping("/add")
    public String addReservation(Model model) {
        populateReservationForm(model, new Reservierung(), null, null);
        return "add_reservierung";
    }

    @PostMapping("/add")
    public String addReservation(@ModelAttribute("reservierung") Reservierung reservierung,
                                 BindingResult bindingResult,
                                 @RequestParam(required = false) Integer patientId,
                                 @RequestParam(required = false) Integer deviceId,
                                 Model model) {
        if (reservierung.getReservationDate() == null) {
            bindingResult.rejectValue("reservationDate", "reservationDate.required",
                    "Bitte ein Reservierungsdatum eingeben.");
        }

        if (reservierung.getReservationTime() == null || reservierung.getReservationTime().isBlank()) {
            bindingResult.rejectValue("reservationTime", "reservationTime.required",
                    "Bitte eine Reservierungszeit eingeben.");
        }

        LocalTime newStart = null;
        if (reservierung.getReservationTime() != null && !reservierung.getReservationTime().isBlank()) {
            try {
                newStart = LocalTime.parse(reservierung.getReservationTime());
            } catch (DateTimeParseException exception) {
                bindingResult.rejectValue("reservationTime", "reservationTime.invalid",
                        "Bitte eine gueltige Uhrzeit eingeben.");
            }
        }

        if (reservierung.getReservationDate() != null && newStart != null) {
            LocalDateTime reservationDateTime = LocalDateTime.of(
                    reservierung.getReservationDate(),
                    newStart
            );

            if (reservationDateTime.isBefore(LocalDateTime.now())) {
                bindingResult.rejectValue("reservationDate", "reservationDate.past",
                        "Ein Termin in der Vergangenheit darf nicht reserviert werden.");
            }
        }

        if (patientId == null) {
            model.addAttribute("errorMessage", "Bitte einen Patienten auswaehlen.");
        } else if (deviceId == null) {
            model.addAttribute("errorMessage", "Bitte ein Geraet auswaehlen.");
        }

        if (bindingResult.hasErrors() || patientId == null || deviceId == null) {
            populateReservationForm(model, reservierung, patientId, deviceId);
            return "add_reservierung";
        }

        Patient patient = patientrepository.findById(patientId).orElse(null);
        Device device = devicerepository.findById(deviceId).orElse(null);

        if (patient == null || device == null) {
            model.addAttribute("errorMessage", "Patient oder Geraet wurde nicht gefunden.");
            populateReservationForm(model, reservierung, patientId, deviceId);
            return "add_reservierung";
        }

        LocalTime newEnd = newStart.plusMinutes(30);

        List<Reservierung> deviceReservations = reservierungrepository
                .findByDeviceIdAndReservationDate(deviceId, reservierung.getReservationDate());
        for (Reservierung existingReservation : deviceReservations) {
            if (hasOverlap(newStart, newEnd, existingReservation.getReservationTime())) {
                model.addAttribute("errorMessage",
                        "Dieses Geraet ist zu dieser Zeit bereits reserviert.");
                populateReservationForm(model, reservierung, patientId, deviceId);
                return "add_reservierung";
            }
        }

        List<Reservierung> patientReservations = reservierungrepository
                .findByPatientIdAndReservationDate(patientId, reservierung.getReservationDate());
        for (Reservierung existingReservation : patientReservations) {
            if (hasOverlap(newStart, newEnd, existingReservation.getReservationTime())) {
                model.addAttribute("errorMessage",
                        "Dieser Patient hat zu dieser Zeit bereits einen Termin.");
                populateReservationForm(model, reservierung, patientId, deviceId);
                return "add_reservierung";
            }
        }

        reservierung.setPatient(patient);
        reservierung.setDevice(device);
        reservierungrepository.save(reservierung);
        return "redirect:/reservierung/list";
    }

    private boolean hasOverlap(LocalTime newStart, LocalTime newEnd, String existingTimeValue) {
        if (existingTimeValue == null || existingTimeValue.isBlank()) {
            return false;
        }

        LocalTime existingStart = LocalTime.parse(existingTimeValue);
        LocalTime existingEnd = existingStart.plusMinutes(30);
        return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }

    private void populateReservationForm(Model model, Reservierung reservierung, Integer patientId, Integer deviceId) {
        model.addAttribute("reservierung", reservierung);
        model.addAttribute("patients", patientrepository.findAll());
        model.addAttribute("devices", devicerepository.findAll());
        model.addAttribute("selectedPatientId", patientId);
        model.addAttribute("selectedDeviceId", deviceId);
    }
}
