# AIO
 
//add tag links
 
A project with a future to show latest trends on Android.

## Who is it for?

- Android Developer folks
- Koltin fans
- Want to learn a real usage of a tool
- Interested in the architecture in use
- Want to participate on crafting a great app
- Beginners/Intermediates/Advanced developers
- Just here to copy-past?! 

## Images of AIO

//show some image


## About

AIO stand for All In One. it's there because I wanted to say 
you might find everything at one place (no surprises!)

AIO is Kotlin-First. so everything you see here in this repository will be in Kotlin.

For now I'm using TMDB as backend and API source.

Architecture is MVI/UDF Using RxJava.

Using [Bitrise](https://www.bitrise.io/) for CI/CD. Release generation is all automatic and human less. every push to master causes a new release.

I try to explain every path I take every workaround I make and all and all anything interesting on my Blog : [worldsnas.com](https://worldsnas.com)
- [Setup a Side-Project](http://bit.ly/326Rm8a)
- [Why I chose Dagger, A Dagger Story](http://bit.ly/2Si0mD2)
- [Deep Dive in Dagger Setup](http://bit.ly/2JOGQu3)
- [Setting up a CI server](http://bit.ly/335AYFx)

make sure to have a look there for a detailed explanation.

## Installing/Running

- First create a local.properties in root folder
- Get a [TMDB API-Key](https://developers.themoviedb.org/3/getting-started/introduction) and add to the properties file with "API_KEY" tag
- Add a empty field for these keys: 
  - keyStorePass
  - aioAlias
  - keyPass
  - keyStoreAddress
  - Fabric_API_KEY
- Lastly disable GoogleServices plugin by commenting ```apply plugin: 'com.google.gms.google-services'``` in ```app/build.gradle``` file


Now you can open the project in AndroidStudio and build/Test/Run it.


## Latest Release

The project has not yet been published on google play. but you can grab the latest release in two ways:

- Using Telegram: [AIO Release Channel](https://t.me/AIO_release)
- Github Release page: [Releases](https://github.com/worldsnas/AIO/releases)


## Features and Goals

All the detail of project Milestones/Features/Progress/Done can be found on [Github Project](https://github.com/worldsnas/AIO/projects/1) for this Repository.
I try to keep it updated and as detailed as possible.


## Technologies

- [Conductor](https://www.bitrise.io/): A small, yet full-featured framework that allows building View-based Android applications. 
- [Dagger2](https://dagger.dev/users-guide): Dependency Injection framework for both Java and Android.
- [ButterKnife](https://github.com/JakeWharton/butterknife): View Binding into fields.
- [RxJava](https://github.com/ReactiveX/RxJava): A library for composing asynchronous and event-based programs by using observable sequences.
- [Epoxy](https://github.com/airbnb/epoxy): Epoxy is an Android library for building complex screens in a RecyclerView.
- [DynamicFeatures](https://developer.android.com/studio/projects/dynamic-delivery): Deliver code to Android onDemand.
- [Retrofit](https://github.com/square/retrofit): Type-safe HTTP client for Android and Java.
- [OkHttp](https://github.com/square/okhttp): An HTTP client for Android, Kotlin, and Java.
- [Fresco](https://github.com/facebook/fresco): An Android library for managing images and the memory they use.
- [LeakCanary](https://github.com/square/leakcanary): LeakCanary is a memory leak detection library for Android.
- [AssertJ](https://joel-costigliola.github.io/assertj/): Fluent assertions for java. 
- [Mockk](https://github.com/mockk/mockk): Mocking library for Kotlin.
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver): A scriptable web server for testing HTTP clients.
- [Robolectric](http://robolectric.org/): Running Android Tests on JVM.
- [Androidx Test](https://developer.android.com/training/testing/): Helper libraries for Android Tests.

## Contribution

There are a ton of stuff that you can contribute to.

- Readme!
- Reporting issues with the app (by creating new issues on this repo)
- New cool feature in mind? add it to the Project page.
- Like to add documentation just send a PR
- Like to see a feature in the app quicker? try to implement it yourself and send a PR
- A typo? sure why not?
- Bug Fix? shoot it!
- Different approach to some implementation? I'm all open.
- Something bothers in this repo? open a discussion issue so we can talk.


