package br.ufs.github.rxassertions.tests;

import br.ufs.github.rxassertions.RxAssertions;
import org.junit.Test;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class RxAssertionsTests {

    @Test public void test_factoryMethod_BlockingObservableReturnsNotNull() {
        assertThat(RxAssertions.assertThat(Observable.just(1).toBlocking()))
                .isNotNull();
    }

    @Test public void test_factoryMethod_RegularObservableReturnsNotNull() {
        assertThat(RxAssertions.assertThat(Observable.just("Test")))
                .isNotNull();
    }

    @Test public void test_EmissionsCount() {
        RxAssertions.assertThat(Observable.range(1, 10))
                .emissionsCount(10);
    }

    @Test public void test_CanChain_TwoAssertions() {
        RxAssertions.assertThat(Observable.range(1, 5))
                .emissionsCount(5)
                .completes();
    }

    @Test public void test_SimpleFail() {
        RxAssertions.assertThat(Observable.error(new IllegalArgumentException()))
                .fails();

    }

    @Test public void test_FailWithException() {
        RxAssertions.assertThat(Observable.error(new IllegalArgumentException()))
                .failsWithThrowable(IllegalArgumentException.class);

    }

}
