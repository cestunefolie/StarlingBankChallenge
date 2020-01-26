# Starling Bank
Technical Challenge for Starling Bank
## Assumptions / TODO's :
###### For time being access token is sources from application.properties and possibly would require refresh
###### Application will calculate round up amount using amounts from all transaction for previous week. 
###### Theoretically we would want to include only Incoming transactions but nothing is mentioned in requirements 
###### Also Current Client Balance would need to be updated as per round up amount that is being transfer into Saving Goal 
###### Currently application will only make another transfer for that amount without updating current balance

## How to run the application:
###### Application will expose REST API that can be tested using Swagger UIe
###### With IDE, run class called **StarlingBankFeature.java** and run/debug.
###### Go to url: http://localhost:8081/swagger-ui.html#/ on your web browser
###### Expand 'round-up-controller' and click 'try it out' and then 'execute'
###### Response message will we display in Swagger UI

## API:
* **GET**: http://localhost:8081/starling/roundup

