package com.library.api.utils;

import java.text.Normalizer;

public class AccentRemover {
    public static String removeAccents(String input) {
        if (input == null) return null;
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
