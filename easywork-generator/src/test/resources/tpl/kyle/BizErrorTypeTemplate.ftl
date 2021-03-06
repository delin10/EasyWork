!=BIZ_ERROR_TYPE
package ${root.basePkg}.enums;

import com.kuaikan.ads.kyle.common.exception.ErrorType;
import lombok.Getter;

/**
* @author easywork.
*/
@Getter
public enum ${entity.name}BizErrorType implements ErrorType {
    /**
    * 业务错误码.
    */
    INSERT_FAILED(1, "插入失败"),
    UPDATE_FAILED(2, "更新失败")
    ;
    // todo change biz prefix
    public static final int BIZ_CODE_PREFIX = 000000;

    private int errorCode;
    private String message;

    ${entity.name}BizErrorType(int errorCode, String message) {
        this.errorCode = BIZ_CODE_PREFIX + errorCode;
        this.message = message;
    }

    @Override
    public int getBizCode() {
        return BIZ_CODE_PREFIX;
    }
}
<#noparse></#noparse>