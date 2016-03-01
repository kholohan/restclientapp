Hello!  Thanks very much for taking the time to code with us!

We'd like you to demonstrate that you can write a simple Java application, which is:

   1. capable of making HTTP GETs to a RESTful API server which returns JSON content, and then
   2. processes the JSON results in simple ways we'll specify.

We'd like your app to lookup the current weather conditions at a location, specified by zipcode.
In particular, for that location, we'd like your app to show:

   1. the "place name" (e.g., "Waltham")
   2. a general description of the weather conditions ("cloudy", "clear, sunny", etc)
   3. the temperature in Fahrenheit

The three public REST APIs you will find useful are:

   1. http://freegeoip.net/json/  (IP-based location lookup)
   2. http://api.zippopotam.us/us/  (zipcode-based location lookup; docs @ http://www.zippopotam.us/)
   3. http://api.openweathermap.org/data/2.5/weather?zip=  (zipcode-based weather lookup; docs @ http://openweathermap.org/api)

Play around with them, to see what's in their JSON responses for different inputs.  Collectively,
they should contain all the data you'll need.

When using API #1, don't be surprised if the result is only "nearish" to your actual location.
This is okay; just use what it gives you.

Also, be aware that API #1 may be rate-limited to a single request every few seconds.

When using API #2, see the API docs @ http://www.zippopotam.us/ .

When using API #3, see the API docs @ http://openweathermap.org/current .

Also, you will need to provide an API key for requests to API #3.  For this, append the following
query parameter to your URLs:

   &appid=284fb95647882aa3e8c4a8f556b00c51

Your app should take an optional zipcode argument.  (If no zipcode is specified, your app should
use the zipcode of the current location.)

How you run your application is up to you; use your choice of IDE, directly in a shell, etc.

How you display the requested data is up to you.  Don't worry about suppressing debug logging
output from any libraries that you use (though it's nice if you do, especially if they're verbose).

We are looking forward to seeing your solution! When you come visit us, you should describe your
approach and your choice of tool(s).

Also, please be prepared to discuss the pros and cons of using your solution and how it could be
improved given more time to allocate to it.
