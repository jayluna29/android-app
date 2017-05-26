
*/Justin Luna's Senior Thesis

About the app:
Travel has always been a persons' best pass time, even mine.
Vacation Tracker is an Android based app designed for travelers to reach their destination with ease and satisfaction due to its multimedia features. 
For the longest time, a camera has been a traveler's best friend.
This app allows users to create, store, and view their vacations, vacation related information, and all the memories that bring with it. Map

--

Trip Logger is the host tab and is displayed as the default tab which gets loaded once the New Vacation button is pressed. 
A unique identifier (ID) is assigned to each vacation.
 When a New Vacation is selected, an ID assigned to the Vacation is passed on to the Tab Activity.

--

Map View is opened through the click of another Tab. 
Activation API Key to access Google API Maps for Android. 
To use the Maps external library, <uses-library> element in the Android Manifest file is defined to “com.google.android.maps”. This thus enables the build tools to link the application against the Maps external library.
Map View properties are set. 

--

Recorder is another important tab for the creating the vacation. The vacation starts from the recorder. 
To capture the Geopoint of the current location
LocationListener interface is implemented. 
This interface uses the onLocationChanged() [17] method, which obtains the Latitude and Longitude of the current location. 
This is possible on extending the Geocoder class and implementing the method getFromLocation(). 
The camera capture feature of this activity works by extending the MediaStore class. 
