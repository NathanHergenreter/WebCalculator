package webcalc.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Calculation {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String calculationString;
	public String stringValue() { return calculationString; }
	
	protected Calculation() {}
	public Calculation(String calculationString)
	{
		this.calculationString = calculationString;
	}
}
