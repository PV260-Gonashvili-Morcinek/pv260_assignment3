package morcinek.assignment3task1;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.HashSet;
import java.util.Set;

/**
 * check for detecting Brain Method Identity Disharmony
 * delegates operations to checker classes which it holds reference to
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public class BrainMethodIdentityDisharmonyCheck extends AbstractCheck {

    private final MethodLengthChecker lengthChecker = new MethodLengthChecker(this);
    private final MethodCyclomaticComplexityChecker complexityChecker = new MethodCyclomaticComplexityChecker(this);
    private final MethodNestingLevelChecker nestingLevelChecker = new MethodNestingLevelChecker(this);
    private final MethodVariableCountChecker variableCountChecker = new MethodVariableCountChecker(this);
    
    private final Set<Integer> defaultTokens = new HashSet<>();
    private final Set<Integer> acceptableTokens = new HashSet<>();
    private final Set<Integer> requiredTokens = new HashSet<>();

    public BrainMethodIdentityDisharmonyCheck() {      
        initializeDefaultTokens();
        initializeAcceptableTokens();
        initializeRequiredTokens();
    } 
    
    @Override
    public int[] getDefaultTokens() {
        return defaultTokens.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public int[] getAcceptableTokens() {
        return acceptableTokens.stream().mapToInt(i -> i).toArray();
    }
    
    @Override
    public int[] getRequiredTokens() {
        return requiredTokens.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void visitToken(DetailAST ast) {
        lengthChecker.visitToken(ast);
        complexityChecker.visitToken(ast);
        nestingLevelChecker.visitToken(ast);
        variableCountChecker.visitToken(ast);
    }

    @Override
    public void leaveToken(DetailAST ast) {
        complexityChecker.leaveToken(ast);
        nestingLevelChecker.leaveToken(ast);
        variableCountChecker.leaveToken(ast);
    }
    
    /**
     * @param lengthMax the maximum length of a method.
     */
    public void setLengthMax(int lengthMax) {
        lengthChecker.setLengthMax(lengthMax);
    }

    /**
     * @param doCountEmpty whether to count empty and single line comments
     *     of the form //.
     */
    public void setDoCountEmpty(boolean doCountEmpty) {
        lengthChecker.setDoCountEmpty(doCountEmpty);
    }
    
    public void setCyclomaticComplexityMax(int cyclomaticComplexityMax){
        complexityChecker.setCyclomaticComplexityMax(cyclomaticComplexityMax);
    }
    
    /**
     * Sets whether to treat the whole switch block as a single decision point.
     * @param switchBlockAsSingleDecisionPoint whether to treat the whole switch
     *                                          block as a single decision point.
     */
    public void setSwitchBlockAsSingleDecisionPoint(boolean switchBlockAsSingleDecisionPoint) {
        complexityChecker.setSwitchBlockAsSingleDecisionPoint(switchBlockAsSingleDecisionPoint);
    }
    
    public void setNestingLevelMax(int nestingLevelMax) {
        nestingLevelChecker.setNestingLevelMax(nestingLevelMax);
    }
    
    public void setVariableCountMax(int variableCountMax) {
       variableCountChecker.setVariableCountMax(variableCountMax);
    }
    
    private void initializeDefaultTokens() {
        for (int token : lengthChecker.getDefaultTokens()) {
            defaultTokens.add(token);
        }
        for (int token : complexityChecker.getDefaultTokens()) {
            defaultTokens.add(token);
        }
        for (int token : nestingLevelChecker.getDefaultTokens()) {
            defaultTokens.add(token);
        }
        for (int token : variableCountChecker.getDefaultTokens()) {
            defaultTokens.add(token);
        }
    }
    
    private void initializeAcceptableTokens() {
        for (int token : lengthChecker.getAcceptableTokens()) {
            acceptableTokens.add(token);
        }
        for (int token : complexityChecker.getAcceptableTokens()) {
            acceptableTokens.add(token);
        }
    }
    
    private void initializeRequiredTokens() {
        for (int token : lengthChecker.getRequiredTokens()) {
            requiredTokens.add(token);
        }
        for (int token: complexityChecker.getRequiredTokens()) {
            requiredTokens.add(token);
        }
    }
   
}
