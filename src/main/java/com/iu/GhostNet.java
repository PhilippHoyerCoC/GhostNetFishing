package com.iu;

import jakarta.persistence.*;

@Entity
//TODO: add implements Serializable?
@Table(name = "ghostnet")
public class GhostNet {
    
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    @Column(name="SIZE")
    private Integer size;
    @Column(name="STATUS")
    private String status;
//    private GhostNetStatusEnum status;
//    @Column(name="REPORTED_DATE")
//    @Temporal(TemporalType.DATE)
//    private LocalDate reportedDate;

    public GhostNet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
