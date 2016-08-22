package com.khripko.qrokapplication.dao;

import com.khripko.qrokapplication.model.DataObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository(value = "dao")
public class DataObjectDaoImpl implements DataObjectDao {

    @Autowired
    SessionFactory sessionFactory;

    public DataObjectDaoImpl() {
    }

    public DataObjectDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public DataObject getById(Integer id) {
        return (DataObject) sessionFactory.getCurrentSession().get(DataObject.class, id);
    }

    @Override
    @Transactional
    public List<DataObject> getAll() {
        Session session = sessionFactory.getCurrentSession();
        ArrayList<DataObject> dataObjects = (ArrayList<DataObject>) session.createCriteria(DataObject.class).list();
        return dataObjects;
    }

    @Override
    @Transactional
    public DataObject saveOrUpdate(DataObject newDataObject) {
        Session session = sessionFactory.getCurrentSession();
        return (DataObject) session.merge(newDataObject);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        DataObject dataObjectToDelete = new DataObject();
        dataObjectToDelete.setId(id);
        sessionFactory.getCurrentSession().delete(dataObjectToDelete);
    }
}
