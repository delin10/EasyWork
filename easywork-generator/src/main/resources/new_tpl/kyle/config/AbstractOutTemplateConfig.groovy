package new_tpl.kyle.config


import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.context.GenerateContextBuilder
import nil.ed.easywork.generator.generator.sql2java.DefaultSql2JavaGenerator
import nil.ed.easywork.generator.generator.sql2java.listener.ListenerContext
import nil.ed.easywork.source.obj.type.BaseClass
import nil.ed.easywork.util.Utils
import nil.ed.easywork.util.naming.NamingTranslatorSingleton

abstract class AbstractOutTemplateConfig extends AbstractCommonTemplateConfig {

    final def ENTITY_CAMEL_NAME = "entityCamelName"

    @Override
    void doActionInternal(Map<String, Object> context, String template, Config config) throws IOException {
        BaseClass entity = context.get(GenerateContextBuilder.ENTITY) as BaseClass
        context.put(ENTITY_CAMEL_NAME, NamingTranslatorSingleton.PASCAL_TO_CAMEL.trans(entity.name))
        ListenerContext.invokeBeforeRender(context, template, config)
        String afterRender = DefaultSql2JavaGenerator.INSTANCE.generate(context, template, config).get(0);
        String fixedRender = ListenerContext.invokeAfterRender(context, template, config, afterRender)
        if (fixedRender != null) {
            afterRender = fixedRender
        }
        Utils.writeToFile(config.getBasePath(), getFile(context, template, entity, config), afterRender);
    }

    abstract String getFile(Map<String, Object> context, String template, BaseClass entity, Config config);

    @Override
    void beforeRegisterClazz(Map<String, Object> context, String template, Config config) {
        BaseClass entity = context.get(GenerateContextBuilder.ENTITY) as BaseClass
        String clazzSuffix = getClazzSuffix(context, template, entity, config)
        String clazzName = clazzSuffix.substring(clazzSuffix.lastIndexOf('.') + 1)
        context.put(GenerateContextBuilder.CURRENT_RENDER_CLAZZ_NAME, clazzName)
    }

    String getClazzSuffix(Map<String, Object> context, String template, BaseClass entity, Config config) {
        return getFile(context, template, entity, config).substring(1)
                .replaceAll("\\.java", "")
                .replaceAll("/", ".");
    }

    @Override
    void registerClazz(Map<String, Object> context, String template, BaseClass entity, Config config) {
        def clazzSuffix = getClazzSuffix(context, template, entity, config);
        def clazz = clazzSuffix.substring(clazzSuffix.lastIndexOf('.') + 1)
        if (clazz != context.get(GenerateContextBuilder.CURRENT_RENDER_CLAZZ_NAME)) {
            registerOneClazz(context, getClazzSuffix(context, template, entity, config))
        }
    }
}
