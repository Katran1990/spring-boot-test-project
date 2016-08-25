package com.khripko.qrokapplication.test;

import com.google.gson.Gson;
import com.khripko.qrokapplication.dao.DataObjectDao;
import com.khripko.qrokapplication.model.DataObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WithMockUser(roles = "ADMIN")
public class DataObjectControllerIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    DataObjectDao dao;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
    }

    @Test
    public void getDataTest() throws Exception {
        DataObject dataObject = dao.saveOrUpdate(new DataObject(0, "testData", (long) 123));

        mockMvc.perform(get("/api/object/" + dataObject.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new Gson().toJson(dataObject), true))
                .andReturn();

        dao.delete(dataObject.getId());
    }

    @Test
    public void getAllDataTest() throws Exception {
        List<DataObject> dataObjects = dao.getAll();
        mockMvc.perform(get("/api/objects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new Gson().toJson(dataObjects), true))
                .andReturn();
    }

    @Test
    public void saveOrUpdateTest() throws Exception {
        DataObject dataObject = dao.saveOrUpdate(new DataObject(0, "TestData", (long) 123));
        dao.delete(dataObject.getId());
        dataObject.setId(dataObject.getId() + 1);

        mockMvc.perform(post("/api/object").content(new Gson().toJson(dataObject)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new Gson().toJson(dataObject), true))
                .andReturn();

        dataObject.setTitle("NewTestData");

        mockMvc.perform(put("/api/object/" + dataObject.getId()).content(new Gson().toJson(dataObject)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new Gson().toJson(dataObject), true))
                .andReturn();

        dao.delete(dataObject.getId());
    }

    @Test
    public void deleteTest() throws Exception {
        DataObject dataObject = dao.saveOrUpdate(new DataObject(0, "TestData", (long) 123));

        mockMvc.perform(delete("/api/object/" + dataObject.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new Gson().toJson(dataObject), true))
                .andReturn();

        assertEquals(null, dao.getById(dataObject.getId()));
    }

}
