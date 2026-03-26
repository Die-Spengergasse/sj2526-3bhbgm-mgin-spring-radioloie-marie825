package at.spengergasse.spring_thymeleaf.entities;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RestController;

@Entity
@Table (name="d_devices")
public class Device
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private int id;
    private String art;
    private int raumnr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public int getRaumnr() {
        return raumnr;
    }

    public void setRaumnr(int raumnr) {
        this.raumnr = raumnr;
    }
}
