# CityWeather
This document is to provide details on Android weather app improvements. The application is created from scratch with the reference to the application shared by the professor. The open weather API reference is used as per the reference.
## MainActivity:
Initial screen loads with the default city as “SanJose”. User has the option to edit the city name and click show button to get the required city weather for a week time. Following the reference screenshot for the mainActivity. User has option the click one of the day to view the details for that day which is explained in the next section


## Day Activity :
Day Activity screen provides the detailed view (3hour based) for the given day. Current screen is a list view of three hour-based weather. It provides details such as hour, temperature and sub-weather details. On the top the current city and date selected from the previous activity has been displayed.  This screen again provides the detailed view for that particular hour selected from the given list. Details are opened up next activity as explained in below.


## Detail Activity :
Day Activity screen provides the detailed view for the given city/day and hour combination. This screen provides the complete details such as temperature at that hour and detailed view such as sunrise, sunset.
