package nl.cofx.jpa.experiment;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AppleRepository extends CrudRepository<Apple, UUID> {}
