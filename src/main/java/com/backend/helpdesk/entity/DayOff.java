package com.backend.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.util.DateTime;
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

    @JsonAlias("day_end_off")
    @Column(nullable = false)
    @NonNull
    private Date dayEndOff;

    @JsonAlias("create_at")
    @Column(nullable = false)
    @NonNull
    private Date createAt;

    @JsonAlias("description")
    @Column(nullable = false)
    @NonNull
    private String description;

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
