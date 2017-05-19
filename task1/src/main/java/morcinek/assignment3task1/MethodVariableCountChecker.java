package morcinek.assignment3task1;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.stream.IntStream;

/**
 *
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public class MethodVariableCountChecker {
    
    private static final int DEFAULT_VARIABLE_COUNT_MAX = 8;
    // <editor-fold defaultstate="collapsed" desc="TOKENS">
    private static final int[] DEFAULT_TOKENS = {
        TokenTypes.METHOD_DEF,
        TokenTypes.VARIABLE_DEF
    };
    // </editor-fold>
    
    private final AbstractCheck parentCheck;
    private int variableCountMax = DEFAULT_VARIABLE_COUNT_MAX;
    private int currentMethodVariableCount = 0;
    
    public MethodVariableCountChecker(AbstractCheck parentCheck) {
        this.parentCheck = parentCheck;
    }
      
    public int[] getDefaultTokens() {
        return DEFAULT_TOKENS;
    }
    
    public void visitToken(DetailAST ast) {
        if(!Utilities.isValidToken(ast, DEFAULT_TOKENS)) {
            return;
        }
        
        if(ast.getType() == TokenTypes.VARIABLE_DEF) {
            ++currentMethodVariableCount;
//            parentCheck.log(ast.getLineNo(), String.valueOf(currentMethodVariableCount));
        }
    }
    
    public void leaveToken(DetailAST ast) {
        if(!Utilities.isValidToken(ast, DEFAULT_TOKENS)) {
            return;
        }
        
        if(ast.getType() == TokenTypes.METHOD_DEF) {
            if(currentMethodVariableCount > variableCountMax) {
                parentCheck.log(ast.getLineNo(), getMethodTooManyVariablesMessage());
                currentMethodVariableCount = 0;
            }
        }
    }
    
    public void setVariableCountMax(int variableCountMax) {
        this.variableCountMax = variableCountMax;
    }
    
    private String getMethodTooManyVariablesMessage() {
        return "Method variable count (" +
                currentMethodVariableCount +
                ") is higher than the maximum method variable count allowed (" +
                variableCountMax +
                ") ";
    }
}
