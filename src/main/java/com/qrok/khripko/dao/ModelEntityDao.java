package com.qrok.khripko.dao;

import com.qrok.khripko.model.ModelEntity;

import java.util.List;

/**
 * Created by Boris on 19.08.2016.
 */
public interface ModelEntityDao {

    ModelEntity getById(Integer id);

    List<ModelEntity> get();

    ModelEntity save(ModelEntity newModel);

    void update(ModelEntity newModel, Integer id);

    void delete(Integer id);



}
