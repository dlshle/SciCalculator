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
public class DecNumber {

    boolean sign;
    char[] numbers;
    char[] decimals;

    public DecNumber() {
        sign = true;
        numbers = new char[1];
        numbers[0] = '0';
        decimals = new char[1];
        decimals[0] = '0';
    }

    public DecNumber(int num) {
        if (num > 0) {
            sign = true;
        } else {
            sign = false;
        }

        long remain = num;
        int modRemain = 0;
        int len = String.valueOf(num).length();
        char[] nums = new char[len];

        len--;

        while (remain > 0) {
            modRemain = (int) (remain % 10);
            nums[len--] = (char) ('0' + modRemain);
            remain /= 10;
        }

        this.numbers = nums;
        this.decimals = new char[1];

        this.decimals[0] = '0';
    }

    public DecNumber(String s) {
        if (s.charAt(0) == '-') {
            this.sign = false;
            s = s.substring(1);
        }

        char[] temp = s.toCharArray();

        int index = 0;
        while (temp[index] >= 48 || temp[index] <= 57) {
            //integer range
            index++;
        }

        numbers = new char[index + 1];

        for (int i = 0; i < index + 1; i++) {
            numbers[i] = temp[i];
        }

        if (temp[index] == 46) {
            index++;
            int counter = 0;
            //continue as it's dot
            for (int i = index; temp[i] >= 48 || temp[i] <= 57; i++) {
                counter++;
            }

            decimals = new char[++counter];

            for (int i = 0; counter > 0; counter--, i++) {
                decimals[i] = temp[index + i];
            }
        }
    }

    public DecNumber(boolean sign, char[] num, char[] dec) {
        this.sign = sign;
        this.numbers = num;
        this.decimals = dec;
    }
    
    public static char[] mergeArr(char[] a, char[] b){
        if(a.length!=b.length)
            return new char[1];
        
        char[] result = new char[a.length+b.length];
        
        for(int i=0;i<a.length;i++){
            result[i]=a[i];
        }
        
        for(int i=a.length;i<result.length;i++){
            result[i]=b[i-a.length];
        }
        
        return result;
    }
    
    public static char[] arrCopy(char[] original, int from){
        if(from>=original.length)
            return original;
        char[] result = new char[original.length-from];
        
        for(int i=0;i<result.length;from++){
            result[i++]=original[from];
        }
        
        return result;
    }
    
    public static char[] fillShortFront(char[] a, char[] b) {
        char[] result;
        char[] l;
        char[] s;

        if (a.length != b.length) {
            if (a.length > b.length) {
                l = a;
                s = b;
            } else {
                l = b;
                s = a;
            }
            
            int lenDiff = l.length - s.length;
            char[] ss = new char[l.length];

            for (int i = 0; i < lenDiff; i++) {
                ss[i] = '0';
            }

            for (int i = lenDiff, counter = 0; counter<s.length; i++,counter++) {
                ss[i] = s[counter];
            }
            
            return ss;
        } else {
            return a;
        }
    }
    
    public static char[] fillShortBack(char[] a, char[] b){
        char[] result;
        char[] l;
        char[] s;
        
        if(a.length>=b.length){
            l = a;
            s = b;
        }else{
            l = b;
            s = a;
        }
        
        char[] ss = new char[l.length];
                
        for(int i=0;i<s.length;i++){
            ss[i]=s[i];
        }
        
        for(int i=s.length;i<ss.length;i++){
            ss[i]='0';
        }
        
        return ss;
     }
    
    public static char[] longestChar(char[] a, char[] b){
        if(a.length>=b.length)
            return a;
        else
            return b;
    }

    public static DecNumber absVal(DecNumber n) {
        return new DecNumber(true, n.numbers, n.decimals);
    }

    public static DecNumber sum(DecNumber a, DecNumber b) {
        return a.add(b);
    }

    public static DecNumber diff(DecNumber a, DecNumber b) {
        return a.subtract(b);
    }

    public static DecNumber absDiff(DecNumber a, DecNumber b) {
        return absVal(diff(a, b));
    }

    public static DecNumber product(DecNumber a, DecNumber b) {
        return a.multiply(b);
    }

    public static DecNumber quotient(DecNumber a, DecNumber b) {
        return a.divide(b);
    }
    
    //returns an arr with the position of dot in the front
    //HAS LOGICAL ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static char[] NumberAdd(DecNumber add, DecNumber adder){
        char[] numa = add.numbers;
        char[] numb = adder.numbers;
        char[] deca = add.decimals;
        char[] decb = adder.decimals;

        if (numa.length != numb.length) {
            numa = longestChar(numa, numb);
            numb = fillShortFront(numa, numb);
        }

        if (deca.length != decb.length) {
            deca = longestChar(deca, decb);
            decb = fillShortBack(deca, decb);
        }

        int dotIndex = numa.length + 2;//temp dot position

        char[] lineA = mergeArr(numa, deca);
        char[] lineB = mergeArr(numb, decb);

        char[] ans = new char[lineA.length + 2];//answer arr

        int a = 0;
        int b = 0;
        int sum = 0;
        for (int i = lineA.length - 1; i >= 0; i--) {
            a = (int) (lineA[i] - '0');
            b = (int) (lineB[i] - '0');
            sum += a + b;
            if (sum > 9) {
                ans[i + 2] = (char) (sum % 10 + '0');
                sum = 1;
            } else {
                ans[i + 2] = (char) (sum + '0');
                sum = 0;
            }
        }

        if (sum == 1) {
            ans[1] = '1';            
        } else {
            ans = arrCopy(ans, 1);
            dotIndex--;
        }
        
        ans[0]=(char)(dotIndex+'0');
        
        return ans;
    }
    
    public static char[] decimalCharAdd(char[] a, char[] b) {
        int na = 0, nb = 0;
        char l[];
        char s[];
        //the first element is for the increamenting on the number part
        char[] result;

        if (a.length == b.length) {
            //avoid unecessary operations
            l = a;
            s = b;
            result = new char[a.length + 1];
        } else {

            if (a.length >= b.length) {
                l = a;
                s = b;
                result = new char[a.length + 1];
            } else {
                l = b;
                s = a;
                result = new char[b.length + 1];
            }

            int lenDiff = l.length - s.length;

            //shift the remain decimals from the longest decimal arr to the result arr
            for (int i = l.length; lenDiff > 0; i--, lenDiff--) {
                //cauz' len(result)=len(l)+1, result[0] is reserved
                result[i] = l[i - 1];
            }
        }

        int sum = 0;

        //add up decimals
        for (int i = s.length; i >= 1; i--) {
            //converting char(number) to int need to -'0'
            na = l[i - 1] - 48;
            nb = s[i - 1] - 48;

            sum += na + nb;

            if (sum > 9) {
                result[i] = (char) ((sum % 10) + '0');
                sum = 1;
            } else {
                result[i] = (char) (sum + '0');
                sum = 0;
            }
        }

        if (sum == 1) {
            result[0] = '+';
        } else {
            result[0] = '=';
        }

        return result;
    }
    
    public static char[] numberCharAdd(char[] a, char[] b){
        char[] lo = longestChar(a, b);
        char[] so = fillShortFront(a, b);
        
        char[] result = decimalCharAdd(lo, so);
        
        if(result[0]=='+')
            result[0]='1';
        else
            result = arrCopy(result, 1);
        
        return result;
    }

    public static char[] decimalAdd(DecNumber a, DecNumber b) {
        return decimalCharAdd(a.decimals, b.decimals);
    }
    
    public static char[] intAdd(DecNumber a, DecNumber b) {
        return numberCharAdd(a.numbers,b.numbers);
    }

    public char[] splitLongInteger(long num) {
        long remain = num;
        int modRemain = 0;
        int len = String.valueOf(num).length();
        char[] nums = new char[len];

        len--;

        while (remain > 0) {
            modRemain = (int) (remain % 10);
            nums[len--] = (char) ('0' + modRemain);
            remain /= 10;
        }

        return nums;
    }

    
    public DecNumber add(DecNumber n) {
        if (this.sign && (!n.sign)) {
            return subtract(n);
        } else if ((!this.sign) && n.sign) {
            return n.subtract(this);
        }

        DecNumber result = new DecNumber();

        if (isInt()) {
            result.decimals = arrCopy(n.decimals, 0);
        }
        if (n.isInt()) {
            result.decimals = arrCopy(decimals, 0);
        }
        
        //do decimal parts
        char[] decResult = decimalAdd(this, n);
        
        //number increamenting and computing
        if(decResult[0]=='+'){
            
        }else{
            
        }
        
        //another way
        DecNumber temp = new DecNumber();
        char[] ans = NumberAdd(this, n);
        int dotIndex = (int)(ans[0]-'0');
        
        temp.numbers = new char[dotIndex-1];
        temp.decimals = new char[ans.length-dotIndex];
        
        for(int i=1, counter=0;i<dotIndex;i++, counter++){
            temp.numbers[counter]=ans[i];
        }
        
        for(int i=dotIndex, counter=0;i<ans.length;i++, counter++){
            temp.decimals[counter]=ans[i];
        }
        
        return temp;     

    }

    public DecNumber subtract(DecNumber n) {
        return null;
    }

    public DecNumber multiply(DecNumber n) {
        return null;
    }

    public DecNumber divide(DecNumber n) {
        return null;
    }

    public boolean isPositive() {
        return sign;
    }

    public boolean isInt() {
        return (decimals.length == 1 && decimals[0] == '0');
    }

}
