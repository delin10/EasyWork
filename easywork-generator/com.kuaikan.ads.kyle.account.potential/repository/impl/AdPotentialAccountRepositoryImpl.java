package com.kuaikan.ads.kyle.account.potential.repository.impl;

import com.kuaikan.ads.kyle.account.potential.model.AdPotentialAccount;
import com.kuaikan.ads.kyle.account.potential.repository.AdPotentialAccountRepo
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.util.List;

/**
 * @author easywork.
 */
@SLF4J
@Service
public class AdPotentialAccountRepositoryImpl implements AdPotentialAccountRepo {

    @Resource
    private AdPotentialAccountMapper adPotentialAccountMapper;

    @Override
    public long insert(AdPotentialAccount adPotentialAccount) {
        return adPotentialAccountMapper.insert(adPotentialAccount);
    }

    @Override
    public long update(AdPotentialAccount adPotentialAccount) {
        return adPotentialAccountMapper.update(adPotentialAccount);
    }

    @Override
    public List<AdPotentialAccount> list(AdPotentialAccountQueryCondition condition) {
        adPotentialAccountMapper.list(condition);
    }

    public long count(AdPotentialAccountQueryCondition condition) {
        adPotentialAccountMapper.list(condition);
    }

}