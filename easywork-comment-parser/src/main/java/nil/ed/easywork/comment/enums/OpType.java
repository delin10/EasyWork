package nil.ed.easywork.comment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lidelin.
 */
@Getter
@AllArgsConstructor
public enum OpType {
    /**
     * 运算符.
     */
    GT(">"),
    LT("<"),
    GT_EQ(">="),
    LT_EQ("<="),
    EQ("="),
    LIKE("like");

    private static final Map<String, OpType> MAP;

    private final String op;

    static {
        MAP = Stream.of(OpType.values())
                .collect(Collectors.toMap(OpType::getOp, Function.identity()));
    }

    public static OpType findByStr(String op) {
        return MAP.getOrDefault(op, EQ);
    }
}
