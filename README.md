#Market Price#

This repository holds the source code of the Market Price Application, a simple Android client for the [Blockchain Market Price Graph](https://blockchain.info/charts/market-price).
This application was created by Mohsen Mirhoseini Argi, as part of the technical assessment by the [Number26](http://number26.de) team.

### What is this repository for? ###

* Market Price Android Application
* v1.0
* Last Update : Sun Mar 27, 2016

### Development Tools ###

* Android Studio v2.0 Beta 7
* gradle-plugin v2.10
* Android SDK Build Tools 23.0.2
* CompileSDKVersion 23

### Dependencies ###
* Android Support Tools v23.1.1
* DBFlow v3.0.0-beta5
* Retrofit v2.0.0-beta4
* Okhttp v3.2.0
* Butterknife v7.0.1
* AppSettings v1.0.0
* AndroidUtils v1.0.0
* jUnit v4.12
* Mockito v1.10.19
* Espresso v2.2.1

### Important Notes ###

The application has one Main Activity which is responsible for presenting data for a specific time span as a Graph view.

All activity lifecycle and network behaviours are implemented and according to situation user get a good response and UX. In case having cache data in Database application load Graph and wait for Network response. If no internet connection, a dialog popup and ask user to turn on it's network. It also shows SnackBar message if app is offline and data is loaded from cache.

A combination of cached data in Database and Network call makes Application work smooth and a result a good UX.

Some simple Test Cases was designed to test application UI functionality and inner classes using jUnit and AndroidUnitTest.

### Application Structure ###
The activities are implemented and structured bases on the MVP pattern, contributed by [Antonio Leiva](http://antonioleiva.com/mvp-android/).

The view, (MainActivity), contain a reference to the presenter. Presenter will be ideally provided by a dependency injector such as Dagger, but in this case I prefer to avoid Dagger because of time and project small scope, it will be responsible for creating the presenter object. The only thing that the view will do is calling a method from the presenter every time there is an interface action.

The presenter is responsible to act as the middle man between view and model. It retrieves data from the model and returns it formatted to the view. It also decides what happens when you interact with the view.

The model would only be the gateway to the domain layer or business logic. In this case it provide the data we want to display in the view from Network of cached data from Database.

The networking is managed by [Retrofit](http://square.github.io/retrofit/) and OkHttp as its httpclient, contributed by [Square](http://square.github.io). It also show decent logs while application is running in Debug mode.

The database is managed by [DBFlow](https://github.com/Raizlabs/DBFlow) as ORM database library, contributed by [Andrew Grosner](https://github.com/agrosner).

The settings of the application is persisted using AppSetting library, a wrapper for Android SharedPreferences, which takes advantage of HashMaps for faster save and retrieval, developed and published on jCenter by myself.

The Graph View is not a perfect one, but all developed by myself using Canvas. It was fast developed to meet deadline and require more maintenance before release.