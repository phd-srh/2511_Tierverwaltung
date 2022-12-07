package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JButton tierAbfragenButton;
    private JButton tierEinfügenButton;
    private JButton tierLöschenButton;
    private JButton tiereDurchsuchenButton;
    private JTextField chipnummerField, nameField, alterField;
    private JComboBox<String> tierartComboBox,
            persönlichkeitComboBox;
    private JButton tierZurückButton, tierVorwärtsButton;
    private JRadioButton geschlechtM, geschlechtW, geschlechtD;

    public MainView() {
        setTitle("Tierverwaltung");
        setSize(400, 300);
        addComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void addComponents() {
        setLayout( new BorderLayout() );
        JPanel topPanel = new JPanel( new FlowLayout() );
        JPanel centerPanel = new JPanel( new GridLayout(6,2) );
        JPanel bottomPanel = new JPanel( new FlowLayout() );
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        topPanel.add( new JLabel("Unser kleiner Zooladen") );

        centerPanel.setBorder( new EmptyBorder(5,5,5,5) );
        centerPanel.add( new JLabel("Chipnummer:") );
        chipnummerField = new JTextField();
        centerPanel.add( chipnummerField );
        centerPanel.add( new JLabel("Name:") );
        nameField = new JTextField();
        centerPanel.add( nameField );
        centerPanel.add( new JLabel("Alter:") );
        alterField = new JTextField();
        centerPanel.add( alterField );
        centerPanel.add( new JLabel("Geschlecht:") );

        JPanel radioPanel = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        ButtonGroup bg = new ButtonGroup();
        geschlechtM = new JRadioButton("m");
        geschlechtW = new JRadioButton("w");
        geschlechtD = new JRadioButton("d");
        bg.add(geschlechtM);
        bg.add(geschlechtW);
        bg.add(geschlechtD);
        radioPanel.add(geschlechtM);
        radioPanel.add(geschlechtW);
        radioPanel.add(geschlechtD);

        centerPanel.add( radioPanel );
        centerPanel.add( new JLabel("Tierart:") );
        tierartComboBox = new JComboBox<>();            // NEU
        tierartComboBox.setEditable(true);
        centerPanel.add( tierartComboBox );
        centerPanel.add( new JLabel("Persönlichkeit:") );
        persönlichkeitComboBox = new JComboBox<>();
        persönlichkeitComboBox.setEditable(false);
        centerPanel.add( persönlichkeitComboBox );

        tierAbfragenButton = new JButton("Abfragen");
        tierEinfügenButton  = new JButton("Einfügen");
        tierLöschenButton = new JButton("Löschen");
        //tierartErstellenButton = new JButton("Tierart erstellen");
        tiereDurchsuchenButton = new JButton("Durchsuchen");
        tierZurückButton = new JButton("\u2190");
        tierVorwärtsButton = new JButton("\u2192");

        bottomPanel.add(tierZurückButton);
        bottomPanel.add(tierAbfragenButton);
        bottomPanel.add(tierEinfügenButton);
        bottomPanel.add(tierLöschenButton);
        bottomPanel.add(tiereDurchsuchenButton);
        bottomPanel.add(tierVorwärtsButton);
    }

    public void addTierAbfragenButtonListener(ActionListener listener) {
        tierAbfragenButton.addActionListener(listener);
    }

    public void addTierEinfügenButtonListener(ActionListener listener) {
        tierEinfügenButton.addActionListener(listener);
    }

    public void addTierLöschenButtonListener(ActionListener listener) {
        tierLöschenButton.addActionListener(listener);
    }

    public void addTiereDurchsuchenButtonListener(ActionListener listener) {
        tiereDurchsuchenButton.addActionListener(listener);
    }

    public void addDefaultTierartModel(DefaultComboBoxModel<String> tierartModel) {
        tierartComboBox.setModel(tierartModel);
    }

    public void addDefaultPersönlichkeitModel(DefaultComboBoxModel<String> persönlichkeitModel) {
        persönlichkeitComboBox.setModel(persönlichkeitModel);
    }

    public void addTierVorwärtsButtonListener(ActionListener listener) {
        tierVorwärtsButton.addActionListener(listener);
    }

    public void addTierZurückButtonListener(ActionListener listener) {
        tierZurückButton.addActionListener(listener);
    }

    public int getChipnummer() {
        String nummer = chipnummerField.getText();
        if (nummer.isEmpty()) return 0;
        try {
            return Integer.parseInt(nummer);
        }
        catch (NumberFormatException e) {
            zeigeFehlerMeldung("Ungültige Chipnummer");
            return 0;
        }
    }

    public void setChipnummer(int chipnummer) {
        chipnummerField.setText( String.valueOf(chipnummer) );
    }

    public String getName() {
        return nameField.getText();
    }

    public void setName(String name)  {
        nameField.setText(name);
    }

    public int getAlter() {
        try {
            return Integer.parseInt(alterField.getText());
        }
        catch (NumberFormatException e) {
            zeigeFehlerMeldung("Ungültiges Alter");
            return 0;
        }
    }
    public void setAlter(int alter) {
        if (alter <= 0) {
            alterField.setText("");
        } else {
            alterField.setText(String.valueOf(alter));
        }
    }

    public char getGeschlecht() {
        if (geschlechtM.isSelected())
            return 'm';
        else if (geschlechtW.isSelected())
            return 'w';
        else
            return 'd';
    }

    public void setGeschlecht(char geschlecht) {
        if (geschlecht == 'm')
            geschlechtM.setSelected(true);
        else if (geschlecht == 'w')
            geschlechtW.setSelected(true);
        else
            geschlechtD.setSelected(true);
    }

    public String getTierart() {
        return (String)tierartComboBox.getSelectedItem();
    }

    public void setTierart(String tierart) {
        if (tierart.isEmpty()) {
            tierartComboBox.setSelectedIndex(0);
            return;
        }

        for (int i=0; i<tierartComboBox.getItemCount(); i++) {
            if (tierartComboBox.getItemAt(i).equals(tierart)) {
                tierartComboBox.setSelectedIndex(i);
                return;
            }
        }
        tierartComboBox.addItem(tierart);
        tierartComboBox.setSelectedIndex( tierartComboBox.getItemCount()-1 );
    }

    public String getPersönlichkeit() {
        return (String)persönlichkeitComboBox.getSelectedItem();
    }

    public void setPersönlichkeit(String persönlichkeit) {
        if (persönlichkeit.isEmpty()) {
            persönlichkeitComboBox.setSelectedIndex(0);
            return;
        }

        for (int i=0; i<persönlichkeitComboBox.getItemCount(); i++) {
            if (persönlichkeitComboBox.getItemAt(i).equals(persönlichkeit)) {
                persönlichkeitComboBox.setSelectedIndex(i);
                return;
            }
        }
        persönlichkeitComboBox.addItem(persönlichkeit);
        persönlichkeitComboBox.setSelectedIndex( persönlichkeitComboBox.getItemCount()-1 );
    }

    public void zeigeMeldung(String nachricht) {
        JOptionPane.showMessageDialog(this, nachricht, "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void zeigeFehlerMeldung(String nachricht) {
        JOptionPane.showMessageDialog(this, nachricht, "Fehler",
                JOptionPane.ERROR_MESSAGE);
    }

    public boolean zeigeRückfrage(String nachricht) {
        return JOptionPane.showConfirmDialog(this, nachricht, "Rückfrage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION;
    }
}
