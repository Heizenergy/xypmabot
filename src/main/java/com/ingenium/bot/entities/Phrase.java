package com.ingenium.bot.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "phrases")
public class Phrase {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String text;

    public Phrase(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, text='%s']",
                id, text);
    }
}