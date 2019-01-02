package cs1302.calc;


import javafx.scene.control.TextField;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * This is a bit class that extends Text in order to use some of its method via inheritance
 * and this also allows to change Text methods to work in favor of this class. This bit class
 * has several methods that will deal solely deal with the result text and bit toggle so that
 * when CalcApp uses its objects it allows users to know the binary representation of the
 * expression result and whether to turn certain bits on or off. This class that methods
 * that will create bits text array and set up mouse actions to change the result, depending
 * on the power of the two that it turned on. It will place the text bits in a HBox so that it 
 * could be represented on the calculator
 * 
 * @author Vraj Pragnesh Patel
 *
 */
public class Bit extends Text{
	
	//Just some instance variables to be used for methods and class objects
	private Text[] textBits = new Text[31];
	private BasicMathOps operator = new BasicMathOps();
	private TextField resultText = new TextField();
	private int result = 0;
	
	/**
	 * This constructors sole purpose is to just set the result textfield's preferred width
	 * with the stage width size.
	 */
	public Bit() {
		resultText.setPrefWidth(400);
		resultText.setAlignment(Pos.TOP_RIGHT);
	}
	
	/**
	 * This method declares all the text bit to 0 and also sets up a mouse click for each 
	 * text bit in the array, so that whenever it is clicked it will determine whether
	 * to turn on or off the bit. If the text is 0 and it is clicked then it will turn on the
	 * bit and add the corresponding power of 2 to the result, and if it is 1 and it is 
	 * clicked then it will turn off the bit and subtract the corresponding power of 2 from
	 * the result.
	 */
	public void textBitsAction() {
		//This loop goes through each index of the text bits array and sets its to zero
		//while declaring each index of the array to have a mouse click for changing
		//the result
		for(int i = 0; i < textBits.length; i++) {
			textBits[i] = new Text("0");
			int index = finalValue(i);
			textBits[i].setOnMouseClicked(event -> {
				//If the bit is 0 then it will turn on the bit and add the corresponding
				//power of 2 to the result, and it is 1 then it will turn off the bit and
				//subtract the corresponding power of 2 from the result
				if((textBits[index].getText()).equals("0")) {
					bitOn(index);
				}
				else {
					bitOff(index);
				}
			});
		}
	}
	
	/**
	 * This private method will correspond to turning on the bit and adding the corresponding
	 * power of 2 to the result, and when it does that it will change the text to 1 and also
	 * change the result on the result textfield
	 * @param index the power that will be used for 2 to add to the result
	 */
	private void bitOn(int index) {
		//Changes the text to 1 and adds the power of 2 at the specific index to the result
		textBits[index].setText("1");
		int binaryVal = operator.pow(2, index);
		result = operator.add(result, binaryVal);
		this.setResultText(Integer.toString(result));
	}
	
	/**
	 * This private method will correspond to turning off the bit and subtract the corresponding
	 * power of 2 to the result, and when it does that it will change the text to 0 and also
	 * change the result on the result textfield
	 * @param index the power that will be used for 2 to subtract from the result
	 */
	private void bitOff(int index) {
		//Changes the text to 0 and subtracts the power of 2 at specific index from the result
		textBits[index].setText("0");
		int binaryVal = operator.pow(2, index);
		//Only positive value will be posted, no negative result will be posted
		if(operator.sub(index, binaryVal) >= 0) {
			result = operator.sub(result, binaryVal);
			this.setResultText(Integer.toString(result));
		}
	}
	/**
	 * This method sets the result text field to an expression that will come from the parameter
	 * @param expression a string that will be posted on the result text field
	 */
	public void setResultText(String expression) {
		resultText.setText(expression);
	}
	
	/**
	 * This method sets the result text field to an expression that will come from the string
	 * parameter. The integer parameter will be used to set the result instance variable so
	 * that it could be changed.
	 * @param expression a string that will be posted on the result text field
	 * @param result an integer value containing the calculated value of expression
	 */
	public void setResultText(String expression, int result) {
		this.result = result;
		resultText.setText(expression);
	}
	
	/**
	 * This method adds the result text field to a HBox so that it could be added to the
	 * border pane of the calculator application
	 * @return HBox that contains the result text field
	 */
	public HBox resultText() {
		HBox answerText = new HBox();
		resultText.setMaxSize(600, 600);
		answerText.getChildren().addAll(resultText);
		return answerText;
	}
	
	/**
	 * This method returns a HBox which will contain the 32-bit text from the most significant
	 * to the least significant order. Also the HBox will contain a S which will be respond to
	 * the sign of the result in binary, and there will be also be a separator between each 8
	 * bit since each byte contains 8-bit.  
	 * @return HBox that contains the 32-bit text with a mouse click action
	 */
	public HBox displayingBits() {
		//Sets all the bit to have a mouse click action
		this.textBitsAction();
		HBox bitToggle = new HBox();
		int textBitsSize = 30;
		Text signBit = new Text("S");
		//This loop will set each text index at even indexes of the HBox, and it will
		//add a blank space at odd indexes of the HBox to make it user friendly application
		for(int i = 0; i <= 68; i++) {
			if(i == 0)
				bitToggle.getChildren().add(signBit);
			//At these specific indexes a separator will be added for separating each byte
			else if(i == 16 || i == 34 || i == 52) {
				Separator separateOnce = new Separator();
				separateOnce.setOrientation(Orientation.VERTICAL);
				bitToggle.getChildren().add(separateOnce);
			}
			else if(i % 2 != 0) {
				bitToggle.getChildren().add(new Text(" "));
			}
			//Text bits are added at even indexes from most significant to least significant
			else {
				bitToggle.getChildren().add(textBits[textBitsSize]);
				textBitsSize--;
			}
		}
		return bitToggle;
	}
	
	/**
	 * This method will display the current bits by storing it in a HBox which will be used
	 * in the CalcApp for the window menuBar so that when the bit toggle is hidden or shown
	 * the state of bit toggle is not set to zero instead it corresponds to the current
	 * result of the string expression
	 * @return HBox that contains the 32-bit from most significant to least significant
	 */
	public HBox displayingCurrentBits() {
		HBox bitToggle = new HBox();
		Text signBit = new Text("S");
		int textBitsSize = 30;
		//This loop will set each text index at even indexes of the HBox, and it will
		//add a blank space at odd indexes of the HBox to make it user friendly application
		for(int i = 0; i <= 68; i++) {
			if(i == 0)
				bitToggle.getChildren().add(signBit);
			//At these specific indexes a separator will be added for separating each byte
			else if(i == 16 || i == 34 || i == 52) {
				Separator separateOnce = new Separator();
				separateOnce.setOrientation(Orientation.VERTICAL);
				bitToggle.getChildren().add(separateOnce);
			}
			else if(i % 2 != 0) {
				bitToggle.getChildren().add(new Text(" "));
			}
			//Text bits are added at even indexes from most significant to least significant
			else {
				bitToggle.getChildren().add(textBits[textBitsSize]);
				textBitsSize--;
			}
		}
		return bitToggle;
	}
	
	/**
	 * This private method will return an integer which will represent it as a final value
	 * so that it could be added in the mouse click action as final int value
	 * @param num
	 * @return
	 */
	private int finalValue(int num) {
		int value = num;
		return value;
	}
	
	/**
	 * This method will determine the binary representation of the result calculated from the
	 * calculator expression and when the binary representation is determined that the text
	 * bit will be changed to 1 and others will stay as 0 and then it will added to the 
	 * HBox that contains the binary represented bits
	 * @param result an integer that contains the calculated value of the expression
	 */
	public void resultBits(int result) {
		this.clearingBitToggle();
		int index = result;
		//If the result is greater than the index 30 then the binary value will start from
		//2 to 30 and so on
		if(result > 30) {
			index = 30;
		}
		//This loop will change the text to 1 and subtract that power of 2 from the result
		//if it is less than the result value. And binary value is greater than result
		//than it will keep it zero and skip that loop
		while(index >= 0){
			int binaryVal = operator.pow(2, index);
			if(binaryVal > result) {
				index--;
				continue;
			}
			else if(binaryVal <= result) {
				textBits[index].setText("1");
				result = operator.sub(result, binaryVal);
			}
			index--;
		}
	}
	
	/**
	 * This method will clear the bit toggle to make the 32-bit text to be all zero when 
	 * there clear is pressed or there is no expression.
	 */
	public void clearingBitToggle() {
		//This loop will go through the text array and set each text to 0
		for(int index = 0; index < textBits.length; index++) {
			textBits[index].setText("0");
		}
	}
	
}
