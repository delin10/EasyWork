package nil.ed.easywork.generator.generator;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.ContextMediator;
import nil.ed.easywork.generator.context.TemplateContext;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.type.ITypeMapper;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;
import nil.ed.easywork.template.ITemplateEngineAdapter;

/**
 * @author lidelin.
 */
@Getter
@Setter
public class AbstractSqlFileToJavaGenerator {

    @SuppressWarnings("rawtypes")
    protected ITemplateEngineAdapter<Object> templateEngineAdapter = new FreeMarkerTemplateEngineAdapter();

    private ContextMediator mediator = new ContextMediator(null, null);

    private String entityTemplateId = TemplateContext.ENTITY;
    private String modelTemplateId = TemplateContext.MODEL;
    private String mapperTemplateId = TemplateContext.MAPPER;
    private String serviceTemplateId = TemplateContext.SERVICE;
    private String controllerTemplateId = TemplateContext.CONTROLLER;
    private String mapperXmlTemplateId = TemplateContext.MAPPER_XML;


    private SQLFileProcessor processor;

    private ITypeMapper mapper = new AdsTypeMapper();

    private Config config;

    public AbstractSqlFileToJavaGenerator(Config config, SQLFileProcessor processor) {
        this.config = config;
        this.processor = processor;
    }

}
