
public class Controller {
	private InfixParser in_parse;
	private PostFixParser post_parse;
	private double result;
	
	public Controller(String input)
	{
		in_parse = new InfixParser(input);
		in_parse.parse();
		post_parse = new PostFixParser(in_parse.getPostFix());
		post_parse.solve();
		setResult();
	}
	
	public void setResult()
	{
		result = post_parse.getResult();
	}
	
	public double getResult()
	{
		return result;
	}
}