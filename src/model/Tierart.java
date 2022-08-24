package model;

public class Tierart {

    private int artnummer;
    private String bezeichnung;

    public Tierart(int artnummer, String bezeichnung) {
        this.artnummer = artnummer;
        this.bezeichnung = bezeichnung;
    }

    public int getArtnummer() {
        return artnummer;
    }

    public void setArtnummer(int artnummer) {
        this.artnummer = artnummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
