package com.qrok.khripko.model;

import javax.persistence.*;

/**
 * Created by Boris on 19.08.2016.
 */
@Entity
@Table(name = "model_data")
public class ModelEntity {
    private Integer id;
    private String title;
    private Long value;

    public ModelEntity() {
    }

    public ModelEntity(Integer id, String title, Long value) {
        this.id = id;
        this.title = title;
        this.value = value;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "value")
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
