package morcinek.assignment3task1;

import com.puppycrawl.tools.checkstyle.api.*;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;

/**
 * checks for too complex methods
 * uses it's parent class to communicate with checkstyle's API
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public class MethodCyclomaticComplexityChecker {

    private static final int DEFAULT_CYCLOMATIC_COMPLEXITY_MAX = 10;
    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;
    // <editor-fold defaultstate="collapsed" desc="TOKENS">
    private static final int[] DEFAULT_TOKENS = {
        TokenTypes.CTOR_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.INSTANCE_INIT,
        TokenTypes.STATIC_INIT,
        TokenTypes.LITERAL_WHILE,
        TokenTypes.LITERAL_DO,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_SWITCH,
        TokenTypes.LITERAL_CASE,
        TokenTypes.LITERAL_CATCH,
        TokenTypes.QUESTION,
        TokenTypes.LAND,
        TokenTypes.LOR
    };
    private static final int[] ACCEPTABLE_TOKENS = {
      TokenTypes.CTOR_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.INSTANCE_INIT,
        TokenTypes.STATIC_INIT,
        TokenTypes.LITERAL_WHILE,
        TokenTypes.LITERAL_DO,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_SWITCH,
        TokenTypes.LITERAL_CASE,
        TokenTypes.LITERAL_CATCH,
        TokenTypes.QUESTION,
        TokenTypes.LAND,
        TokenTypes.LOR 
    };
    private static final int[] REQUIRED_TOKENS = {
      TokenTypes.CTOR_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.INSTANCE_INIT,
        TokenTypes.STATIC_INIT 
    };
    // </editor-fold>
    
    private final AbstractCheck parentCheck;
    private final Deque<BigInteger> valueStack = new ArrayDeque<>();
    
    private int cyclomaticComplexityMax = DEFAULT_CYCLOMATIC_COMPLEXITY_MAX;
    /** Stack of values - all but the current value. */
    private boolean switchBlockAsSingleDecisionPoint;
    private BigInteger currentValue = INITIAL_VALUE;


    public MethodCyclomaticComplexityChecker(AbstractCheck parentCheck) {
        this.parentCheck = parentCheck;
    }
    
    public int[] getDefaultTokens() {
        return DEFAULT_TOKENS;
    }
    
    public int[] getAcceptableTokens() {
        return ACCEPTABLE_TOKENS;
    }
    
    public int[] getRequiredTokens() {
        return REQUIRED_TOKENS;
    }
    
    public void visitToken(DetailAST ast) {
        if(!Utilities.isValidToken(ast, DEFAULT_TOKENS)) {
            return;
        }
        
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                visitMethodDef();
                break;
            default:
                visitTokenHook(ast);
        }
    }
    
    public void leaveToken(DetailAST ast) {
        if(!Utilities.isValidToken(ast, DEFAULT_TOKENS)) {
            return;
        }
        
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                leaveMethodDef(ast);
                break;
            default:
                break;
        }
    }
    
    public void setCyclomaticComplexityMax(int cyclomaticComplexityMax) {
        this.cyclomaticComplexityMax = cyclomaticComplexityMax;
    }
    
    /**
     * Sets whether to treat the whole switch block as a single decision point.
     * @param switchBlockAsSingleDecisionPoint whether to treat the whole switch
     *                                          block as a single decision point.
     */
    public void setSwitchBlockAsSingleDecisionPoint(boolean switchBlockAsSingleDecisionPoint) {
        this.switchBlockAsSingleDecisionPoint = switchBlockAsSingleDecisionPoint;
    }
    
    /**
     * Hook called when visiting a token. Will not be called the method
     * definition tokens.
     *
     * @param ast the token being visited
     */
    private void visitTokenHook(DetailAST ast) {
        if (switchBlockAsSingleDecisionPoint) {
            if (ast.getType() != TokenTypes.LITERAL_CASE) {
                incrementCurrentValue(BigInteger.ONE);
            }
        }
        else if (ast.getType() != TokenTypes.LITERAL_SWITCH) {
            incrementCurrentValue(BigInteger.ONE);
        }
    }
    
    /**
     * Process the end of a method definition.
     *
     * @param ast the token representing the method definition
     */
    private void leaveMethodDef(DetailAST ast) {
        final BigInteger bigIntegerMax = BigInteger.valueOf(cyclomaticComplexityMax);
        if (currentValue.compareTo(bigIntegerMax) > 0) {
            parentCheck.log(ast.getLineNo(), getTooComplexMethodMessage(), currentValue, bigIntegerMax);
        }
        popValue();
    }
    
    /**
     * Increments the current value by a specified amount.
     *
     * @param amount the amount to increment by
     */
    private void incrementCurrentValue(BigInteger amount) {
        currentValue = currentValue.add(amount);
    }
    
    /** Push the current value on the stack. */
    private void pushValue() {
        valueStack.push(currentValue);
        currentValue = INITIAL_VALUE;
    }
    
    /**
     * Pops a value off the stack and makes it the current value.
     * @return pop a value off the stack and make it the current value
     */
    private BigInteger popValue() {
        currentValue = valueStack.pop();
        return currentValue;
    }
    
    /** Process the start of the method definition. */
    private void visitMethodDef() {
        pushValue();
    }
    
    private String getTooComplexMethodMessage() {
        return "Method cyclomatic complexity value (" +
                currentValue +
                ") is higher than maximum cyclomatic complexity value (" +
                cyclomaticComplexityMax +
                ") ";
    }
}
