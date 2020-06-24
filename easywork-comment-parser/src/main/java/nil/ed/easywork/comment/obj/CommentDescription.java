package nil.ed.easywork.comment.obj;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nil.ed.easywork.comment.enums.FunctionEnum;

/**
 * @author delin10
 * @since 2020/5/28
 **/
@Getter
@Setter
@ToString
public class CommentDescription {

    private FunctionEnum func;

    private String originName;

    private String name;

    private String type;

}
