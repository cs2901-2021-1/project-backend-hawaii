package data.repositories;

import data.entities.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TIRepository extends JpaRepository<Viewer, String> {
}
