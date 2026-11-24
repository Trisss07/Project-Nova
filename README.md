Raspberry Pi Pico W Weather Monitor

Developed an IoT weather monitoring system using a Raspberry Pi Pico W, Python, and the OpenWeatherMap API. The device retrieves real-time weather data for five user-selected cities and uses an RGB LED to visually indicate temperature ranges. A push button allows users to cycle through the configured cities and view current weather conditions.

Project Overview

This project combines embedded systems, networking, and API integration to create a simple weather-monitoring device. The Raspberry Pi Pico W connects to a Wi-Fi network, retrieves live weather data from the OpenWeatherMap API, and displays temperature information through colour changes on an RGB LED.

The system supports five user-configurable cities and demonstrates how hardware and software can be combined to create a practical Internet of Things (IoT) application.

Features
Wi-Fi connectivity using Raspberry Pi Pico W
Real-time weather data retrieval from OpenWeatherMap API
Support for five user-selected cities
Push-button navigation between cities
RGB LED temperature visualisation
Temperature-based colour mapping
API error detection and handling
PWM-controlled LED output
Technologies Used
Python (MicroPython)
Raspberry Pi Pico W
OpenWeatherMap API
Network Programming
PWM Control
IoT Development
Hardware Components
Raspberry Pi Pico W
RGB LED
Push Button
Breadboard
Jumper Wires
Resistors
Temperature Visualisation

The RGB LED changes colour according to the current temperature:

Purple: 5°C and below
Teal: 6°C to 10°C
Green: 11°C to 15°C
Yellow-Green: 16°C to 20°C
Yellow: 21°C to 25°C
Orange: 26°C to 30°C
Red-Orange: 31°C to 35°C
Red: Above 35°C
How It Works
The device connects to a Wi-Fi network.
The user configures five cities.
Live weather data is requested from the OpenWeatherMap API.
Pressing the button cycles through each city.
The current temperature is retrieved and displayed through RGB LED colours.
If an API request fails, the LED flashes red to indicate an error.
Skills Demonstrated
Python Programming
Embedded Systems Development
Internet of Things (IoT)
API Integration
Hardware-Software Interaction
Network Communication
Error Handling and Debugging
PWM Signal Control
Future Improvements
Add OLED display support
Store city information permanently on the device
Display humidity and wind-speed data
Add weather condition indicators
Create a web dashboard for monitoring
Log weather history for analysis

Author

Trisha Nandan

Computer Science Student, University of Dundee
