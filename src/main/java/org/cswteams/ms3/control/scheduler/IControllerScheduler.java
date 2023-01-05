package org.cswteams.ms3.control.scheduler;

import java.time.LocalDate;

import org.cswteams.ms3.entity.Schedule;

/**
 * Questo controller si occupa di gestire le richieste di creazione di
 * una pianificazione e della sua gestione.
 */
public interface IControllerScheduler {

    Schedule createSchedule(LocalDate startDate, LocalDate endDate) throws UnableToBuildScheduleException;


}
