package org.cswteams.ms3.dto;


import lombok.Data;
import org.cswteams.ms3.dto.medicalservice.MedicalServiceDTO;
import org.cswteams.ms3.dto.user.UserDTO;
import org.cswteams.ms3.enums.TimeSlot;

import java.util.Set;

@Data
public class GiustificazioneForzaturaVincoliDTO {

    private String message;

    private String utenteGiustificatoreId;

    private int giorno;
    private int mese;
    private int anno;

    private TimeSlot timeSlot;
    private Set<UserDTO> utentiAllocati;
    private MedicalServiceDTO servizio;


    //private Set<Waiver> liberatorie;

    public GiustificazioneForzaturaVincoliDTO() {

    }

}
