package com.caring.user_service.domain.manager.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.Submission;
import com.caring.user_service.domain.manager.entity.SubmissionStatus;
import com.caring.user_service.domain.manager.repository.ManagerRepository;
import com.caring.user_service.domain.manager.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Adaptor
@RequiredArgsConstructor
public class ManagerAdaptorImpl implements ManagerAdaptor{

    private final ManagerRepository managerRepository;
    private final SubmissionRepository submissionRepository;

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
        return managerRepository.findByShelterUuid(shelterUuid);
    }

    @Override
    public Submission querySubmissionByUuid(String submissionUuid) {
        return submissionRepository.findBySubmissionUuid(submissionUuid)
                .orElseThrow(() -> new RuntimeException("not found submission"));
    }

    @Override
    public List<Submission> querySubmissionsByStatus(SubmissionStatus status) {
        return submissionRepository.findByStatus(status);
    }

    @Override
    public List<Manager> queryAll() {
        return managerRepository.findAll();
    }

}
