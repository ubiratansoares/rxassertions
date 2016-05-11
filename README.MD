# RxAssertions

[![Status](https://travis-ci.com/ubiratansoares/rxassertions.svg?token=RHSqKpkhyMned4PZSn8q&branch=master)](https://travis-ci.org/ubiratansoares/rxassertions)  

RxAssertions is a simple idea for better RxJava assertions. 

I found the original idea from the guys of [Ribot](https://github.com/ribot/assertj-rx) : in fact, I think this a good idea and helps to keep tests clean.

However, Ribot guys deprecated their original repo some time ago in favor of vanilla [TestSubscriber](http://reactivex.io/RxJava/javadoc/rx/observers/TestSubscriber.html), so I decided to take my own shot on this.

This library mimics and improves the original Ribot`s idea with the following goodies :

1. AssertJ powered assertions for RxJava (as the original one)
2. All tests rely on `BlockingObservable` internally
2. Internal API rely 100% on `TestSubscriber`
3. Improved public API mirror most of TestSubscriber provided assertions
4. Improved _Assertions_ entry points from factory methods, with **Observable**, **BlockingObservable**, **Single** and **Completable** support

We can see some code diet :

**Regular assertions with TestSubscriber**
```java
TestSubscriber<String> testSubscriber = new TestSubscriber<>();
Observable.just("RxJava", "Assertions").toBlocking().subscribe(testSubscriber);
testSubscriber.assertCompleted();
testSubscriber.assertNoErrors();
testSubscriber.assertValues("RxJava", "Assertions");
```

**Assertions with RxAssertions**
```java
assertThat(Observable.just("RxJava", "Assertions"))
		.completes()
		.withoutErrors()
		.expectedValues("RxJava", "Assertions");
```

You can find other examples at `test` folder

# Setup

// Todo

# Contributing

PRs are wellcome. :rocket:

# Credits

- Ribot guys for the original idea
- RxJava and AssertJ guys for these awesome libraries

# License

```
Copyright (C) 2016 Ubiratan Soares

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


