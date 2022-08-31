package DAO;

import model.Persönlichkeit;
import model.Tier;
import model.Tierart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLTierDB implements TierDAO {
    private static final String SQLDriver   = "com.mysql.cj.jdbc.Driver";
    private static final String SQLServer   = "localhost"; // "localhost" für die meisten
    private static final String SQLPort     = "3306";
    private static final String SQLDatabase = "2511_tierverwaltung";
    private static final String SQLUsername = "2511_tieradm";
    private static final String SQLPasswort = "geheim123";

    private Connection sqlConnection = null;

    public SQLTierDB() {
        try {
            Class.forName(SQLDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Connector nicht gefunden");
            e.printStackTrace();
            System.exit(-1);
        }
        openConnection();
    }

    private void openConnection() {
        try {
            sqlConnection = DriverManager.getConnection(
                    "jdbc:mysql://" + SQLServer + ":" +
                            SQLPort + "/" + SQLDatabase,
                    SQLUsername, SQLPasswort
            );
            sqlConnection.setAutoCommit(true);  // default, aber damit es klar ist!
        }
        catch (SQLException e) {
            System.err.println("Problem beim Aufbau der Datenbankverbindung");
            e.printStackTrace();
            System.exit(-2);
        }
    }

    @Override
    public boolean insertTier(Tier tier) {
        if (tier == null) return false;

        int artnummer = getArtnummerByBezeichnung( tier.getArt().getBezeichnung() );
        if (artnummer > 0) tier.getArt().setArtnummer(artnummer);
        else {
            artnummer = holeNächsteFreieArtnummer();
            tier.getArt().setArtnummer(artnummer);
            insertTierart( tier.getArt() );
        }

        int persönlichkeitsnummer = getPersönlichkeitsnummerByBezeichnung( tier.getPersönlichkeit().toString() );
        if (persönlichkeitsnummer < 0)
            throw new IllegalArgumentException("Unbekannte Persönlichkeit");

        int neueDatensätze = 0;
        try {
            PreparedStatement insertCommand = sqlConnection.prepareStatement(
                    """
                    INSERT INTO tiere (chipnummer, name, `alter`, geschlecht, tierart, persönlichkeit)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """
            );
            insertCommand.setInt(1, tier.getChipnummer());
            insertCommand.setString(2, tier.getName());
            insertCommand.setInt(3, tier.getAlter());
            insertCommand.setString(4, String.valueOf(tier.getGeschlecht()));
            insertCommand.setInt(5, artnummer);
            insertCommand.setInt(6, persönlichkeitsnummer);
            neueDatensätze = insertCommand.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println("Problem beim Einfügen in die Datenbank");
            e.printStackTrace();
        }
        return (neueDatensätze > 0);
    }

    @Override
    public Tier getTierByChipnummer(int chipnummer) {
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT chipnummer, name, `alter`, geschlecht, tierart, persönlichkeit
                    FROM tiere
                    WHERE chipnummer = ?
                    """
            );
            selectCommand.setInt(1, chipnummer);
            ResultSet ergebnis = selectCommand.executeQuery();
            if ( ergebnis.next() )
                return erstelleTier(ergebnis);
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return null;
    }

    private Tier erstelleTier(ResultSet ergebnis) throws SQLException {
        int chipnummer = ergebnis.getInt("chipnummer");
        String name = ergebnis.getString("name");
        int alter = ergebnis.getInt("alter");
        char geschlecht = ergebnis.getString("geschlecht").charAt(0);
        Tierart tierart = getTierartByArtnummer( ergebnis.getInt("tierart") );
        Persönlichkeit persönlichkeit = getPersönlichkeitByPersönlichkeitsnummer( ergebnis.getInt("persönlichkeit") );
        return new Tier(chipnummer, name, alter, geschlecht, tierart, persönlichkeit);
    }

    @Override
    public List<Tier> getAllTiere() {
        List<Tier> tiere = new ArrayList<>();
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT chipnummer, name, `alter`, geschlecht, tierart, persönlichkeit
                    FROM tiere
                    ORDER BY chipnummer
                    """
            );
            ResultSet ergebnis = selectCommand.executeQuery();
            while ( ergebnis.next() )
                tiere.add( erstelleTier(ergebnis) );
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return tiere;
    }

    @Override
    public boolean updateTier(int chipnummer, Tier tier) {
        deleteTier(chipnummer);
        return insertTier(tier);
    }

    @Override
    public boolean deleteTier(int chipnummer) {
        int gelöschteDatensätze = 0;
        try {
            PreparedStatement deleteCommand = sqlConnection.prepareStatement(
                    """
                    DELETE FROM tiere
                    WHERE chipnummer = ?
                    """
            );
            deleteCommand.setInt(1, chipnummer);
            gelöschteDatensätze = deleteCommand.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println("Problem beim Löschen aus der Datenbank");
            e.printStackTrace();
        }
        return (gelöschteDatensätze > 0);
    }

    @Override
    public int holeNächsteFreieChipnummer() {
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT MAX(chipnummer) AS max
                    FROM tiere
                    """
            );
            ResultSet ergebnis = selectCommand.executeQuery();
            if ( ergebnis.next() )
                return ergebnis.getInt("max") + 1;
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return 0;
    }

    // ----------------------------------------------------------------

    @Override
    public boolean insertTierart(Tierart tierart) {
        if (tierart == null) return false;
        try {
            PreparedStatement insertCommand = sqlConnection.prepareStatement(
                    """
                    INSERT INTO tierart (tierart, bezeichnung)
                    VALUES (?, ?)
                    """
            );
            insertCommand.setInt(1, tierart.getArtnummer());
            insertCommand.setString(2, tierart.getBezeichnung());
            return (insertCommand.executeUpdate() > 0);
        }
        catch (SQLException e) {
            System.err.println("Problem beim Einfügen in die Datenbank");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Tierart getTierartByArtnummer(int artnummer) {
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT tierart, bezeichnung
                    FROM tierart
                    WHERE tierart = ?
                    """
            );
            selectCommand.setInt(1, artnummer);
            ResultSet ergebnis = selectCommand.executeQuery();
            if ( ergebnis.next() )
                return new Tierart(ergebnis.getInt("tierart"), ergebnis.getString("bezeichnung"));
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getArtnummerByBezeichnung(String bezeichnung) {
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT tierart
                    FROM tierart
                    WHERE bezeichnung = ?
                    """
            );
            selectCommand.setString(1, bezeichnung);
            ResultSet ergebnis = selectCommand.executeQuery();
            if ( ergebnis.next() )
                return ergebnis.getInt("tierart");
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Tierart> getAllTierarten() {
        List<Tierart> tierarten = new ArrayList<>();
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT tierart, bezeichnung
                    FROM tierart
                    ORDER BY tierart
                    """
            );
            ResultSet ergebnis = selectCommand.executeQuery();
            while ( ergebnis.next() )
                tierarten.add( new Tierart(ergebnis.getInt("tierart"), ergebnis.getString("bezeichnung")) );
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return tierarten;
    }

    @Override
    public boolean updateTierart(int artnummer, Tierart tierart) {
        deleteTierart(artnummer);
        return insertTierart(tierart);
    }

    @Override
    public boolean deleteTierart(int artnummer) {
        try {
            PreparedStatement deleteCommand = sqlConnection.prepareStatement(
                    """
                    DELETE FROM tierart
                    WHERE tierart = ?
                    """
            );
            deleteCommand.setInt(1, artnummer);
            return (deleteCommand.executeUpdate() > 0);
        }
        catch (SQLException e) {
            System.err.println("Problem beim Löschen aus der Datenbank");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int holeNächsteFreieArtnummer() {
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT MAX(tierart) AS max
                    FROM tierart
                    """
            );
            ResultSet ergebnis = selectCommand.executeQuery();
            if ( ergebnis.next() )
                return ergebnis.getInt("max") + 1;
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return 0;
    }

    // ----------------------------------------------------------------

    @Override
    public Persönlichkeit getPersönlichkeitByPersönlichkeitsnummer(int persönlichkeitsnummer) {
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT persönlichkeit, bezeichnung
                    FROM persönlichkeit
                    WHERE persönlichkeit = ?
                    """
            );
            selectCommand.setInt(1, persönlichkeitsnummer);
            ResultSet ergebnis = selectCommand.executeQuery();
            if ( ergebnis.next() )
                return Persönlichkeit.valueOf(ergebnis.getString("bezeichnung"));
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getPersönlichkeitsnummerByBezeichnung(String bezeichnung) {
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT persönlichkeit
                    FROM persönlichkeit
                    WHERE bezeichnung = ?
                    """
            );
            selectCommand.setString(1, bezeichnung);
            ResultSet ergebnis = selectCommand.executeQuery();
            if ( ergebnis.next() )
                return ergebnis.getInt("persönlichkeit");
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Persönlichkeit> getAllPersönlichkeiten() {
        List<Persönlichkeit> persönlichkeiten = new ArrayList<>();
        try {
            PreparedStatement selectCommand = sqlConnection.prepareStatement(
                    """
                    SELECT persönlichkeit, bezeichnung
                    FROM persönlichkeit
                    ORDER BY persönlichkeit
                    """
            );
            ResultSet ergebnis = selectCommand.executeQuery();
            while ( ergebnis.next() )
                persönlichkeiten.add( Persönlichkeit.valueOf(ergebnis.getString("bezeichnung")) );
        }
        catch (SQLException e) {
            System.err.println("Problem beim Abfrage der Datenbank");
            e.printStackTrace();
        }
        return persönlichkeiten;
    }
}
