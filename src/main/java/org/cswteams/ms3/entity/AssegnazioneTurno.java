package org.cswteams.ms3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AssegnazioneTurno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    private Set<Utente> utentiDiGuardia;

    @ManyToMany
    private Set<Utente> utentiReperibili;

    private long dataEpochDay;

    @ManyToOne
    private Turno turno;

    public AssegnazioneTurno() {

    }

    public AssegnazioneTurno(LocalDate data, Turno turno, Set<Utente> utentiReperibili, Set<Utente> utentiDiGuardia) {
        this.dataEpochDay = data.toEpochDay();
        this.utentiDiGuardia = utentiDiGuardia;
        this.utentiReperibili = utentiReperibili;
        this.turno = turno;
    }

    public LocalDate getData() {
        return LocalDate.ofEpochDay(this.dataEpochDay);
    }


}