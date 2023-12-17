package ru.muzafarov.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {

    public static String getString() {
        return RandomStringUtils.randomAlphabetic(10) + "_test_api";

    }
}
