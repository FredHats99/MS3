package org.cswteams.ms3.control.assegnazioneTurni;

import org.cswteams.ms3.dto.AssegnazioneTurnoDTO;
import org.cswteams.ms3.dto.ModificaAssegnazioneTurnoDTO;
import org.cswteams.ms3.dto.RegistraAssegnazioneTurnoDTO;
import org.cswteams.ms3.entity.AssegnazioneTurno;
import org.cswteams.ms3.entity.Utente;
import org.cswteams.ms3.exception.AssegnazioneTurnoException;

import java.text.ParseException;
import java.util.Set;

public interface IControllerAssegnazioneTurni {
    Set<AssegnazioneTurnoDTO> leggiTurniAssegnati() throws ParseException;

    AssegnazioneTurno creaTurnoAssegnato(RegistraAssegnazioneTurnoDTO c) throws AssegnazioneTurnoException;

    Set<AssegnazioneTurnoDTO> leggiTurniUtente(Long idUtente) throws ParseException;

    AssegnazioneTurno leggiTurnoByID(long idAssegnazione);

    AssegnazioneTurno sostituisciUtenteAssegnato(AssegnazioneTurno assegnazioneTurno, Utente utenteSostituendo, Utente utenteSostituto) throws AssegnazioneTurnoException;
}
