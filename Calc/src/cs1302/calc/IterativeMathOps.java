package cs1302.calc;

/**
 * This class contains the math operation that will be used for the CalcApp class
 * however, these operators are using iterative methods, loops, in order to solve
 * the operation. The operations include addition, subtraction, multiplication, division
 * factorial, exponent, left-shift, and right-shift.
 * 
 * @author Vraj Pragnesh Patel
 *
 */
public class IterativeMathOps implements MathOps{
	 /**
     * Returns the result of the addition operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int add(int lhs, int rhs) {
    	//This loop will iterate until rhs is zero while continuously increasing the lhs
    	while(rhs != 0) {
    		lhs = inc(lhs);
    		rhs = dec(rhs);
    	}
    	return lhs;
    }

    /**
     * Returns the result of the subtraction operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int sub(int lhs, int rhs) {
    	//If rhs is greater than lhs than zero will be returned instead of negative number
    	if(rhs > lhs)
    		return 0;
    	//This loop will iterate until rhs is zero while decreaing the lhs by one
    	while(rhs != 0) {
    		lhs = dec(lhs);
    		rhs = dec(rhs);
    	}
    	return lhs;
    }

    /**
     * Returns the result of the binary multiplication.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int mul(int lhs, int rhs) {
    	int sum = 0;
    	//This loop will iterate until rhs is equal to zero and it will then add lhs to
    	//a variable called sum because multiplication is addition at the end
    	while(rhs != 0) {
    		sum = add(sum, lhs);
    		rhs = dec(rhs);
    	}
    	return sum;
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
    	//If it is divided by zero then an exception will be thrown
    	if(rhs == 0)
    		throw new ArithmeticException("Sorry Can't divide by zero");
    	else {
    		int count = 0;
    		//The loop will iterate until finally the lhs is greater than rhs and until
    		//then count will increase with each iteration which represents the quotient
    		while(lhs >= rhs) {
    			count = inc(count);
    			lhs = sub(lhs, rhs);
    		}
    		return count;
    	}
    }

    /**
     * Returns the result of the factorial operation.
     *
     * @param num the operand
     * @return the result of the operation
     */
    public int fac(int num) {
    	int multiple = 1;
    	//This loop will iterate until num is equal to zero and with each iteration the num
    	//will be multipled by the number less than it
    	while(num != 0) {
    		multiple = mul(multiple, num);
    		num = dec(num);
    	}
    	return multiple;
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
    	int multiple = 1;
    	//This loop will iterate until rhs is zero and until that it will multiple lhs 
    	//because power is just multiplication 
    	while(rhs != 0) {
    		multiple = mul(multiple, lhs);
    		rhs = dec(rhs);
    	}
    	return multiple;
    }

    /**
     * Returns the result of the left-shift operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int lshift(int lhs, int rhs) {
    	//This loop will iterate until rhs is equal to zero, and with each iteration
    	//the lhs will be multipled by the lhs raised to the power of 2
    	while(rhs != 0) {
    		lhs = mul(lhs, 2);
    		rhs = dec(rhs);
    	}
    	return lhs;
    }

    /**
     * Returns the result of the right-shift operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int rshift(int lhs, int rhs) {
    	rhs = pow(2,rhs);
    	int count = 0;
    	//This loop will iterate until rhs is less than lhs and with each iteration count
    	//will increase which represents the final answer while lhs is being subtracted.
    	while(lhs >= rhs) {
    		count = inc(count);
    		lhs = sub(lhs, rhs);
    	}
    	return count;
    }
}
