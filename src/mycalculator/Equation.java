/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycalculator;

/**
 *
 * @author Xuri
 */
public class Equation {

    String left;
    String right;

    public Equation(String ex) {
        if (MyCalculator.occurance('=', ex) != 1) {
            left = "0";
            right = "0";
        } else {
            //Unfinished
        }
    }

}
