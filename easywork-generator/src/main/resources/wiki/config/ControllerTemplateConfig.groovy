package wiki.config

import nil.ed.easywork.generator.config.AbstractTemplateConfig
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.generator.sql2java.listener.macro.MacroTemplateRenderListener
import nil.ed.easywork.generator.generator.wiki.bean.ControllerBean
import nil.ed.easywork.generator.generator.wiki.context.ResolveContext
import nil.ed.easywork.generator.generator.wiki.enums.ParamType
import nil.ed.easywork.generator.singleton.BeanContext
import nil.ed.easywork.util.Utils
import nil.ed.easywork.util.naming.NamingTranslatorSingleton

import java.util.stream.Collectors

class ControllerTemplateConfig extends AbstractTemplateConfig {

    @Override
    void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        List<ControllerBean> controllerBeans = context.get(ResolveContext.CONTROLLERS) as List<ControllerBean>
        controllerBeans.forEach(controller -> {
            Map<String, String> tmp = new HashMap<>(controller.apis.size(), 1)
            controller.apis.forEach(api -> {
                api.getContainers().forEach(container -> {
                    if (container.getType() == ParamType.REQ_PARAM) {
                        String reqParam = container.getParams().stream()
                                .map(p ->
                                        "@RequestParam(name=\"${p.name}\"${p.defaultValue != null ? ", defaultValue = \"" + p.defaultValue + "\"" : ""}${!p.required ? ", required = false" : ""}) ${p.type} ${NamingTranslatorSingleton.UNDERLINE_TO_CAMEL.trans(p.name)}")
                                .collect(Collectors.joining(", \n"));
                        tmp.put("${api.id}-sign".toString(), reqParam)
                    } else if (container.getType() == ParamType.REQ_BODY) {
                        String reqBody = "@RequestBody ${container.id} ${NamingTranslatorSingleton.PASCAL_TO_CAMEL.trans(container.id)}"
                        tmp.put("${api.id}-sign".toString(), reqBody)
                    }
                })
            })
            context.put("tmp", tmp)
            context.put("current", controller)
            String afterRender = BeanContext.FREE_MARKER_TEMPLATE_ENGINE.process(template, context)
            String newResult = new MacroTemplateRenderListener().afterRender(context, template, config, afterRender)
            Utils.writeToFile(config.getBasePath(), "controller/${controller.id}", newResult)
            context.remove("tmp")
            context.remove("current")
        })
    }

}
