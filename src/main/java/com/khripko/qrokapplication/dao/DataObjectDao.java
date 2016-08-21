package com.khripko.qrokapplication.dao;

import com.khripko.qrokapplication.model.DataObject;

import java.util.List;

/**
 * Created by Boris on 19.08.2016.
 */
public interface DataObjectDao {

    DataObject getById(Integer id);

    List<DataObject> get();

    DataObject saveOrUpdate(DataObject newModel);

    void delete(Integer id);



}
