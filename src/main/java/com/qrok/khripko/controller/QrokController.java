package com.qrok.khripko.controller;

import com.qrok.khripko.dao.ModelEntityDao;
import com.qrok.khripko.model.ModelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Boris on 19.08.2016.
 */
@RestController
@RequestMapping("/api")
public class QrokController {

    @Autowired
    ModelEntityDao dao;

    @GetMapping("/objects")
    public List<ModelEntity> getModels() {
        return dao.get();
    }

    @GetMapping("/object/{id}")
    public ModelEntity getModel(@PathVariable Integer id){
        return dao.getById(id);
    }

    @PostMapping("/object")
    public ModelEntity addModel(@RequestBody ModelEntity newModel){
        return dao.save(newModel);
    }

    @PutMapping("/object/{id}")
    public ModelEntity updateModel(@PathVariable Integer id, @RequestBody ModelEntity newModel){
        newModel.setId(id);
        dao.update(newModel, id);
        return dao.getById(id);
    }

    @DeleteMapping("/object/{id}")
    public void delete(@PathVariable Integer id){
        dao.delete(id);
    }

}
