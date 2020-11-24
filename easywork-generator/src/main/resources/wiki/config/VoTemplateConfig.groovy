package wiki.config

import nil.ed.easywork.generator.config.AbstractTemplateConfig
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.generator.sql2java.listener.macro.MacroTemplateRenderListener
import nil.ed.easywork.generator.generator.wiki.bean.ControllerBean
import nil.ed.easywork.generator.generator.wiki.bean.ParamsContainer
import nil.ed.easywork.generator.generator.wiki.context.ResolveContext
import nil.ed.easywork.generator.generator.wiki.enums.ParamType
import nil.ed.easywork.generator.singleton.BeanContext
import nil.ed.easywork.util.Utils
import org.apache.commons.collections4.CollectionUtils

import java.util.stream.Collectors

class VoTemplateConfig extends AbstractTemplateConfig {

    @Override
    void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        List<ControllerBean> controllerBeans = context.get(ResolveContext.CONTROLLERS) as List<ControllerBean>
        controllerBeans.forEach(controller -> {
            List<ParamsContainer> vos = controller.apis.stream()
            .map(api -> api.getContainers())
            .filter(containers -> CollectionUtils.isNotEmpty(containers))
            .flatMap(containers -> containers.stream())
            .filter(container ->
                    container.getType() == ParamType.REQ_BODY || ParamType.RESP_BODY)
            .filter(c -> c.id != null)
            .collect(Collectors.toList())
            vos.forEach(vo -> {
                context.put("current", vo)
                String afterRender = BeanContext.FREE_MARKER_TEMPLATE_ENGINE.process(template, context)
                String newResult = new MacroTemplateRenderListener().afterRender(context, template, config, afterRender)
                Utils.writeToFile(config.getBasePath(), "vo/${vo.id}", newResult)
                context.remove("current")
            })
        })
    }

}
