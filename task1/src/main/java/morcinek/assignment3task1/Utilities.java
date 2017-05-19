package morcinek.assignment3task1;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import java.util.stream.IntStream;

/**
 *
 * @author Pavel Morcinek (433491@mail.muni.cz)
 */
public final class Utilities {

    private Utilities() {
    }

    public static boolean isValidToken(DetailAST ast, int[] tokens) {
        return IntStream.of(tokens).anyMatch(x -> x == ast.getType());
    }
}
