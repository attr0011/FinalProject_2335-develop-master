package com.bearm.owlbotdictionary.Model;


public class WordEntry {

    public String word;
    public String pronunciation;
    public String definition;
    public String type;
    public String image;
    public String example;

    public WordEntry(String word, String pronunciation, String definition, String type, String image, String example) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.definition = definition;
        this.type = type;
        this.image = image;
        this.example = example;
    }
}
