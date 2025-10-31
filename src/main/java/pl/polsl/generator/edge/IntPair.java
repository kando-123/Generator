package pl.polsl.generator.edge;

/**
 * Pair of integer primitives.
 *
 * @author Kay Jay O'Nail
 */
public class IntPair
{
    public final int first, last;

    public IntPair(int first, int last)
    {
        this.first = first;
        this.last = last;
    }

    public int getFirst()
    {
        return first;
    }

    public int getLast()
    {
        return last;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof IntPair that
                ? this.first == that.first && this.last == that.last
                : false;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + this.first;
        hash = 97 * hash + this.last;
        return hash;
    }
}
