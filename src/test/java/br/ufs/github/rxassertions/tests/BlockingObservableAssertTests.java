package br.ufs.github.rxassertions.tests;

import br.ufs.github.rxassertions.BlockingObservableAssert;
import org.junit.Test;
import rx.Observable;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class BlockingObservableAssertTests {

    @Test public void completesAssertionShouldPass() {
        new BlockingObservableAssert<>(Observable.just("a", "b").toBlocking())
                .completes();
    }

    @Test public void emissionsAssertionsShouldPass() {
        new BlockingObservableAssert<>(Observable.range(1, 3).toBlocking())
                .emissionsCount(3);
    }
}
