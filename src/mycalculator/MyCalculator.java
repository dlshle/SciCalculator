package mycalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * The main class(driver class) of the calculator Ver.01: can do basic number
 * calculations.
 *
 * @author dlshle(Xuri Li)
 */
public class MyCalculator {

    /*
    xx=calculating expression assign
    marco: using t_reg to store numbers, format: function(num1, num2, num3...)=exp
     */
    public static double[] reg = new double[100];
    public static byte reg_counter = 0;
    public static HashMap<String, Double> var_map = new HashMap<>();
    public static HashMap<String, Function> func_map = new HashMap<>();
    public static HashMap<String, Double> t_reg = new HashMap<>();
    public static HashMap<String, Matrix> m_map = new HashMap<>();
    public static HashSet<String> operators = new HashSet<>();

    //public static String regex_assign = "[A-Za-z]+[0-9]*\\s*=\\s*[0-9]*.?[0-9]+";
    //(abc123=123.456) var name has to be begin with a char end with a char or a num, not num in the middle
    public static String regex_reg = "[R|r][0-9]{1,2}";
    public static String regex_complex_assign = "[A-Za-z]+_?[0-9]*[A-Za-z]*\\s*=\\s*.+";
    public static String regex_func = "[A-Za-z]+_?[0-9]*[A-Za-z]*\\s*\\((\\s*.+\\s*,?)+\\)";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Xuri's calculator:\n"
                + "Input expression and press enter to compute."
                + "\nInput stop to exit the program."
                + "\nTo input a negative number, use \"_\" as the negative sign.");

        String exp = input.nextLine();

        init();

        while (!exp.toLowerCase().trim().equals("stop")) {
            if (exp.equals("list reg")) {
                printREG();
            } else {
                System.out.println(evaluateExpression(exp));
            }
            System.out.println("Please input a new expression or 'stop' to stop");
            exp = input.nextLine();
        }
    }

    public static void init() {
        var_map.put("pi", Math.PI);
        var_map.put("e", Math.E);
    }

    public static String evaluateExpression(String exp) {
        exp = exp.trim();
        if (exp.matches(regex_complex_assign)) {
            return complexAssign(exp);
        } else if (exp.startsWith("list ")) {
            return listOperation(exp.substring(exp.indexOf(' ') + 1));
        } else if (exp.startsWith("post ")) {
            return postFixCore(exp);
        } else if (exp.equals("matrix test")) {
            testClass.matrixTest(null);
            return "Test finished...";
        } else {
            return computExpression(exp);
        }
    }

    public static String listOperation(String exp) {
        exp = exp.trim();

        switch (exp) {
            case "reg":
                return getREG() + "listing finished";
            case "var":
                return getVAR() + "listing finished.";
            default:
                return "Unknown operation.";
        }
    }

    public static String assignMatrix(String key, String exp) {
        //matrix assign
        ArrayList<ArrayList<String>> resultGroups = captureGroups(exp, '[', ']');
        if (!Matrix.isRightGroups(resultGroups)) {
            return "Syntax error";
        }
        double[][] rows = new double[resultGroups.get(0).size()][];
        //parse numbers
        for (int i = 0; i < resultGroups.get(0).size(); i++) {
            String group = resultGroups.get(0).get(i).trim();
            group = group.substring(1, group.length() - 1);
            rows[i] = parseStringsToDoubles(group.split(","));
            if (rows[i] == null) {
                return "Syntax error";
            }
        }
        m_map.put(key, new Matrix(rows));
        return "Matrix " + key + " = \n" + m_map.get(key).toString();
    }

    public static String complexAssign(String exp) {
        int index = exp.indexOf('=');

        String key = exp.substring(0, index).trim();

        if (!isValidKey(key))
            return "Incorrect key format!";
        
        //checking for operators
        for(int i=0;i<key.length();i++){
            if(isOperator(key.charAt(i)))
                return "Key contains illegal characters.";
        }

        String svalue = exp.substring(index + 1, exp.length()).trim();
        if (svalue.startsWith("[") && svalue.endsWith("]")) {
            return assignMatrix(key, svalue);
        }
        try {
            Double value = Double.valueOf(computExpression(svalue));
            var_map.put(key, value);
            if (var_map.containsKey(key)) {
                return key + " is reassigned to " + value;
            } else {
                return key + " is assigned to " + value;
            }
        } catch (NumberFormatException ex) {
            return "Syntax error";
        }
    }

    public static boolean funcComplexAssign(String exp) {
        int index = exp.indexOf('=');

        String key = exp.substring(0, index).trim();

        if (isValidKey(key)) {
            return false;
        }

        String svalue = exp.substring(index + 1, exp.length()).toLowerCase();

        try {
            Double value = Double.valueOf(computExpression(svalue));
            t_reg.put(key, value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    public static String computeFunc(String exp) {
        //marco exp is like 
        //MARCO_NAME(var0,var1,var3,var4,var5)=var0+var1!+var2*var3+var4.

        String function = exp.substring(0, exp.indexOf('(')).trim();

        if (!func_map.containsKey(function)) {
            return "undefined function";
        }

        String[] paras = exp.substring(exp.indexOf('(') + 1, exp.lastIndexOf(')')).split("//s*,//s*");

        try {

        } catch (NumberFormatException nfe) {
            return "Incorrect number format!";
        } catch (Exception e) {
            return "Syntax error!";
        }
        //parse paras to double 
        //get the marco by key
        //do the calculation
        //return val
        return "";
    }

    public static String computExpression(String exp) {
        if (exp.charAt(0) == ')') {
            exp = "(0)+" + exp.substring(1);
            computExpression(exp);
        }

        //see if the first operator is +-*/%
        char first = exp.charAt(0);

        if (isOperator(first)) {
            if (reg_counter > 0) {
                double lastVar = reg[reg_counter - 1];
                exp = (lastVar < 0 ? "_" + (0 - lastVar) : lastVar) + exp;//check if reg[reg_counter-1]<0
                System.out.println("\t-" + exp.substring(1));//has to show the natural expression
            } else {
                exp = "0" + exp;
            }
        }

        Stack<Double> operant = new Stack<>();

        Stack<Character> operator = new Stack<>();

        exp = insertSpace(exp);

        String[] tokens = exp.split("@");

        try {

            for (String s : tokens) {
                s = s.trim();
                if (s.length() == 0 || s.isEmpty()) {
                    continue;
                } else if (isOperator(s.trim().charAt(0))) {
                    //operators
                    char op = s.charAt(0);
                    switch (op) {
                        case '+':
                        case '-':
                            //*,/ first
                            while (!operator.isEmpty() && (operator.peek() == '*' || operator.peek() == '/' || operator.peek() == '+' || operator.peek() == '-' || operator.peek() == '%' || op == '^')) {
                                //do all the calculation before
                                processOperation(operant, operator);
                            }
                            operator.push(op);
                            break;
                        case '*':
                        case '/':
                        case '%':
                        case '^':
                            while (!operator.isEmpty() && (operator.peek() == '*' || operator.peek() == '/' || operator.peek() == '%' || op == '^')) {
                                //do calculations except for +,-
                                processOperation(operant, operator);
                            }
                            operator.push(op);
                            break;
                        case '!':
                            //factorial
                            double result = MyMath.factorial(operant.pop());
                            if(result==-1)
                                return "ERROR!\nParameter of a factorial should be positive number!";
                            operant.push(result);
                            break;
                        case '(':
                            operator.push(op);
                            break;
                        case ')':
                            while (operator.peek() != '(') {
                                processOperation(operant, operator);
                            }
                            operator.pop();
                            break;
                        default:
                            break;
                    }
                } else if (s.charAt(0) >= 65 && s.charAt(0) <= 90 || s.charAt(0) >= 97 && s.charAt(0) <= 122) {
                    //var operations
                    if (s.length() > 256) {
                        return "Unknown variable " + s;
                    }
                    //start with a letter
                    if (s.matches(regex_reg)) {
                        //using register
                        byte num = Byte.parseByte(s.substring(1));

                        if (num < reg_counter) {
                            operant.push(reg[num]);
                        } else {
                            return "Can not pull register[" + num + "].\nreg_counter = " + reg_counter;
                        }
                    } else if (var_map.containsKey(s)) {
                        operant.push(var_map.get(s));
                    } else if (t_reg.containsKey(s)) {
                        operant.push(t_reg.get(s));
                    } else {
                        //unknown variable
                        return "Unknown variable " + s;
                    }
                } else if (s.matches(regex_func)) {
                    //using marco

                } else {
                    //numbers
                    operant.push(new Double(s));
                }
            }

            while (!operator.isEmpty()) {
                processOperation(operant, operator);
            }

            //Now, operant.size() has to be 1(the answer).
            if (operator.isEmpty() && operant.size() == 1) {
                //"push" hte answer to the register
                double ans = operant.pop();

                //clear stacks
                operator = null;
                operant = null;

                if (reg_counter != 100) {
                    reg[reg_counter++] = ans;
                } else {
                    reg[0] = reg[99];
                    reg_counter = 1;
                    reg[reg_counter++] = ans;
                }
                return String.valueOf(ans);
            } else {
                return "Syntax error!";
            }
        } catch (NumberFormatException e) {
            return "Syntax error!\nIncorrect Number Format!";
        } catch (Exception ex) {
            return "Syntax error!";
        }
    }

    public static String funcComputExpression(String exp) {
        if (exp.charAt(0) == ')') {
            exp = "(" + exp.substring(1);
            computExpression(exp);
        }

        Stack<Double> operant = new Stack<>();

        Stack<Character> operator = new Stack<>();

        exp = insertSpace(exp);

        String[] tokens = exp.split("@");

        try {

            for (String s : tokens) {
                s = s.trim();
                if (s.length() == 0 || s.isEmpty()) {
                    continue;
                } else if (isOperator(s.trim().charAt(0))) {
                    //operators
                    char op = s.charAt(0);
                    switch (op) {
                        case '+':
                        case '-':
                            //*,/ first
                            while (!operator.isEmpty() && (operator.peek() == '*' || operator.peek() == '/' || operator.peek() == '+' || operator.peek() == '-' || operator.peek() == '%' || op == '^')) {
                                //do all the calculation before
                                processOperation(operant, operator);
                            }
                            operator.push(op);
                            break;
                        case '*':
                        case '/':
                        case '%':
                        case '^':
                            while (!operator.isEmpty() && (operator.peek() == '*' || operator.peek() == '/' || operator.peek() == '%' || op == '^')) {
                                //do calculations except for +,-
                                processOperation(operant, operator);
                            }
                            operator.push(op);
                            break;
                        case '!':
                            double result = MyMath.factorial(operant.pop());
                            if(result==-1)
                                return "ERROR!\nParameter of a factorial should be positive number!";
                            operant.push(result);
                            break;
                        case '(':
                            operator.push(op);
                            break;
                        case ')':
                            while (operator.peek() != '(') {
                                processOperation(operant, operator);
                            }
                            operator.pop();
                            break;
                        default:
                            break;
                    }
                } else if (s.charAt(0) >= 65 && s.charAt(0) <= 90 || s.charAt(0) >= 97 && s.charAt(0) <= 122) {
                    if (s.length() > 256) {
                        return "UV";
                    }

                    if (t_reg.containsKey(s)) {
                        operant.push(t_reg.get(s));
                    } else {
                        //unknown variable
                        return "UV";
                    }
                } else if (s.matches(regex_func)) {
                    //using marco

                } else {
                    //numbers
                    operant.push(new Double(s));
                }
            }

            while (!operator.isEmpty()) {
                processOperation(operant, operator);
            }

            //Now, operant.size() has to be 1(the answer).
            if (operator.isEmpty() && operant.size() == 1) {
                //"push" hte answer to the register
                double ans = operant.pop();

                //clear stacks
                operator = null;
                operant = null;

                return String.valueOf(ans);
            } else {
                return "Syntax Error!";
            }
        } catch (NumberFormatException e) {
            return "Syntax Error!\nIncorrect Number Format!";
        } catch (Exception ex) {
            return "Sysntex Error!";
        }
    }

    public static String postFixCore(String exp) {
        if (exp.contains("(") || exp.contains(")")) {
            return "No parenthesis is allowed in the PostFix mode";
        }

        Stack<Double> operants = new Stack<>();
        Stack<Character> operator = new Stack<>();

        exp = exp.substring(4);
        exp = exp.trim();

        exp = postInsert(exp);

        String[] token = exp.split("@+");

        try {
            for (String s : token) {
                s = s.trim();
                if (isOperator(s.charAt(0))) {
                    if(s.charAt(0)=='!'){
                            double result = MyMath.factorial(operants.pop());
                            if(result==-1)
                                return "ERROR!\nParameter of a factorial should be positive number!";
                            operants.push(result);
                        continue;
                    }
                    operator.push(s.charAt(0));
                    processOperation(operants, operator);
                } else if (s.charAt(0) >= 65 && s.charAt(0) <= 90 || s.charAt(0) >= 97 && s.charAt(0) <= 122) {
                    if (s.length() > 256) {
                        return "Unknown variable " + s;
                    }
                    //start with a letter
                    if (s.matches(regex_reg)) {
                        //using register
                        byte num = Byte.parseByte(s.substring(1));

                        if (num < reg_counter) {
                            operants.push(reg[num]);
                        } else {
                            return "Can not pull register[" + num + "].\nreg_counter = " + reg_counter;
                        }
                    } else if (var_map.containsKey(s)) {
                        operants.push(var_map.get(s));
                    } else if (t_reg.containsKey(s)) {
                        operants.push(t_reg.get(s));
                    } else {
                        //unknown variable
                        return "Unknown variable " + s;
                    }
                } else if (s.matches(regex_func)) {
                    //using marco

                } else {
                    //numbers
                    operants.push(new Double(s));
                }
            }

            while (!operator.isEmpty()) {
                processOperation(operants, operator);
            }

            if (operants.isEmpty()) {
                return "Syntax error!";
            }

            double ans = operants.pop();

            if (reg_counter != 100) {
                reg[reg_counter++] = ans;
            } else {
                reg[0] = reg[99];
                reg_counter = 1;
                reg[reg_counter++] = ans;
            }

            return String.valueOf(ans);
        } catch (NumberFormatException e) {
            return "Syntax error!\nIncorrect Number Format!";
        } catch (Exception ex) {
            return "Syntax Error!";
        }
    }

    //will do the matrix part here
    public static boolean processOperation(Stack<Double> operant, Stack<Character> operator) {
        try {
            char op = operator.pop();
            double a = operant.pop();
            double b = operant.pop();

            switch (op) {
                case '+':
                    operant.push(a + b);
                    return true;
                case '-':
                    operant.push(b - a);
                    return true;
                case '*':
                    operant.push(a * b);
                    return true;
                case '/':
                    operant.push(b / a);
                    return true;
                case '%':
                    operant.push(b % a);
                    return true;
                case '^':
                    operant.push(Math.pow(b, a));
                    return true;
                case '!':
                    operant.push(a);
                    double result = MyMath.factorial(b);
                    if(result==-1)
                            throw new Exception("Invalid parameter");
                    operant.push(MyMath.factorial(b));
                    return true;
                default:
                    return true;
            }
        } catch (Exception ex) {
            operant.removeAllElements();
            operator.removeAllElements();
            operant.push(0.0);
            return false;
        }
    }

    public static String insertSpace(String exp) {

        String result = "";

        int len = exp.length();
        for (int i = 0; i < len; i++) {
            if (exp.charAt(i) == ' ') {
                continue;
            } else if (isOperator(exp.charAt(i))) {
                //considering there's already an operator before/after the parenthesis
                switch (exp.charAt(i)) {
                    case '(':
                        result += exp.charAt(i) + "@";
                        //if the token after ( is -, replace it with (0-
                        if (exp.charAt(i + 1) == '-') {
                            result += "0@-@";
                            ++i;
                        }
                        break;
                    case ')':
                        result += "@" + exp.charAt(i);
                        break;
                    default:
                        result += "@" + exp.charAt(i) + "@";
                        break;
                }
            } else if (exp.charAt(i) == '_') {
                //negative sign. (0-num), num is the next char
                String temp = tillNextOperator(exp, i);
                int index = temp.lastIndexOf('i');
                String num = temp.substring(0, index);
                int increament = Integer.valueOf(temp.substring(index + 1));
                result += "(@0@-@" + num + "@)";
                i += increament;
            } else {
                result += exp.charAt(i);
            }
        }
        //System.out.println(result);
        return result;
    }

    public static String tillNextOperator(String exp, int index) {
        int len = exp.length();
        String result = "";

        for (int i = index + 1; i < len; i++) {
            if (isOperator(exp.charAt(i))) {
                result += "i" + (i - index - 1);
                return result;
            }
            result += exp.charAt(i);
        }

        return result + "i" + (len - index - 1);
    }

    public static int occurance(char c, String s) {
        int occ = 0;
        for (int i = 0; i < s.length(); i++) {
            if (c == s.charAt(i)) {
                occ++;
            }
        }
        return occ;
    }

    public static ArrayList<ArrayList<String>> captureGroups(String expression, char left, char right) {
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        int degree = 0;
        int lindex = -1;
        int lastIndex = -1;
        int maxDegree = 0;
        int rindex = -1;
        //trim to the compact form LEFT...RIGHT
        expression = expression.substring(expression.indexOf(left), expression.lastIndexOf(right) + 1);
        int len = expression.length();
        //find the largest level by capturing the first group
        lindex = expression.indexOf(left);
        degree = 1;
        for (int i = lindex + 1; i < len; i++) {
            if (expression.charAt(i) == left) {
                //if is left, increament the level
                degree++;
                if (degree > maxDegree) //keep the max level
                {
                    maxDegree = degree;
                }
            }
            if (expression.charAt(i) == right) {
                degree--;
                if (degree == 0) {
                    //if level after the decreament is 0, break and keep rindex 
                    rindex = i;
                    break;
                }
            }
        }

        if (rindex == -1) {
            //no matching rindex
            System.out.println("Incorrect group format. Unmatched group sign.");
            return null;
        }

        //add empty groups
        for (int i = 0; i < maxDegree; i++) {
            groups.add(new ArrayList<>());
        }
        //add the largerst group to the list
        groups.get(maxDegree - 1).add(expression.substring(lindex, rindex + 1));

        //main loop, if lindex==-1 return the grops
        while (lindex < len) {
            //assign the lindex to the last index
            lastIndex = lindex;
            //get the next left index
            lindex = expression.substring(lindex + 1).indexOf(left);

            if (lindex == -1) {
                return groups;
            }

            lindex += lastIndex + 1;
            //initialize degree and maxdegree
            degree = 1;
            maxDegree = 1;
            //loop through the string within(lindex+1,len) find the group
            for (int i = lindex + 1; i < len; i++) {
                if (expression.charAt(i) == left) {
                    //if is left, increament the level
                    degree++;
                    if (degree > maxDegree) //keep the max level
                    {
                        maxDegree = degree;
                    }
                }
                if (expression.charAt(i) == right) {
                    degree--;
                    if (degree == 0) {
                        //if level after the decreament is 0, break and keep rindex 
                        rindex = i;
                        break;
                    }
                }
            }
            //add the group to the groups
            groups.get(maxDegree - 1).add(expression.substring(lindex, rindex + 1));
        }
        return groups;
    }

    public static void printGroups(ArrayList<ArrayList<String>> groups) {
        System.out.println("There are " + groups.size() + " degree groups.");
        for (int i = groups.size() - 1; i > -1; i++) {
            System.out.println("Degree:" + i);
            for (int j = 0; j < groups.get(i).size(); j++) {
                System.out.println("\t" + groups.get(i).get(j));
            }
        }
    }

    public static double[] parseStringsToDoubles(String[] numbers) {
        double[] dnumbers = new double[numbers.length];
        try {
            for (int i = 0; i < numbers.length; i++) {
                dnumbers[i] = Double.valueOf(numbers[i]);
            }
            return dnumbers;
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    public static String postInsert(String exp) {
        String result = "";

        int len = exp.length();
        for (int i = 0; i < len; i++) {
            if (exp.charAt(i) == ' ') {
                result += "@";
                continue;
            }
            if (isOperator(exp.charAt(i))) {
                //considering there's already an operator before/after the parenthesis
                switch (exp.charAt(i)) {
                    case '(':
                        result += exp.charAt(i) + "@";
                        break;
                    case ')':
                        result += "@" + exp.charAt(i);
                        break;
                    default:
                        result += "@" + exp.charAt(i) + "@";
                        break;
                }
            } else if (exp.charAt(i) == '_') {
                //negative sign. (0-num), num is the next char
                String temp = tillNextOperator(exp, i);
                int index = temp.lastIndexOf('i');
                String num = temp.substring(0, index);
                int increament = Integer.valueOf(temp.substring(index + 1));
                result += "(@0@" + num + "@-@)";
                i += increament;
            } else {
                result += exp.charAt(i);
            }
        }
        //System.out.println(result);
        return result;
    }

    public static boolean isOperator(char c) {
        //37-45 47 60 61 62 94
        if ((c >= 37 && c <= 45) || c == 47 || c==33 || c==124 || (c >= 60 && c <= 62) || c == 94) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isValidKey(String key){
        if (key.length() > 256) 
            return false;
        else if (key.matches(regex_reg)) 
            return false;
        //number can not be the beginning of the key
        if(key.charAt(0)>47&&key.charAt(0)<58)
            return false;
        char c;
        //checking for illegal characters
        for(int i=0;i<key.length();i++){
            c = key.charAt(i);
            if(!((c>=48&&c<=57)||(c>=65&&c<=90)||(c>=97&&c<=122)))
                return false;
        }
        return true;
    }

    public static void printREG() {
        System.out.println("Print the register form:");
        for (int i = 0; i < reg_counter; i++) {
            System.out.println("R" + i + "\t" + reg[i]);
        }
    }

    public static String getREG() {
        StringBuilder sb = new StringBuilder("Register form:");
        for (int i = 0; i < reg_counter; i++) {
            sb.append("R").append(i).append("\t").append(reg[i]).append("\n");
        }
        return sb.toString();
    }

    public static void printVAR() {
        System.out.println("Print the variable form(unordered):");
        Set set = var_map.entrySet();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static String getVAR() {
        StringBuilder sb;
        sb = new StringBuilder("Variable form(unordered):");
        Set set = var_map.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append("\n");
        }
        return sb.toString();
    }

    public static void print(String exp) {
        String regex_var = "%[A-Za-z]+[0-9]*[A-Za-z]*%";
    }

}
