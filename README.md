# PandApp Android
PandApp on Android
<div id="top"></div>
<a href="https://img.shields.io/badge/Swift5-100%25-yellow"><img alt="Android use" src="https://img.shields.io/badge/Android-100%25-green"></a> <a href="https://img.shields.io/badge/Kotlin.js-red"><img alt="StoryBoard use" src="https://img.shields.io/badge/Used-kotlin-blue"></a> 
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="200" height="200">
  </a>

  <h3 align="center">Best-README-Template</h3>

  <p align="center">
   PandApp android application
    <br />
    <a href="https://github.com/KlaiGhassen/androidPim"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/KlaiGhassen/PandApp-Android">View Demo</a>
    ·
    <a href="https://github.com/KlaiGhassen/PandApp-Android/issues">Report Bug</a>
    ·
    <a href="https://github.com/KlaiGhassen/PandApp-Android/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)
this is the frontEnd of PandApp Application and it is created with android using Kotlin and it consume restApi using expressJs
BackEnd repo : [PandApp BackEnd](https://github.com/KlaiGhassen/PandApp-backEnd)

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* [Android](https://www.android.com)
* [Kotlin](https://kotlinlang.org)
* Project dependencies
   ```sh
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.4.1'
    implementation 'androidx.navigation:navigation-ui-ktx:2.4.1'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    implementation('com.google.android.material:material:1.5.0')
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.9.3'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.9.3'
    implementation 'com.github.dhaval2404:imagepicker:2.1'
    implementation 'com.github.chnouman:AwesomeDialog:1.0.4'
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    implementation 'com.github.florent37:inline-activity-result:1.0.4'
    implementation 'com.github.bumptech.glide:glide:4.13.0'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation 'com.github.marcoscgdev:DialogSheet:2.1.2'
    implementation 'com.github.Spikeysanju:MotionToast:1.4'
    implementation 'com.airbnb.android:lottie:3.6.1'
    implementation 'com.github.florent37:shapeofview:1.4.7'
    implementation 'com.google.android.gms:play-services-auth:20.1.0'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation('io.socket:socket.io-client:1.0.0') {
        exclude group: 'org.json', module: 'json'
    }
    implementation "org.jetbrains.anko:anko:0.10.8"
    implementation "io.karn:notify:1.4.0"
    implementation 'com.github.barteksc:android-pdf-viewer:2.8.2'
    implementation 'com.mindorks.android:prdownloader:0.6.0'
    implementation 'com.github.kenglxn.qrgen:android:2.6.0'
    implementation 'com.journeyapps:zxing-android-embedded:4.3.0'
    implementation 'com.journeyapps:zxing-android-embedded:4.3.0@aar'
    implementation 'com.google.zxing:core:3.4.1'
    implementation 'com.mapbox.maps:android:10.4.2'
    implementation ('com.mapbox.maps:android:10.4.2'){
        exclude group: 'group_name', module: 'module_name'
    }
    implementation 'com.mapbox.mapboxsdk:mapbox-android-plugin-markerview-v9:0.4.0'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-plugin-annotation-v9:0.9.0'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-plugin-places-v9:0.12.0'
    implementation 'com.mikhaellopez:circularimageview:4.3.0'
    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    implementation 'com.github.WilliBoelke:simple-recycler-view-swipe-gestures:1.3'

   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

setting up this project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Android
 [Download Android](https://developer.android.com/studio)

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

. Clone the repo
   ```sh
   git clone https://github.com/KlaiGhassen/PandApp-Android/
   ```
   
###### ATTENTION: Node.js Server is required prior to use!

- open terminal
- clone the repo: `https://github.com/KlaiGhassen/PandApp-backEnd`
- Open it with Xcode
- build the aaplication and run it 
- run the server

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<a href="https://img.shields.io/badge/License-Esprit-brightgreen"><img alt="Esprit License use" src="https://img.shields.io/badge/License-Esprit-brightgreen"></a>

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Email Address:
- ghassen.klai@esprit.tn 
- yassine.zitoun@esprit.tn

[Ghassen's Github Profile](https://github.com/KlaiGhassen)
[Yassine's Github Profile](https://github.com/zwayten)

[Github Repo](https://github.com/KlaiGhassen/IosBack)

[esprit university](https://esprit.tn/)
<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/KlaiGhassen/PandApp-Android/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/KlaiGhassen/PandApp-Android/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/KlaiGhassen/PandApp-Android/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/KlaiGhassen/PandApp-Android/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[product-screenshot]: images/ReadMe1.png
<div id="top"></div>
