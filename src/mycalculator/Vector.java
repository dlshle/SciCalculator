package mycalculator;

import java.util.ArrayList;

/**The vector class of the calculator
 *
 * @author dlshle(Xuri Li)
 */
public class Vector {
    private double[] components;
    private int dimension;
    
    public Vector(double[] com){
        this.dimension = com.length;
        this.components = new double[this.dimension];
        for(int i=0;i<this.dimension;i++)
            this.components[i] = com[i];
    }
    
    public Vector(ArrayList<Double> com){
        this.dimension = com.size();
        this.components = new double[this.dimension];
        for(int i=0;i<this.dimension;i++){
            this.components[i] = com.get(i);
        }
    }
    
    public Vector(){
        dimension = 0;
    }
    
    public boolean isZero(){
        if(dimension==0)
            return true;
        for(int i=0;i<dimension;i++){
            if(components[i]!=0)
                return false;
        }
        return true;
    }
    
    public Vector add(Vector v){
        if(this.dimension!=v.getDimension())
            return null;
        double com[] = new double[dimension];
        for(int i=0;i<dimension;i++){
            com[i]=components[i]+v.getComponent(i);
        }
        return new Vector(com);
    }
    
    public Vector subtract(Vector v){
        if(this.dimension!=v.getDimension())
            return null;
        double com[] = new double[dimension];
        for(int i=0;i<dimension;i++){
            com[i]=components[i]-v.getComponent(i);
        }
        return new Vector(com);        
    }
    
    public double dot(Vector v){
        if(this.dimension!=v.getDimension())
            return Double.MIN_VALUE;
        double sum = 0.0;
        for(int i=0;i<dimension;i++){
            sum+=components[i]*v.getComponent(i);
        }
        return sum;        
    }
    
    public Vector scalarProduct(double scalar){
        if(this.dimension == 0)
            return null;
        double com[] = new double[dimension];
        for(int i=0;i<dimension;i++){
            com[i]=components[i]*scalar;
        }
        return new Vector(com);
    }
    
    public double cross(Vector v){
        //TODO: cross product(+-+-...) the 2nd determinent is reversed
        return Double.MIN_VALUE;
    }
    
    public double getComponent(int index){
        if(index>=dimension)
            return Double.MIN_VALUE;
        return components[index];
    }
    
    public int getDimension(){
        return this.dimension;
    }
    
    public double get2DDirection(){
        if(dimension!=2)
            return Double.MIN_VALUE;
        return Math.atan(components[1]/components[0]);
    }
    
    public Matrix toMatrix(){
        if(dimension==0)
            return null;
        Matrix m = new Matrix();
        m.addColArray(components);
        return m;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        for(int i=0;i<dimension;i++){
            sb.append(components[i]).append(i==dimension-1?">":" ");
        }
        return sb.toString();
    }
}
