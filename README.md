# Spring-Zipkin-Sleuth-Oauth-Sample
Spring boot sample application demonstrating zipkin,sleuth and oauth with spring cloud gateway

It has 4 application : 
1. cloud-gateway - Will be responsible to route requests
2. oauth-server - Will be responsible to authenticate the user's request
3. resource-server - protected resource
4. zipkin.jar - downloaded latest from zipkin github repository

cloud-gateway runs on port 8081

oauth-sever runs on port 9091

resource-server runs on port 8080

**Steps to run :** 
```
cd cloud-gateway 
mvn spring-boot:run 

cd oauth-server
mvn spring-boot:run

cd resource-server
mvn spring-boot:run
```

**to run zipkin**
cd into the zipkin download directory and start using the following command 

```java -jar zipkin.jar```

all the above 3 applications are pointing to zipkin server **listening on port 9411 (configured via : application.properties/yml)**

Get access-token from oauth-server
```
curl --location --request POST 'http://localhost:9091/oauth/token' --header 'Authorization: Basic Y2xpZW50YXBwOjEyMzQ1Ng==' --header 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'grant_type=client_credentials'

{"access_token":"f7caae5c-7278-47a8-9ea1-d53e99133c74","token_type":"bearer","expires_in":119999,"scope":"read_profile_info"}
```
Hit the resource api using to ```access_token``` received from above request 
```
curl http://localhost:8081/rs1/api/protect-resource?access_token=f7caae5c-7278-47a8-9ea1-d53e99133c74
```

Now with the above setup, when we view the zipkin trace logs in UI , it shows two requestt logs 

1. cloud-gateway -> resource-server
2. oauth-server 

but in reality it is 1 request flow

``` cloud-gateway -> resource-server -> oauth-server -> resource-server -> cloud-gateway```
  
