package com.caring.user_service.domain.authority.business.adaptor;

import com.caring.user_service.common.annotation.Adaptor;
import com.caring.user_service.common.util.EnumConvertUtil;
import com.caring.user_service.domain.authority.entity.Authority;
import com.caring.user_service.domain.authority.entity.ManagerRole;
import com.caring.user_service.domain.authority.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class AuthorityAdaptorImpl implements AuthorityAdaptor{

    private final AuthorityRepository authorityRepository;


    @Override
    public Authority queryByManagerRoleKey(String key) {
        return authorityRepository.findByManagerRole(EnumConvertUtil.convert(ManagerRole.class, key))
                .orElseThrow(() -> new RuntimeException("not found ManagerRole"));
    }
}
