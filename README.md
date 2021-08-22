News App :

An application to show news headlines and details which uses NewsAPI to fetch the top headlines.

Architecture

The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.

![App_Architecture](https://user-images.githubusercontent.com/1692038/130364144-d5781f65-c655-4aa1-8fd5-eab04c8e8097.png)

API key 🔑
You'll need to provide API key to fetch the news from the News Service (API). Currently the news is fetched from NewsAPI

Generate an API key (It's only 2 steps!) from [NewsAPI](https://newsapi.org/)
Create new file named -> credentials.properties in our project root folder
Add the API key as shown below [Make sure to keep the double quotes]:
    NEWS_API_KEY = "<INSERT_YOUR_API_KEY>"
Build the app


