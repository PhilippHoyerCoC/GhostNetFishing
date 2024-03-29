package com.iu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ghostnet")
public class GhostNet {
    
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    @Column(name="SIZE")
    private Integer size;
    @Column(name="STATUS")
    private GhostNetStatusEnum status;
    @Embedded
    @Column(name="LOCATION")
    private Coordinates coordinates;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    public GhostNet() {
        //Needed by JPA
    }

    @Override
    public String toString() {
        return "GhostNet{" +
                "id=" + id +
                ", size=" + size +
                ", status=" + status +
                ", coordinates=" + coordinates +
                ", user=" + user +
                '}';
    }
}
