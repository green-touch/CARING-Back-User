package com.caring.user_service.domain.manager.business.service;

import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.manager.entity.Manager;
import com.caring.user_service.domain.manager.entity.Submission;

public interface ManagerDomainService {

    Manager registerManager(String name, String password, Authority authority);

    Submission applyManager(String name, String password, String shelterUuid);

    void removeSubmission(String submissionUuid);
}
