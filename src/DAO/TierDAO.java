package DAO;

import model.Persönlichkeit;
import model.Tier;
import model.Tierart;

import java.util.List;

public interface TierDAO {

    boolean insertTier(Tier tier);
    Tier getTierByChipnummer(int chipnummer);
    List<Tier> getAllTiere();
    boolean updateTier(int chipnummer, Tier tier);
    boolean deleteTier(int chipnummer);
    int holeNächsteFreieChipnummer();

    boolean insertTierart(Tierart tierart);
    Tierart getTierartByArtnummer(int artnummer);
    Tierart getArtnummerByBezeichnung(String bezeichnung);
    List<Tierart> getAllTierarten();
    boolean updateTierart(int artnummer, Tierart tierart);
    boolean deleteTierart(int artnummer);
    int holeNächsteFreieArtnummer();

    Persönlichkeit getPersönlichkeitByPersönlichkeitsnummer(int persönlichkeitsnummer);
    Persönlichkeit getPersönlichkeitByBezeichnung(String bezeichnung);
    List<Persönlichkeit> getAllPersönlichkeiten();
}
