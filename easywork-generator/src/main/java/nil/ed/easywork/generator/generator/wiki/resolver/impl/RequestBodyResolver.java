package nil.ed.easywork.generator.generator.wiki.resolver.impl;

import nil.ed.easywork.generator.generator.wiki.enums.ParamType;

/**
 * @author lidelin.
 */
class RequestBodyResolver extends AbstractParamsResolver {
    @Override
    public String selector() {
        return "request-body";
    }

    @Override
    protected ParamType getType() {
        return ParamType.REQ_BODY;
    }

}
