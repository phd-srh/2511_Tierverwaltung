package DAO;

import model.Persönlichkeit;
import model.Tier;
import model.Tierart;

import java.sql.*;
import java.util.List;

public class SQLTierDB implements TierDAO {
    private static final String SQLDriver   = "com.mysql.cj.jdbc.Driver";
    private static final String SQLServer   = "192.168.1.110"; // "localhost" für die meisten
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
            sqlConnection.setAutoCommit(false);
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
        if (artnummer == 0) {
            artnummer = holeNächsteFreieArtnummer();
            tier.getArt().setArtnummer(artnummer);
            insertTierart( tier.getArt() );
        }

        int persönlichkeitsnummer = getPersönlichkeitsnummerByBezeichnung( tier.getPersönlichkeit().toString() );
        if (persönlichkeitsnummer < 0)
            throw new IllegalArgumentException("Unbekannte Persönlichkeit");

        // TODO: INSERT statement zusammen bauen ...
        return true;
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
        return null;
    }

    @Override
    public boolean updateTier(int chipnummer, Tier tier) {
        deleteTier(chipnummer);
        return insertTier(tier);
    }

    @Override
    public boolean deleteTier(int chipnummer) {
        return false;
    }

    @Override
    public int holeNächsteFreieChipnummer() {
        return 0;
    }

    @Override
    public boolean insertTierart(Tierart tierart) {
        return false;
    }

    @Override
    public Tierart getTierartByArtnummer(int artnummer) {
        return null;
    }

    @Override
    public int getArtnummerByBezeichnung(String bezeichnung) {
        return 0;
    }

    @Override
    public List<Tierart> getAllTierarten() {
        return null;
    }

    @Override
    public boolean updateTierart(int artnummer, Tierart tierart) {
        return false;
    }

    @Override
    public boolean deleteTierart(int artnummer) {
        return false;
    }

    @Override
    public int holeNächsteFreieArtnummer() {
        return 0;
    }

    @Override
    public Persönlichkeit getPersönlichkeitByPersönlichkeitsnummer(int persönlichkeitsnummer) {
        return null;
    }

    @Override
    public int getPersönlichkeitsnummerByBezeichnung(String bezeichnung) {
        return -1;
    }

    @Override
    public List<Persönlichkeit> getAllPersönlichkeiten() {
        return null;
    }
}
