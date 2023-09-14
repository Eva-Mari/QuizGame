package com.example.application.game;

public class URLhandler {

    private final String baseUrl;

    private String category;

    private String difficulty;

    public URLhandler(){
        this.baseUrl = "https://the-trivia-api.com/v2/questions?limit=10";
        this.category = "";
        this.difficulty = "";
    }

    public void setCategory(String categoryValue) {
        this.category = !categoryValue.equals("all") ? "&categories=" + categoryValue : "";
        System.out.println(this.category);
    }

    public void setDifficulty(String difficultyValue) {
        this.difficulty = !difficultyValue.equals("all") ? "&difficulties=" + difficultyValue : "";
    }

    public String getUpdatedUrl() {
        return baseUrl + category + difficulty;
    }

}
