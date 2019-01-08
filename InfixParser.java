import java.util.HashMap;
import java.util.Stack;
import java.util.Map;

public class InfixParser {
	private Map <String, Integer> pemdas;
	private Stack <String>operators;
	private Stack <String> postFix;
	private String [] calculation;
	
	public InfixParser(String calculation) {
		operators = new Stack<String>();
		postFix = new Stack<String>();
		this.calculation = calculation.split(" "); 
		pemdas = new HashMap<String, Integer>();
		pemdas.put("(", 1);
		pemdas.put(")", 1);
		pemdas.put("^", 2);
		pemdas.put("X", 3);
		pemdas.put("/", 3);
		pemdas.put("+", 4);
		pemdas.put("-", 4);
	}
	
	public void parse()
	{
		String temp;
		for(int i = 0; i < calculation.length; i++)
		{
			temp = calculation[i].trim();
			if(!isOperator(temp))
			{
				postFix.push(temp);
			}
			else
			{
				if(operators.isEmpty())
				{
					operators.push(temp);
					continue;
				}
				else if(!checkPrecedence(temp) && !(operators.peek().equals("(")))
				{	
					
					while(!operators.isEmpty() && !checkPrecedence(temp) && !(operators.peek().equals("(")))
					{
					String pop = operators.pop();
					postFix.push(pop);
						
					}
					operators.push(temp);
				}
				else if(temp.equals(")"))
				{
					while(!operators.peek().equals("("))
					{
						postFix.push(operators.pop());
					}
					operators.pop();
				}
				
				else {
					operators.push(temp);
				}
				}
			
		}
		while(!operators.isEmpty())
		{
			postFix.push(operators.pop());
		}
	}

	public boolean isOperator(String k)
	{
		if(pemdas.get(k) != null)
			return true;
		return false;
	}
	
	public boolean checkPrecedence(String a)
	{
		return (int)pemdas.get(a) < (int)pemdas.get(operators.peek());
	}
	
	public Stack<String> getPostFix()
	{
		return postFix;
	}

}
