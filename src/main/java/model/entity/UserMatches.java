package model.entity;

public class UserMatches {

    private String name;
    private int matches;


    public UserMatches(String name, int matches) {
        this.name = name;
        this.matches = matches;
    }

    public String getName() {
        return name;
    }

    public int getMatches() {
        return matches;
    }
}
