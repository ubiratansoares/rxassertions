package br.ufs.github.rxassertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;
import rx.Notification;
import rx.observables.BlockingObservable;
import rx.observers.TestSubscriber;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ubiratansoares for RxJava Workshop.
 */

class TestSubscriberAssertionsWrapper<T>
        extends AbstractAssert<TestSubscriberAssertionsWrapper<T>, BlockingObservable<T>> {

    private List<Throwable> onErrorEvents;
    private List<T> onNextEvents;
    private List<Notification<T>> onCompletedEvents;

    TestSubscriberAssertionsWrapper(BlockingObservable<T> actual) {
        super(actual, TestSubscriberAssertionsWrapper.class);
        TestSubscriber<T> subscriber = new TestSubscriber<>();
        actual.subscribe(subscriber);
        onErrorEvents = subscriber.getOnErrorEvents();
        onNextEvents = subscriber.getOnNextEvents();
        onCompletedEvents = subscriber.getOnCompletedEvents();
    }

    TestSubscriberAssertionsWrapper<T> completes() {
        assertThat(onCompletedEvents).isNotNull().isNotEmpty();
        return this;
    }

    TestSubscriberAssertionsWrapper<T> notCompletes() {
        assertThat(onCompletedEvents).isNullOrEmpty();
        return this;
    }

    TestSubscriberAssertionsWrapper<T> emissionsCount(int count) {
        assertThat(onNextEvents).isNotNull().isNotEmpty().hasSize(count);
        return this;
    }

    TestSubscriberAssertionsWrapper<T> fails() {
        assertThat(onErrorEvents).isNotNull().isNotEmpty();
        return this;
    }

    TestSubscriberAssertionsWrapper<T> failsWithThrowable(Class thowableClazz) {
        assertThat(onErrorEvents).isNotNull();
        assertThat(onErrorEvents.get(0)).isInstanceOf(thowableClazz);
        return this;
    }

    TestSubscriberAssertionsWrapper<T> emitsNothing() {
        assertThat(onNextEvents).isEmpty();
        return this;
    }

    TestSubscriberAssertionsWrapper<T> receivedTerminalEvent() {
        assertThat(onCompletedEvents).isNotNull();
        assertThat(onErrorEvents).isNotNull();
        assertThat(onCompletedEvents.size() + onErrorEvents.size()).isEqualTo(1);
        return this;
    }

    TestSubscriberAssertionsWrapper<T> withoutErrors() {
        assertThat(onErrorEvents).isNotNull().isEmpty();
        return this;
    }

    TestSubscriberAssertionsWrapper<T> expectedSingleValue(T expected) {
        assertThat(onNextEvents)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(onNextEvents.get(0)).isEqualTo(expected);
        return this;
    }

    public TestSubscriberAssertionsWrapper<T> expectedValues(T... expected) {
        return expectedValues(Arrays.asList(expected));
    }

    TestSubscriberAssertionsWrapper<T> expectedValues(Collection<T> ordered) {
        assertThat(onNextEvents)
                .isNotNull()
                .isNotEmpty()
                .hasSameElementsAs(ordered);
        return this;
    }

    TestSubscriberAssertionsWrapper<T> eachItemMatches(Condition<? super T> condition) {
        assertThat(onNextEvents).are(condition);
        return this;
    }

    TestSubscriberAssertionsWrapper<T> oneEmissionMatches(Condition<? super T> condition) {
        assertThat(onNextEvents).areAtLeast(1, condition);
        return this;
    }

    TestSubscriberAssertionsWrapper<T> allItemsNotMaching(Condition<? super T> condition) {
        assertThat(onNextEvents).areNot(condition);
        return this;
    }

    TestSubscriberAssertionsWrapper<T> emits(T... values) {
        assertThat(onNextEvents).contains(values);
        return this;
    }

    TestSubscriberAssertionsWrapper<T> failsOnCondition(Condition<? super Throwable> condition) {
        assertThat(onErrorEvents).areAtLeast(1, condition);
        return this;
    }
}
