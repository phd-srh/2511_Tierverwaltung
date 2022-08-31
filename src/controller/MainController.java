package controller;

import DAO.SQLTierDB;
import DAO.TierDAO;
import model.Persönlichkeit;
import model.Tier;
import model.Tierart;
import view.MainView;

import java.awt.event.ActionEvent;

public class MainController {
    private MainView mainView;
    private TierDAO tierDB;

    public MainController(MainView mainView, TierDAO tierDB) {
        this.mainView = mainView;
        this.tierDB = tierDB;

        mainView.addTierAbfragenButtonListener( this::performAbfragen );
        mainView.addTierEinfügenButtonListener( this::performEinfügen );
        mainView.addTierLöschenButtonListener( this::performLöschen );
    }

    private void performAbfragen(ActionEvent actionEvent) {
        Tier tier = tierDB.getTierByChipnummer( mainView.getChipnummer() );
        if (tier != null) showTier(tier);
        else clearTier();
    }

    private void performEinfügen(ActionEvent actionEvent) {
        int chipnummer = tierDB.holeNächsteFreieChipnummer();
        String name = mainView.getName();
        int alter = mainView.getAlter();
        char geschlecht = mainView.getGeschlecht();
        String tierartBezeichnung = mainView.getTierart();
        String persönlichkeitBezeichnung = mainView.getPersönlichkeit();

        // sanity check
        if (name.isEmpty() || alter <= 0 ||
                (geschlecht != 'm' && geschlecht != 'w' && geschlecht != 'd') ||
                tierDB.getPersönlichkeitsnummerByBezeichnung(persönlichkeitBezeichnung) < 0) {
            mainView.zeigeFehlerMeldung("Ungültige Eingabedaten. Bitte korrigieren!");
            return;
        }

        Tier tier = new Tier(
                chipnummer,
                name,
                alter,
                geschlecht,
                new Tierart( 0, tierartBezeichnung ),
                Persönlichkeit.valueOf( persönlichkeitBezeichnung )
        );
        if (tierDB.insertTier(tier)) {
            mainView.zeigeMeldung("Tier erfolgreich eingefügt");
        } else {
            clearTier();
        }
    }

    private void performLöschen(ActionEvent actionEvent) {
        boolean abfrage = mainView.zeigeRückfrage("Soll das Tier wirklich gelöscht werden?");
        if (abfrage) {
            if (tierDB.deleteTier(mainView.getChipnummer())) {
                mainView.zeigeMeldung("Tier erfolgreich gelöscht");
            } else {
                mainView.zeigeFehlerMeldung("Tier konnte nicht gelöscht werden");
            }
        }
    }

    private void showTier(Tier tier) {
        mainView.setName( tier.getName() );
        mainView.setAlter( tier.getAlter() );
        mainView.setGeschlecht( tier.getGeschlecht() );
        mainView.setTierart( tier.getArt().getBezeichnung() );
        mainView.setPersönlichkeit( tier.getPersönlichkeit().toString() );
    }

    private void clearTier() {
        mainView.setName( "" );
        mainView.setAlter( 0 );
        mainView.setGeschlecht( ' ' );
        mainView.setTierart( "" );
        mainView.setPersönlichkeit( "" );
    }

    public static void main(String[] args) {
        new MainController( new MainView(), new SQLTierDB() );
    }
}
