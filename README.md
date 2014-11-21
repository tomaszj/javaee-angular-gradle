# Java EE Businesses app

A sample, fairly bare-bones setup for Java EE app which serves necessary files for a frontend application. Built using maven.

# Building locally

run `mvn package` and then deploy the .war to your container (e.g. Glassfish server).

# Interesting bits

All static assets are served from src/main/webapp folder. Interesting bit is the AssetsServingFilter class which essentially handles the redirection of all user's requests to serve index.html page, excluding API calls, JS, CSS files and images.
 The purpose is to support HTML5 mode paths in Angular application. This way, `http://localhost:8080/businesses`
 link is resolved properly to Angular application, while preserving the path for correct resolution.

# License

Standard MIT.
