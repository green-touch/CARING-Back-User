package com.caring.user_service.presentation.manager.usecase;

import com.caring.user_service.common.annotation.UseCase;
import com.caring.user_service.domain.manager.business.adaptor.ManagerAdaptor;
import com.caring.user_service.domain.manager.entity.Submission;
import com.caring.user_service.domain.manager.entity.SubmissionStatus;
import com.caring.user_service.presentation.manager.vo.ResponseSubmission;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.caring.user_service.domain.manager.entity.SubmissionStatus.APPLY;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetPendingSubmissionsUseCase {

    private final ManagerAdaptor managerAdaptor;

    public List<ResponseSubmission> execute() {
        List<Submission> submissions = managerAdaptor.querySubmissionsByStatus(APPLY);
        return submissions.stream()
                .map(submission -> ResponseSubmission.builder()
                        .submissionUuid(submission.getSubmissionUuid())
                        .name(submission.getName())
                        .shelterUuid(submission.getShelterUuid())
                        .build())
                .collect(Collectors.toList());
    }
}
