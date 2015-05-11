package org.vtsukur.rest;

import java.util.Map;

/**
 * @author volodymyr.tsukur
 */
public final class MapBasedHalRepresentations {

    // Non-instantiable by design.
    private MapBasedHalRepresentations() {}

    public static boolean propertyMatches(final Map<String, ?> representation, final String key, final String expectedValue) {
        return representation.get(key).equals(expectedValue);
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
