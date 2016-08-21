package com.khripko.qrokapplication.dao;

import com.khripko.qrokapplication.model.DataObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Boris on 19.08.2016.
 */
@Repository(value = "dao")
public class DataObjectDaoImpl implements DataObjectDao {

    @Autowired
    SessionFactory sessionFactory;

    public DataObjectDaoImpl() {
    }

    public DataObjectDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public DataObject getById(Integer id) {
        return (DataObject) sessionFactory.getCurrentSession().get(DataObject.class, id);
    }

    @Override
    @Transactional
    public List<DataObject> get() {
        Session session = sessionFactory.getCurrentSession();
        ArrayList<DataObject> dataObjects = (ArrayList<DataObject>) session.createCriteria(DataObject.class).list();
        if (dataObjects.isEmpty()) {
            return Collections.emptyList();
        }
        return dataObjects;
    }

    @Override
    @Transactional
    public DataObject saveOrUpdate(DataObject newModel) {
        Session session = sessionFactory.getCurrentSession();
        return (DataObject) session.merge(newModel);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        DataObject modelToDelete = new DataObject();
        modelToDelete.setId(id);
        sessionFactory.getCurrentSession().delete(modelToDelete);
    }
}
