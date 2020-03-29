# Description
This is a simple API test framework aiming to test   
<a href="https://github.com/eMerchantPay/codemonsters_api_full">https://github.com/eMerchantPay/codemonsters_api_full</a>

This is `Java 1.8` project built with `Maven`.

#Config:
Configuration is here "src/test/resources/Config.properties"
Following properties can be defined inside the config:

- **baseUrl** - the location where Codemonster project was set to listen for requests. e.g. http://localhost:3001
- **basicUser** - the "user" which will be used for generating the Basic Authorization
- **basicPassword** - the "password" which will be used for generating the Basic Authorization

#Run tests:
- ###  All test:
    execute this: `mvn clean test`
 
- ### Selected tests:
    TBA