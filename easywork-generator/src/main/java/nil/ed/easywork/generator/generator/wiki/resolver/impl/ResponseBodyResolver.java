package nil.ed.easywork.generator.generator.wiki.resolver.impl;

import nil.ed.easywork.generator.generator.wiki.enums.ParamType;

/**
 * @author lidelin.
 */
class ResponseBodyResolver extends AbstractParamsResolver {
    @Override
    public String selector() {
        return "response";
    }

    @Override
    protected ParamType getType() {
        return ParamType.RESP_BODY;
    }

}
