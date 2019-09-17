package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class DayOffType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonAlias("name")
    @Column(nullable = false)
    @NonNull
    private int name;

    @OneToMany(mappedBy = "dayOffType")
    List<DayOff> dayOffs;
}
