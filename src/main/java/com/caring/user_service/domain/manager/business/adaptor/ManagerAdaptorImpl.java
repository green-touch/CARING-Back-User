package com.caring.user_service.domain.manager.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.shelter.entity.Shelter;
import com.caring.user_service.domain.shelter.entity.ShelterStaff;
import com.caring.user_service.domain.shelter.repository.ShelterRepository;
import com.caring.user_service.domain.shelter.repository.ShelterStaffRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Adaptor
@RequiredArgsConstructor
public class ManagerAdaptorImpl implements ManagerAdaptor{

    private final ManagerRepository managerRepository;
    private final ShelterStaffRepository shelterStaffRepository;

    @Override
    public Manager queryByMemberCode(String memberCode) {
        return managerRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new RuntimeException("not found manager"));
    }

    @Override
    public Manager queryByManagerUuid(String managerUuid) {
        return managerRepository.findByManagerUuid(managerUuid)
                .orElseThrow(() -> new RuntimeException("not found manager"));
    }

    @Override
    public List<Manager> queryByShelter(String shelterUuid) {
        //TODO query 최적화
        List<ShelterStaff> shelterStaffList = shelterStaffRepository.findByShelterShelterUuid(shelterUuid);
        return shelterStaffList.stream()
                .map(shelterStaff -> shelterStaff.getManager())
                .collect(Collectors.toList());
    }

}
