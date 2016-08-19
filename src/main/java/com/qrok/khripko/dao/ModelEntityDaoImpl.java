package com.qrok.khripko.dao;

import com.qrok.khripko.model.ModelEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Boris on 19.08.2016.
 */
@Repository(value = "dao")
public class ModelEntityDaoImpl implements ModelEntityDao {

    @Autowired
    SessionFactory sessionFactory;

    public ModelEntityDaoImpl() {
    }

    public ModelEntityDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public ModelEntity getById(Integer id) {
        return (ModelEntity) sessionFactory.getCurrentSession().get(ModelEntity.class, id);
    }

    @Override
    @Transactional
    public List<ModelEntity> get() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(ModelEntity.class).list();
    }

    @Override
    @Transactional
    public ModelEntity save(ModelEntity newModel) {
        Integer id = (Integer) sessionFactory.getCurrentSession().save(newModel);
        return (ModelEntity) sessionFactory.getCurrentSession().get(ModelEntity.class, id);
    }

    @Override
    @Transactional
    public void update(ModelEntity newModel, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(newModel);
        session.close();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ModelEntity modelToDelete = new ModelEntity();
        modelToDelete.setId(id);
        sessionFactory.getCurrentSession().delete(modelToDelete);
    }
}
