Xrob
====

Xrob is an Android Device Manager Application.
with the help of Xrob we could able to control our Android device through SMS Commands.

What can it do ??
=================

- Turn ON/OFF Mobile Data
- Turn ON/OFF WiFi
- Turn ON/OFF WiFi Tethering
- Change WiFi Tethering Password
- Play music from online
- Change Ringer Mode
- Vibrate Device
- Play sounds
- Change Wallpaper (Google Image API integrated)
- Resize Wallpaper according to device screen dimensions
- Sound Recording and Uploading it to server
- Make Phone Calls
- Collect device location (GPS)
- Clean Phone Memmory
- Clean MemmoryCard
- Message Grabbing

Features
========

# These SMS commands cannot be found in inbox or anywhere
# user won't get any kind of notification.
# No User Interface
# No Launcher
# Built-in application like package name
# Simple Commands

How to use ?
===========

1 ) Install this application in your Android device.
2 ) Take another phone , type the command and send it to your mobile number.
3 ) Done.

What are the Commands ??
========================

Note : Every command message should contain the word 'xrob' (CASE SENSITIVE,WITHOUT QUOTES)

Commands:
`````````
power : Enables Mobile Data and WiFi

darknight :  Enable Mobile Data and Change Ringer Mode to SILENT

sunrise : Disable Mobile Data and Change Ringer Mode to NORMAL

angels5 : Enable Mobile Data

automove : Vibrate device. Normal vibration duration 5 Seconds. can also add custom time duration with comma.
         example : xrob automove,10,
         then the device will vibrate for 10 Seconds
        
bcast : Play sound from internet.
       syntax : xrob bcast,url of the music,
       example : xrob bcast,http://example.com/mysong.mp3,
       
shhh : Change Ringer mode to Silent.

dingdong : Change Ringer mode to Normal.

grrr : Change Ringer mode to Vibration Only.

hotdog : Change Device Wallpaper

        syntax : xrob hotdog,Any Name,
        example : xrob hotdog,Tony Stark,
        
        

pling : Disable Mobile Data
        Disable WiFi
        Disable WiFi Tethering

autocat : Enable Mobile Data
          Enable WiFi Tethering
          Change WiFi Tethering Password
          
          syntax : xrob autocat 
          result : enable internet and wifi tethering with default password 'password123'
          
          syntax : xrob autocat,CUSTOM PASSWORD GOES HERE,
          example : xrob autocat,4887asda234$$,
          result : enable internet and wifi tethering with  password '4887asda234$$'
          
roger : Record Sound and Upload it to Server

        syntax: xrob roger,Record Duration,
        example : xrob roger,60,
        
         (MORE COMMANDS COMING SOON)....
         ```









