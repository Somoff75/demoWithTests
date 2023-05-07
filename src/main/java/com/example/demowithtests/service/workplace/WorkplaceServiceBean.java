package com.example.demowithtests.service.workplace;


import com.example.demowithtests.domain.workplace.Workplace;
import com.example.demowithtests.repository.WorkplaceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j


public class WorkplaceServiceBean implements WorkplaceService {

    private final WorkplaceRepository workplaceRepository;

    @Override
    public Workplace create(Workplace workplace) {
        log.debug("Create workplace: {}", workplace);
        Workplace newWorkplace = workplaceRepository.save(workplace);
        return workplaceRepository.save(newWorkplace);
    }
    @Override
    public Workplace getById(Integer id){
        return workplaceRepository.getById(id);
    }

    @Override
    public boolean isAvailable(Integer id, Integer employeeSize) {
        Workplace workplace = getById(id);
        return (workplace.getCapacity() - workplace.getOccupied()) >= employeeSize;
    }

    @Override
    public List<Workplace> getAvailableWorkplaces(Integer employeeSize) {
        List<Workplace> workplaces = workplaceRepository.findAll();
        workplaces.removeIf(workplace -> workplace.getCapacity() - workplace.getOccupied() < employeeSize);
        return workplaces;
    }

    @Override
    public Workplace updateWorkplace(Integer id, Workplace newWorkplace){
        Workplace workplace = getById(id);
        workplace.setName(newWorkplace.getName());
        workplace.setOccupied(newWorkplace.getOccupied());
        workplace.setCapacity(newWorkplace.getCapacity());
        return workplaceRepository.save(workplace);

    }

    @Override
    public Workplace update(Workplace workplaceToUpdate) {
        log.info("Update workplace: {}", workplaceToUpdate);
        return workplaceRepository.save(workplaceToUpdate);

    }


}
