package org.vtsukur.rest;

import java.util.Map;

/**
 * @author volodymyr.tsukur
 */
public final class MapBasedHalRepresentations {

    // Non-instantiable by design.
    private MapBasedHalRepresentations() {}

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

    public static boolean selfLinkPresent(final Map<String, ?> representation) {
        return linkPresent(representation, "self");
    }

    public static boolean linkPresent(final Map<String, ?> representation, final String name) {
        final Map<?, ?> _links = subMap(representation, "_links");
        if (_links == null) {
            return false;
        }

        final Map<?, ?> self = subMap(_links, name);
        if (self == null) {
            return false;
        }

        final String href = (String) self.get("href");

        return !href.trim().isEmpty();
    }

    private static Map<?, ?> subMap(final Map<?, ?> source, final String key) {
        return source.get(key) instanceof Map ? (Map<?, ?>) source.get(key) : null;
    }

}
