package com.bravi.agenda.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.bravi.agenda.constant.Sequences.EVENT_SEQ;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = EVENT_SEQ)
    @SequenceGenerator(name = EVENT_SEQ, sequenceName = EVENT_SEQ, allocationSize = 1)
    private Integer id;

    private String eventDescription;

    private Integer year;
    private Integer month;
    private Integer day;
    private LocalDateTime created;
    private LocalDateTime updated;

}
