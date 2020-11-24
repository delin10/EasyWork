package nil.ed.easywork.generator.generator.wiki.resolver.impl;

import nil.ed.easywork.generator.generator.wiki.enums.ParamType;

/**
 * @author lidelin.
 */
class ParamsResolver extends AbstractParamsResolver {
    @Override
    public String selector() {
        return "params";
    }

    @Override
    protected ParamType getType() {
        return ParamType.REQ_PARAM;
    }
}
