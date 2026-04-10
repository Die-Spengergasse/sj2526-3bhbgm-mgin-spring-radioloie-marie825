package at.spengergasse.spring_thymeleaf.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "r_reservierung")
public class Reservierung
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private LocalDateTime reservationDate;
    private LocalDate reservationTime;
    private String bodyPart;
    private String commentar;

    // Instanzen
    private int patient;
    private int device;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public LocalDate getReservationTime() {return reservationTime;}

    public void setReservationTime(LocalDate reservationTime) {this.reservationTime = reservationTime;}

    public String getBodyPart() {return bodyPart;}

    public void setBodyPart(String bodyPart) {this.bodyPart = bodyPart;}

    public String getCommentar() {return commentar;}

    public void setCommentar(String commentar) {this.commentar = commentar;}
}
