package com.github.renuevo.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pay_id;
    private Long pay_tax;
    private String location;

    public Tax(Long pay_id, Long pay_tax, String location){
        this.pay_id = pay_id;
        this.pay_tax = pay_tax;
        this.location = location;
    }

}
