package com.github.renuevo.one_plus_n;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "academy_id", foreignKey = @ForeignKey(name = "FK_SUBJECT_ACADEMY"))
    private Academy academy;

    @Builder
    public Subject(String name, Academy academy) {
        this.name = name;
        this.academy = academy;
    }

    public void updateAcademy(Academy academy){
        this.academy = academy;
    }

}
