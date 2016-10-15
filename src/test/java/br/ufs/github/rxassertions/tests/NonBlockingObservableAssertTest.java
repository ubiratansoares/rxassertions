package br.ufs.github.rxassertions.tests;

import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.ufs.github.rxassertions.NonBlockingObservableAssert;
import rx.Observable;
import rx.schedulers.TestScheduler;

public class NonBlockingObservableAssertTest  {

    @Test
    public void completesAssertion_ShouldPass() {
        Observable<String> obs = Observable.just("a", "b");
        new NonBlockingObservableAssert<>(obs).completes();
    }

    @Test(expected = AssertionError.class)
    public void completes_ChainingWithFail_ShouldFail() {
        Observable<Void> obs = Observable.error(new RuntimeException());
        new NonBlockingObservableAssert<>(obs).fails().completes();
    }

    @Test
    public void completesShouldRespectScheduler() throws Exception {
        TestScheduler scheduler = new TestScheduler();
        Observable<String> obs = Observable.just("a", "b").subscribeOn(scheduler);
        NonBlockingObservableAssert<String> assertion = new NonBlockingObservableAssert<>(obs);
        assertion.notCompletes();
        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertion.completes();
    }

    @Test
    public void errorsShouldRespectScheduler() throws Exception {
        TestScheduler scheduler = new TestScheduler();
        Observable<Void> obs = Observable.<Void>error(new RuntimeException()).subscribeOn(scheduler);
        NonBlockingObservableAssert<Void> assertion = new NonBlockingObservableAssert<>(obs);
        assertion.withoutErrors();
        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertion.fails();
    }

    @Test
    public void onNextShouldRespectScheduler() throws Exception {
        TestScheduler scheduler = new TestScheduler();
        Observable<Long> obs = Observable.interval(1, TimeUnit.MILLISECONDS, scheduler);
        NonBlockingObservableAssert<Long> assertion = new NonBlockingObservableAssert<>(obs);
        assertion.emitsNothing();
        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertion.emissionsCount(1);
        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertion.emissionsCount(2);
    }

    @Test public void notCompletesAssertion_ShouldPass() {
        Observable<Void> obs = Observable.error(new IllegalStateException());
        new NonBlockingObservableAssert<>(obs).notCompletes();
    }

    @Test public void emissionsAssertion_ShouldPass() {
        Observable<Integer> obs = Observable.range(1, 3);
        new NonBlockingObservableAssert<>(obs).emissionsCount(3);
    }

    @Test public void failsAssertion_ShouldPass() {
        Observable<Void> obs = Observable.error(new RuntimeException());
        new NonBlockingObservableAssert<>(obs).fails();
    }

    @Test public void failsWithThrowableAssertion_ShouldPass() {
        Observable<Void> obs = Observable.error(new RuntimeException());
        new NonBlockingObservableAssert<>(obs).failsWithThrowable(RuntimeException.class);
    }

    @Test public void emitisNothingAssertion_ShouldPass() {
        Observable<Void> obs = Observable.from(Collections.emptyList());
        new NonBlockingObservableAssert<>(obs).emitsNothing();
    }

    @Test public void receivesTerminalEvent_OnCompleted_ShouldPass() {
        Observable<String> obs = Observable.just("c", "d");
        new NonBlockingObservableAssert<>(obs).receivedTerminalEvent();
    }

    @Test public void receivesTerminalEvents_OnError_ShouldPass() {
        Observable<Void> obs = Observable.error(new RuntimeException());
        new NonBlockingObservableAssert<>(obs).receivedTerminalEvent();
    }

    @Test public void withoutErrors_ShouldPass() {
        Observable<String> obs = Observable.just("RxJava");
        new NonBlockingObservableAssert<>(obs).withoutErrors();
    }

    @Test public void withExpectedSingleValue_ShouldPass() {
        Observable<String> obs = Observable.just("Expected");
        new NonBlockingObservableAssert<>(obs).expectedSingleValue("Expected");
    }

    @Test(expected = AssertionError.class)
    public void withUnexpectedSingleValue_ShouldFail() {
        Observable<String> obs = Observable.just("Unexpected");
        new NonBlockingObservableAssert<>(obs).expectedSingleValue("Expected");
    }

    @Test public void withConditionsMatchingEachItem_ShoulPass() {
        List<String> expected = Arrays.asList("These", "are", "Expected", "Values");
        Observable<String> obs = Observable.from(expected);

        Condition<String> atLeastTwoChars = new Condition<String>() {
            @Override public boolean matches(String value) {
                return value.length() >= 2;
            }
        };

        Condition<String> noMoreThanTenChars = new Condition<String>() {
            @Override public boolean matches(String value) {
                return value.length() <= 10;
            }
        };

        new NonBlockingObservableAssert<>(obs)
              .eachItemMatches(atLeastTwoChars)
              .eachItemMatches(noMoreThanTenChars);
    }

    @Test public void withConditionsMatchingOnlyOneItem_ShoulPass() {
        List<String> expected = Arrays.asList("These", "are", "Expected", "Values");
        Observable<String> obs = Observable.from(expected);

        Condition<String> exactlyThreeChars = new Condition<String>() {
            @Override public boolean matches(String value) {
                return value.length() == 3;
            }
        };

        new NonBlockingObservableAssert<>(obs).oneEmissionMatches(exactlyThreeChars);
    }

    @Test public void withConditionsNotMatchingAnyItems_ShoulPass() {
        List<String> expected = Arrays.asList("These", "are", "Expected", "Values");
        Observable<String> obs = Observable.from(expected);

        Condition<String> nullString = new Condition<String>() {
            @Override public boolean matches(String value) {
                return value == null;
            }
        };

        new NonBlockingObservableAssert<>(obs).allItemsNotMaching(nullString);
    }

    @Test(expected = AssertionError.class)
    public void withConditionsNotMathchingEachItem_ShouldFail() {
        List<String> expected = Arrays.asList("These", "two");
        Observable<String> obs = Observable.from(expected);

        Condition<String> moreThanFourChars = new Condition<String>() {
            @Override public boolean matches(String value) {
                return value.length() >= 4;
            }
        };

        new NonBlockingObservableAssert<>(obs).eachItemMatches(moreThanFourChars);
    }

    @Test public void withContains_ShoudPass() {
        List<String> expected = Arrays.asList("Expected", "Values");
        Observable<String> obs = Observable.from(expected);
        new NonBlockingObservableAssert<>(obs).emits("Values");
    }

    @Test(expected = AssertionError.class) public void withContains_ShoudFail() {
        List<String> expected = Arrays.asList("Expected", "Values");
        Observable<String> obs = Observable.from(expected);
        new NonBlockingObservableAssert<>(obs).emits("RxJava");
    }

    @Test public void failsAssertion_WithCondition_ShouldPass() {
        Observable<Void> obs = Observable.error(new NullPointerException("Desired message"));

        Condition<Throwable> messageChecking = new Condition<Throwable>() {
            @Override public boolean matches(Throwable value) {
                return value.getMessage().contains("Desired message");
            }
        };

        new NonBlockingObservableAssert<>(obs).failsOnCondition(messageChecking);
    }
}
