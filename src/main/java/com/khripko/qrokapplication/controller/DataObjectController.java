package com.khripko.qrokapplication.controller;

import com.khripko.qrokapplication.dao.DataObjectDao;
import com.khripko.qrokapplication.model.DataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Boris on 19.08.2016.
 */
@RestController
@RequestMapping("/api")
public class DataObjectController {

    @Autowired
    DataObjectDao dao;

    @GetMapping("/objects")
    public List<DataObject> getModels() {
        return dao.get();
    }

    @GetMapping("/object/{id}")
    public DataObject getModel(@PathVariable Integer id){
        return dao.getById(id);
    }

    @RequestMapping(value = {"/object/{id}", "/object"}, method = {RequestMethod.POST, RequestMethod.PUT})
    public DataObject updateModel(@PathVariable Optional<Integer> id, @RequestBody DataObject newModel){
        if(id.isPresent()){
            newModel.setId(id.get());
            return dao.saveOrUpdate(newModel);
        }
        return dao.saveOrUpdate(newModel);

    }

    @DeleteMapping("/object/{id}")
    public DataObject delete(@PathVariable Integer id){
        DataObject oldData = dao.getById(id);
        dao.delete(id);
        return oldData;
    }

}
