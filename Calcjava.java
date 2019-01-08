import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;


public class Calcjava extends Application {
	//BorderPane root=new BorderPane();
	VBox num_display= new VBox();
	Label short_display;
	Label full_display;
	String expression = "";
	String short_exp = "";
	boolean periodSet;
	boolean operatorSet;
	Controller control;
	boolean newExp;
	int para;
	boolean priorWasPara;
	
	public void start(Stage stage) {
		GridPane numPane = new GridPane();
		Button [] numbers = new Button[10];
		Button [] operators = new Button[10];
		operatorSet = true;
		periodSet = false;
		newExp = false;
		para = 0;
		priorWasPara = false;
		
		operators[0] = new Button("=");
		operators[1] = new Button("+");
		operators[2] = new Button("-");
		operators[3] = new Button("X");
		operators[4] = new Button("/");
		operators[5] = new Button(".");
		operators[6] = new Button("^");
		operators[7] = new Button("CE");
		operators[8] = new Button("<-");
		operators[9] = new Button("( )");
		
	
		
		for(Button btn : operators)
		{
			btn.prefHeightProperty().bind(num_display.heightProperty());
			btn.prefWidthProperty().bind(num_display.widthProperty());
			btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null,null)));
		}
		
		//Creates all number buttons and binds to screen
		for(int i=0; i<10;i++){
			String number= Integer.toString(i);
			numbers[i]=new Button(number);
			numbers[i].setOnAction(numbersEvent);
			numbers[i].prefHeightProperty().bind(num_display.heightProperty());
			numbers[i].prefWidthProperty().bind(num_display.widthProperty());
			
			numbers[i].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null,null)));
		}
		
		
		for(int j = 1; j < 7; j++)
		{
			operators[j].setOnAction(operatorEvent);
		}
		
		operators[0].setOnAction(equalsButton);
		operators[7].setOnAction(delButtons);
		operators[8].setOnAction(delButtons);
		operators[9].setOnAction(paraButton);
		
		//adds operators to grid
		for(int i = 0; i < 5; i++)
		{
			numPane.add(operators[i], 3, operators.length - i - 6);
		}
		
		//adds remainder of operators to grid
		numPane.add(operators[5], 2, 4);
		numPane.add(operators[6], 0, 0);
		numPane.add(operators[7], 0, 4);
		numPane.add(operators[8], 2, 0);
		numPane.add(operators[9], 1, 0);

		
		//Adds zero to grid
		numPane.add(numbers[0], 1, 4);
		//Add numbers 1-9 to grid
		for(int i=3, j=1; i>=1; i--) {
			for(int k=0; k<3; k++) {
			numPane.add(numbers[j], k, i);
			j++;
			}
		}
		
		
		full_display = new Label();
		double temp = num_display.getHeight() *.25;
		System.out.println(temp);
	    full_display.setMinHeight(75);
	    full_display.setPrefHeight(150);;
	    full_display.prefHeightProperty().bind(num_display.heightProperty());
	    full_display.prefWidthProperty().bind(num_display.widthProperty());
	    
	    short_display=new Label();
	    short_display.setMinHeight(75);
	    short_display.setPrefHeight(150);;
	    short_display.prefHeightProperty().bind(num_display.heightProperty());
	    short_display.prefWidthProperty().bind(num_display.widthProperty());
		
	    num_display.getChildren().add(short_display);
		num_display.getChildren().add(full_display);
		num_display.getChildren().add(numPane);
		
		
	
	Scene scene=new Scene(num_display,300,400);
	stage.setScene(scene);
	stage.show();
	
	
	}
	
	void unset()
	{
		periodSet = false;
		priorWasPara = false;
		operatorSet = true;
		newExp = true;
		short_display.setText("");
		short_exp = "";
		expression = "";
		para = 0;
	}
	
	boolean isOperator(String e)
	{
		return e == "+" || e == "-" || e == "^" || e == "X" || e == "/";
	}
	
	
	
	EventHandler<ActionEvent> numbersEvent = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent e) {
			priorWasPara = false;
			if(newExp == true)
			{
				full_display.setText("");
				newExp = false;
			}
			
			String temp =  ((Button)e.getSource()).getText();
			operatorSet = false;
			short_exp += temp;
			short_display.setText(short_exp);
			
		}
		
		
	};
	
	EventHandler<ActionEvent> operatorEvent = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent e) {
			if(newExp == true)
			{
				full_display.setText("");
				newExp = false;
			}
			
			String temp =  ((Button)e.getSource()).getText();
			
			
			if(temp == "." && periodSet != true)
			{	
				short_exp += temp;
				short_display.setText(short_exp);
				periodSet = true;
			}
			
			else if(isOperator(temp) && operatorSet != true)
			{
				operatorSet = true;
				periodSet = false;
				if(priorWasPara == false)
					short_exp += " " + temp;
				else 
					short_exp += temp;
				expression += " " + short_exp;
				short_exp = "";
				short_display.setText(short_exp);
				full_display.setText(expression);
			}
			priorWasPara = false;
		}
	
	};
	
	EventHandler<ActionEvent> equalsButton = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent e) {
			
			expression += " " + short_exp;
			System.out.println(expression.trim());
			control = new Controller(expression.trim());
			Double result = control.getResult();
			
			//if((result % 1) < 1)
				full_display.setText(result.toString());
				unset();
			//else
				//full_display.setText((new Integer((int)result)).toString());
			
		}
		
	};
	
	EventHandler<ActionEvent> delButtons = new EventHandler<ActionEvent> () {

		@Override
		public void handle(ActionEvent e) {
			String temp =  ((Button)e.getSource()).getText();
			if(temp.equals("CE"))
			{
				unset();
				short_display.setText("");
				full_display.setText("");
			}
			
			else
			{
				int len = short_exp.length();
				if(len > 0)
				{	
				short_exp = short_exp.substring(0, len -1);
				short_display.setText(short_exp);
				}
			}
		}
		
	};
	
	EventHandler<ActionEvent> paraButton = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent e) {
			if(newExp == true)
			{
				full_display.setText("");
				newExp = false;
			}

			if(expression =="" || (short_exp.equals("")) || para == 0)
			{
				if(operatorSet == false || priorWasPara == true) {
					if(short_exp.equals(""))
						expression += " " + "X" + " " + "(";
					else 
						expression +=  " " + short_exp + " " + "X" + " " + "(";
					short_exp = "";
					short_display.setText(short_exp);
					operatorSet = true;
				}
				else
					expression += " " + "(";
				para ++;
			}
			
			else if(para > 0 ) {
				
				if(short_exp == "")
					expression += " " + ")";
				else
					expression += " " + short_exp + " " + ")";
				short_exp = "";
				short_display.setText(short_exp);
				para --;	
			}
		
			full_display.setText(expression);
			priorWasPara = true;
			
		}
		
	};
	public boolean illegalInput(String temp)
	{
		return ((temp == "." && periodSet == true) || operatorSet == true);
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}