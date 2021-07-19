package data.repositories;

import data.entities.TI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface TIRepository extends JpaRepository<TI, String> {

}