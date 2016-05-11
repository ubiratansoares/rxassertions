package br.ufs.github.rxassertions.tests;

import br.ufs.github.rxassertions.RxAssertions;
import org.junit.Test;
import rx.Completable;
import rx.Observable;

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
        RxAssertions.assertThat(Observable.error(new IllegalArgumentException()))
                .fails();
    }

    @Test public void failWithException() {
        RxAssertions.assertThat(Observable.error(new IllegalStateException()))
                .failsWithThrowable(IllegalStateException.class);
    }

    @Test public void noEmissions() {
        RxAssertions.assertThat(Observable.empty())
                .emitsNothing();
    }

    @Test public void noEmissions_Completes() {
        RxAssertions.assertThat(Observable.empty())
                .emitsNothing()
                .completes();
    }

    @Test public void completable_completesWithoutErrors() {
        RxAssertions.assertThat(
                Completable.fromObservable(Observable.range(1, 5)))
                .completes()
                .withoutErrors();
    }

}
