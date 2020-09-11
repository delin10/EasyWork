package com.kuaikan.ads.kyle.account.potential.entity;

import com.kuaikan.ads.kyle.account.potential.model.AdPotentialAccount.
import lombok.Data
import lombok.experimental.Accessors;

/**
 * @author easywork.
 */
@Data
@Accessors(chain = true)
public class AdPotentialAccountEntity {

    private Long id;

    private String companyName;

    private String sourceApp;

    private Long id;


    public AdPotentialAccount toAdPotentialAccount() {
        AdPotentialAccount adPotentialAccount = new AdPotentialAccount();
        BeanUtils.copyProperties(this, adPotentialAccount);
        return adPotentialAccount;
    }

    public static AdPotentialAccountEntity parse(AdPotentialAccount model) {
        AdPotentialAccountEntity adPotentialAccountEntity = new AdPotentialAccountEntity();
        BeanUtils.copyProperties(model, adPotentialAccountEntity);
        return adPotentialAccountEntity;
    }
}