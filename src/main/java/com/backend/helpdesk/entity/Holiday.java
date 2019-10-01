package com.backend.helpdesk.entity;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Holiday {
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

    @JsonAlias("reason")
    @Column(nullable = false)
    @NonNull
    private String reason;
}
