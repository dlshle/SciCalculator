package mycalculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Xuri
 */
public class Matrix {

    private int numOfRows;
    private int numOfCols;
    private final ArrayList<ArrayList<Double>> matrix;

    public Matrix(double[][] matrix) {
        this.numOfRows = matrix.length;
        this.numOfCols = matrix[0].length;
        this.matrix = new ArrayList<>(numOfRows);
        for (int i = 0; i < matrix.length; i++) {
            //rows
            if (matrix[i].length < numOfCols) {
                break;
            }
            this.matrix.add(new ArrayList<>(numOfCols));
            for (int j = 0; j < matrix[i].length; j++) {
                this.matrix.get(i).add(matrix[i][j]);
            }
        }
    }

    public Matrix(double[] row) {
        this.numOfRows = 1;
        this.numOfCols = row.length;
        matrix = new ArrayList<>();
        matrix.add(new ArrayList<>(numOfCols));
        for (int i = 0; i < numOfCols; i++) {
            matrix.get(0).add(row[i]);
        }
    }

    public Matrix(int numOfRows, int numOfCols) {
        matrix = new ArrayList<>();
        for (int i = 0; i < numOfRows; i++) {
            this.plugRowArray(new double[numOfCols]);
        }
    }

    public Matrix(int numOfRows, int numOfCols, ArrayList<ArrayList<Double>> matrix) {
        this.numOfRows = numOfRows;
        this.numOfCols = numOfCols;
        this.matrix = matrix;
    }

    public Matrix() {
        this.numOfRows = 0;
        this.numOfCols = 0;
        matrix = new ArrayList<>();
    }

    public static boolean isRightFormat(String exp) {
        String regex = "\\[*\\[(\\s*[0-9]*\\.?[0-9]+\\s*,)+\\s*[0-9]*\\.?[0-9]+\\s*\\]\\]*";
        ArrayList<ArrayList<String>> groups;
        if (!exp.matches(regex)) {
            return false;
        }
        //get all degree 0 groups
        groups = MyCalculator.captureGroups(exp, '[', ']');
        return isRightGroups(groups);
    }

    public static boolean isRightGroups(ArrayList<ArrayList<String>> groups) {
        String[] row;
        int occu = -1;
        String str;
        if (groups == null || groups.size() > 2) {
            return false;
        }
        str = groups.get(0).get(0);
        str = str.trim();
        row = str.substring(1, str.length()).split(",");
        occu = row.length;
        for (int i = 1; i < groups.get(0).size(); i++) {
            str = groups.get(0).get(i);
            str = str.trim();
            row = str.substring(1, str.length()).split(",");
            if (occu != row.length) {
                return false;
            }
        }
        return true;
    }

    public boolean plugRowArray(double[] row) {
        if (numOfRows != 0) {
            if (row.length != numOfCols) {
                return false;
            }
        } else {
            numOfCols = row.length;
        }
        this.matrix.add(new ArrayList<>(numOfCols));
        this.numOfRows++;
        for (int i = 0; i < numOfCols; i++) {
            this.matrix.get(numOfRows - 1).add(row[i]);
        }
        return true;
    }

    public boolean plugRow(ArrayList<Double> row) {
        if (numOfRows != 0) {
            if (row.size() != numOfCols) {
                return false;
            }
        } else {
            numOfCols = row.size();
        }
        this.matrix.add(new ArrayList<>(numOfCols));
        this.numOfRows++;
        for (int i = 0; i < numOfCols; i++) {
            this.matrix.get(numOfRows - 1).add(row.get(i));
        }
        return true;
    }

    public boolean addColArray(double[] col) {
        if (!isEmpty()) {
            if (col.length != numOfRows) {
                return false;
            }
            for (int i = 0; i < numOfRows; i++) {
                this.matrix.get(i).add(col[i]);
            }
            this.numOfCols++;
            return true;
        } else {
            for (int i = 0; i < col.length; i++) {
                matrix.add(new ArrayList<>());
                numOfRows++;
                matrix.get(i).add(col[i]);
            }
            numOfCols++;
            return true;
        }
    }

    public boolean addCol(ArrayList<Double> col) {
        if (!isEmpty()) {
            if (col.size() != numOfRows) {
                return false;
            }
            for (int i = 0; i < numOfRows; i++) {
                this.matrix.get(i).add(col.get(i));
            }
            this.numOfCols++;
            return true;
        } else {
            for (int i = 0; i < col.size(); i++) {
                matrix.add(new ArrayList<>());
                numOfRows++;
                matrix.get(i).add(col.get(i));
            }
            numOfCols++;
            return true;
        }
    }

    public boolean removeCol(int index) {
        if (index < 0 || index >= numOfCols || isEmpty()) {
            return false;
        }
        for (int i = 0; i < numOfRows; i++) {
            matrix.get(i).remove(index);
        }
        numOfCols--;
        return true;
    }

    public boolean removeRow(int index) {
        if (index < 0 || index >= numOfRows || isEmpty()) {
            return false;
        }
        matrix.remove(index);
        numOfRows--;
        return true;
    }

    public Matrix add(Matrix m) {
        if (numOfCols != m.getNumOfCols() || numOfRows != m.getNumOfRows()) {
            return null;
        }
        double[][] matrix = new double[numOfRows][numOfCols];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                matrix[i][j] = getEntry(i, j) + m.getEntry(i, j);
            }
        }
        return new Matrix(matrix);
    }

    public Matrix subtract(Matrix m) {
        if (numOfCols != m.getNumOfCols() || numOfRows != m.getNumOfRows()) {
            return null;
        }
        double[][] matrix = new double[numOfRows][numOfCols];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                matrix[i][j] = this.matrix.get(i).get(j) - m.getEntry(i, j);
            }
        }
        return new Matrix(matrix);
    }

    public Matrix scalarProduct(double val) {
        double[][] matrix = new double[numOfRows][numOfCols];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                matrix[i][j] = this.matrix.get(i).get(j) * val;
            }
        }
        return new Matrix(matrix);
    }

    //vector part
    /**
     * Get the Row Echelon Form
     *
     * @return
     */
    public Matrix getREF() {
        if (isZero()) {
            return this;
        }
        if (isEmpty()) {
            return null;
        }
        if (numOfRows == 1 || numOfCols == 1) {
            return this;
        }
        //if valid, then:
        //1.reorder the matrix
        int rowIndex = -1;
        int pivotIndex = -1;
        double pivot = 0;
        Matrix result = getEchelonOrderedMatrix();
        //use pivots to do reduction
        for (int i = 0; i < numOfRows; i++) {
            //find the pivot
            //colIndex = leftMostPositions[numOfRows-i];
            pivotIndex = result.getLeadingCoefficientIndex(i);
            pivot = result.getEntry(i, pivotIndex);
            for (int j = i + 1; j < numOfRows; j++) {
                if (result.getEntry(j, pivotIndex) == 0) {
                    continue;
                }
                //Rb->Rb-Ra*Rb/Ra
                result.subtractRow(j, i, 1, result.getEntry(j, pivotIndex) / pivot);
                //OR rb->Ra-Rb*Ra/Rb etc..
            }
        }
        return result;
    }

    /**
     * Change to the Row Echelon Form
     *
     * @return
     */
    public boolean REF() {
        if (isZero()) {
            return false;
        }
        if (isEmpty()) {
            return false;
        }
        if (numOfRows == 0 || numOfCols == 0) {
            return true;
        }
        //if valid, then:
        //1.reorder the matrix
        int rowIndex = -1;
        int pivotIndex = -1;
        double pivot = 0;
        echelonOrderedMatrix();
        //use pivots to do reduction
        for (int i = 0; i < numOfRows; i++) {
            //find the pivot
            //colIndex = leftMostPositions[numOfRows-i];
            pivotIndex = getLeadingCoefficientIndex(i);
            pivot = getEntry(i, pivotIndex);
            for (int j = i + 1; j < numOfRows; j++) {
                if (getEntry(j, pivotIndex) == 0) {
                    continue;
                }
                //Rb->Rb-Ra*Rb/Ra
                subtractRow(j, i, 1, getEntry(j, pivotIndex) / pivot);
                //OR rb->Ra-Rb*Ra/Rb etc..
            }
        }
        return true;
    }

    /**
     * Get the reduced row echelon form
     *
     * @return
     */
    public Matrix getRREF() {
        if (isZero()) {
            return this;
        }
        if (isEmpty()) {
            return null;
        }
        if (numOfCols == 1 || numOfRows == 1) {
            return this;
        }
        //if valid, then:
        //1.reorder the matrix
        int rowIndex = -1;
        int pivotIndex = -1;
        double pivot = 0;
        Matrix result = getEchelonOrderedMatrix();
        //use pivots to do reduction
        for (int i = 0; i < numOfRows; i++) {
            //find the pivot
            //colIndex = leftMostPositions[numOfRows-i];
            pivotIndex = result.getLeadingCoefficientIndex(i);
            //make the pivot to 1
            pivot = result.getEntry(i, pivotIndex);
            if (pivot != 1.0) {
                result.multiplyRow(i, 1 / pivot);
            }
            for (int j = 0; j < numOfRows; j++) {
                if (j == i || result.getEntry(j, pivotIndex) == 0) {
                    continue;
                }
                //Rb->Rb-Ra*Rb/Ra
                result.subtractRow(j, i, 1, result.getEntry(j, pivotIndex));
                //OR rb->Ra-Rb*Ra/Rb etc..
            }
        }
        result.echelonOrderedMatrix();
        return result;
    }

    public boolean RREF() {
        if (isZero()) {
            return false;
        }
        if (isEmpty()) {
            return false;
        }
        if (numOfCols == 1 || numOfRows == 1) {
            return false;
        }
        //if valid, then:
        //1.reorder the matrix
        int rowIndex = -1;
        int pivotIndex = -1;
        double pivot = 0;
        echelonOrderedMatrix();
        //use pivots to do reduction
        for (int i = 0; i < numOfRows; i++) {
            //find the pivot
            //colIndex = leftMostPositions[numOfRows-i];
            pivotIndex = getLeadingCoefficientIndex(i);
            //make the pivot to 1
            pivot = getEntry(i, pivotIndex);
            if (pivot != 1.0) {
                multiplyRow(i, 1 / pivot);
            }
            for (int j = 0; j < numOfRows; j++) {
                if (j == i || getEntry(j, pivotIndex) == 0) {
                    continue;
                }
                //Rb->Rb-Ra*Rb/Ra
                subtractRow(j, i, 1, getEntry(j, pivotIndex));
                //OR rb->Ra-Rb*Ra/Rb etc..
            }
        }
        echelonOrderedMatrix();
        return true;
    }

    public boolean swapRow(int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 >= numOfRows || index2 >= numOfRows || index1 == index2) {
            return false;
        }
        ArrayList<Double> tempRow = getRow(index2);
        matrix.set(index2, getRow(index1));
        matrix.set(index1, tempRow);
        return true;
    }

    public boolean multiplyRow(int index, double scalar) {
        if (index < 0 || index >= numOfRows) {
            return false;
        }
        matrix.set(index, getMultipliedRow(index, scalar));
        return true;
    }

    public ArrayList<Double> getMultipliedRow(int index, double scalar) {
        if (index < 0 || index >= numOfRows) {
            return null;
        }
        ArrayList<Double> result = new ArrayList<>(numOfCols);
        double product = 0.0;
        for (int i = 0; i < numOfCols; i++) {
            product = getEntry(index, i) * scalar;
            result.add(product == -0.0 ? 0.0 : product);
        }
        return result;
    }

    public boolean addRow(int mainRow, int secondaryRow, double scalarMain, double scalarSecondary) {
        if (mainRow < 0 || secondaryRow < 0 || mainRow >= numOfRows || secondaryRow >= numOfRows) {
            return false;
        }
        matrix.set(mainRow, getAddedRow(mainRow, secondaryRow, scalarMain, scalarSecondary));
        return true;
    }

    public ArrayList<Double> getAddedRow(int r1, int r2, double scalarR1, double scalarR2) {
        if (r1 < 0 || r2 < 0 || r1 >= numOfRows || r2 >= numOfRows) {
            return null;
        }
        ArrayList<Double> result = new ArrayList<>(numOfCols);
        for (int i = 0; i < numOfCols; i++) {
            result.add(scalarR1 * getEntry(r1, i) + scalarR2 * getEntry(r2, i));
        }
        return result;
    }

    public boolean subtractRow(int mainRow, int secondaryRow, double scalarMain, double scalarSecondary) {
        if (mainRow < 0 || secondaryRow < 0 || mainRow >= numOfRows || secondaryRow >= numOfRows) {
            return false;
        }
        matrix.set(mainRow, getSubtractedRow(mainRow, secondaryRow, scalarMain, scalarSecondary));
        return true;
    }

    public ArrayList<Double> getSubtractedRow(int r1, int r2, double scalarR1, double scalarR2) {
        if (r1 < 0 || r2 < 0 || r1 >= numOfRows || r2 >= numOfRows) {
            return null;
        }
        ArrayList<Double> result = new ArrayList<>(numOfCols);
        for (int i = 0; i < numOfCols; i++) {
            result.add(scalarR1 * getEntry(r1, i) - scalarR2 * getEntry(r2, i));
        }
        return result;
    }

    public double getDeterminant() {
        return Matrix.getDeterminant(this);
    }

    public static double getDeterminant(Matrix m) {
        if (m.isEmpty() || !(m.isSquare())) {
            System.out.println("ERROR: The matrix is either empty or not square.");
            return 0;
        }
        if(m.isZero())
            return 0;
        if (m.getSize() == 4) {
            return m.getEntry(0, 1) * m.getEntry(1, 1) - m.getEntry(1, 0) * m.getEntry(0, 1);
        }
        double result = 0.0;
        Matrix temp;
        for (int i = 0; i < m.numOfCols; i++) {
            temp = m.getMatrixByIsolatedEntry(0, i);
            result += (i % 2 == 0 ? 1 : -1) * m.getEntry(0, i) * Matrix.getDeterminant(temp);
        }
        return result;
    }

    public boolean isEmpty() {
        return numOfRows == 0 && numOfCols == 0;
    }

    public boolean isZero() {
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                if (getEntry(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSquare() {
        if (numOfRows > 1 && numOfRows == numOfCols) {
            return true;
        }
        return false;
    }

    public boolean isDiagnoal() {
        if (isEmpty() || isZero()) {
            return false;
        }
        if (!isSquare()) {
            return false;
        }
        for (int i = 0; i < numOfRows; i++) {
            if (!isIsolatedInRow(i, i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isInvertable(){
        if(isSquare()&&getDeterminant()!=0.0)
            return true;
        return false;
    }
    
    public boolean isSingular(){
        return !(isInvertable());
    }
    
    public boolean isLinearlyIndependent(){
        if(!isInvertable())
            return false;
        if(numOfRows>numOfCols)
            return false;
        Matrix copy = deepCopy();
        copy.RREF();
        //UNFINISHED
        return true;
    }

    public boolean isIsolatedInRow(int row, int col) {
        if (row >= numOfRows || col >= numOfCols || row < 0 || col < 0) {
            return false;
        }
        for (int i = 0; i < numOfCols; i++) {
            if (getEntry(row, i) != 0) {
                if (i != col) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isIsolatedInCol(int row, int col) {
        if (row >= numOfRows || col >= numOfCols || row < 0 || col < 0) {
            return false;
        }
        for (int i = 0; i < numOfRows; i++) {
            if (getEntry(i, col) != 0) {
                if (i != row) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isREF() {
        if (isZero() || isEmpty()) {
            return true;
        }
        int lastIndex = getLeadingCoefficientIndex(0);
        //first row is all zero
        if (lastIndex == -1) {
            return false;
        }
        int thisIndex = -1;
        for (int i = 1; i < numOfRows; i++) {
            thisIndex = getLeadingCoefficientIndex(i);
            if (thisIndex == -1) {
                //checking if the rows below are all zero 
                for (int j = i + 1; j < numOfRows; j++) {
                    if (getLeadingCoefficientIndex(j) != -1) {
                        return false;
                    }
                }
                return true;
            }
            if (lastIndex >= thisIndex) {
                return false;
            }
            lastIndex = thisIndex;
        }
        return true;
    }

    /*
    Check if the matrix can lead to the reduced echelon form
    */
    public boolean isRREF() {
        if (isZero() || isEmpty()) {
            return true;
        }
        int lastIndex = getLeadingCoefficientIndex(0);
        //first row is all zero
        if (lastIndex == -1 || getEntry(0, lastIndex) != 1) {
            return false;
        }
        int thisIndex = -1;
        for (int i = 1; i < numOfRows; i++) {
            thisIndex = getLeadingCoefficientIndex(i);
            if (thisIndex == -1) {
                //checking if the rows below are all zero 
                for (int j = i + 1; j < numOfRows; j++) {
                    if (getLeadingCoefficientIndex(j) != -1) {
                        return false;
                    }
                }
                return true;
            }
            if (getEntry(i, thisIndex) == 1) {
                //checking if other entries in this column are 0
                if (!isIsolatedInCol(i, thisIndex)) {
                    return false;
                }
            } else {
                //if the leading entry is not 1, return false
                return false;
            }
            if (lastIndex >= thisIndex) {
                return false;
            }
            lastIndex = thisIndex;
        }
        return true;
    }

    public boolean setEntry(int row, int col, double val) {
        if (row >= numOfRows || col >= numOfCols || row < 0 || col < 0) {
            return false;
        }
        this.matrix.get(row).set(col, val);
        return true;
    }

    public double getEntry(int row, int col) {
        if (row >= numOfRows || col >= numOfCols || col < 0 || row < 0) {
            return Double.MIN_VALUE;
        }
        return this.matrix.get(row).get(col);
    }

    public double getLeadingCoefficient(int row) {
        if (row < 0 || row >= numOfRows) {
            return Double.MIN_VALUE;
        }
        for (int i = 0; i < numOfCols; i++) {
            if (getEntry(row, i) == 0) {
                continue;
            }
            return getEntry(row, i);
        }
        //if all passes, all zero
        return Double.MIN_VALUE;
    }

    public int getLeadingCoefficientIndex(int row) {
        if (row < 0 || row >= numOfRows) {
            return -1;
        }
        for (int i = 0; i < numOfCols; i++) {
            if (getEntry(row, i) == 0) {
                continue;
            }
            return i;
        }
        //if all passes, all zero
        return -1;
    }

    public int getLeftMostRowIndex() {
        if (isEmpty()) {
            return -1;
        }
        if (isZero()) {
            return 0;
        }
        int max = -1;
        for (int i = 0; i < numOfCols; i++) {
            if (getLeadingCoefficientIndex(i) > getLeadingCoefficientIndex(max)) {
                max = i;
            }
        }
        return max;
    }

    public Matrix getEchelonOrderedMatrix() {
        if (isEmpty()) {
            return null;
        }
        if (isZero()) {
            return this;
        }
        int[] positions = new int[numOfRows];
        int min = 0;
        Matrix result = new Matrix();
        for (int i = 0; i < numOfRows; i++) {
            positions[i] = getLeadingCoefficientIndex(i);
        }

        //find min-left-pos positions and add to the new matrix
        for (int i = 0; i < numOfRows; i++) {
            min = 0;
            for (int j = 1; j < numOfRows; j++) {
                if (positions[j] < positions[min]) {
                    min = j;
                }
            }
            result.plugRow(getRow(min));
            positions[min] = numOfCols + 1;
        }
        return result;
    }

    public boolean echelonOrderedMatrix() {
        if (isEmpty()) {
            return false;
        }
        if (isZero()) {
            return true;
        }
        int[] positions = new int[numOfRows];
        int min = 0;
        for (int i = 0; i < numOfRows; i++) {
            positions[i] = getLeadingCoefficientIndex(i);
        }

        //find min-left-pos positions and add to the new matrix
        for (int i = 0; i < numOfRows; i++) {
            min = i;
            for (int j = i + 1; j < numOfRows; j++) {
                if (positions[j] < positions[min]) {
                    min = j;
                }
            }
            //switch row and position
            swapRow(min, i);
            positions[min] = positions[i];
            positions[i] = numOfCols + 1;
        }
        return true;
    }

    public ArrayList<Double> getLeftMostRow() {
        if (isEmpty()) {
            return null;
        }
        if (isZero()) {
            return null;
        }
        int max = -1;
        ArrayList<Double> result = new ArrayList<>(numOfCols);
        for (int i = 0; i < numOfCols; i++) {
            if (getLeadingCoefficientIndex(i) > max) {
                max = i;
            }
        }
        for (int i = 0; i < numOfCols; i++) {
            result.add(getEntry(max, i));
        }
        return result;
    }

    public ArrayList<Double> getRow(int index) {
        if (index < 0 || index >= numOfRows) {
            return null;
        }
        return matrix.get(index);
    }

    public ArrayList<Double> getCol(int index) {
        if (index < 0 || index >= numOfCols) {
            return null;
        }
        ArrayList<Double> col = new ArrayList<>(numOfCols);
        for (int i = 0; i < numOfCols; i++) {
            col.add(getEntry(index, i));
        }
        return col;
    }

    public Matrix getMatrixByIsolatedEntry(int row, int col) {
        if (row < 0 || row >= numOfRows || col < 0 || col >= numOfCols) {
            return null;
        }
        Matrix copy = deepCopy();
        if (!(copy.removeCol(col) && copy.removeRow(row))) {
            return null;
        }
        return copy;
    }
    
    public boolean hasPivotInCol(int col){
        for(int i=0;i<numOfRows;i++){
            if(getEntry(i,col)==0)
                continue;
            if(getEntry(i,col)!=1.0)
                return false;
        }
        return true;
    }
    
    public boolean hasFreeVarInCol(int col){
        return !hasPivotInCol(col);
    }
    
    public int getRank(){
        if(isZero()||isEmpty())
            return 0;
        if(getSize()==1)
            return 1;
        int rank = 0;
        Matrix copy = deepCopy();
        copy.RREF();
        for(int i=0;i<copy.getNumOfCols();i++){
            //find the ones with leading variable
            if(copy.hasPivotInCol(i))
                rank++;
        }
        return rank;
    }
    
    public Set<Vector> getColSpace(){
        HashSet<Vector> set = new HashSet<>();
        if(isZero()||isEmpty())
            return null;
        if(getSize()==1){
            //since it's not all zero row/col
            set.add(new Vector(getCol(0)));
            return set;
        }
        Matrix copy = deepCopy();
        copy.RREF();
        for(int i=0;i<copy.getNumOfCols();i++){
            if(copy.hasPivotInCol(i))
                set.add(new Vector(getCol(i)));
        }
        return set;
    }
    
    public int getNullity(){
        return numOfCols - getRank();
    }
    
    public Set<Vector> getNullSpace(){
        HashSet<Vector> set = new HashSet<>();
        if(isEmpty())
            return null;
        if(isZero()){
            for(int i=0;i<numOfCols;i++){
                set.add(new Vector(getCol(i)));
            }
            return set;
        }
        Matrix copy = deepCopy();
        copy.RREF();
        for(int i=0;i<copy.getNumOfCols();i++){
            if(copy.hasFreeVarInCol(i))
                set.add(new Vector(getCol(i)));
        }
        return set;        
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public int getSize() {
        return numOfRows * numOfCols;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (numOfRows > 1) {
            for (int i = 0; i < numOfRows; i++) {
                sb.append("[");
                for (int j = 0; j < numOfCols; j++) {
                    sb.append(getEntry(i, j)).append(j == numOfCols - 1 ? "" : " ");
                }
                sb.append("]").append(i == numOfRows - 1 ? "" : "\n ");
            }
        } else {
            for (int i = 0; i < numOfCols; i++) {
                sb.append(getEntry(0, i)).append(i == numOfCols - 1 ? "" : " ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public Matrix deepCopy() {
        ArrayList<ArrayList<Double>> copyList = new ArrayList<>();
        for(int i=0;i<numOfRows;i++){
            copyList.add(new ArrayList<Double>());
            for(int j=0;j<numOfCols;j++){
                copyList.get(i).add(matrix.get(i).get(j));
            }
        }
        return new Matrix(numOfRows, numOfCols, copyList);
    }

}
