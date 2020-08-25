package nil.ed.easywork.generator.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author delin10
 * @since 2020/6/4
 **/
@Setter
@Getter
public class Config {

    private String basePkg;

    private String basePath = "/Users/admin/delin/generated";
    private String modelPath = basePath + "/model";
    private String entityPath = basePath + "/entity";
    private String daoPath = basePath + "/dao";
    private String repoPath = basePath + "/repo";
    private String servicePath = basePath + "/service";

    private String prefix = "";

}
