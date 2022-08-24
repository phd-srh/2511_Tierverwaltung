package controller;

import DAO.SQLTierDB;
import DAO.TierDAO;
import view.MainView;

public class MainController {
    private MainView mainView;
    private TierDAO tierDB;

    public MainController(MainView mainView, TierDAO tierDB) {
        this.mainView = mainView;
        this.tierDB = tierDB;
    }

    public static void main(String[] args) {
        new MainController( new MainView(), new SQLTierDB() );
    }
}
