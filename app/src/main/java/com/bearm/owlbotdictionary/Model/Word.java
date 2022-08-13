package com.bearm.owlbotdictionary.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Word {

    public String word;
    public String pronunciation;
    public ArrayList<WordEntry> entries;

    public Word(String word, String pronunciation, ArrayList<WordEntry> entries) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.entries = entries;
    }

    public Word(String word, String pronunciation) {
        this.word = word;
        this.pronunciation = pronunciation;
    }
}