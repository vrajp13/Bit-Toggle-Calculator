package cs1302.calc;

/**
 * This class contains the math operation that will be used for the CalcApp class
 * however, these operators are using pure-tail recursive methods in order to solve
 * the operation. The operations include addition, subtraction, multiplication, division
 * factorial, exponent, left-shift, and right-shift.
 * 
 * @author Vraj Pragnesh Patel
 *
 */
public class RecursiveMathOps implements MathOps{

	 /**
     * Returns the result of the addition operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int add(int lhs, int rhs) {
    	if(rhs == 0) 
    		return lhs;
    	//The method calls itself for the recursive base until it reaches the base case
    	//which is return the lhs which increased with each recursive call
    	return add(inc(lhs), dec(rhs));
    }

    /**
     * Returns the result of the subtraction operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int sub(int lhs, int rhs) {
    	//If the rhs is greater than lhs then it will return 0 instead of negative value
    	if(rhs >= lhs)
    		return 0;
    	if(rhs == 0)
    		return lhs;
    	//The method calls itself for the recursive base until it reaches the base case
    	//which will return lhs which decreaased with each recursive call
    	return sub(dec(lhs), dec(rhs));
    }

    /**
     * Returns the result of the binary multiplication.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int mul(int lhs, int rhs) {
    	//Calls a private method that will accomplish the task of recursively multiplying
    	return mul_accum(0, lhs, rhs);
	}
    
    /**
     * This private method will multiply using a pure-tail recursion
     * @param sum the result of the operation
     * @param lhs the first operand
     * @param rhs the second operand
     * @return integer which is the result of the operation
     */
	private int mul_accum(int sum, int lhs, int rhs) {
		if(rhs == 0)
			return sum;
		//The method calls itself for the recursive base until it reaches the base case
		//which will return the sum which is added with the lhs will rhs decreases by 1
		return mul_accum(add(sum, lhs), lhs, dec(rhs));
	}

    /**
     * Returns the result of the division operation.
     * <code>lhs / rhs</code>.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     * @throws ArithmeticException if the second operand is equal to zero.
     */
    public int div(int lhs, int rhs) throws ArithmeticException{
    	//If the second operand then it will throw an exception
    	if(rhs == 0) 
    		throw new ArithmeticException("Sorry Can't divide by zero");
    	//Calls a private method that will return the answer to the operation
    	else
    		return div_accum(0, lhs, rhs);	
    }
    
    /**
     * This private method will divide using a pure-tail recursion
     * @param count the result of the division
     * @param lhs the first operand
     * @param rhs the second operand
     * @return integer which is the result of the division
     */
    private int div_accum(int count, int lhs, int rhs) {
    	if(lhs < rhs)
    		return count;
    	//The method calls itself for the recursive base until it reaches the base case
    	//which will return the increased value of count with each subtraction of lhs
    	//with the rhs
    	return div_accum(inc(count), sub(lhs,rhs), rhs);
    }

    /**
     * Returns the result of the factorial operation.
     *
     * @param num the operand
     * @return the result of the operation
     */
    public int fac(int num) {
    	//Calls the fac_accum to return the answer to the factorial
    	return fac_accum(1,num);
    }
    
    /**
     * This private method calculates the factorial of the num using pure tail recursion
     * @param multiple integer which will store the result of the factorial
     * @param num the operand
     * @return integer which is the result of factorial
     */
    private int fac_accum(int multiple, int num) {
    	if(num == 0)
    		return multiple;
    	//The method calls itself for the recursive base until it reaches the base case
    	//which will return the multiplication of the multiple variable and num while
    	//decreeasing the num by one
    	return fac_accum(mul(multiple,num), dec(num));
    }

    /**
     * Returns the result of the exponentiation operation 
     * <code>lhs ^^ rhs</code>. 
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int pow(int lhs, int rhs) {
    	//Calls a private method to return the answer of the power operation
    	return pow_accum(1, lhs, rhs);
    }
    
    /**
     * A private method that returns the result of the exponentiation operation
     * @param multiple stores the answer for the operation
     * @param lhs the first operand
     * @param rhs the second operand
     * @return integer which is the result of the operation
     */
    private int pow_accum(int multiple, int lhs, int rhs) {
    	if(rhs == 0)
    		return multiple;
    	//The method calls itself for the recursive base until it reaches the base
    	//case which will return the repeated multiplication of lhs until rhs is 0
    	return pow_accum(mul(multiple,lhs), lhs, dec(rhs));
    }
    /**
     * Returns the result of the left-shift operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int lshift(int lhs, int rhs) {
    	if(rhs == 0)
    		return lhs;
    	//The method calls itself for the recursive base until it reaches the base case
    	//which will return the multiplication of lhs with 2 until rhs reaches 0
    	return lshift(mul(lhs, 2), dec(rhs));
    }

    /**
     * Returns the result of the right-shift operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int rshift(int lhs, int rhs) {
    	//Calls a private method that will return the answer to the rshift
    	return rshift_accum(0, lhs, pow(2,rhs));
    }
    
    /**
     * This method returns the result of the right-shift operator using pure-tail recursion
     * @param count stores answer to the right-shift operator
     * @param lhs the first operand
     * @param binaryBase power of 2 raised to the second operand
     * @return integer which is the result of the right shift operation
     */
    private int rshift_accum(int count, int lhs, int binaryBase) {
    	if(lhs < binaryBase)
    		return count;
    	//The method calls itself for the recursive base until the base case
    	//is reached where the count will increase by 1 until the lhs is less than
    	//binaryBase, where lhs subtracted by it everytime
    	return rshift_accum(inc(count), sub(lhs, binaryBase), binaryBase);
    }


}
