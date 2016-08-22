package com.khripko.qrokapplication.controller;

import com.khripko.qrokapplication.dao.DataObjectDao;
import com.khripko.qrokapplication.model.DataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("/api")
public class DataObjectController {

    @Autowired
    DataObjectDao dao;

    @GetMapping("/objects")
    public List<DataObject> getAllData() {
        List<DataObject> dataObjects = dao.getAll();
        if (dataObjects.isEmpty()) {
            return emptyList();
        }
        return dataObjects;
    }

    @GetMapping("/object/{id}")
    public DataObject getData(@PathVariable Integer id) {
        return dao.getById(id);
    }

    @RequestMapping(value = {"/object/{id}", "/object"}, method = {RequestMethod.POST, RequestMethod.PUT})
    public DataObject saveOrUpdateData(@PathVariable Optional<Integer> id, @RequestBody DataObject newDataObject) {
        if (id.isPresent()) {
            newDataObject.setId(id.get());
            return dao.saveOrUpdate(newDataObject);
        }
        return dao.saveOrUpdate(newDataObject);
    }

    @DeleteMapping("/object/{id}")
    public DataObject delete(@PathVariable Integer id) {
        DataObject oldData = dao.getById(id);
        dao.delete(id);
        return oldData;
    }

}
