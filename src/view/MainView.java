package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainView extends JFrame {

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
        JPanel centerPanel = new JPanel( new GridLayout(5,2) );
        JPanel bottomPanel = new JPanel( new FlowLayout() );
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        topPanel.add( new JLabel("Unser kleiner Zooladen") );

        centerPanel.setBorder( new EmptyBorder(5,5,5,5) );
        centerPanel.add( new JLabel("Name:") );
        centerPanel.add( new JTextField() );
        centerPanel.add( new JLabel("Alter:") );
        centerPanel.add( new JTextField() );
        centerPanel.add( new JLabel("Geschlecht:") );
        centerPanel.add( new JTextField() );
        centerPanel.add( new JLabel("Tierart:") );
        centerPanel.add( new JTextField() );
        centerPanel.add( new JLabel("Persönlichkeit:") );
        centerPanel.add( new JTextField() );

        JButton beendenButton = new JButton("Beenden");
        beendenButton.addActionListener( e -> dispose() );
        bottomPanel.add(beendenButton);
    }
}
