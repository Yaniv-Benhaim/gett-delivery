# Gett.Delivery Mobile Assignment
Notes:

Thanks for the opportunity to make this project, enjoyed myself a lot.
The reason the project has a lot more ui components & screens within the ui/components package is -
because i made the project a lot bigger from the beginning but in the end made it just to fit the requirements
or else it would have been harder to complete in time.

# The api key is located in the constants file and the strings file. Normally this would of course be in a more secure location

Time to build: -> Around 10 hours, tried to implement a lot more screens including bottom navigation but found myself short on time.
Technologies used:
1. Jetpack compose
2. Kotlin Coroutines
3. Kotlin Flows
4. Google Directions 
5. Google Roads for snapping the user back to the road if the points are inaccurate
6. Room database
7. Dagger hilt for dependency injection
8. Livedata
9. Design pattern: MVVM

Basic App Flow:
1. App loads up with splash screen and requests permission to track the user's location
2. When the user accepts the location permissions the location tracking service is started
3. The first screen loads up with Google Maps and receives the user's current location as a flow of Latitude/Longitude objects
4. The user's marker is updated with an animation every time a new location is received.
5. While the user is located on the map the repository already loaded the journey.json file from my website(https://gamedev-il.tech.journey.json)
6. The directions are shown on the map as a continues poly line from the user's location to the destination with the help of the Google Directions Api.
7. The user's location should already be a snapped points retrieved from the Google Roads Api
8. The first pickup target is shown above.
9. user presses arrived when he arrives to target.
10. user is shown a screen with parcels.
11. And all the rest is exactly according to the wireframe.





