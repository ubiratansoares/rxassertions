package br.ufs.github.rxassertions.tests;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import br.ufs.github.rxassertions.NonBlockingTestSubscriberAssertionsWrapper;
import br.ufs.github.rxassertions.RxAssertionsNonBlock;
import rx.Observable;
import rx.schedulers.TestScheduler;

public class UsageExamples {

    @Test
    public void testWithScheduler() throws Exception {
        TestScheduler scheduler = new TestScheduler();

        NonBlockingTestSubscriberAssertionsWrapper<Long> assertion =
              RxAssertionsNonBlock.assertThat(
                    Observable.interval(1, TimeUnit.MILLISECONDS, scheduler).take(3));
        assertion
              .notCompletes()
              .emitsNothing()
              .withoutErrors();

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertion.emissionsCount(1);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertion.emissionsCount(2);

        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        assertion.emissionsCount(3)
              .completes();
    }
}
