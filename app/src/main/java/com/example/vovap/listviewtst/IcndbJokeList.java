package com.example.vovap.listviewtst;

import java.util.ArrayList;
import java.util.List;

public class IcndbJokeList {

    private String type;
    private Joke[] value;

    public List<String> getJoke() {
        List<String> l = new ArrayList<>();
        for (Joke v: value){
            l.add(v.getJoke());
        }

        return l;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Joke[] getValue() {
        return value;
    }

    public void setValue(Joke[] value) {
        this.value = value;
    }

    private static class Joke {
        private int id;
        private String joke;
        private String[] categories;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJoke() {
            return joke;
        }

        public void setJoke(String joke) {
            this.joke = joke;
        }

        public String[] getCategories() {
            return categories;
        }

        public void setCategories(String[] categories) {
            this.categories = categories;
        }
    }
}
