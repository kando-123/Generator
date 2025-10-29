package pl.polsl.generator.triangle;

/**
 *
 * @author Kay Jay O'Nail
 */
public class OrderedIntPair extends IntPair
{
    public OrderedIntPair(int item1, int item2)
    {
        super(item1 < item2 ? item1 : item2, item1 < item2 ? item2 : item1);
    }
}
