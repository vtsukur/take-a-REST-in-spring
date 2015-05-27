package org.vtsukur.rest;

import java.util.Map;

/**
 * @author volodymyr.tsukur
 */
public final class MapBasedRepresentations {

    // Non-instantiable by design.
    private MapBasedRepresentations() {}

    public static boolean propertyMatches(final Map<String, ?> representation, final String compoundKey, final Object expectedValue) {
        final String[] keys = compoundKey.split("\\.");

        Map level = representation;
        int depth = 0;
        while (depth < keys.length - 1) {
            final Object rawLevel = representation.get(keys[depth]);
            if (!(rawLevel instanceof Map)) {
                return false;
            }

            level = (Map) rawLevel;
            ++depth;
        }

        final Object value = level.get(keys[depth]);
        return valueEquals(value, expectedValue);
    }

    private static boolean valueEquals(final Object lhs, final Object rhs) {
        if (lhs == null || rhs == null) {
            return lhs == rhs;
        }

        if (lhs == rhs) {
            return true;
        }

        if (rhs instanceof Number && lhs instanceof Number) {
            return ((Number) rhs).doubleValue() == ((Number) lhs).doubleValue();
        }
        else {
            return rhs.equals(lhs);
        }
    }

}
