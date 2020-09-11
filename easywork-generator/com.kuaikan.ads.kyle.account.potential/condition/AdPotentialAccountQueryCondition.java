package com.kuaikan.ads.kyle.account.potential.condition;

import lombok.Data
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.List;
/**
 * @author easywork.
 */
@Data
@Accessors(chain = true)
public class AdPotentialAccountQueryCondition extends PageCondition {

    private String sourceApp;

}