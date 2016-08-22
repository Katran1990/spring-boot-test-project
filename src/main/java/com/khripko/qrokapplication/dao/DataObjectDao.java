package com.khripko.qrokapplication.dao;

import com.khripko.qrokapplication.model.DataObject;

import java.util.List;

public interface DataObjectDao {

    DataObject getById(Integer id);

    List<DataObject> getAll();

    DataObject saveOrUpdate(DataObject newModel);

    void delete(Integer id);


}
