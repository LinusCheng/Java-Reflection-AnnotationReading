package practice.util;

import practice.exception.EnumException;

import java.util.EnumSet;

public class EnumUtil {

    public static <E extends Enum<E>> E getEnumByValue(String str, Class<E> elementType) {
        if (str == null) {
            return null;
        }
        for (final E elem : EnumSet.allOf(elementType)) {
            if (elem.toString().equals(str)) {
                return elem;
            }
        }
        throw new EnumException(str + " is not a valid enum " + elementType.getName());
    }

}
