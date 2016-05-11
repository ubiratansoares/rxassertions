package br.ufs.github.rxassertions.tests;

import br.ufs.github.rxassertions.BlockingObservableAssert;
import org.junit.Test;
import rx.Observable;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class BlockingObservableAssertTests {

    @Test public void completesAssertion_ShouldPass() {
        new BlockingObservableAssert<>(Observable.just("a", "b").toBlocking())
                .completes();
    }

    @Test public void emissionsAssertion_ShouldPass() {
        new BlockingObservableAssert<>(Observable.range(1, 3).toBlocking())
                .emissionsCount(3);
    }

    @Test public void failsAssertion_ShouldPass() {
        new BlockingObservableAssert<>(
                Observable.error(new RuntimeException()).toBlocking()
        ).fails();
    }

    @Test public void failsWithThrowableAssertion_ShouldPass() {
        new BlockingObservableAssert<>(
                Observable.error(new RuntimeException()).toBlocking()
        ).failsWithThrowable(RuntimeException.class);
    }

}
