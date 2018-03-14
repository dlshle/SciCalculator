/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycalculator;

/**
 *
 * @author 958015835
 */
public class testClass {

    /**
     * @param args the command line arguments
     */
    public static void matrixTest(String[] args) {
        /*
        double[][] rows = {{1,2,3},{2,3,4},{3,4,5}};
        Matrix m = new Matrix(); 
        double[] row  ={1,2,3};
        double[] row1 = {2,3,4};
        double[] row2 = {3,4,5};
        m.addRowArray(row);
        m.addRowArray(row1);
        m.addRowArray(row2);
        double[] col = {4,5,6};
        m.addColArray(col);
        System.out.println(m);
        m.removeCol(3);
        System.out.println(m);
        System.out.println(m.getEntry(0, 0));
        
        Matrix m2 = new Matrix(rows);
        m2.swapRow(0, 1);
        Matrix m3 = m.add(m2);
        System.out.println(m.toString());
        System.out.println(m2.toString());
        System.out.println(m.add(m2));
        System.out.println(m3);
        System.out.println(m3.subtract(m2));
        System.out.println(m);
        System.out.println(m.scalarProduct(2));
        System.out.println("-------------------------------");
        m.multiplyRow(0,2);
        System.out.println(m);
        System.out.println("-------------------------------");
        
        double[][] rrefmat = {{1,0,0},{0,1,0},{0,0,1}};
        double[][] refmat = {{2,3,4},{0,4,5},{0,0,0}}; 
        Matrix rrefMatrix = new Matrix(rrefmat);
        Matrix refMatrix = new Matrix(refmat);
        System.out.println("----------------------------------------------");
        System.out.println("REF test");
        double[][] newREFMAT = {{1,2,3},{0,5,6},{0,0,0}};
        refMatrix = new Matrix(newREFMAT);
        System.out.println(refMatrix);
        System.out.println("isRef()="+refMatrix.isREF());
        double[][] wrongREFMAT = {{0,1,0},{1,2,3},{2,3,4}};
        refMatrix = new Matrix(wrongREFMAT);
        System.out.println(refMatrix+"\nisREF()="+refMatrix.isREF());
        double[][] nwrongREFMAT = {{1,2,3},{0,0,0},{1,2,3}};
        refMatrix = new Matrix(nwrongREFMAT);
        System.out.println(refMatrix+"\nisREF()="+refMatrix.isREF());
        System.out.println("RREF TEST");
        refMatrix = new Matrix(newREFMAT);
        System.out.println(refMatrix+"\nisRREF()="+refMatrix.isRREF());
        double[][] newrrefmat = {{1,0,0,0,0},{0,0,1,0,0},{0,0,0,0,1}};
        rrefMatrix = new Matrix(newrrefmat);
        System.out.println(rrefMatrix);
        System.out.println("isRREF()="+rrefMatrix.isRREF());
        double[][] wrongRREFMAT = {{1,1,0},{0,0,0},{0,0,1}};
        rrefMatrix =  new Matrix(wrongRREFMAT);
        System.out.println(rrefMatrix+"\nisRREF()="+rrefMatrix.isRREF());
        double[][] nwrongRREFMAT = {{1,1,0},{0,1,0},{0,0,1}};
        rrefMatrix =  new Matrix(nwrongRREFMAT);
        System.out.println(rrefMatrix+"\nisRREF()="+rrefMatrix.isRREF());
        double[][] correctRREFMAT = {{1,0,4},{0,1,3},{0,0,0}};
        rrefMatrix =  new Matrix(correctRREFMAT);
        System.out.println(rrefMatrix+"\nisRREF()="+rrefMatrix.isRREF());
        System.out.println(refMatrix.isRREF());
        System.out.println("-----------------------------------------------");
        System.out.println("Test getEchelonOrderedMatrix()");
        double[][] unordered = {{0,0,0,2,3},{0,2,3,4,5},{0,0,4,5,6},{1,0,2,3,4},{0,0,3,3,3}};
        m = new Matrix(unordered);
        System.out.println(m);
        System.out.println("ordored:\n" + m.getEchelonOrderedMatrix());
        System.out.println("-----------------------------------------------");
        System.out.println("Test getREF()");
        m = new Matrix(unordered);
        System.out.println(m);
        System.out.println("ordered:\n"+m.getEchelonOrderedMatrix());
        System.out.println("REF:\n"+m.getREF());
        System.out.println(m.getREF().isREF());
        System.out.println("-----------------NEW ONE-----------------------");
        double[][] anotherRREFMAT = {{2,3,5,7,8},{1,6,6,9,10},{5,8,9,7,3},{9,9,8,8,7},{6,6,6,6,6}};
        m = new Matrix(anotherRREFMAT);
        System.out.println(m);
        System.out.println(m.getEchelonOrderedMatrix());
        System.out.println(m.getREF());
        System.out.println(m.getREF().isREF());
        System.out.println("-----------------------------------------------------------------");
        m = new Matrix(unordered);
        System.out.println(m);
        m.echelonOrderedMatrix();
        System.out.println("ordered:\n"+m);
        m = new Matrix(unordered);
        m.REF();
        System.out.println("ref=\n"+m);
        System.out.println("isREF():"+m.isREF());
        System.out.println("isRREF():"+m.isRREF());
        System.out.println("---------------------------------------------------------------");
        System.out.println("getRREF() test");
        m = new Matrix(unordered);
        System.out.println(m);
        System.out.println(m.getRREF());
        System.out.println(m.getRREF().isRREF());
        System.out.println("Wrong format test:");
        m  = new Matrix(wrongREFMAT);
        System.out.println(m);
        System.out.println(m.getRREF());
        System.out.println(m.getRREF().isRREF());
        System.out.println("-----------------Self test-----------------------");
        m = new Matrix(unordered);
        System.out.println(m);
        System.out.println(m.RREF());
        System.out.println(m);
        System.out.println(m.isRREF());
        System.out.println("Wrong format test:");
        m  = new Matrix(wrongREFMAT);
        System.out.println(m);
        System.out.println(m.RREF());
        System.out.println(m);
        System.out.println(m.isRREF());        
         */
        double comp[] = {1, 3, 5};
        Vector vec = new Vector(comp);
        System.out.println(vec);
        //one col matrix
        double ents[][] = {{1}, {2}, {3}};
        Matrix mat = new Matrix(ents);
        System.out.println(mat);
        System.out.println(vec.toMatrix());
        double com2[] = {2, 4, 6};
        Vector vec1 = new Vector(com2);
        System.out.println(vec.add(vec1));
        System.out.println(vec.subtract(vec1));
        System.out.println(vec.dot(vec1));
        System.out.println(vec.scalarProduct(2.333));

        System.out.println("---------------------------");
        Matrix mm = new Matrix(5, 3);
        System.out.println(mm);
        System.out.println("---------------------------");
        System.out.println("diagonal matrix test");
        double ents1[][] = {{1, 0, 0, 1}, {0, 2, 0, 3}, {0, 0, 3, 8}};
        mat = new Matrix(ents1);
        System.out.println(mat.isDiagnoal());
        System.out.println(mat);
        System.out.println(mat.getDeterminant());
        mat.RREF();
        System.out.println(mat);
        System.out.println(mat.isRREF());
        System.out.println(mat.getDeterminant());
        System.out.println("--------------------------");
        System.out.println("rank and range space(getColSpace) test");
        double[][] ents2 = {{2, 3, 5}, {6, 9, 3}, {1, 3, 3,}};
        mat = new Matrix(ents2);
        System.out.println(mat);
        System.out.println(mat.getRREF());
        mat.getRank();
        System.out.println("rank:\n" + mat.getRank());
        System.out.println("colspace:\n" + mat.getColSpace());
        System.out.println("nullity:\n" + mat.getNullity());
        System.out.println("nullspace:\n" + mat.getNullSpace());
        System.out.println("-------------------------");
        System.out.println("transpose and identity matrix");
        System.out.println(mat);
        System.out.println(mat.getTranspose());
        System.out.println(Matrix.getIdentityMatrix(mat.size()));
        System.out.println("------------------------");
        System.out.println("digitalRoot(24566)");
        System.out.println(MyMath.digitalRoot(24566));
        System.out.println(MyCalculator.isOperator('|'));
        System.out.println((int)'|');
        System.out.println("--------------------------");
        vec = new Vector(comp);
        vec1 = new Vector(com2);
        System.out.println(vec.cross(vec1));
    }

}
