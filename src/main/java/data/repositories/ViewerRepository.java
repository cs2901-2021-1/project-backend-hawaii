package data.repositories;

import data.entities.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewerRepository extends JpaRepository<Viewer, String> {

}
