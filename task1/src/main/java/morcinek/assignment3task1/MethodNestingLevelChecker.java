package morcinek.assignment3task1;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 *
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public class MethodNestingLevelChecker {
    
    private static final int DEFAULT_NESTING_LEVEL_MAX = 3;
    // <editor-fold defaultstate="collapsed" desc="TOKENS">
    private static final int[] DEFAULT_TOKENS = {
        TokenTypes.SLIST
    };
    // </editor-fold>
    
    private final AbstractCheck parentCheck;
    private int nestingLevelMax = DEFAULT_NESTING_LEVEL_MAX;
    private int currentMethodNestingLevel = 0;
    private int currentMethodNestingLevelMax = 0;
    
    public MethodNestingLevelChecker(AbstractCheck parentCheck) {
        this.parentCheck = parentCheck;
    }
      
    public int[] getDefaultTokens() {
        return DEFAULT_TOKENS;
    }
    
    public void visitToken(DetailAST ast) {
        if(!Utilities.isValidToken(ast, DEFAULT_TOKENS)) {
            return;
        }
        
        ++currentMethodNestingLevel;
        if(currentMethodNestingLevel > currentMethodNestingLevelMax) {
            currentMethodNestingLevelMax = currentMethodNestingLevel;
        }
    }
    
    public void leaveToken(DetailAST ast) {
        if(!Utilities.isValidToken(ast, DEFAULT_TOKENS)) {
            return;
        }
        
        --currentMethodNestingLevel;
        
        if (isAtTheEndOfMethod()) {
            if (currentMethodNestingLevelMax > nestingLevelMax) {
                parentCheck.log(ast.getLineNo(), getMethodTooNestedMessage(nestingLevelMax));
                currentMethodNestingLevelMax = 0;
            }
        }
    }
    
    public void setNestingLevelMax(int nestingLevelMax) {
        this.nestingLevelMax = nestingLevelMax;
    }
    
    private String getMethodTooNestedMessage(int methodNestedLevel) {
        return "Method nesting level (" +
                currentMethodNestingLevelMax +
                ") is higher than the maximum nesting level allowed (" +
                nestingLevelMax +
                ") ";
    }
    
    private boolean isAtTheEndOfMethod() {
        return currentMethodNestingLevel == 0;
    }
}
