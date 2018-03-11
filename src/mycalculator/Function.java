/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycalculator;

import java.util.Arrays;

/**
 *
 * @author Xuri
 */
public class Function {
    private String lines[];
    
    public Function(String[] lines, double[] params){
        launch(lines[0]);
        this.lines = Arrays.copyOfRange(lines, 1, lines.length);
    }
    
    public void init(String header, double[] params){
        
    }
    
    public String launch(String line){
        return "";
    }
    
}
