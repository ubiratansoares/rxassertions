package br.ufs.github.rxassertions.tests;

import br.ufs.github.rxassertions.RxAssertions;
import org.assertj.core.api.Condition;
import org.junit.Test;
import rx.Completable;
import rx.Observable;
import rx.Single;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class RxAssertionsTests {

    @Test public void factoryMethod_BlockingObservable_ReturnsNotNull() {
        assertThat(RxAssertions.assertThat(Observable.just(1).toBlocking()))
                .isNotNull();
    }

    @Test public void factoryMethod_RegularObservable_ReturnsNotNull() {
        assertThat(RxAssertions.assertThat(Observable.just("Test")))
                .isNotNull();
    }

    @Test public void factoryMethod_Single_ReturnsNotNull() {
        Single<Boolean> single = Single.fromCallable(() -> Boolean.TRUE);
        assertThat(RxAssertions.assertThat(single)).isNotNull();
    }

    @Test public void factoryMethod_Completable_ReturnsNotNull() {
        Completable completable = Completable.fromCallable(() -> "Done");
        assertThat(RxAssertions.assertThat(completable)).isNotNull();
    }

    @Test public void emissionsCount() {
        RxAssertions.assertThat(Observable.range(1, 10))
                .emissionsCount(10);
    }

    @Test public void canChain_TwoAssertions() {
        RxAssertions.assertThat(Observable.range(1, 5))
                .emissionsCount(5)
                .completes();
    }

    @Test public void simpleFail() {
        Observable<?> error = Observable.error(new IllegalArgumentException());
        RxAssertions.assertThat(error).fails().notCompletes();
    }

    @Test public void failWithException() {
        Observable<?> error = Observable.error(new IllegalStateException());
        RxAssertions.assertThat(error).failsWithThrowable(IllegalStateException.class);
    }

    @Test public void noEmissions() {
        RxAssertions.assertThat(Observable.empty())
                .emitsNothing()
                .completes()
                .withoutErrors();
    }

    @Test public void regularObservable_CompletesWithEmissions() {
        RxAssertions.assertThat(Observable.just("RxJava", "Assertions"))
                .completes()
                .withoutErrors()
                .emits("RxJava", "Assertions");
    }

    @Test public void completable_completesWithoutErrors() {
        Completable trivial = Completable.fromObservable(Observable.range(1, 5));
        RxAssertions.assertThat(trivial)
                .completes()
                .withoutErrors();
    }

    @Test public void single_CompletesWithExpectedValue() {
        Single<String> single = Single.fromCallable(() -> "RxJava");
        RxAssertions.assertThat(single).completes().expectedSingleValue("RxJava");
    }

    @Test public void multipleEmissions_AreMatchedOnCondition() {
        List<String> expected = Arrays.asList("These", "are", "Expected", "Values");
        Observable<String> values = Observable.from(expected);

        Condition<String> notNullAndNotEmpty = new Condition<String>() {
            @Override public boolean matches(String value) {
                return value != null && !value.isEmpty();
            }
        };

        RxAssertions.assertThat(values)
                .completes()
                .eachItemMatches(notNullAndNotEmpty);
    }

    @Test public void singleEmission_IsMatchedOnCondition() {
        List<String> expected = Arrays.asList("These", "are", "the", "values");
        Observable<String> values = Observable.from(expected);

        Condition<String> withThreeChars = new Condition<String>() {
            @Override public boolean matches(String value) {
                return value != null && value.length() == 3;
            }
        };

        RxAssertions.assertThat(values)
                .completes()
                .oneEmissionMatches(withThreeChars);
    }

    @Test public void multipleEmissions_AreNotMatchedOnCondition() {
        List<String> expected = Arrays.asList("These", "are", "Expected", "Values");
        Observable<String> values = Observable.from(expected);

        Condition<String> greaterThanTen = new Condition<String>() {
            @Override public boolean matches(String value) {
                return value != null && value.length() > 10;
            }
        };

        RxAssertions.assertThat(values)
                .completes()
                .allItemsNotMaching(greaterThanTen);
    }

    @Test public void emissions_Contains_DesiredValues() {
        Observable<Integer> values = Observable.range(1, 100);

        RxAssertions.assertThat(values)
                .completes()
                .emits(10)
                .emits(20);
    }
}
