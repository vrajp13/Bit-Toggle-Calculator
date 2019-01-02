package cs1302.calc;


import java.util.EmptyStackException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is the class for launching the calculator using javafx library
 * classes and other classes to accomplish making a calculator with little
 * changes, still with normal functionalities. This CalcApp class has several
 * classes objects, buttons, menubars, gridpane, borderpane, vbox, hbox, and etc
 * in order to create a calculator that will have functions such as bit toggle, 
 * simple operators, shift operator, factorial, exponent, implementation toggle,
 * and etc. The bit toggle in this CalcApp allows users to turn bits on and off.
 * 
 * @author Vraj Pragnesh Patel
 *
 */
public class CalcApp extends Application {
	//Just some instance variable for the use of the class methods
	private Stage stage = new Stage();
	private MenuBar fileBar = new MenuBar();
	private MenuBar helpBar = new MenuBar();
	private MenuBar themeBar = new MenuBar();
	private MenuBar windowBar = new MenuBar();
	private BorderPane borderPane = new BorderPane();
	private GridPane gridPane = new GridPane();
	private TextField expressionText = new TextField();
	private VBox topPortion = new VBox();
	private HBox menu = new HBox();
	private Bit bitsText = new Bit();
	private Button iterative = new Button("Iterative");
	private Button recursive = new Button("[Recursive]");
	private Button evaluate = new Button("Evaluate");
	private Button backspace = new Button("BACKSPACE");
	private Button clear = new Button("CLEAR");
	private Button leftShift = new Button("<<");
	private Button rightShift = new Button(">>");
	private Button exponent = new Button("^^");
	private Button factorial = new Button("!");
	private Button division = new Button("/");
	private Button multiply = new Button("*");
	private Button subtract = new Button("-");
	private Button addition = new Button("+");
	private Button zero = new Button("0");
	private Button one = new Button("1");
	private Button two = new Button("2");
	private Button three = new Button("3");
	private Button four = new Button("4");
	private Button five = new Button("5");
	private Button six = new Button("6");
	private Button seven = new Button("7");
	private Button eight = new Button("8");
	private Button nine = new Button("9");
	private String expression = "";
	private int expressionSize = expression.length();
	private Button[] allButtons = new Button[] {iterative, recursive, backspace,
			clear, leftShift, rightShift, exponent, factorial, seven, eight, nine,
			division, four, five, six, multiply, one, two, three, subtract, zero,
			evaluate, addition};
	private char[] operators = new char[] {'+', '-', '*', '/', '<', '>', '^', '!'};
	private boolean operandCheck = false;
	private boolean operatorCheck = false;
	private boolean recursiveImplement = false;
	private boolean iterativeImplement = false;
	private int result = 0;
	private MathOps recursiveMathOps = new RecursiveMathOps();
	private MathOps iterativeMathOps = new IterativeMathOps();
	
	/**
	 * This is the start that has been overriden from the extension of the Application
	 * class. This start method will set up the stage and scene for the entire calculator
	 * app. Also this start method contains several other methods that will allow the users
	 * to have friendly experience with the buttons, and its functions.
	 * 
	 * @param stage that sets up the the scene for the application
	 */
    @Override
    public void start(Stage stage) {
	    this.stage = stage;
	    expressionText.setAlignment(Pos.TOP_RIGHT);
	    //These methods are declared in the start so that when the application is launched
	    //the proper and user friendly application is maintained. 
	    recursiveImplement = true;
	    iterativeImplement = false;
	    addingButtons();
	    evaluateExpression();
		fileMenuBar();
		helpMenuBar();
		themeMenuBar();
		windowMenuBar();
		//Sets up the action for all of the buttons. 
		defaultColors();
		//Here the menu bars are added to the HBox so that it could be added to the VBox
		menu.getChildren().addAll(fileBar, themeBar, windowBar, helpBar);
		topPortion.getChildren().addAll(menu, expressionText, bitsText.resultText(), bitsText.displayingBits());
		//Later the VBox is added to the top part for borderpane
		borderPane.setTop(topPortion);
		//GridPane is added to the center of the borderpane
		borderPane.setCenter(gridPane);
	    Scene scene = new Scene(borderPane);
		stage.setMaxWidth(420);
		stage.setMaxHeight(400);
		stage.setTitle("cs1302-calc!");
	    stage.setScene(scene);
		stage.sizeToScene();
	    stage.show();
    } // start  
    /**
     * This private method sets up the button action for the evaluate button
     * so that when the user presses the button, it will evaluate the expression
     * according to the implementation that the calculator is in at that moment. 
     * If for some reason the expression is invalid, the this method will catch the
     * error and print out an error message to the users, to allow them to change 
     * their expression for evaluation
     */
    private void evaluateExpression() {
    	// Setting up the action for the evaluate button to accomplish its task
    	evaluate.setOnAction(event -> {
    	try {
    		try {
    			//The result will store the either the answer or it will have an
    			//error, and if there is no error than it will print to the textfield
    			//which is found in the bit class. 
    			result = findingResult();
    			System.out.printf("%s = %d \n", expression, result);
        	    bitsText.setResultText(Integer.toString(result), result);
        	//If any of these errors are caught then it will print an alert message
    		}catch(NumberFormatException n){
    			creatingAlert();
    		}
    		catch(EmptyStackException e) {
    			creatingAlert();
    		}
    		catch(ArithmeticException a) {
    			creatingAlert();
    		}
    		catch(StackOverflowError s) {
    			creatingAlert();
    		}
    	} catch (Exception e) {
    	    System.err.println(e);
    	    System.err.println("something went wrong!");
    	} // try
    	});
    }

    /**
     * This private method uses the current implementation toggle, and uses the 
     * MathOpsEvaluator to solve the expression, using the specific implementation
     * toggle. Whatever the result comes out to, the bit 32-bit toggle will be updated
     * according to the result calculated from the expression. 
     * 
     * @return int value which is the result of the expression
     * @throws NumberFormatException if there is an error in the expression
     * @throws EmptyStackException if the stack is stack empty
     * @throws ArthimeticException if the expression is dividing by zero
     */
    private int findingResult() throws NumberFormatException, EmptyStackException, ArithmeticException{
    	int result = 0;
    	//So if the expression is empty and evaluate is pressed then zero will be shown
    	//on the result textField
    	if(expression.equals("")) {
    		return result;
    	}
    	else {
    		//If there is a number after the factorial sign then it will throw an exception so 
    		//that an alert message could pop up because only operators allowed after factorial
    		if(checkingFactorial() != true) {
    			throw new NumberFormatException("Wrong usage of factorial operator");
    		}
    		else {
	    		//These if/else if statements accomplish the task of calculating the result
	    		//from the expression, but the implementation will depend on the buttons pressed
	    		//by the users. 
				if(recursiveImplement == false && iterativeImplement == true) {
					result = MathOpsEvaluator.evaluate(iterativeMathOps, expression);
					bitsText.resultBits(result);
				}
				else if(recursiveImplement == true && iterativeImplement == false) {
					result = MathOpsEvaluator.evaluate(recursiveMathOps, expression);
					bitsText.resultBits(result);
				}
			   	return result;
    		}
    	}
    }
    
    /**
     * This private method determines whether the next non-whitespace character after the
     * factorial is either operator or operand. If the next character is indeed an operator
     * then it will return true to continue as normal, but if it is an operand then it
     * return false and make the evaluate button to pop an alert message so that the user
     * knows that it is wrong to do that
     * @return
     */
    private boolean checkingFactorial() {
    	boolean check = false;
    	int count = 0;
    	//So this loop will go through the expression string and find whether it contains
    	//a factorial operator. And if it does than it will determine whether to function
    	//normally or pop up the alert message
    	for(int i = 0; i < expressionSize; i++) {
			if(expression.charAt(i) == '!'){
				if(i != expression.length()-1) {
					count++;
					//This loop will determine if the next character is one of operators
					//used in this calculator and return true if it is operator or it
					//will return false if it is a number.
					for(int j = 0; j < operators.length; j++) {
						if(expression.charAt(i+2) ==  operators[j]) {
							check = true;
							break;
						}
						else {
							check = false;
						}
					}
					if(check == false) {
						break;
					}
				}
				else {
					check = true;
				}
			}
		}
    	if(count == 0) {
    		check = true;
    	}
    	return check;
    }
    
    
    /**
     * This private method creates the alert message for the evaluate method so that
     * whenever the exception is caught the alert message will pop up. Also, this method
     * will pop up while the calculator is still open in the background.
     */
    private void creatingAlert() {
		//By using the alert object, a pop window will open with a error message 
		//for letting the user know what is wrong
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Look, an Error Dialog");
		alert.setContentText("Sorry, can't evaluate the expression Try Again!");
		alert.showAndWait();	
	}
    
    /**
     * This private method will set the action event for the operand buttons used
     * in this calculator app. Since the action event is same for all of the operand
     * buttons then a single lambda expression will be created for the same event and
     * it will used as the event handler for the specific operand set on action
     * 
     * @param count the index for the button in the button array
     */
    private void operandHandler(int count) {
    	//Creating a event handler variable which is using a lambda expression in order to
    	//create a common event for the set on action for the operand button
		EventHandler<ActionEvent> operandHandler = (event -> {
			//This if statement will add space after a operator is pressed
			if(operandCheck == true) {
				expression+= " ";
				operandCheck = false;
			}
				//The specific button text is added to the expression string
			expression+= allButtons[count].getText();
			expressionSize = expression.length();
			operatorCheck = true;
			expressionText.setText(expression);
		});
		allButtons[count].setOnAction(operandHandler);
    }
    
    /**
     * This private method will set the action event for the operator buttons used
     * in this calculator app. Since the action event is same for all of the operator
     * buttons then a single lambda expression will be created for the same event and
     * it will used as the event handler for the specific operator set on action
     * 
     * @param count the index for the button in the button array
     */
    private void operatorHandler(int count) {
    	//Creating a event handler variable which is using a lambda expression in order to
    	//create a common event for the set on action for the operator button
    	EventHandler<ActionEvent> operatorHandler = (event -> {
    		//This if statement will add a space after any operator or operand is pressed
    		if(operatorCheck == true || operandCheck == true) {
    			expression+= " ";
    			operatorCheck = false;
    		}
    		//The specific button text is added to the expression string
    		//evaluateExpression();
    		expression+= allButtons[count].getText();
    		expressionSize = expression.length();
    		operandCheck = true;
    		expressionText.setText(expression);
    	});
    	allButtons[count].setOnAction(operatorHandler);
    }
    
    /**
     * This private method is adding all the buttons to the gridpane while making sure
     * that the all the buttons are the same size and there is a space between each buttons.
     * In order to have the buttons with same size all the buttons are set at preferred width
     * and height of 90 and 10 respectively. 
     */
    private void addingButtons() {
    	//The gridPane is set to a preferred width and height so that all the buttons can fit
    	//properly
    	gridPane.setPrefSize(370, 50);
    	//The horizontal and vertical gaps are added in each row and column of the gridpane
    	gridPane.setHgap(5);
		gridPane.setVgap(5);
		int count = 0;
    	//This loop adds buttons in order from the buttons array into the gridPane and it also
    	//sets the buttons size to preferred size so that all buttons are same
    	for(int row = 0; row < 5; row++) {
    		for(int col = 0; col < 4; col++) {
    			allButtons[count].setPrefSize(90, 10);
    			gridPane.add(allButtons[count], col, row);
    			//These if/else if statements will add action events to the operator and operand
    			//buttons and since the same action is being added a private method will used to
    			//for buttons at the specific index
    			if(count == 4 || count == 5 || count == 6 || count == 7
    					|| count == 11 || count == 15 || count == 19) {
    				operatorHandler(count);
    			}
    			else if(count == 8 || count == 9 || count == 10 || count == 12 || count == 13
    					|| count == 14 || count == 16 || count == 17 || count == 18) {
    				operandHandler(count);
    			}
    			count++;
    		}
    	}
    	//This is out of the loop because there is one column missing in the gridPane
    	allButtons[20].setPrefSize(90, 10);
    	gridPane.add(allButtons[20], 0, 5);
    	operandHandler(20);
     	allButtons[21].setPrefSize(90, 10);
     	gridPane.add(allButtons[21], 1, 5);
       	allButtons[22].setPrefSize(90, 10);
       	gridPane.add(allButtons[22], 3, 5);
       	operatorHandler(22);
       	stage.sizeToScene();
       	//Sets the action event for the backspace and implementation buttons
       	backspaceAndClear();
       	implementationAction();
    }

    /**
     * This private method sets the button action for the implementation toggle of recursive
     * and iterative, inside the button action it changes the text to include the brackets so
     * that the user knows these classes methods will be used for calculation. Also, inside the
     * button action a boolean variable that will be activated and it help with the evaluation 
     * of the expression
     */
    private void implementationAction() {
    	//Recursive action is set where the text will be changed and the boolean variable will 
    	//activated to true so that it could be used
    	recursive.setOnAction(event -> {
    		recursive.setText("[Recursive]");
    		iterative.setText("Iterative");
    		recursiveImplement = true;
    		iterativeImplement = false;
    	});
    	//Iterative action is set where the text will be changed and the boolean variable will 
    	//activated to true so that it could be used
    	iterative.setOnAction(event -> {
    		iterative.setText("[Iterative]");
    		recursive.setText("Recursive");
    		iterativeImplement = true;
    		recursiveImplement = false;
    	});
    }

    /**
     * This private method sets the button action for the backspace button and clear button.
     * The backspace button action is will erase the non-whitespace characters with each pressed
     * of the backspace button. When the last element is removed than the result text will be
     * empty so that a new expression could be started. Also the bit expression is cleared. When
     * the clear button is pressed than the boolean variables are set to the default values
     * and the expression text field, result text field and the bit toggle is cleared.
     */
    private void backspaceAndClear() {
    	//The backspace button action is set 
    	backspace.setOnAction(event -> {
    		expressionSize = expressionSize - 1;
    		//This loop will go through the expression to find the whitespace and remove the 
    		//characters after the space including the whitespace. 
    		for(int i = expressionSize; i >= 0; i--) {
    			//Finds the white space and removes one character with each click
    			if(expression.charAt(expressionSize) == ' ') {
    				String temp = expression.substring(expressionSize);
    				int tempLength = temp.length() - 1;
    				//This if/else statement will remove the non space white character 
    				//depending on if it is a operator or operand it will function separately
    				if(tempLength > 1) {
    					if(check(temp.charAt(1)))
    						expression = expression.substring(0, expressionSize);
	    				else {
	    					expression = expression.substring(0, expressionSize + tempLength);
	    					expressionSize = expressionSize + tempLength;}
    				}
    				else {
    					expression = expression.substring(0, expressionSize);}
    				checkingOperator();
    				break;
    			}
    			//If it expression size is 0 then the expression will be cleared, along with
    			//bit toggle and result text field if the last character is operator or else
    			//it will erase one character at a time
    			else if(expressionSize == 0) {
    				String temp = expression.substring(expressionSize);
    				int tempLength = temp.length()-1;
    				if(check(temp.charAt(0))) {
    					expression = "";
    					bitsText.clearingBitToggle();
    					bitsText.setResultText("");
    					operatorCheck = false;
    					operandCheck = false;}
    				else {
    					expression = expression.substring(0, tempLength);
    					expressionSize = tempLength;}
    				if(tempLength == 0) {
    					expression = "";
    					bitsText.clearingBitToggle();
    					bitsText.setResultText("");
    					operatorCheck = false;
        	    		operandCheck = false;}
    				break;}
    			expressionSize--;}
    		expressionText.setText(expression);
    		expressionSize = expression.length();});
    	clearAction();
    }
    
   /**
    * The purpose of this private method is to set the button action for the clear button
    * and when it does that then it will set the spacing value to its default values so
    * that it could be used again in the other button actions
    */
    private void clearAction() {
    	//The clear button action is set to clear all the text fields and the bit toggle
    	clear.setOnAction(event -> {
    		//The text fields are cleared and few of the boolean variables are set to default
    		expression = "";
    		bitsText.setResultText("");
    		expressionSize = expression.length();
    		expressionText.setText(expression);
    		operatorCheck = false;
    		operandCheck = false;
    		bitsText.clearingBitToggle();
    	});
	}
    
    
    /**
     * This private method will check if the parameter character is actually a number or operator
     * and if it is an operator then it will return true otherwise it will return false
     * @param c the character to determine if it is an operator or operand
     * @return true if the parameter is operator otherwise false
     */
    private boolean check(char c) {
    	//This goes through the loop and checks if the character is an operator or operand
		for(int i = 0; i < operators.length; i++) {
			if(c == operators[i]) {
				return true;
			}
		}
		return false;
	}
    
    /**
     * This private method will check if the last character in the expression is an operator 
     * or number and then it will determine whether to set the variable that controls space
     * for other buttons. 
     */
    private void checkingOperator() {
    	//This loop goes through a char array that contains all the operators and checks
    	//whether to set the operandCheck true or false depending on the last character
    	//of the expression string
		for(int j = 0; j < operators.length; j++) {
			if(expression.charAt(expressionSize - 1) == operators[j]) {
				operandCheck = true;
				break;
			}
			else {
				operandCheck = false;
				operatorCheck = true;
			}
		}
    }

    /**
     * Creates a file menubar that will have one menu item which will terminate the application
     */
    private void fileMenuBar(){
    	//Creates a menuBar with a menu called file and inside that a menuItem 
    	//called exit which will terminate the application
		Menu file = new Menu("File");
		MenuItem exiting = new MenuItem("Exit");
		//When exiting menuItem is pressed then both the program and application terminate
		exiting.setOnAction(event -> {
			System.out.println("Exiting....");
			Platform.exit();
			System.exit(1);
		    });
		//Adds the menuItem to the menu and menuBar
		file.getItems().add(exiting);
		fileBar.getMenus().add(file);
    }
    
    /**
     * Creates a help menubar that will have one menu item which is some information about
     * the author. However, when this menu item is pressed it will open a new window
     * and it will have nothing to do with the parent application window. 
     */
    private void helpMenuBar() {
    	//Creates a menuBar with a menu called help and inside that a menuItem 
    	//called about which will open a new window containing info about the author
    	Menu help = new Menu("Help");
		MenuItem about = new MenuItem("About");
		//A new stage is created for the menu item about
		//This close button will only close the window and not the whole application
		about.setOnAction(event -> {
			Stage newWindow = new Stage();
			BorderPane newBorderPane = new BorderPane();
			VBox information = new VBox();
			//In a vertical box the image will be stored, after which it will in a border pane
			Image myPhoto = new Image("https://d1b10bmlvqabco.cloudfront.net/photos/j66m61jjhqa2p8/1541305507_375.png");
			ImageView photoView = new ImageView(myPhoto);
			newBorderPane.setTop(photoView);
			BorderPane.setAlignment(newBorderPane.getTop(), Pos.CENTER);
			information.getChildren().addAll(new Text("Name: Vraj Pragnesh Patel"), 
					new Text("Email: vraj.patel4913@gmail.com"), 
					new Text("Application Version: 1.0.1"));
			information.setAlignment(Pos.CENTER);
			//The top of border pane is image, center has info and bottom the close button.
			newBorderPane.setCenter(information);
			newBorderPane.setBottom(closeButton(newWindow));
			//A new scene is created for the new stage and the application modal
			Scene newScene = new Scene(newBorderPane, 450, 450);
			newWindow.setMaxWidth(500);
			newWindow.setMaxHeight(500);
			newWindow.setScene(newScene);
			newWindow.setTitle("About Vraj Pragnesh Patel");
			newWindow.initModality(Modality.APPLICATION_MODAL);
			newWindow.show();
		});
		help.getItems().add(about);
		helpBar.getMenus().add(help);
    }
    
    /**
     * This private method sets up the window menuBar as the menu item for the actual
     * calculator application. This method will show case one option which will allow
     * the user to either hide the bit toggle or show the bit toggle, and while it changes
     * it will still correlate with the current state of the result and its binary representation
     */
    private void windowMenuBar() {
    	//Creates the window menu and one menu item which will control whether bit toggle
    	//will be shown or stay hidden.
    	Menu window = new Menu("Window");
    	MenuItem bitToggle = new MenuItem("Hide Bit Toggle");
    	//When the menu item will be pressed it will show or hide the bit toggle and change
    	//the text depending on which condition it is in
    	bitToggle.setOnAction(event -> {
    		//If the text reads hide then it will hide bit toggle and change the text to show
    		if((bitToggle.getText()).equals("Hide Bit Toggle")) {
    			bitToggle.setText("Show Bit Toggle");
    			borderPane.setTop(addingBitToggle(1));
    			stage.sizeToScene();    			
    		}
    		//If the text reads show then it will show bit toggle and change the text to hide
    		else if ((bitToggle.getText()).equals("Show Bit Toggle")) {
    			bitToggle.setText("Hide Bit Toggle");
    			borderPane.setTop(addingBitToggle(0));
    			stage.sizeToScene();
    		}
    	});
    	window.getItems().add(bitToggle);
    	windowBar.getMenus().add(window);
    }

    /**
     * This private method will either add bit toggle or take away the bit toggle depending on
     * the number that it is corresponds to. Number 0 will add the bit toggle and number 1 will
     * take away the bit toggle from the VBox, which will be added to the borderPane
     * @param num correlates to either hiding or showing the bit toggle
     * @return VBox that will either contain the bit toggle or not depending on the parameter
     */
    private VBox addingBitToggle(int num) {
    	VBox topPortion = new VBox();
    	//If the parameter is 0 then it will add the bit toggle to the VBox and it will add
    	//using a bit class method that will contain the current binary representation
    	if(num == 0) {
    		topPortion.getChildren().addAll(menu, expressionText, bitsText.resultText());
			topPortion.getChildren().add(bitsText.displayingCurrentBits());
    	}
    	//If the parameter is 1 then it will take away the bit toggle from the VBox
    	else {
    		topPortion.getChildren().addAll(menu, expressionText, bitsText.resultText());
       	}
    	return topPortion;
    }
    
    /**
     * This private button method is creating the closing button for the helpBar 
     * method so it is convinent for the user to just press close to close the
     * application modal without closing the whole GUI Application
     * 
     * @param newWindow the stage where the close button needs to added
     * @return button that already has action set on it
     */
    private Button closeButton(Stage newWindow) {
		Button close = new Button("Close");
		//Sets the close button to have some action when it is pressed.
		close.setOnAction(event -> {
			System.out.println("Exiting...");
			newWindow.close();
		});
		return close;
    }
    
    /**
     * This private method will set the default styling of the button colors and it
     * will also help the theme menuBar to allow the default button to go back to
     * the default button colors
     */
    private void defaultColors() {
    	//The background color of the button will be set to a certain default color style
		backspace.setStyle("-fx-background-color: indianred");
		clear.setStyle("-fx-background-color: indianred");
		evaluate.setStyle("-fx-background-color: lightgreen");
		iterative.setStyle("-fx-background-color: blueviolet");
		recursive.setStyle("-fx-background-color: blueviolet");
		leftShift.setStyle("-fx-background-color: cyan");
		rightShift.setStyle("-fx-background-color: cyan");
		exponent.setStyle("-fx-background-color: cyan");
		factorial.setStyle("-fx-background-color: cyan");
		division.setStyle("-fx-background-color: cyan");
		multiply.setStyle("-fx-background-color: cyan");
		subtract.setStyle("-fx-background-color: cyan");
		addition.setStyle("-fx-background-color: cyan");
	}

    /**
     * This private method will set the theme one styling of the button colors and it
     * will also help the theme menuBar to allow the theme1 button to go back to
     * the theme1 button colors
     */
    private void themeOneColors() {
    	//The background color of the button will be set to a certain theme1 colors
    	//which are different from the default colors
    	backspace.setStyle("-fx-background-color: chocolate");
		clear.setStyle("-fx-background-color: chocolate");
		evaluate.setStyle("-fx-background-color: darksalmon");
		iterative.setStyle("-fx-background-color: gold");
		recursive.setStyle("-fx-background-color: gold");
		leftShift.setStyle("-fx-background-color: lightcoral");
		rightShift.setStyle("-fx-background-color: lightcoral");
		exponent.setStyle("-fx-background-color: lightcoral");
		factorial.setStyle("-fx-background-color: lightcoral");
		division.setStyle("-fx-background-color: lightcoral");
		multiply.setStyle("-fx-background-color: lightcoral");
		subtract.setStyle("-fx-background-color: lightcoral");
		addition.setStyle("-fx-background-color: lightcoral");
    }
    
    /**
     * This private method will set the theme two styling of the button colors and it
     * will also help the theme menuBar to allow the theme2 button to go back to
     * the theme2 button colors
     */
    private void themeTwoColors() {
    	//The background color of the button will be set to a certain theme2 colors
    	//which are different from the default colors and theme1 colors
    	backspace.setStyle("-fx-background-color: salmon");
		clear.setStyle("-fx-background-color: salmon");
		evaluate.setStyle("-fx-background-color: orange");
		iterative.setStyle("-fx-background-color: steelblue");
		recursive.setStyle("-fx-background-color: steelblue");
		leftShift.setStyle("-fx-background-color: moccasin");
		rightShift.setStyle("-fx-background-color: moccasin");
		exponent.setStyle("-fx-background-color: moccasin");
		factorial.setStyle("-fx-background-color: moccasin");
		division.setStyle("-fx-background-color: moccasin");
		multiply.setStyle("-fx-background-color: moccasin");
		subtract.setStyle("-fx-background-color: moccasin");
		addition.setStyle("-fx-background-color: moccasin");
    }

    /**
     * Creates a theme menubar that will have three menu item which will change the overall
     * theme background color of the button
     */
    private void themeMenuBar() {
    	//A theme menu is created which will contain three menu items and those menu items 
    	//will change the background colors of the button. 
    	Menu theme = new Menu("Themes");
		MenuItem theme1 = new MenuItem("Theme1");
		MenuItem defaultTheme = new MenuItem("Default");
		MenuItem theme2 = new MenuItem("Theme2");
		//When either default, theme1, or theme2 menuitem is pressed then it will call the
		//specific methods that will change the button background colors.
		defaultTheme.setOnAction(event -> {
			this.defaultColors();
		});
		theme1.setOnAction(event -> {
			this.themeOneColors();
		});
		theme2.setOnAction(event -> {
			this.themeTwoColors();
		});
		theme.getItems().addAll(defaultTheme, theme1, theme2);
		themeBar.getMenus().add(theme);
    }
    
    /**
     * This main method launches the overall application
     * 
     * @param args start method used as the first argument
     */    
    public static void main(String[] args) {
	try {
	    Application.launch(args);
	} catch (UnsupportedOperationException e) {
	    System.out.println(e);
	    System.err.println("If this is a DISPLAY problem, then your X server connection");
	    System.err.println("has likely timed out. This can generally be fixed by logging");
	    System.err.println("out and logging back in.");
	    System.exit(1);
	} // try
    } // main

} // CalcApp