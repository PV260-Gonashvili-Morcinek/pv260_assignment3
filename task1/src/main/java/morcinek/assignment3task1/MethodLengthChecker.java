package morcinek.assignment3task1;

import com.puppycrawl.tools.checkstyle.api.*;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import java.util.stream.IntStream;

/**
 * checks for too long methods
 * uses it's parent class to communicate with checkstyle's API
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public class MethodLengthChecker {

    private static final int DEFAULT_LENGTH_MAX = 100;
    // <editor-fold defaultstate="collapsed" desc="TOKENS">
    private static final int[] DEFAULT_TOKENS = {
        TokenTypes.METHOD_DEF,
        TokenTypes.CTOR_DEF
    };
    // </editor-fold>
    
    private final AbstractCheck parentCheck;
    private int lengthMax = DEFAULT_LENGTH_MAX;
    private boolean doCountEmpty = true;
    

    public MethodLengthChecker(AbstractCheck parentCheck) {
        this.parentCheck = parentCheck;
    }
      
    public int[] getDefaultTokens() {
        return DEFAULT_TOKENS;
    }
    
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }
    
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }
    
    public void visitToken(DetailAST ast) {
        if(!Utilities.isValidToken(ast, DEFAULT_TOKENS)) {
            return;
        }
        
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);
         
        if (openingBrace != null) {
            final DetailAST closingBrace =
                    openingBrace.findFirstToken(TokenTypes.RCURLY);
            final int length = getLengthOfBlock(openingBrace, closingBrace);
            if (length > lengthMax) {
                parentCheck.log(ast.getLineNo(), ast.getColumnNo(), getMethodTooLongMessage(length),
                        length, lengthMax);
            }
        }
    }
    
    public void setLengthMax(int lengthMax) {
        this.lengthMax = lengthMax;
    }

    public void setDoCountEmpty(boolean doCountEmpty) {
        this.doCountEmpty = doCountEmpty;
    }
     
     /**
     * Returns length of code only without comments and blank lines.
     * @param openingBrace block opening brace
     * @param closingBrace block closing brace
     * @return number of lines with code for current block
     */
    private int getLengthOfBlock(DetailAST openingBrace, DetailAST closingBrace) {
        int length = closingBrace.getLineNo() - openingBrace.getLineNo() + 1;

        if (!doCountEmpty) {
            final FileContents contents = parentCheck.getFileContents();
            final int lastLine = closingBrace.getLineNo();
            for (int i = openingBrace.getLineNo() - 1; i < lastLine; i++) {
                if (contents.lineIsBlank(i) || contents.lineIsComment(i)) {
                    length--;
                }
            }
        }
        return length;
    }
    
    private String getMethodTooLongMessage(int methodLength) {
        return "Method length (" +
                methodLength +
                ") is longer than maximum number of lines allowed (" +
                lengthMax +
                ") ";
    }
}
