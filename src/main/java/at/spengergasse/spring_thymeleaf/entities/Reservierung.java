package at.spengergasse.spring_thymeleaf.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "r_reservierung")
public class Reservierung
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private int id;

    @Column
    private LocalDate reservationDate;

    @Column
    private String reservationTime;

    private String bodyPart;

    private String commentar;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="device_id")
    private Device device;

    public Reservierung(int id, LocalDate reservationDate, String reservationTime, String bodyPart, String commentar, Patient patient, Device device) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.bodyPart = bodyPart;
        this.commentar = commentar;
        this.patient = patient;
        this.device = device;
    }
    public Reservierung(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {return patient;}

    public void setPatient(Patient patient) {this.patient = patient;}

    public Device getDevice() {return device;}

    public void setDevice(Device device) {this.device = device;}

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {return reservationTime;}

    public void setReservationTime(String reservationTime) {this.reservationTime = reservationTime;}

    public String getBodyPart() {return bodyPart;}

    public void setBodyPart(String bodyPart) {this.bodyPart = bodyPart;}

    public String getCommentar() {return commentar;}

    public void setCommentar(String commentar) {this.commentar = commentar;}
}
