package nil.ed.easywork.generator.generator.sql;

import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;
import nil.ed.easywork.util.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lidelin.
 */
public class SqlGenerator {

    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        int before = 25;
        StringBuilder builder = new StringBuilder();
        IntStream.range(63, 64).forEach(i -> {
        LocalDateTime start = dateTime.minusDays(25);
        List<String> sqls = new LinkedList<>();
        Map<String, String> map = new HashMap<>();
        map.put("accountId", "1090");
        map.put("parentAccountId", "1089");
        map.put("groupId", "84");
        map.put("campaignId", "92");
        map.put("creativeId", String.valueOf(i));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while (start.isBefore(dateTime)) {
            Long amount = ThreadLocalRandom.current().nextLong(3333 * 100_000, 3333 * 100_000 + 1);
            map.put("recordTime", formatter.format(start));
            map.put("amount", String.valueOf(amount));
            long expose = ThreadLocalRandom.current().nextLong(1, 1000);
            map.put("expose", String.valueOf(expose));
            Long click = ThreadLocalRandom.current().nextLong(0, expose);
            map.put("click", String.valueOf(click));
            long downloadStart = ThreadLocalRandom.current().nextLong(0, 100_000);
            map.put("download_begin", String.valueOf(downloadStart));
            long downloadCompleted = ThreadLocalRandom.current().nextLong(0, downloadStart);
            map.put("download_completed", String.valueOf(downloadCompleted));
            long install = ThreadLocalRandom.current().nextLong(0, downloadCompleted);
            map.put("install", String.valueOf(install));

            sqls.add(FreeMarkerTemplateEngineAdapter.INSTANCE.process(getTemplate(), map));
            start = start.plusHours(1);
        }
        builder.append("insert into kyle_ad_effect(`account_id`,\n" +
                        "                         `parent_account_id`,\n" +
                        "                         `group_id`,\n" +
                        "                         `campaign_id`,\n" +
                        "                         `creative_id`,\n" +
                        "                         `record_time`,\n" +
                        "                         `ad_pos_id`,\n" +
                        "                         `os`,\n" +
                        "                         `expose`,\n" +
                        "                         `click`,\n" +
                        "                         `download_begin`,\n" +
                        "                         `download_completed`,\n" +
                        "                         `install`,\n" +
                        "                         `amount`) values");
        builder.append(sqls.stream().collect(Collectors.joining(",\n", "", ";")));
        });
        Utils.writeToFile("/Users/admin/delin/sqlGenerator/", System.currentTimeMillis() + ".sql", builder.toString());
    }

    public static String getTemplate() {
        return  "(${accountId}, ${parentAccountId}, ${groupId}, ${campaignId}, ${creativeId}, '${recordTime}', 0, 1, ${expose} ,${click}, ${download_begin}, ${download_completed}, ${install}, ${amount})";
    }

}
