package at.spengergasse.spring_thymeleaf.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservierungRepository extends JpaRepository<Reservierung,Integer>
{
    List<Reservierung> findByDeviceId(Integer deviceId);
}
