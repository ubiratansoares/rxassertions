package br.ufs.github.rxassertions;

import org.assertj.core.api.AbstractAssert;
import rx.Notification;
import rx.observables.BlockingObservable;
import rx.observers.TestSubscriber;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class BlockingObservableAssert<T>
        extends AbstractAssert<BlockingObservableAssert<T>, BlockingObservable<T>> {

    private List<Throwable> onErrorEvents;
    private List<T> onNextEvents;
    private List<Notification<T>> onCompletedEvents;

    public BlockingObservableAssert(BlockingObservable<T> actual) {
        super(actual, BlockingObservableAssert.class);
        TestSubscriber<T> subscriber = new TestSubscriber<>();
        actual.subscribe(subscriber);
        onErrorEvents = subscriber.getOnErrorEvents();
        onNextEvents = subscriber.getOnNextEvents();
        onCompletedEvents = subscriber.getOnCompletedEvents();
    }

    public BlockingObservableAssert completes() {
        assertThat(onCompletedEvents).isNotNull().isNotEmpty();
        return this;
    }

    public BlockingObservableAssert emissionsCount(int count) {
        assertThat(onNextEvents).hasSize(count);
        return this;
    }

    public BlockingObservableAssert fails() {
        assertThat(onErrorEvents).isNotNull().isNotEmpty();
        return this;
    }

    public BlockingObservableAssert failsWithThrowable(Class thowableClazz) {
        assertThat(onErrorEvents.get(0)).isInstanceOf(thowableClazz);
        return this;
    }

    public BlockingObservableAssert emitsNothing() {
        assertThat(onNextEvents).isEmpty();
        return this;
    }
}
