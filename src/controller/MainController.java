package controller;

import DAO.SQLTierDB;
import DAO.TierDAO;
import model.Persönlichkeit;
import model.Tier;
import model.Tierart;
import view.ListenView;
import view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainController {
    private final MainView mainView;
    private final TierDAO tierDB;

    public MainController(MainView mainView, TierDAO tierDB) {
        this.mainView = mainView;
        this.tierDB = tierDB;

        mainView.addTierAbfragenButtonListener( this::performAbfragen );
        mainView.addTierEinfügenButtonListener( this::performEinfügen );
        mainView.addTierLöschenButtonListener( this::performLöschen );
        mainView.addTiereDurchsuchenButtonListener( this::performDurchsuchen );
    }

    private void performDurchsuchen(ActionEvent actionEvent) {
        ListenView listenView = new ListenView();
        DefaultListModel<Tier> tierListModel = new DefaultListModel<>();
        for (Tier tier : tierDB.getAllTiere()) tierListModel.addElement(tier);
        listenView.addDefaultTierModel(tierListModel);

        listenView.addTierJListMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Tier t = listenView.getSelectedTier();
                    listenView.dispose();
                    mainView.setChipnummer(t.getChipnummer());
                    showTier(t);
                }
            }
        });
    }

    private void performAbfragen(ActionEvent actionEvent) {
        Tier tier = tierDB.getTierByChipnummer( mainView.getChipnummer() );
        if (tier != null) showTier(tier);
        else clearTier();
    }

    private void performEinfügen(ActionEvent actionEvent) {
        boolean updateTier = false;
        int chipnummer = mainView.getChipnummer();
        if (chipnummer <= 0)
            chipnummer = tierDB.holeNächsteFreieChipnummer();
        else {
            if (tierDB.getTierByChipnummer(chipnummer) != null) {
                if (!mainView.zeigeRückfrage(
                        "Chipnummer existiert bereits. Überschreiben erwünscht?"))
                    return;
                updateTier = true;
            }
        }

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
        if (updateTier && tierDB.updateTier(chipnummer, tier) ||
            tierDB.insertTier(tier)) {
            mainView.zeigeMeldung("Tier erfolgreich eingefügt");
            mainView.setChipnummer(chipnummer);
        } else {
            clearTier();
        }
    }

    private void performLöschen(ActionEvent actionEvent) {
        boolean abfrage = mainView.zeigeRückfrage("Soll das Tier wirklich gelöscht werden?");
        if (abfrage) {
            if (tierDB.deleteTier(mainView.getChipnummer())) {
                mainView.zeigeMeldung("Tier erfolgreich gelöscht");
                clearTier();
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
