public class CalculatorOperations {
	

public static double add(double a, double b)
{
	return a+b;
}

public static double multiply(double a, double b)
{
	return a*b;
}

public static double subtract(double a, double b)
{
	return a - b;
}

public static double divide(double a, double b)
{
	if( b == 0)
		throw new IllegalArgumentException("Cannot divide by zero");
	else
		return a/b;
}

public static double power(double a, double b)
{
	if(b == 1.0)
	{
		return a;
	}
	
	else
		return a * power(a, b-1);
}
}