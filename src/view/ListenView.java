package view;

import model.Tier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class ListenView extends JFrame {
    private JList<Tier> tierJList;
    private JComboBox<String> tierartComboBox;
    private JTextField tierbezeichnungField;
    private JButton sortiereAlterAbsteigendButton,
            sortiereAlterAufsteigendButton;

    public ListenView() {
        setTitle("Alle Tiere");
        setSize(600, 250);
        addComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addComponents() {
        setLayout( new BorderLayout() );
        JPanel controlPanel = new JPanel( new GridLayout(1,3) );
        JPanel contentPanel = new JPanel(  new GridLayout(1,1) );
        controlPanel.setBorder( new EmptyBorder(5,5,5,5) );
        contentPanel.setBorder( new EmptyBorder(5,5,5,5) );

        tierbezeichnungField = new JTextField();
        controlPanel.add(tierbezeichnungField);

        tierartComboBox = new JComboBox<>();

        JPanel extraPanel = new JPanel( new FlowLayout() );
        sortiereAlterAufsteigendButton = new JButton("\u2b06");
        sortiereAlterAbsteigendButton = new JButton("\u2b07");
        sortiereAlterAufsteigendButton.setToolTipText("Nach Alter aufsteigend sortieren");
        sortiereAlterAbsteigendButton.setToolTipText("Nach Alter absteigend sortieren");

        extraPanel.add( new JLabel("    ") );
        extraPanel.add( sortiereAlterAufsteigendButton );
        extraPanel.add( sortiereAlterAbsteigendButton );
        extraPanel.add( new JLabel("   Tierart:") );
        controlPanel.add( extraPanel );

        controlPanel.add( tierartComboBox );

        tierJList = new JList<>();
        tierJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tierJList);
        contentPanel.add(scrollPane);

        add(controlPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    public void addDefaultTierModel(DefaultListModel<Tier> tierListModel) {
        tierJList.setModel(tierListModel);
    }

    public void addTierartComboBoxListener(ActionListener listener) {
        tierartComboBox.addActionListener(listener);
    }

    public void addTierJListMouseListener(MouseListener listener) {
        tierJList.addMouseListener(listener);
    }

    public void addDefaultTierartModel(DefaultComboBoxModel<String> tierartModel) {
        tierartComboBox.setModel(tierartModel);
    }

    public void addTierbezeichnungFeldKeyListener(KeyListener listener) {
        tierbezeichnungField.addKeyListener(listener);
    }

    public void setSortiereAlterAufsteigendButtonListener(ActionListener listener) {
        sortiereAlterAufsteigendButton.addActionListener(listener);
    }

    public void setSortiereAlterAbsteigendButtonListener(ActionListener listener) {
        sortiereAlterAbsteigendButton.addActionListener(listener);
    }

    public String getTierbezeichnung() {
        return tierbezeichnungField.getText();
    }

    public Tier getSelectedTier() {
        return tierJList.getSelectedValue();
    }

    public String getSelectedTierart() {
        return (String)tierartComboBox.getSelectedItem();
    }
}
