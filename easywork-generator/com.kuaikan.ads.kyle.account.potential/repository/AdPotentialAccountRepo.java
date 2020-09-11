package com.kuaikan.ads.kyle.account.potential.repository;

import com.kuaikan.ads.kyle.account.potential.model.AdPotentialAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.util.List;

/**
 * @author easywork.
 */
@SLF4J
@Service
public interface AdPotentialAccountRepo {

    @Resource
    private AdPotentialAccountMapper adPotentialAccountMapper;

    /**
     * 更新实体.
     * @param adPotentialAccount 更新对象.
     * @return 影响行数.
     */
    long insert(AdPotentialAccount adPotentialAccount);

    /**
     * 更新实体.
     * @param adPotentialAccount 更新对象.
     * @return 影响行数.
     */
    long update(AdPotentialAccount adPotentialAccount);

    /**
     * 根据条件查询列表.
     * @param condition 条件.
     * @return 结果.
     */
    List<AdPotentialAccount> list(AdPotentialAccountQueryCondition condition);

    /**
     * 根据条件查询列表.
     * @param condition 条件.
     * @return 结果.
     */
    long count(AdPotentialAccountQueryCondition condition);

}