import java.util.Stack;

public class PostFixParser {
	private Stack<Double> result;
	private Stack<String> postFix;
	
	public PostFixParser(Stack<String> e)
	{
		result = new Stack<Double>();
		postFix = new Stack<String>();
		while(!e.isEmpty())
		{
			postFix.push(e.pop());
		}
	}
	
	public Stack<Double> solve()
	{
		String pop;
		while(!postFix.isEmpty())
		{
			pop = postFix.pop();
			//test
			//System.out.println(pop);
			
			if(!isOperator(pop))
			{
				
				result.push(Double.parseDouble(pop));
				continue;
			}
			
			double operandR = result.pop();
			double operandL = result.pop();
			
			//System.out.println(operandL + " " + operandR);
			switch(pop)
			{
			case "+":
					//test
					double res = CalculatorOperations.add(operandL, operandR);
					//System.out.println("res " + res);
					result.push(res);
					break;
			case "-":
				    //test
					double res1 = CalculatorOperations.subtract(operandL, operandR);
					//System.out.println("res " + res1);
					result.push(res1);
					break;
			case "^":
					result.push(CalculatorOperations.power(operandL, operandR));
					break;
			case "/":
				    //test
					double res2 = CalculatorOperations.divide(operandL, operandR);
					//System.out.println("res " + res2);
					result.push(res2);
					break;
			case "X":
					//test
					double res3 = CalculatorOperations.multiply(operandL, operandR);
					//System.out.println("res " + res3);
					result.push(res3);
					break;
				
			}
			//System.out.println(result.peek());
		}
		return result;
	}
	
	public boolean isOperator(String e)
	{
		return (e.equals("+") || e.equals("/") || e.equals("-") || e.equals("X") || e.equals("^"));
	}
	
	public Double getResult()
	{
		return result.pop();
	}
}
