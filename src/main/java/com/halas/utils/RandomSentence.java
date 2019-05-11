package com.halas.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomSentence {
    private static final List<String> sentences = Arrays.asList(
            "Hello, i am a robot",
            "How are you!?",
            "Do you know java!?",
            "Can you tell me something?",
            "You are the best!",
            "Mr. Groot, hello!",
            "Selenium is super, innit?");

    private RandomSentence() {
    }

    public static String getSentence() {
        return sentences.get(new Random().nextInt(sentences.size()));
    }
}
