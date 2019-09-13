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

//linkify
Using Bitrise for CI/CD. Release generation is all automatic and human less. every push to master causes a new release.

//linkify
I try to explain every path I take every workaround I make and all and all anything interesting on my Blog : https://worldsnas.com 
- first
- second
- third

make sure to have a look there for a detailed explanation.

## Installing/Running

- First create a local.properties in root folder
- Get a TMDB API-Key and add to the properties file with "API_KEY" tag
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

- Using Telegram: https://t.me/AIO_release //linkify
- Github Release pages //add link


## Features and Goals
//linkify Github Project
All the detail of project Milestones/Features/Progress/Done can be found in Github Project for this Repository.
I try to keep it updated and as detailed as possible.


## Technologies

//linkify

- Conductor 
- Dagger2
- ButterKnife
- RxJava
- Epoxy
- DynamicFeatures
- Retrofit
- OkHttp
- Fresco
- LeakCanary
- AssertJ
- Mockk
- MockWebServer
- Robolectric
- Androidx Test

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


