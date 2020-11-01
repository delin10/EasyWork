package nil.ed.easywork.generator.generator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author lidelin.
 */
public class ExcelToSqlGenerator {

    public static void main(String[] args) throws Exception {
        String path = "/Users/admin/Downloads/行业类别.xlsx";
        int sheetIndex = 0;
        XSSFWorkbook workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(path)));
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Map<String, List<String>> map = new LinkedHashMap<>(128);
        String fixedParent = "";
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            String parent = row.getCell(0).getStringCellValue();
            String sub = row.getCell(1).getStringCellValue();
            if (StringUtils.isNotBlank(parent)) {
                fixedParent = parent;
            }
            if (StringUtils.isBlank(parent) && StringUtils.isBlank(sub)) {
                break;
            }
            List<String> subList = map.computeIfAbsent(fixedParent, k -> new LinkedList<>());
            subList.add(sub);
        }

        System.out.println(map);
        List<String> parentSqlList = new LinkedList<>();
        List<String> subSqlList = new LinkedList<>();
        long globalId = 1;
        Map<String, Long> parentIdMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            parentIdMap.put(entry.getKey(), globalId);
            parentSqlList.add(formatSql(globalId++, 0, entry.getKey()));
        }

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> subList = entry.getValue();
            for (String sub : subList) {
                subSqlList.add(formatSql(globalId++, parentIdMap.get(entry.getKey()), sub));
            }
        }
        Stream.concat(parentSqlList.stream(), subSqlList.stream()).forEach(System.out::println);
    }

    private static String formatSql(long globalId, long parentId, String name) {
        return String.format("insert into `kyle_ad_category`(`id`, `parent_id`, `name`) values(%d, %d, '%s');",
                globalId, parentId, name);
    }

}
