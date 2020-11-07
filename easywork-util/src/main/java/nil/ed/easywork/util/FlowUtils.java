package nil.ed.easywork.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author lidelin.
 */
public class FlowUtils {

    public static <T> Stream<T> continueStreamIfNotNull(Collection<T> c) {
        if (CollectionUtils.isNotEmpty(c)) {
            return c.stream();
        }
        return Stream.empty();
    }

    public static <T> Stream<T> continueStreamIfNotNull(T[] array) {
        if (array != null && array.length != 0) {
            return Arrays.stream(array);
        }
        return Stream.empty();
    }

    public static <T> Collection<T> iterateIfNotNull(Collection<T> c) {
        if (CollectionUtils.isNotEmpty(c)) {
            return c;
        }
        return Collections.emptyList();
    }

    public static <T> Optional<T> continueIfNotNull(T o) {
        return Optional.ofNullable(o);
    }

    public static <S, T> Optional<T> continueIfValidCast(S o, Class<T> clazz) {
        if (o != null && clazz.isAssignableFrom(o.getClass())) {
            return Optional.of((T) o);
        }
        return Optional.empty();
    }

    public static <S, R> R notNullOrElse(S src, Function<S, R> mapper, R defaultValue) {
        return Optional.ofNullable(src).map(mapper).orElse(defaultValue);
    }

}
