package com.caring.user_service.domain.manager.business.adaptor;

import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.Submission;
import com.caring.user_service.domain.manager.entity.SubmissionStatus;

import java.util.List;

public interface ManagerAdaptor {

    Manager queryByMemberCode(String memberCode);

    Manager queryByManagerUuid(String managerUuid);

    List<Manager> queryByShelter(String shelterUuid);

    Submission querySubmissionByUuid(String submissionUuid);

    List<Submission> querySubmissionsByStatus(SubmissionStatus status);

    List<Manager> queryAll();

}
