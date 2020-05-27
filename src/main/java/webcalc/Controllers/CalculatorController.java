package webcalc.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import webcalc.Entities.Calculation;
import webcalc.Entities.CalculationRepository;

@Controller
public class CalculatorController {

    @Autowired
    private CalculationRepository repo;
    
    // Displays a form to perform calculations, contains /calculations as a subpage
	@GetMapping(value="/")
    public String calculatorPage(Model model) 
    {
    	return "calculator";
    }
	
	// Gets current size of Calculations table
	@GetMapping(value="/size")
	public String sizePage(Model model)
	{
		model.addAttribute("size", repo.count());
		return "size";
	}
	
	// Page with a table of the last 10 calculations
	@GetMapping(value="/calculations")
	public String calculationsPage(Model model)
	{
		model.addAttribute("calculations", lastTen());
		return "calculations";
	}
	
	// Gets the last ten (or all of them if < 10) calculations in the db
	// In order by most to least recent
	@GetMapping(value="/lastTen")
	public List<String> lastTen()
	{
		List<Calculation> calculations = repo.findAll();
		int firstIdx = calculations.size() - 10;
		firstIdx = firstIdx < 0 ? 0 : firstIdx;
		calculations = calculations.subList(firstIdx, calculations.size());
		
		List<String> stringCalculations = new ArrayList<String>();
		for(int idx = calculations.size() - 1; idx >= 0; idx--)
			stringCalculations.add(calculations.get(idx).stringValue());

		return stringCalculations;
	}
	
	@PostMapping(value="/calculate")
    @ResponseStatus(value = HttpStatus.OK)
	public void calculate(@RequestParam(name = "number0") int num0,
			  			  @RequestParam(name = "operation") String op,
						  @RequestParam(name = "number1") int num1)
	{
		int calculationVal;
		switch(op)
		{
		case "+":
			calculationVal = num0 + num1; 
			break;
		case "-":
			calculationVal = num0 - num1;
			break;
		case "x":
			calculationVal = num0 * num1;
			break;
		case "/":
			calculationVal = num0 / num1;
			break;
		// Shouldn't happen
		default:
			calculationVal = 0;
			break;
		}
		
		Calculation calculation = new Calculation(
				new String(num0 + " " + op + " " + num1 + " = " + calculationVal));

		repo.save(calculation);
	}
}
