package com.example.moodtracker.ui.model;

public class Mood {
    private  String thoughts;

    public Mood(String thoughts) {
        this.thoughts = thoughts;
    }

    public Mood() {
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }
}
