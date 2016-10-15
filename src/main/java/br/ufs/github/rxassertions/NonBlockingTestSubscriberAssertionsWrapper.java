package br.ufs.github.rxassertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;

import java.util.Arrays;
import java.util.Collection;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

public class NonBlockingTestSubscriberAssertionsWrapper<T> extends
      AbstractAssert<NonBlockingTestSubscriberAssertionsWrapper<T>, Observable<T>> {

    private final TestSubscriber subscriber;

    NonBlockingTestSubscriberAssertionsWrapper(Observable<T> actual) {
        super(actual, NonBlockingTestSubscriberAssertionsWrapper.class);
        subscriber = new TestSubscriber<>();
        actual.subscribe(subscriber);
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> completes() {
        assertThat(subscriber.getOnCompletedEvents()).isNotNull().isNotEmpty();
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> notCompletes() {
        assertThat(subscriber.getOnCompletedEvents()).isNullOrEmpty();
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> emissionsCount(int count) {
        assertThat(subscriber.getOnNextEvents()).isNotNull().isNotEmpty().hasSize(count);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> fails() {
        assertThat(subscriber.getOnErrorEvents()).isNotNull().isNotEmpty();
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> failsWithThrowable(Class thowableClazz) {
        assertThat(subscriber.getOnErrorEvents()).isNotNull();
        assertThat(subscriber.getOnErrorEvents().get(0)).isInstanceOf(thowableClazz);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> emitsNothing() {
        assertThat(subscriber.getOnNextEvents()).isEmpty();
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> receivedTerminalEvent() {
        assertThat(subscriber.getOnCompletedEvents()).isNotNull();
        assertThat(subscriber.getOnErrorEvents()).isNotNull();
        assertThat(subscriber.getOnCompletedEvents().size() + subscriber.getOnErrorEvents().size()).isEqualTo(1);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> withoutErrors() {
        assertThat(subscriber.getOnErrorEvents()).isNotNull().isEmpty();
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> expectedSingleValue(T expected) {
        assertThat(subscriber.getOnNextEvents())
              .isNotNull()
              .isNotEmpty()
              .hasSize(1);

        assertThat(subscriber.getOnNextEvents().get(0)).isEqualTo(expected);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> expectedValues(T... expected) {
        return expectedValues(Arrays.asList(expected));
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> expectedValues(Collection<T> ordered) {
        assertThat(subscriber.getOnNextEvents())
              .isNotNull()
              .isNotEmpty()
              .hasSameElementsAs(ordered);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> eachItemMatches(Condition<? super T> condition) {
        assertThat(subscriber.getOnNextEvents()).are(condition);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> oneEmissionMatches(Condition<? super T> condition) {
        assertThat(subscriber.getOnNextEvents()).areAtLeast(1, condition);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> allItemsNotMaching(Condition<? super T> condition) {
        assertThat(subscriber.getOnNextEvents()).areNot(condition);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> emits(T... values) {
        assertThat(subscriber.getOnNextEvents()).contains(values);
        return this;
    }

    public NonBlockingTestSubscriberAssertionsWrapper<T> failsOnCondition(Condition<? super Throwable> condition) {
        assertThat(subscriber.getOnErrorEvents()).areAtLeast(1, condition);
        return this;
    }
}