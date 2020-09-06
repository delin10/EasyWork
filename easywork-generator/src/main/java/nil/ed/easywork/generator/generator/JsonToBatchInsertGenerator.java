package nil.ed.easywork.generator.generator;

import com.alibaba.fastjson.JSONArray;
import nil.ed.easywork.util.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lidelin
 */
public class JsonToBatchInsertGenerator {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public String generate(String json, String table, String...ignores) {
        @SuppressWarnings("rawtypes")
        List<HashMap> ja = JSONArray.parseArray(json, HashMap.class);
        if (ja.isEmpty()) {
            return "";
        }
        HashMap map = ja.get(0);
        Object[] cols = map.keySet().stream()
                .map(String::valueOf)
                .filter(c -> Arrays.binarySearch(ignores, c) < 0)
                .toArray(Object[]::new);
        StringBuilder builder = new StringBuilder("insert into ");
        builder.append(table);
        String insertCols = Arrays.stream(cols).map(String::valueOf)
                .collect(Collectors.joining(",", "(", ")"));
        builder.append(insertCols);
        builder.append(" values");
        builder.append("\n");
        String values = ja.stream().map(m -> mapValues(cols, m))
                .collect(Collectors.joining(",\n", "", ";\n"));
        builder.append(values);
        return builder.toString();
    }

    @SuppressWarnings({"rawtypes"})
    public String mapValues(Object[] cols, HashMap map) {
        return Arrays.stream(cols)
                .map(String::valueOf)
                .map((Function<String, Object>) map::get)
                .map(String::valueOf)
                .map(String::trim)
                .map(v -> "'" + v + "'")
                .collect(Collectors.joining(",", "(", ")"));
    }

    public static void main(String[] args) throws Exception {
        String json = Utils.readText("/Users/admin/Desktop/user_installed_app.json", StandardCharsets.UTF_8);
        JsonToBatchInsertGenerator generator = new JsonToBatchInsertGenerator();
        System.out.println(generator.generate(json, "ad_target_app", "max(install_user)"));
    }

}
