package nil.ed.easywork.generator.generator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonCalulator {

    public static void main(String[] args) {
        String json = "[{\"max(rate)\":0.1519,\"len\":6},{\"max(rate)\":0.1802,\"len\":8},{\"max(rate)\":0.0921,\"len\":9},{\"max(rate)\":0.2346,\"len\":11},{\"max(rate)\":0.1429,\"len\":13},{\"max(rate)\":0.9288,\"len\":14},{\"max(rate)\":0.9288,\"len\":15},{\"max(rate)\":0.9288,\"len\":16},{\"max(rate)\":0.9288,\"len\":17},{\"max(rate)\":0.9288,\"len\":18},{\"max(rate)\":0.5634,\"len\":19},{\"max(rate)\":0.9288,\"len\":20},{\"max(rate)\":0.5733,\"len\":21},{\"max(rate)\":0.5876,\"len\":22},{\"max(rate)\":0.5634,\"len\":23},{\"max(rate)\":0.8618,\"len\":24},{\"max(rate)\":0.3222,\"len\":25},{\"max(rate)\":0.5634,\"len\":26},{\"max(rate)\":0.7189,\"len\":27},{\"max(rate)\":0.1076,\"len\":28},{\"max(rate)\":0.7189,\"len\":29},{\"max(rate)\":0.0959,\"len\":30},{\"max(rate)\":0.0192,\"len\":31},{\"max(rate)\":0.1673,\"len\":32},{\"max(rate)\":0.1673,\"len\":33},{\"max(rate)\":0.0600,\"len\":34},{\"max(rate)\":0.1076,\"len\":35},{\"max(rate)\":0.1076,\"len\":36},{\"max(rate)\":0.8618,\"len\":37},{\"max(rate)\":0.1076,\"len\":38},{\"max(rate)\":0.0025,\"len\":39},{\"max(rate)\":0.1076,\"len\":40},{\"max(rate)\":0.0005,\"len\":44},{\"max(rate)\":0.0008,\"len\":45},{\"max(rate)\":0.0004,\"len\":46},{\"max(rate)\":0.0192,\"len\":49}]";
        JSONArray ja = JSONArray.parseArray(json);
        List<Pair<Double, Integer>> ls = ja.stream()
                .map(o -> {
                    JSONObject jo = (JSONObject) o;
                    return Pair.of(jo.getDouble("max(rate)"), jo.getInteger("len"));
                })
                .collect(Collectors.toList());
        json = "[{\"count(len)/464\":0.0022,\"len\":6},{\"count(len)/464\":0.0022,\"len\":8},{\"count(len)/464\":0.0022,\"len\":9},{\"count(len)/464\":0.0022,\"len\":11},{\"count(len)/464\":0.0108,\"len\":13},{\"count(len)/464\":0.0194,\"len\":14},{\"count(len)/464\":0.0345,\"len\":15},{\"count(len)/464\":0.0345,\"len\":16},{\"count(len)/464\":0.0625,\"len\":17},{\"count(len)/464\":0.0345,\"len\":18},{\"count(len)/464\":0.0603,\"len\":19},{\"count(len)/464\":0.0560,\"len\":20},{\"count(len)/464\":0.0841,\"len\":21},{\"count(len)/464\":0.0776,\"len\":22},{\"count(len)/464\":0.0647,\"len\":23},{\"count(len)/464\":0.0517,\"len\":24},{\"count(len)/464\":0.0625,\"len\":25},{\"count(len)/464\":0.0560,\"len\":26},{\"count(len)/464\":0.0409,\"len\":27},{\"count(len)/464\":0.0259,\"len\":28},{\"count(len)/464\":0.0345,\"len\":29},{\"count(len)/464\":0.0216,\"len\":30},{\"count(len)/464\":0.0043,\"len\":31},{\"count(len)/464\":0.0237,\"len\":32},{\"count(len)/464\":0.0237,\"len\":33},{\"count(len)/464\":0.0237,\"len\":34},{\"count(len)/464\":0.0194,\"len\":35},{\"count(len)/464\":0.0129,\"len\":36},{\"count(len)/464\":0.0172,\"len\":37},{\"count(len)/464\":0.0086,\"len\":38},{\"count(len)/464\":0.0086,\"len\":39},{\"count(len)/464\":0.0065,\"len\":40},{\"count(len)/464\":0.0022,\"len\":44},{\"count(len)/464\":0.0022,\"len\":45},{\"count(len)/464\":0.0022,\"len\":46},{\"count(len)/464\":0.0043,\"len\":49}]";
        JSONArray ja2 = JSONArray.parseArray(json);
        Map<Integer, Double> map = ja2.stream()
                .map(JSONObject.class::cast)
                .collect(Collectors.toMap(jo ->  jo.getInteger("len"), jo -> jo.getDouble("count(len)/464")));
        double sum = ls.stream()
                .mapToDouble(p -> p.getLeft() * p.getRight() * map.getOrDefault(p.getRight(), 0D))
                .sum();
        System.out.println(sum * 36071646 / 1024 / 1024 / 1024);
    }

}
