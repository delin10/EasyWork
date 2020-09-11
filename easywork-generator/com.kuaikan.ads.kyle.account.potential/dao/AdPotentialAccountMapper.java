package com.kuaikan.ads.kyle.account.potential.dao;

import org.apache.ibatis.annotations.Param;
import com.kuaikan.ads.kyle.account.potential.condition.AdPotentialAccountQueryCondition;
import com.kuaikan.ads.kyle.account.potential.model.AdPotentialAccount;

import java.util.List;

/**
 * @author easywork.
 */
public interface AdPotentialAccountMapper {

    Long insert(AdPotentialAccount adPotentialAccount);

    Long update(AdPotentialAccount adPotentialAccount);

    List<AdPotentialAccount> list(AdPotentialAccountQueryCondition condition);

    long count(AdPotentialAccountQueryCondition condition);

}