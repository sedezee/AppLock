package time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A pair class, designed to hold ordered L and R data.
 *
 * @author Holland Louis Delrin
 */
public class Pair<L, R> implements Serializable {
    private static final long serialVersionUID = 0;
    private final L left;
    private final R right;

    /**
     * Constructs and places data within the pair
     * @param left the left hand data, of type L
     * @param right the right hand data, of type R
     */
    public Pair (L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Get the left hand side of the pair
     * @return the left hand data
     */
    public L left() {
        return left;
    }

    /**
     * Get the right hand side of the pair
     * @return the right hand data
     */
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

        Pair<?,?> oPair = (Pair<?,?>) other;
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
}