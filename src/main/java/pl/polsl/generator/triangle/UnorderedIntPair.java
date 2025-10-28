package pl.polsl.generator.triangle;

/**
 * Pair of integer primitives.
 * 
 * Two pairs compare equal if they have the same items,
 * either in the same or reversed order.
 * 
 * @author Kay Jay O'Nail
 */
public class UnorderedIntPair
{
    public final int item1, item2;

    public UnorderedIntPair(int item1, int item2)
    {
        this.item1 = item1;
        this.item2 = item2;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof UnorderedIntPair that
                ? this.item1 == that.item1 && this.item2 == that.item2 || this.item1 == that.item2 && this.item2 == that.item1
                : false;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + this.item1;
        hash = 97 * hash + this.item2;
        return hash;
    }
}
