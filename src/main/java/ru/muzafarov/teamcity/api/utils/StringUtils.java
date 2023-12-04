package ru.muzafarov.teamcity.api.utils;

import java.util.Objects;

public class StringUtils {

    public static String format(String str, Object... args) {
        for (Object arg : args)
            str = str.replaceFirst("\\{}", Objects.requireNonNull(arg).toString());
        return str;
    }
}
