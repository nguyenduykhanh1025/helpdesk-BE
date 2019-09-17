package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class DayOff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonAlias("day_start_off")
    @Column(nullable = false)
    @NonNull
    private Date dayStartOff;

    @JsonAlias("number_of_day_off")
    @Column(nullable = false)
    @NonNull
    private float numberOfDayOff;

    @JsonAlias("create_at")
    @Column(nullable = false)
    @NonNull
    private float createAt;

    @JsonAlias("description")
    @Column(nullable = false)
    @NonNull
    private float description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_days_off_type")
    private DayOffType dayOffType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private UserEntity userEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status")
    private Status status;



}
