==>>/new_tpl/kyle/config/BizErrorTypeTemplateConfig.groovy
package ${root.basePkg}.enums;

__IMPORTS__
/**
* @author easywork.
*/
@Getter
public enum ${entity.name}BizErrorType implements ErrorType {
    /**
    * 业务错误码.
    */
    SYSTEM_ERR(1, "系统错误"),
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