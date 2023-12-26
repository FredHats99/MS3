package org.cswteams.ms3.entity;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
/*
TODO: Check why there is this constraint
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames={
        "doctor_id",
        "schedule_id",
    })
})
*/
public class DoctorScheduleState {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**  Utente a cui appartiene questo stato */
    @ManyToOne
    private Doctor doctor;

    /**  Pianificazione a cui appartiene questo stato */
    @OneToOne
    private Schedule schedule;

    private int uffaParziale=0;
    private int uffaCumulativo=0;

    /** tutti i turni assegnati a questo utente nella pianificazione corrente */
    @Transient
    List<ConcreteShift> assegnazioniTurnoCache;



    public List<ConcreteShift> getAssegnazioniTurnoCache(){
        /*
        if (assegnazioniTurnoCache == null){
            this.assegnazioniTurnoCache = new ArrayList<>();
            for (ConcreteShift at: schedule.getConcreteShifts()){
                for (Doctor collega : at.getDoctorsOnDuty().keySet()){
                    if (collega.getId() == this.doctor.getId()){
                        assegnazioniTurnoCache.add(at);
                        break;
                    }
                }
            }
        }
        return assegnazioniTurnoCache;*/
        return null;
    }

    /**Aggiunge in ordine la nuova assegnazione alla lista delle assegnazioni dell'utente **/
    public void addAssegnazioneTurno(ConcreteShift nuovaAssegnazione){
        List<ConcreteShift> turniAssegnati = getAssegnazioniTurnoCache();
        int idInsert = turniAssegnati.size();
        for(int i = 0; i < turniAssegnati.size(); i++){
            if(turniAssegnati.get(i).getDate() > nuovaAssegnazione.getDate() || turniAssegnati.get(i).getDate() == (nuovaAssegnazione.getDate())){
                if(turniAssegnati.get(i).getShift().getStartTime().isAfter(nuovaAssegnazione.getShift().getStartTime())) {
                    idInsert = i;
                }
            }
        }
        turniAssegnati.add(idInsert,nuovaAssegnazione);
    }

    public void saveUffaTemp(){
        this.uffaCumulativo = this.uffaParziale;
    }

    public void addUffaTemp(int uffa){
        this.uffaParziale =this.uffaCumulativo+ uffa;
    }

    public DoctorScheduleState() {
    }
    
    public DoctorScheduleState(Doctor doctor, Schedule schedule) {
        this.doctor = doctor;
        this.schedule = schedule;
    }
}