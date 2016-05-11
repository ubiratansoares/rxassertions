# RxAssertions

RxAssertions is a simple idea for better RxJava assertions. 

I found the original idea from the guys of [Ribot](https://github.com/ribot/assertj-rx) : in fact, I think this a good idea and helps to keep tests clean. However, Ribot guys deprecated their original code some time ago in favor of TestSubscriber and I decided to take my own shot on this.

This library mimics and improves the original Ribot`s idea with the following goodies :

1. AssertJ assertions for RxJava (as the original one)
2. All tests rely on BlockingObservable internally
2. Internal API rely 100% on TestSubscriber
3. Improved public API mirroring from TestSubscriber provided assertions
4. Improved assertions entry points from factory methods, with Observable, BlockingObservable, Single and Completable support

Let`s see the benefits:

**Regular assertions with TestSubscriber**

        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        Observable.just("RxJava", "Assertions").toBlocking().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValues("RxJava", "Assertions");



**Assertions with RxAssertions**

        assertThat(Observable.just("RxJava", "Assertions"))
                .completes()
                .withoutErrors()
                .expectedValues("RxJava", "Assertions");


# Setup

// Todo

# Contributing

// Todo


# License

// To add

# Credits

- Ribot guys for the original idea
- RxJava and AssertJ guys for this awesome libraries
