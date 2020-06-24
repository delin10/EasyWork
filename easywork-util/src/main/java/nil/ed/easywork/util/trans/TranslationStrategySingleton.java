package nil.ed.easywork.util.trans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import nil.ed.easywork.util.trans.impl.BaiduApiTranslationStrategy;
import nil.ed.easywork.util.trans.impl.GoogleApiTranslationStrategy;

/**
 * @author delin10
 * @since 2020/6/24
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TranslationStrategySingleton implements TranslationStrategy {
    /**
     * 翻译api
     */
    BAIDU(new BaiduApiTranslationStrategy()),
    GOOGLE(new GoogleApiTranslationStrategy());
    private TranslationStrategy strategy;

    @Override
    public List<String> trans(String chineseName) {
        return strategy.trans(chineseName);
    }
}
