package cs1302.calc;

/** 
 * Provides an interface for different mathematical operations
 * 
 * @author Michael E. Cotterell
 */
public interface MathOps {

    /**
     * Returns the successor of the operand.
     *
     * @param n the operand
     * @return the result of n + 1
     */
    public default int succ(int n) {
	return n + 1;
    } // succ

    /**
     * Returns the predeccessor of the operand.
     *
     * @param n the operand
     * @return the result of n - 1
     */
    public default int pred(int n) {
	return n - 1;
    } // pred
    
    /**
     * Returns the result of incrementing the operand via the successor method.
     *
     * @param n the operand
     * @return the result of <code>succ(n)</code>
     */
    public default int inc(int n) {
	return succ(n);
    } // inc

    /**
     * Returns the result of deccrementing the operand via the predeccessor
     * method.
     *
     * @param n the operand
     * @return the result of <code>pred(n)</code>
     */
    public default int dec(int n) {
	return pred(n);
    } // dec

    /**
     * Returns the result of the addition operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int add(int lhs, int rhs);

    /**
     * Returns the result of the subtraction operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int sub(int lhs, int rhs);

    /**
     * Returns the result of the binary multiplication.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int mul(int lhs, int rhs);

    /**
     * Returns the result of the division operation.
     * <code>lhs / rhs</code>.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     * @throws ArithmeticException if the second operand is equal to zero.
     */
    public int div(int lhs, int rhs) throws ArithmeticException;

    /**
     * Returns the result of the factorial operation.
     *
     * @param num the operand
     * @return the result of the operation
     */
    public int fac(int num);

    /**
     * Returns the result of the exponentiation operation 
     * <code>lhs ^^ rhs</code>. 
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int pow(int lhs, int rhs);

    /**
     * Returns the result of the left-shift operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int lshift(int lhs, int rhs);

    /**
     * Returns the result of the right-shift operation.
     *
     * @param lhs the first operand
     * @param rhs the second operand
     * @return the result of the operation
     */
    public int rshift(int lhs, int rhs);


} // MathOps