/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magazynsqlconnectivity.dao;

import com.magazynsqlconnectivity.data.Odpad;
import com.magazynsqlconnectivity.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szydzik
 */
public class SQLOdpadDao implements DaoManual<Odpad> {

    DBConnection dbc = new DBConnection();

    private static SQLOdpadDao instance;

    public synchronized static SQLOdpadDao getInstance() {
        if (instance == null) {
            instance = new SQLOdpadDao();
        }
        return instance;
    }

    @Override
    public void insert(Odpad t) {
        String sql = "INSERT INTO odpad (ID, GRUPA, PODGRUPA, RODZAJ, TYP, OPIS) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, t.getID());
            ps.setInt(2, t.getGRUPA());
            ps.setInt(3, t.getPODGRUPA());
            ps.setInt(4, t.getRODZAJ());
            ps.setString(5, t.getTYP());
            ps.setString(6, t.getOPIS());
            ps.execute();
            System.out.println("Dodano rekord");

        } catch (SQLException se) {
            System.out.println("Blad podczas dodawania " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas dodawania " + e.getMessage());
        }
    }

    @Override
    public void update(Odpad t) {
        String sql = "UPDATE odpad SET GRUPA = ?, PODGRUPA = ?, RODZAJ = ?, TYP = ?, OPIS = ? WHERE ID = ?";

        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, t.getGRUPA());
            ps.setInt(2, t.getPODGRUPA());
            ps.setInt(3, t.getRODZAJ());
            ps.setString(4, t.getTYP());
            ps.setString(5, t.getOPIS());
            ps.setLong(6, t.getID());
            ps.executeUpdate();
            System.out.println("Zmodyfikowano rekord");

        } catch (SQLException se) {
            System.out.println("Blad podczas Edycji " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas Edycji " + e.getMessage());
        }
    }

    @Override
    public void delete(Odpad t) {
        String sql = "DELETE FROM odpad WHERE ID = ?";
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
    public Odpad findOne(Long id) {
        String sql = "SELECT ID, GRUPA, PODGRUPA, RODZAJ, TYP, OPIS FROM odpad WHERE ID = '" + id + "'";
        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            rs.next();
            return new Odpad(rs.getLong(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
        } catch (SQLException se) {
            System.out.println("Blad podczas odczytu " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas odczytu " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Odpad> findAll() {
        List<Odpad> objs = new ArrayList<>();
        String sql = "SELECT ID, GRUPA, PODGRUPA, RODZAJ, TYP, OPIS FROM odpad";

        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Odpad odpad = new Odpad(rs.getLong(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                objs.add(odpad);
            }
        } catch (SQLException se) {
            System.out.println("Blad podczas odczytu " + se.getMessage());
        } catch (Exception e) {
            System.out.println("Blad podczas odczytu " + e.getMessage());
        }

        return objs;
    }

    public void insertBigData(List<Odpad> listOdpad) {
        String sql = "INSERT INTO odpad (ID, GRUPA, PODGRUPA, RODZAJ, TYP, OPIS) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = dbc.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            c.setAutoCommit(false);

            int i = 0;
            int wynik = 0;
            for (Odpad t : listOdpad) {
                System.out.println(""+t);
                if (i++ >= 10000) {
                    wynik += ps.executeLargeBatch().length;
                    c.commit();
                    System.out.println("BATCH EXECUTE!!!");
                    ps.clearBatch();
                    i = 0;
                }
                ps.setLong(1, t.getID());
                ps.setObject(2, t.getGRUPA());
                ps.setObject(3, t.getPODGRUPA());
                ps.setObject(4, t.getRODZAJ());
                ps.setObject(5, t.getTYP());
                ps.setObject(6, t.getOPIS());
                ps.addBatch();

            }

            wynik += ps.executeLargeBatch().length;
            c.commit();
            c.setAutoCommit(true);
            System.out.println("Wynik = " + wynik);
            System.out.println("Dodano rekordy!");

        } catch (SQLException se) {
            se.printStackTrace();
            System.out.println("Blad podczas dodawania " + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Blad podczas dodawania " + e.getMessage());
        }
    }

}
