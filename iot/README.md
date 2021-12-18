<div id="top"></div>



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">
2021 MCIoT Delta Study Booths Tracker</h3>

  <p align="center">
 Final work for 2021 MCIoT course
  </p>
</div>





<!-- ABOUT THE IOT PART -->
## About The Project
Delta Study Booth Tracker is an app for anyone who uses delta study booths. You can see whether a booth is occupied from our simple to use app. The booths are tracked via a motion sensor and also have a LED light to indicate visually, without the app, if the booth is full or not. 

### Process

Firstly, I configured the ultra-sonic sensor, oled and single-rgb and deploy it through IoTempower in which I didn't face with any issues.
Second part was to combine all data from sensors and made them work together accordingly and send appropriate data to the firebase for Mobile implementation.
I installed "node-red-contrib-firebase" library to the node-red in order to be able to send the data to the firestore. Although I managed to install and configure it properly, I got a linux related error which took quite much time to resolve. It was "aborted (core dumped)". Therefore, I reinstalled ubuntu on my computer and installed all the important thing for IoT again. Unfortunately, I got same error which were causing node-red shut down. After reinstalling ubuntu several times and changing nodejs version, it somehow worked. Then, I talked to my teammate to whether he can make use of realtime database for getting the data. After doing a bit research we came across that it is more complex, and I started using another library for firestore "node-red-contrib-cloud-firestore" as our app were using firestore for getting the data.
As ultra-sonic sensor measured the distance in mm, I used switch to detect the person who went in.
It is obvious that if someone goes in, the distance will change. Hence, via switch I wrote if statement that will detect the change and by the help of change nodes I send json data to the next node in the following format:
{
"occupied": true,
"timestamp": "_serverTimestamp"
}
This data goes through filter node which blocks data unless value changes. Sequentially, data comes to the function which has some javascript snippet for not changing room status unless there is a second distance change in the sensor meaning that the person went out.
Single-rgb shows red when the room is occupied and green when the room is empty. Additionally, oled is used to show the distance which comes from ultra-sonic sensor to ease our work. I also attached the pictures of node-red flow.

### IoTempower setup codes
- Oled: 
U8G2_SSD1306_64X48_ER_F_HW_I2C u8g2(U8G2_R0);
display(oled, u8g2, u8g2_font_profont29_mf);
- Ultra-sonic sensor:
  hcsr04(distance, D5, D7).with_precision(10);
- RGB-single:
  rgb_single(rgb1, D3, D4, D2);
  
### IoTempower setup codes
- [Ultra-sonic sensor](https://github.com/KarlErikMander/2021-MCIoT-Delta-Study-Booths-Tracker/tree/main/iot/ultra-sonic)


## Authors
Ziya Mammadov




