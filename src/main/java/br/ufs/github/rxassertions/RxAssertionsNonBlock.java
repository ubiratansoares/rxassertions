package br.ufs.github.rxassertions;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.observables.BlockingObservable;

public final class RxAssertionsNonBlock {

    public static <T> NonBlockingTestSubscriberAssertionsWrapper<T> assertThat(Observable<T> observable) {
        return new NonBlockingTestSubscriberAssertionsWrapper<T>(observable);
    }

    public static <T> NonBlockingTestSubscriberAssertionsWrapper<T> assertThat(Completable observable) {
        return assertThat(observable.toObservable());
    }

    public static <T> NonBlockingTestSubscriberAssertionsWrapper<T> assertThat(Single<T> observable) {
        return assertThat(observable.toObservable());
    }

    public static <T> BlockingObservableAssert<T> assertThat(BlockingObservable<T> observable) {
        return RxAssertions.assertThat(observable);
    }

    private RxAssertionsNonBlock() {
        throw new AssertionError("No instances, please");
    }
}
