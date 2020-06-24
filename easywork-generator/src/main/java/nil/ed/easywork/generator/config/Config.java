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

    private String basePath = "/";

    private String prefix = "";

}
