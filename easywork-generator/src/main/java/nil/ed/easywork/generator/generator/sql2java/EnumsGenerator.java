package nil.ed.easywork.generator.generator.sql2java;

import nil.ed.easywork.comment.obj.CommentDescription;
import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.generator.singleton.BeanContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author lidelin.
 */
public class EnumsGenerator implements Sql2JavaGenerator {

    private static final String CURRENT_ENUM_PARAM = "currentEnum";

    private EnumRenderCallback callback;

    public EnumsGenerator(EnumRenderCallback callback) {
        this.callback = callback;
    }

    @Override
    public List<Object> generate(Map<String, Object> cxt, String template, Config config) {
        @SuppressWarnings("unchecked")
        Map<String, CommentDescription> descriptionMap = (Map<String, CommentDescription>) cxt.get(
                GenerateContextBuilder.FIELD_DESC);
        descriptionMap.entrySet().stream()
                .filter(e -> CollectionUtils.isNotEmpty(e.getValue().getEnums()))
                .forEach(e -> {
            cxt.put(CURRENT_ENUM_PARAM, e.getValue());
            String afterRender = BeanContext.FREE_MARKER_TEMPLATE_ENGINE.process(template, cxt);
            callback.callback(afterRender, e.getValue());

        });
        cxt.remove(CURRENT_ENUM_PARAM);
        return Collections.emptyList();
    }

    public interface EnumRenderCallback {
        /**
         * 回调.
         * @param afterRender 渲染后的字符串.
         * @param currentDesc 当前的注释描述.
         */
        void callback(String afterRender, CommentDescription currentDesc);
    }

}
