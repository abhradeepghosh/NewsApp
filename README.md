### News App :
============

An application to show news headlines and details which uses [NewsAPI](https://newsapi.org/) to fetch the top headlines.

## Architecture
---------------

The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.

![App_Architecture](https://user-images.githubusercontent.com/1692038/130364144-d5781f65-c655-4aa1-8fd5-eab04c8e8097.png)

## API key 🔑
----------

You'll need to provide API key to fetch the news from the News Service (API). Currently the news is fetched from [NewsAPI](https://newsapi.org/)

- Generate an API key (It's only 2 steps!) from [NewsAPI](https://newsapi.org/)
- Create new file named -> `credentials.properties` in our project root folder
- Add the API key as shown below [Make sure to keep the double quotes]:
```
    NEWS_API_KEY = "<INSERT_YOUR_API_KEY>"
```
- Build the app 

## Libraries and tools 🛠
----------------------

News App uses libraries and tools used to build Modern Android application, mainly part of Android Jetpack 🚀

- [Kotlin](https://kotlinlang.org/) first
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Architecture components](https://developer.android.com/topic/libraries/architecture/)
- [Dagger 2](https://developer.android.com/training/dependency-injection) for dependency injection 🗡
- [Retrofit](https://square.github.io/retrofit/)
- [Coil](https://github.com/coil-kt/coil)
- Other [Android Jetpack](https://developer.android.com/jetpack) components

Screen Design
-------------

<img src="https://user-images.githubusercontent.com/1692038/130364498-bbddb36e-eba3-4646-976f-67e3f29773ff.jpeg" width="250"> <img src="https://user-images.githubusercontent.com/1692038/130364506-a28ec2f4-1fe4-406d-95b5-f98f1723a690.jpeg" width="250">


