package com.example.demowithtests.service.workplace;


import com.example.demowithtests.domain.workplace.Workplace;

import java.util.List;

public interface WorkplaceService {

    Workplace create(Workplace workplace);


    Workplace getById(Integer id);


    boolean isAvailable(Integer id, Integer employeeSize);


    List<Workplace> getAvailableWorkplaces(Integer employeeSize);



    Workplace updateWorkplace(Integer id, Workplace newWorkplace);


    Workplace update(Workplace workplaceToUpdate);
}
