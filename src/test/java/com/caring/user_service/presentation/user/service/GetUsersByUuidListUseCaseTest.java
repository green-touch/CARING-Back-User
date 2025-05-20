package com.caring.user_service.presentation.user.service;

import com.caring.user_service.common.service.DatabaseCleanUp;
import com.caring.user_service.common.util.RandomNumberUtil;
import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.repository.UserRepository;
import com.caring.user_service.presentation.user.vo.ResponseUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.caring.user_service.common.consts.StaticVariable.USER_MEMBER_CODE_PRESET;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class GetUsersByUuidListUseCaseTest {

    @Autowired
    private GetUsersByUuidListUseCase getUsersByUuidListUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    private User user1;
    private User user2;
    private List<String> uuidList;

    @BeforeEach
    void setUp() {
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();

        user1 = User.builder()
                .userUuid(uuid1)
                .name("User1")
                .password("password1")
                .memberCode(RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .build();

        user2 = User.builder()
                .userUuid(uuid2)
                .name("User2")
                .password("password2")
                .memberCode(RandomNumberUtil.generateRandomMemberCode(USER_MEMBER_CODE_PRESET))
                .build();

        userRepository.saveAll(Arrays.asList(user1, user2));
        uuidList = Arrays.asList(uuid1, uuid2);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("UUID 리스트로 사용자 목록을 조회한다")
    void execute() {
        // when
        List<ResponseUser> result = getUsersByUuidListUseCase.execute(uuidList);

        // then
        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(ResponseUser::getUserUuid)
                .containsExactlyInAnyOrder(user1.getUserUuid(), user2.getUserUuid());
    }
}
