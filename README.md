# Starling Bank
Technical Challenge for Starling Bank
## Assumptions / TODO's :
###### For time being access token is sources from application.properties and possibly would require refresh
###### Application will calculate round up amount using amounts from all transaction for previous week. 
###### Theoretically we would want to include only Incoming transactions but nothing is mentioned in requirements 
###### Also Current Client Balance would need to be updated as per round up amount that is being transfer into Saving Goal 
###### Currently application will only make another transfer for that amount without updating current balance
###### More unit test to come 

##  Validate/Refresh the Access Token:
###### Go to the Starling Bank Developers Account and refresh/generate a new access token.
###### Get new access token from your Developer Starling Account 
###### Replace the access token on file "application.properties" in the project called token

## How to run the application:
###### Application will expose REST API that can be tested using Swagger UI
###### With IDE, run class called **StarlingBankFeature.java**
###### Go to url: http://localhost:8081/swagger-ui.html#/ on your web browser
###### Expand 'round-up-controller' and click 'try it out' and then 'execute'
###### Response message will be displayed in Swagger UI

## How to test:
###### Application has end to end Integration test that can be run by executing file RoundUpControllerIntegrationTest
###### To run test access token has to be updated in /test/resources/application.properties file

## API:
* **GET**: http://localhost:8081/starling/roundup

