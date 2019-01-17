package com.ingenium.bot.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "scheduler")
public class Raid {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String name;
    private String difficulty;
    private Timestamp time;

    public Raid(String name, String difficulty, Timestamp time) {
        this.name = name;
        this.difficulty = difficulty;
        this.time = time;
    }
}