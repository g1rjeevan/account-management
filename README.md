# account-management
This is a case study; A micro-service develop in spring boot


To run the application locally:
1. To run Account-Management micro-service rest app using "mvn spring-boot:run".
2. To run the corresponding the frontend use 
    a). npm install 
    b). ng serve

To run using the docker container, Use below repositories:

    1. docker pull jeevanrpro/accountmanagement:1.0.0
    2. docker pull jeevanrpro/account-management-client:1.0.0
    3. sudo docker run -d -p 8080:8080 jeevanrpro/accountmanagement:1.0.0
    4. sudo docker run --rm -d -p 80:80 jeevanrpro/account-management-client:1.0.0
