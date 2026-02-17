package pl.polsl.generator.connection;

/**
 * Pair of integer primitives. The {@code first} value is non-greater than the {@code last} value.
 *
 * @author Kay Jay O'Nail
 */
public class Connection
{
    public final int first, last;

    public Connection(int first, int last)
    {
        if (first < last)
        {
            this.first = first;
            this.last = last;
        }
        else
        {
            this.first = last;
            this.last = first;
        }
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
        return obj instanceof Connection that
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
