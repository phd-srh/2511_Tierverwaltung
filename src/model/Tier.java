package model;

public class Tier {

    private String name;
    private int alter;
    private char geschlecht;
    private Tierart art;
    private Persönlichkeit persönlichkeit;

    public Tier(String name, int alter, char geschlecht, Tierart art, Persönlichkeit persönlichkeit) {
        this.name = name;
        this.alter = alter;
        this.geschlecht = geschlecht;
        this.art = art;
        this.persönlichkeit = persönlichkeit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public char getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(char geschlecht) {
        this.geschlecht = geschlecht;
    }

    public Tierart getArt() {
        return art;
    }

    public void setArt(Tierart art) {
        this.art = art;
    }

    public Persönlichkeit getPersönlichkeit() {
        return persönlichkeit;
    }

    public void setPersönlichkeit(Persönlichkeit persönlichkeit) {
        this.persönlichkeit = persönlichkeit;
    }
}
