package org.cswteams.ms3.control.medicalService;

import org.cswteams.ms3.dto.medicalservice.MedicalServiceDTO;
import org.cswteams.ms3.entity.MedicalService;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface IMedicalServiceController {

    Set<MedicalServiceDTO> getAllMedicalServices();
    MedicalServiceDTO leggiServizioByNome(@NotNull String nome);
    MedicalService creaServizio(@NotNull MedicalServiceDTO servizio);
}
