package br.ufs.github.rxassertions;

import org.assertj.core.api.Condition;
import rx.observables.BlockingObservable;

import java.util.Collection;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class BlockingObservableAssert<T> {

    private TestSubscriberAssertionsWrapper<T> wrapper;

    public BlockingObservableAssert(BlockingObservable<T> actual) {
        wrapper = new TestSubscriberAssertionsWrapper<>(actual);
    }

    public BlockingObservableAssert<T> completes() {
        wrapper.completes();
        return this;
    }

    public BlockingObservableAssert<T> notCompletes() {
        wrapper.notCompletes();
        return this;
    }

    public BlockingObservableAssert<T> emissionsCount(int count) {
        wrapper.emissionsCount(count);
        return this;
    }

    public BlockingObservableAssert<T> fails() {
        wrapper.fails();
        return this;
    }

    public BlockingObservableAssert<T> failsWithThrowable(Class thowableClazz) {
        wrapper.failsWithThrowable(thowableClazz);
        return this;
    }

    public BlockingObservableAssert<T> emitsNothing() {
        wrapper.emitsNothing();
        return this;
    }

    public BlockingObservableAssert<T> receivedTerminalEvent() {
        wrapper.receivedTerminalEvent();
        return this;
    }

    public BlockingObservableAssert<T> withoutErrors() {
        wrapper.withoutErrors();
        return this;
    }

    public BlockingObservableAssert<T> expectedSingleValue(T expected) {
        wrapper.expectedSingleValue(expected);
        return this;
    }

    public BlockingObservableAssert<T> expectedValues(T... ordered) {
        wrapper.expectedValues(ordered);
        return this;
    }

    public BlockingObservableAssert<T> expectedValues(Collection<T> ordered) {
        wrapper.expectedValues(ordered);
        return this;
    }

    public BlockingObservableAssert<T> eachItemMatches(Condition<? super T> condition) {
        wrapper.eachItemMatches(condition);
        return this;
    }

    public BlockingObservableAssert<T> oneEmissionMatches(Condition<? super T> condition) {
        wrapper.oneEmissionMatches(condition);
        return this;
    }

    public BlockingObservableAssert<T> allItemsNotMaching(Condition<? super T> condition) {
        wrapper.allItemsNotMaching(condition);
        return this;
    }

    public BlockingObservableAssert<T> emits(T... values) {
        wrapper.emits(values);
        return this;
    }

    public BlockingObservableAssert<T> failsOnCondition(Condition<? super Throwable> condition) {
        wrapper.failsOnCondition(condition);
        return this;
    }
}
