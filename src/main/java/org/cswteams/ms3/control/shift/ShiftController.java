package org.cswteams.ms3.control.shift;

import org.cswteams.ms3.dao.MedicalServiceDAO;
import org.cswteams.ms3.dao.ShiftDAO;
import org.cswteams.ms3.dto.shift.*;
import org.cswteams.ms3.entity.MedicalService;
import org.cswteams.ms3.entity.Shift;
import org.cswteams.ms3.entity.constraint.AdditionalConstraint;
import org.cswteams.ms3.enums.Seniority;
import org.cswteams.ms3.enums.TimeSlot;
import org.cswteams.ms3.jpa_constraints.validant.Validant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Service
public class ShiftController implements IShiftController {

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private MedicalServiceDAO medicalServiceDAO ;

    private ShiftDTOOut convertShiftToDTO(Shift shift) {
        Set<String> daysOfWeek = new HashSet<>() ;
        for (DayOfWeek dayOfWeek : shift.getDaysOfWeek()) {
            daysOfWeek.add(dayOfWeek.name()) ;
        }
        MedicalServiceShiftDTO serviceShiftDTO = new MedicalServiceShiftDTO(
                shift.getMedicalService().getId(), shift.getMedicalService().getLabel()
        );

        HashMap<String, Integer> quantityShiftSeniorities = new HashMap<>() ;
        for (Map.Entry<Seniority, Integer> entry : shift.getQuantityShiftSeniority().entrySet()) {
            quantityShiftSeniorities.put(entry.getKey().name(), entry.getValue()) ;
        }
        return new ShiftDTOOut(
                shift.getId(), shift.getTimeSlot().name(), shift.getStartTime().getHour(),
                shift.getStartTime().getMinute(), shift.getDuration().toMinutesPart(),
                daysOfWeek, serviceShiftDTO, quantityShiftSeniorities
        ) ;
    }

    private Shift convertDTOToShift(ShiftDTOIn shiftDTOIn) {
        Set<DayOfWeek> daysOfWeek = new HashSet<>() ;
        for (String dayOfWeek : shiftDTOIn.getDaysOfWeek()) {
            daysOfWeek.add(DayOfWeek.valueOf(dayOfWeek)) ;
        }
        MedicalService service;

            if(shiftDTOIn.getMedicalService().getId() != null)
                service =new MedicalService(
                        shiftDTOIn.getId(), List.of(), shiftDTOIn.getMedicalService().getLabel()
                );
            else
                service =new MedicalService(
                        List.of(), shiftDTOIn.getMedicalService().getLabel()
                );


        HashMap<Seniority, Integer> quantityShiftSeniorities = new HashMap<>() ;
        for (Map.Entry<String, Integer> entry : shiftDTOIn.getQuantityShiftSeniority().entrySet()) {
            quantityShiftSeniorities.put(Seniority.valueOf(entry.getKey()), entry.getValue()) ;
        }

        ArrayList<AdditionalConstraint> constraints = new ArrayList<>() ;
        for (AdditionalConstraintShiftDTO dto : shiftDTOIn.getAdditionalConstraints()) {
            AdditionalConstraint constraint = new AdditionalConstraint() ;
            if(dto.getId() != null) constraint.setId(dto.getId());
            constraint.setDescription(dto.getDescription());
            constraint.setViolable(dto.isViolable());
        }

        if(shiftDTOIn.getId() != null)
            return new Shift(shiftDTOIn.getId(), TimeSlot.valueOf(shiftDTOIn.getTimeSlot()),
                    LocalTime.of(shiftDTOIn.getStartHour(), shiftDTOIn.getStartMinute()),
                    Duration.ofMinutes(shiftDTOIn.getDurationMinutes()),
                    daysOfWeek, service,
                    quantityShiftSeniorities, constraints
            ) ;
        else
            return new Shift(
                    LocalTime.of(shiftDTOIn.getStartHour(), shiftDTOIn.getStartMinute()),
                    Duration.ofMinutes(shiftDTOIn.getDurationMinutes()),
                    service, TimeSlot.valueOf(shiftDTOIn.getTimeSlot()),
                    quantityShiftSeniorities, daysOfWeek, constraints
            ) ;
    }

    @Override
    public List<ShiftDTOOut> getAllShifts() {

        List<Shift> shifts = shiftDAO.findAll() ;
        ArrayList<ShiftDTOOut> retVal = new ArrayList<>() ;

        for (Shift shift : shifts) {
            retVal.add(convertShiftToDTO(shift)) ;
        }

        return retVal ;
    }

    @Override
    @Validant
    public List<ShiftDTOOut> getShiftsOfService(@Valid ShiftServiceNameDTOIn serviceName) {

        List<Shift> shifts = shiftDAO.findAllByMedicalServiceLabel(serviceName.getServiceLabel()) ;
        ArrayList<ShiftDTOOut> retVal = new ArrayList<>() ;

        for (Shift shift : shifts) {
            retVal.add(convertShiftToDTO(shift)) ;
        }

        return retVal ;
    }

    @Override
    @Validant
    public ShiftDTOOut createShift(@Valid ShiftDTOIn shift) {
        Shift shiftEntity = convertDTOToShift(shift) ;

        if(shiftEntity.getMedicalService().getId() == null) medicalServiceDAO.save(shiftEntity.getMedicalService());

        return convertShiftToDTO(shiftDAO.save(shiftEntity)) ;
    }
}
