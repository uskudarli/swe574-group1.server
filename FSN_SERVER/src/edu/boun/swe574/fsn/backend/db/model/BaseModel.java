package edu.boun.swe574.fsn.backend.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 2023689120521915568L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long            id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity [id:" + id + "]";
    }

    public static Long getBaseModelId(BaseModel baseModel){
        if (baseModel == null)
            return null;
        
        return baseModel.getId();
    }
}