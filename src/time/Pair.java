package src.time;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;


public class Pair<L, R> implements Serializable {
    private static final long serialVersionUID = 0; 
    private final L left; 
    private final R right; 

    public Pair (L left, R right) {
        this.left = left; 
        this.right = right; 
    }

    public L left() {
        return left; 
    }

    public R right() {
        return right; 
    }

    public int hashCode() {
        final int prime = 31; 
        return prime + (left == null ? 0 : left.hashCode()) * (right == null ? 0 : right.hashCode()); 
    }

    public boolean equals(Object other) {
        if (!(other instanceof Pair)) 
            return false; 
        
        Pair oPair = (Pair) other;
        return this.left.equals(oPair.left) && this.right.equals(oPair.right);     
    }

    public String toString() {
        return "<" + left() + ", " + right.toString() + ">"; 
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }   

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); 
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream Data Required"); 
    }
}