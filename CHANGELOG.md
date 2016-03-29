# Version 2.3.0 (?)

* [new] Add methods `exists()`, `count()` and `clear()` on `Repository`
* [brk] The `Comparable` interface have been removed from `BaseValueObject`
* [brk] Methods `do*()` on `BaseRepository` have been removed. Directly implement their matching method on `Repository`.
* [chg] The `business-web` module has been merged into `business-core` module (the dependency can be safely removed from your poms).
* [chg] `serialVersionUID` of event classes (`org.seedstack.business.domain.events` package) has been changed to 1L.

# Version 2.2.0 (2016-01-21)

* [new] `BaseRangeFinder` is a persistence-agnostic base class for paginated finders. It notably replaces `BaseJpaRangeFinder` from the JPA add-on.

# Version 2.1.0 (2015-11-26)

* [brk] Remove `org.seedstack.business.api.BaseClassSpecifications`. Use `org.seedstack.seed.core.utils.BaseClassSpecifications` instead.
* [brk] `business-test` module is merged into `business-core` module.
* [brk] `business-jpa` module is merged into `jpa` addon.

# Version 2.0.0 (2015-07-30)

* [nfo] Initial Open-Source version.
