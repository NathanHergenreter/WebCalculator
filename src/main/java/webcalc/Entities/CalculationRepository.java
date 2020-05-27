package webcalc.Entities;

import org.springframework.data.jpa.repository.JpaRepository;

import webcalc.Entities.Calculation;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {
	
}
