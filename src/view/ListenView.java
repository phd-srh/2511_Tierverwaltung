package view;

import model.Tier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseListener;

public class ListenView extends JFrame {
    private JList<Tier> tierJList;

    public ListenView() {
        setTitle("Alle Tiere");
        setSize(400, 250);
        addComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addComponents() {
        getRootPane().setBorder( new EmptyBorder(5,5,5,5) );
        setLayout( new GridLayout(1,1) );

        tierJList = new JList<>();
        tierJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tierJList);
        add(scrollPane);
    }

    public void addDefaultTierModel(DefaultListModel<Tier> tierListModel) {
        tierJList.setModel(tierListModel);
    }

    public void addTierJListMouseListener(MouseListener listener) {
        tierJList.addMouseListener(listener);
    }

    public Tier getSelectedTier() {
        return tierJList.getSelectedValue();
    }
}
