package new_tpl.kyle.config


import nil.ed.easywork.generator.config.AbstractTemplateConfig
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.context.GenerateContextBuilder
import nil.ed.easywork.generator.groovy.Globals
import nil.ed.easywork.source.obj.type.BaseClass

abstract class AbstractCommonTemplateConfig extends AbstractTemplateConfig {

    {
        (Globals.GROOVY_CXT.get(Globals.CFG_LIST) as List).add(this as AbstractCommonTemplateConfig)
    }

    @Override
    void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        context.put(GenerateContextBuilder.ADDITIONAL_PATTERN_IMPORT, new HashMap<String, String>())
        BaseClass entity = context.get(GenerateContextBuilder.ENTITY) as BaseClass
        beforeRegisterClazz(context, template, config)
        registerAll(context, template, entity, config)
        doActionInternal(context, template, config)
        context.remove(GenerateContextBuilder.ADDITIONAL_PATTERN_IMPORT)
    }

    void beforeRegisterClazz(Map<String, Object> context, String template, Config config) {
    }

    abstract void doActionInternal(Map<String, Object> context, String template, Config config) throws IOException;

    abstract void registerClazz(Map<String, Object> context, String template, BaseClass entity, Config config);

    static void registerOneClazz(Map<String, Object> context, String clazzSuffix) {
        def clzMap = context.get(GenerateContextBuilder.ADDITIONAL_PATTERN_IMPORT) as Map<String, String>
        def root = context.get(GenerateContextBuilder.ROOT) as Config
        clzMap.put(clazzSuffix.substring(clazzSuffix.lastIndexOf('.') + 1), ("${root.getBasePkg()}.${clazzSuffix}").toString())
    }

    private static void registerAll(Map<String, Object> context, String template, BaseClass entity, Config config) {
        (Globals.GROOVY_CXT.get(Globals.CFG_LIST) as List<AbstractCommonTemplateConfig>).forEach(e -> {
            e.registerClazz(context, template, entity, config)
        })
    }

}
