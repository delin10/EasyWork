package nil.ed.easywork.generator.generator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lidelin.
 */
public class CSVProcessor {

    public static final Pattern PTN = Pattern.compile("\\{\"time_cost\":\"([0-9]+?)\"");

    public static final Pattern PTN_ERROR = Pattern.compile("\\{\"error_msg\":\"([\\s\\S]*?)\"}");

    public static void main(String...args) throws IOException {
//        CSVParser parser = new CSVParser(Files.newBufferedReader(Paths.get("/Users/admin/delin/statis", "data.csv")), CSVFormat.DEFAULT);
//        List<CSVRecord> records = parser.getRecords();
        int success = 0;
        int failed = 0;
        int lt1 = 0;
        int lt3 = 0;
        int gt3 = 0;
        List<String> lines = Files.readAllLines(Paths.get("/Users/admin/delin/statis", "20201111_error_jump.csv"));
        System.out.println(lines.size());
        Map<String, Integer> counter = new HashMap<>(100);
        for (String line : lines) {
//            if (line.contains("\"launch_success\":true")) {
//                success++;
//            } else {
//                failed++;
//            }
//            Matcher matcher = PTN.matcher(line);
//            if (matcher.find()) {
//                long value = Long.parseLong(matcher.group(1));
//                if (value <= 1_000) {
//                    lt1++;
//                } else if (value <= 3_000) {
//                    lt3++;
//                } else {
//                    gt3++;
//                }
//            }
            Matcher matcher =  PTN_ERROR.matcher(line);
            if (matcher.find()) {
                counter.putIfAbsent(matcher.group(1), 0);
                counter.put(matcher.group(1), counter.get(matcher.group(1)) + 1);
            }
        }
        counter.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
//        for (CSVRecord r:records) {
//            String result = r.get(1);
//            if (result.contains("\"launch_success\":true")) {
//                success++;
//            } else {
//                failed++;
//            }
//        }
//        System.out.println("success = " + success);
//        System.out.println("failed = " + failed);
        System.out.println("total: " + lines.size());
        System.out.println("<=1: " + (100.0 * lt1 / lines.size()) + '%');
        System.out.println("<=3: " + (100.0 * lt3 / lines.size()) + '%');
        System.out.println("> 3: " + (100.0 * gt3 / lines.size()) + '%');
    }

}
