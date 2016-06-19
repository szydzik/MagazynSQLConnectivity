/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magazynsqlconnectivity.dao;

import com.magazynsqlconnectivity.data.Magazynp;
import com.magazynsqlconnectivity.data.NumerKarty;
import com.magazynsqlconnectivity.data.Odpad;
import com.magazynsqlconnectivity.utils.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bartosz Szydzik
 */
public class SQLMagazynpDao implements DaoManual<Magazynp> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    DBConnection dbc = new DBConnection();

    private static SQLMagazynpDao instance;

    public synchronized static SQLMagazynpDao getInstance() {
        if (instance == null) {
            instance = new SQLMagazynpDao();
        }
        return instance;
    }

    @Override
    public void insert(Magazynp t) {
        String sql = "INSERT INTO magazynp (ID, NR_KARTY, ODPAD_ID, NR_KLIENTA, FIRMA, JEDN, MASA, DATAD) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, t.getID());
            ps.setString(2, t.getNR_KARTY().toString());
            ps.setLong(3, t.getODPAD().getID());
            ps.setInt(4, t.getNR_KLIENTA());
            ps.setInt(5, t.getFIRMA());
            ps.setString(6, t.getJEDN());
            ps.setDouble(7, t.getMASA());
            ps.setDate(8, new java.sql.Date(t.getDATAD().getTime()));
            ps.execute();
            System.out.println("Dodano rekord");

        } catch (SQLException se) {
            System.out.println("Blad podczas dodawania " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas dodawania " + e.getMessage());
        }
    }

    @Override
    public void update(Magazynp t) {
        String sql = "UPDATE magazynp SET NR_KARTY = ?, ODPAD_ID = ?, NR_KLIENTA = ?, FIRMA = ?, JEDN = ?, MASA = ?, DATAD = ? WHERE ID = ?";

        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getNR_KARTY().toString());
            ps.setLong(2, t.getODPAD().getID());
            ps.setInt(3, t.getNR_KLIENTA());
            ps.setInt(4, t.getFIRMA());
            ps.setString(5, t.getJEDN());
            ps.setDouble(6, t.getMASA());
            ps.setDate(7, new java.sql.Date(t.getDATAD().getTime()));
            ps.setLong(8, t.getID());
            ps.executeUpdate();
            System.out.println("Zmodyfikowano rekord");

        } catch (SQLException se) {
            System.out.println("Blad podczas Edycji " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas Edycji " + e.getMessage());
        }

    }

    @Override
    public void delete(Magazynp t) {
        String sql = "DELETE FROM magazynp WHERE ID = ?";
        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setLong(1, t.getID());
            ps.executeUpdate();
            System.out.println("Usunięto rekord");
        } catch (SQLException se) {
            System.out.println("Blad podczas usuwania " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas usuwania " + e.getMessage());
        }
    }

    @Override
    public Magazynp findOne(Long id) {
//        String sql = "SELECT ID, NR_KARTY_id, NR_ODPADU, NR_KLIENTA, FIRMA, JEDN, MASA, DATAD FROM magazynp WHERE ID = '" + id + "'";
//        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            rs.next();
//            NumerKarty nk = new NumerKarty(rs.getString(2));
//            Odpad = new Odpad
////            Magazynp m = new Magazynp(rs.getLong(1), rs.getLong(2), nk, rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getDouble(8), rs.getDate(9));
//            Magazynp m1 = new Magazynp(rs.getLong(1), nk, rs.getLong(3), , Integer.SIZE, sql, Double.NaN, DATAD);
//
//            System.out.println("Pobrano rekord");
//            return m;
//        } catch (SQLException se) {
//            System.out.println("Blad podczas odczytu " + se.getMessage());
//        } catch (Exception e) {
//            System.out.println("Blad podczas odczytu " + e.getMessage());
//        }
        return null;
    }

    @Override
    public List<Magazynp> findAll() {
        List<Magazynp> objs = new ArrayList<>();
        List<Odpad> odpady = new SQLOdpadDao().findAll();
        String sql = "SELECT ID, NR_KARTY, ODPAD_ID, NR_KLIENTA, FIRMA, JEDN, MASA, DATAD FROM magazynp";
        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NumerKarty nk = new NumerKarty(rs.getString(2));
                int idOdpadu = rs.getInt(3);
                Odpad odpad = (Odpad) odpady.stream().filter(o -> o.getID() == idOdpadu).findAny().orElse(null);
                Magazynp m = new Magazynp(rs.getLong(1), nk, odpad, rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDate(8));
                objs.add(m);
            }
        } catch (SQLException se) {
            System.out.println("Blad podczas odczytu " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas odczytu " + e.getMessage());
        }

        return objs;
    }

    public void insertBigData(List<Magazynp> list) {
        String sql = "INSERT INTO magazynp (ID, NR_KARTY, ODPAD_ID, NR_KLIENTA, FIRMA, JEDN, MASA, DATAD) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            c.setAutoCommit(false);

            int i = 0;
            int wynik = 0;
            for (Magazynp t : list) {
                if (i++ >= 10000) {
                    wynik += ps.executeLargeBatch().length;
                    c.commit();
                    System.out.println("BATCH EXECUTE!!!");
                    ps.clearBatch();
                    i = 0;
                }
                ps.setLong(1, t.getID());
                ps.setObject(2, t.getNR_KARTY().toString());
                ps.setLong(3, t.getODPAD().getID());
                ps.setObject(4, t.getNR_KLIENTA());
                ps.setObject(5, t.getFIRMA());
                ps.setObject(6, t.getJEDN());
                ps.setObject(7, t.getMASA());
                ps.setDate(8, new java.sql.Date(t.getDATAD().getTime()));
                ps.addBatch();

            }

            wynik += ps.executeLargeBatch().length;
            c.commit();
            c.setAutoCommit(true);
            System.out.println("Wynik = " + wynik);
            System.out.println("Dodano rekordy!");

        } catch (SQLException se) {
            System.out.println("Blad podczas dodawania " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas dodawania " + e.getMessage());
        }
    }

    public NumerKarty findNumerKartyFromID(Long id) {
        String sql = "{call getNR_KARTY (?, ?)}";
        NumerKarty nk = null;
        try (Connection c = dbc.getConnection(); CallableStatement stmt = c.prepareCall(sql)) {
            stmt.setLong(1, id);
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            stmt.execute();
            nk = new NumerKarty(stmt.getString(2));
            stmt.close();
            c.close();
            
        } catch (SQLException se) {
            System.out.println("Blad podczas odczytu " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas odczytu " + e.getMessage());
        }

        return nk;
    }
}
