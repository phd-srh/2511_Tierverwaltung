package controller;

import view.MainView;

public class MainController {
    private MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;
    }

    public static void main(String[] args) {
        new MainController( new MainView() );
    }
}
