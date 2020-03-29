# Description
This is a simple API test framework aiming to test   
<a href="https://github.com/eMerchantPay/codemonsters_api_full">https://github.com/eMerchantPay/codemonsters_api_full</a>

This is `Java 1.8` project built with `Maven`.

# Config:
Configuration is here "src/test/resources/Config.properties"
Following properties can be defined inside the config:

- **baseUrl** - the location where Codemonster project was set to listen for requests. e.g. http://localhost:3001
- **basicUser** - the "user" which will be used for generating the Basic Authorization
- **basicPassword** - the "password" which will be used for generating the Basic Authorization

# Run tests:
- ###  All test:
    execute this: `mvn clean test`
 
- ### Selected tests:
    If you want to run only the test methods of a given class <b>OR</b> a single test method,
     specify the `class#method` name like:
    <br>
    - Run whole class, execute this: `mvn -Dtest=<className> clean test`  
      where **\<className\>** provided like _VoidTransactionTests_**.java** OR _VoidTransactionTests_  
    
    - Run single method, execute this: `mvn -Dtest=<className>#<methodName> clean test`  
     where **\<className#methodName\>** provided like _VoidTransactionTests#voidExistingTransaction_
     
     (i) Find more details here http://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html

# List of test classes/features:
located in _src/test/java/emp/codemonster/tests/_
1. SaleTransactionTests.java
1. SaleTransactionNegativeTests
1. VoidTransactionTests