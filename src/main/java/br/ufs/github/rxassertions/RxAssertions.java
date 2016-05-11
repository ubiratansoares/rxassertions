package br.ufs.github.rxassertions;

import rx.Observable;
import rx.observables.BlockingObservable;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class RxAssertions {

    public static <T> BlockingObservableAssert<T> assertThat(BlockingObservable<T> actual) {
        return new BlockingObservableAssert<>(actual);
    }

    public static <T> BlockingObservableAssert<T> assertThat(Observable<T> actual) {
        return new BlockingObservableAssert<>(actual.toBlocking());
    }

    private RxAssertions() {
        throw new AssertionError("No instances.");
    }
}
