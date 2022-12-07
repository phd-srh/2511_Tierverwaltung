package DAO;

import model.Persönlichkeit;
import model.Tier;
import model.Tierart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempTierDB implements TierDAO {
    private List<Tier> tierListe;
    private List<Tierart> tierartListe;

    public TempTierDB() {
        tierListe = new ArrayList<>();
        tierartListe = new ArrayList<>();

        // hier könnten wir noch ein paar Testdaten hinzufügen
        Tierart hundeArt = new Tierart(1, "Hund");
        Tierart katzenArt = new Tierart(2, "Katze");
        Tierart vogelArt = new Tierart(3, "Vogel");
        Tierart fischArt = new Tierart(4, "Fisch");

        insertTierart(hundeArt);
        insertTierart(katzenArt);
        insertTierart(vogelArt);
        insertTierart(fischArt);

        insertTier( new Tier(1, "Lessie", 2, 'w', hundeArt, Persönlichkeit.nett) );
        insertTier( new Tier(2, "Hachiko", 5, 'm', hundeArt, Persönlichkeit.treu) );
        insertTier( new Tier(6, "Tom", 3, 'm', katzenArt, Persönlichkeit.schelmisch) );
        insertTier( new Tier(10, "Kiara", 1, 'w', katzenArt, Persönlichkeit.frech) );
        insertTier( new Tier(11, "Zazu", 12, 'm', vogelArt, Persönlichkeit.scheu) );
        insertTier( new Tier(12, "Hedwig", 6, 'd', vogelArt, Persönlichkeit.neugierig) );
        insertTier( new Tier(15, "Nemo",'1','d', fischArt, Persönlichkeit.neugierig) );
        insertTier( new Tier(16, "Dori",'2','w', fischArt, Persönlichkeit.aufgeweckt) );
    }

    private static Tier cloneTier(Tier tier) {
        if (tier == null) return null;
        return new Tier(tier.getChipnummer(), tier.getName(), tier.getAlter(),
                tier.getGeschlecht(),
                cloneTierart(tier.getArt()), tier.getPersönlichkeit());
    }

    private static Tierart cloneTierart(Tierart tierart) {
        if (tierart == null) return null;
        return new Tierart(tierart.getArtnummer(), tierart.getBezeichnung());
    }

    @Override
    public boolean insertTier(Tier tier) {
        if (tier == null) return false;
        for (Tier t : tierListe) {
            if (t.getChipnummer() == tier.getChipnummer())
                return false;
        }
        tierListe.add( cloneTier(tier) );
        return true;
    }

    @Override
    public Tier getTierByChipnummer(int chipnummer) {
        for (Tier tier : tierListe) {
            if (chipnummer == tier.getChipnummer())
                return cloneTier(tier);
        }
        return null;
    }

    @Override
    public List<Tier> getAllTiere() {
        List<Tier> copyList = new ArrayList<>( tierListe.size() );
        for (Tier tier : tierListe) {
            copyList.add( cloneTier(tier) );
        }
        return copyList;
    }

    @Override
    public boolean updateTier(int chipnummer, Tier tier) {
        deleteTier(chipnummer);
        return insertTier(tier);
    }

    @Override
    public boolean deleteTier(int chipnummer) {
        for (int i=0; i < tierListe.size(); i++) {
            if (tierListe.get(i).getChipnummer() == chipnummer) {
                tierListe.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int holeNächsteFreieChipnummer() {
        int maxChipnummer = 0;
        for (Tier tier : tierListe) {
            if (tier.getChipnummer() > maxChipnummer)
                maxChipnummer = tier.getChipnummer();
        }
        return maxChipnummer + 1;
    }

    @Override
    public boolean insertTierart(Tierart tierart) {
        if (tierart == null) return false;
        for (Tierart ta : tierartListe) {
            if (ta.getArtnummer() == tierart.getArtnummer())
                return false;
        }
        tierartListe.add( cloneTierart(tierart) );
        return true;
    }

    @Override
    public Tierart getTierartByArtnummer(int artnummer) {
        for (Tierart tierart : tierartListe) {
            if (artnummer == tierart.getArtnummer())
                return cloneTierart(tierart);
        }
        return null;
    }

    @Override
    public int getArtnummerByBezeichnung(String bezeichnung) {
        for (Tierart tierart : tierartListe) {
            if ( bezeichnung.equals(tierart.getBezeichnung()) )
                return tierart.getArtnummer();
        }
        return 0;
    }

    @Override
    public List<Tierart> getAllTierarten() {
        List<Tierart> copyList = new ArrayList<>( tierartListe.size() );
        for (Tierart tierart : tierartListe) {
            copyList.add( cloneTierart(tierart) );
        }
        return copyList;
    }

    @Override
    public boolean updateTierart(int artnummer, Tierart tierart) {
        deleteTierart(artnummer);
        return insertTierart(tierart);
    }

    @Override
    public boolean deleteTierart(int artnummer) {
        for (int i=0; i < tierartListe.size(); i++) {
            if (tierartListe.get(i).getArtnummer() == artnummer) {
                tierartListe.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int holeNächsteFreieArtnummer() {
        int maxArtnummer = 0;
        for (Tierart tierart : tierartListe) {
            if (tierart.getArtnummer() > maxArtnummer)
                maxArtnummer = tierart.getArtnummer();
        }
        return maxArtnummer + 1;
    }

    @Override
    public Persönlichkeit getPersönlichkeitByPersönlichkeitsnummer(int persönlichkeitsnummer) {
        return Persönlichkeit.values()[ persönlichkeitsnummer ];
    }

    @Override
    public int getPersönlichkeitsnummerByBezeichnung(String bezeichnung) {
        return Persönlichkeit.valueOf(bezeichnung).ordinal();
    }

    @Override
    public List<Persönlichkeit> getAllPersönlichkeiten() {
        List<Persönlichkeit> persönlichkeitList = new ArrayList<>();
        persönlichkeitList.addAll(Arrays.asList(Persönlichkeit.values()));
        return persönlichkeitList;
    }
}
