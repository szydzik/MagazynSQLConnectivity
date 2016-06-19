/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magazynsqlconnectivity.model;

import com.magazynsqlconnectivity.dao.SQLMagazynpDao;
import com.magazynsqlconnectivity.data.Magazynp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author xxbar
 */
public final class MagazynModel extends AbstractTableModel {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private List<Magazynp> list;

    public MagazynModel(List<Magazynp> list) {
        this.list = list;
    }

    public MagazynModel() {
        refresh();
    }

    public void refresh() {

        this.list = SQLMagazynpDao.getInstance().findAll();

        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getID();
            case 1:
                return list.get(rowIndex).getNR_KARTY().toString();

            case 2: {
                String s = "" + list.get(rowIndex).getODPAD().getGRUPA()
                        + "/" + list.get(rowIndex).getODPAD().getPODGRUPA()
                        + "/" + list.get(rowIndex).getODPAD().getRODZAJ();

                return s;
            }
            case 3: {
                return list.get(rowIndex).getNR_KLIENTA();
            }
            case 4: {
                return list.get(rowIndex).getFIRMA();
            }
            case 5: {
                return list.get(rowIndex).getJEDN();
            }
            case 6: {
                return list.get(rowIndex).getMASA();
            }
            case 7: {
                return DATE_FORMAT.format(list.get(rowIndex).getDATAD());
            }
            default:
                return "?";
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "NR_KARTY";
            case 2:
                return "NR_ODPADU";
            case 3:
                return "NR_KLIENTA";
            case 4:
                return "FIRMA";
            case 5:
                return "JEDN";
            case 6:
                return "MASA";
            case 7:
                return "DATAD";
            default:
                return "?";
        }
    }

    public void insert(Magazynp m) {
        SQLMagazynpDao.getInstance().insert(m);
        refresh();
    }

    public void update(Magazynp t) {
        SQLMagazynpDao.getInstance().update(t);
        refresh();
    }

    public void delete(Magazynp t) {
        SQLMagazynpDao.getInstance().delete(t);
        refresh();
    }

    public Magazynp findOne(long id) {
        return SQLMagazynpDao.getInstance().findOne(id);
    }

    public List<Magazynp> findAll() {
        return SQLMagazynpDao.getInstance().findAll();
    }

    public Magazynp getFromIndex(int index) {
        return list.get(index);
    }
}
