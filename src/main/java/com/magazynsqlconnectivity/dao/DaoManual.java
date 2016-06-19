/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magazynsqlconnectivity.dao;

import java.util.List;

/**
 * @author xxbar
 * @param <T>
 */
public interface DaoManual<T> {
	
	void insert(T t);
	void update(T t);
	void delete(T t);
	T findOne(Long id);
	List<T> findAll();
		
//        
//            Choroba select(int id) throws SQLException;
//
//    List<Choroba> selectAll() throws SQLException;
//
//    void update(Choroba obj) throws SQLException;
//
//    void insert(Choroba obj) throws SQLException;
//
//    void delete(int id) throws SQLException;
	
}
