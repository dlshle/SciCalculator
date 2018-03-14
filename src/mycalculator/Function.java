package mycalculator;

import java.util.Arrays;

/**
 * Function class of the calculator Not yet finished.
 *
 * @author dlshle(Xuri Li)
 */
public class Function {

    private String lines[];

    public Function(String[] lines, double[] params) {
        launch(lines[0]);
        this.lines = Arrays.copyOfRange(lines, 1, lines.length);
    }

    public void init(String header, double[] params) {

    }

    public String launch(String line) {
        return "";
    }

}
