package com.khripko.qrokapplication.controller;

import com.khripko.qrokapplication.dao.DataObjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    @Autowired
    DataObjectDao dao;

    @GetMapping(value = "/objects")
    public ModelAndView list(){
        ModelAndView model = new ModelAndView("objects");
        model.addObject("dataList", dao.getAll());
        return model;
    }

    @GetMapping(value = {"/create-or-update/{id}", "/create-or-update"})
    public ModelAndView createOrUpdate(@PathVariable Optional<Integer> id){
        ModelAndView model = new ModelAndView("createOrUpdate");
        if (id.isPresent()){
            model.addObject("data", dao.getById(id.get()));
        }
        return model;
    }
}
