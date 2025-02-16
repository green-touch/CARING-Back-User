package com.caring.user_service.presentation.manager.service;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.shelter.business.adaptor.ShelterAdaptor;
import com.caring.user_service.presentation.manager.mapper.ManagerMapper;
import com.caring.user_service.presentation.manager.vo.response.ResponseManager;
import com.caring.user_service.presentation.manager.vo.response.ResponseSpecificManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetShelterStaffUseCase {

    private final ManagerAdaptor managerAdaptor;

    public List<ResponseSpecificManager> execute(String shelterUuid) {
        List<Manager> managers = managerAdaptor.queryByShelter(shelterUuid);
        return managers.stream()
                .map(ResponseSpecificManager::of)
                .toList();
    }
}
