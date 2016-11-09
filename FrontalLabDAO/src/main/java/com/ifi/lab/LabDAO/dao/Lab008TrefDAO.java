package com.ifi.lab.LabDAO.dao;

import java.util.ArrayList;
import java.util.List;

import com.ifi.lab.LabDAO.model.Lab008Tref;

/**
 * Created by nmha on 06/04/2016.
 */
public interface Lab008TrefDAO {    
    boolean deleteAll();
    Integer create(ArrayList<Lab008Tref> objList);   
    List<Lab008Tref> getAllData();
}
