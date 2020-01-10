package Web;

public class NullCoalescing {

    public static <T> T coalesce(T one, T two)
    {
        return one != null ? one : two;
    }
}
