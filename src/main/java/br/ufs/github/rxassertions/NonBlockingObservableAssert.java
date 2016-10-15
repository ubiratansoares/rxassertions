package br.ufs.github.rxassertions;

import org.assertj.core.api.Condition;

import java.util.Collection;

import rx.Observable;

public class NonBlockingObservableAssert<T> {

    private NonBlockingTestSubscriberAssertionsWrapper <T> wrapper;

    public NonBlockingObservableAssert(Observable<T> actual) {
        wrapper = new NonBlockingTestSubscriberAssertionsWrapper<>(actual);
    }

    public NonBlockingObservableAssert<T> completes() {
        wrapper.completes();
        return this;
    }

    public NonBlockingObservableAssert<T> notCompletes() {
        wrapper.notCompletes();
        return this;
    }

    public NonBlockingObservableAssert<T> emissionsCount(int count) {
        wrapper.emissionsCount(count);
        return this;
    }

    public NonBlockingObservableAssert<T> fails() {
        wrapper.fails();
        return this;
    }

    public NonBlockingObservableAssert<T> failsWithThrowable(Class thowableClazz) {
        wrapper.failsWithThrowable(thowableClazz);
        return this;
    }

    public NonBlockingObservableAssert<T> emitsNothing() {
        wrapper.emitsNothing();
        return this;
    }

    public NonBlockingObservableAssert<T> receivedTerminalEvent() {
        wrapper.receivedTerminalEvent();
        return this;
    }

    public NonBlockingObservableAssert<T> withoutErrors() {
        wrapper.withoutErrors();
        return this;
    }

    public NonBlockingObservableAssert<T> expectedSingleValue(T expected) {
        wrapper.expectedSingleValue(expected);
        return this;
    }

    public NonBlockingObservableAssert<T> expectedValues(Collection<T> ordered) {
        wrapper.expectedValues(ordered);
        return this;
    }

    public NonBlockingObservableAssert<T> eachItemMatches(Condition<? super T> condition) {
        wrapper.eachItemMatches(condition);
        return this;
    }

    public NonBlockingObservableAssert<T> oneEmissionMatches(Condition<? super T> condition) {
        wrapper.oneEmissionMatches(condition);
        return this;
    }

    public NonBlockingObservableAssert<T> allItemsNotMaching(Condition<? super T> condition) {
        wrapper.allItemsNotMaching(condition);
        return this;
    }

    public NonBlockingObservableAssert<T> emits(T... values) {
        wrapper.emits(values);
        return this;
    }

    public NonBlockingObservableAssert<T> failsOnCondition(Condition<? super Throwable> condition) {
        wrapper.failsOnCondition(condition);
        return this;
    }
}

