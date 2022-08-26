package controller;

import DAO.SQLTierDB;
import DAO.TierDAO;
import model.Tier;
import view.MainView;

import java.awt.event.ActionEvent;

public class MainController {
    private MainView mainView;
    private TierDAO tierDB;

    public MainController(MainView mainView, TierDAO tierDB) {
        this.mainView = mainView;
        this.tierDB = tierDB;

        mainView.addTierAbfragenButtonListener( this::performAbfragen );
    }

    private void performAbfragen(ActionEvent actionEvent) {
        Tier tier = tierDB.getTierByChipnummer( mainView.getChipnummer() );
        if (tier != null) showTier(tier);
        else clearTier();
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
