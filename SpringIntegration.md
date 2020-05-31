# Spring Integration

##Introduction

###Endpoints
* Adapters - connect channel to some other system
* Filters - remove some messages from channel based on header, content
* Transformer - convert a message or structure
* Enricher - add content to the message header or payload
* Service Activator - invoke service operations based on the arrival of a message
* Gateway - connect your channels without Spring Integration coupling
* Routers

###Channels
* Pollable Channel
    * May buffer its messages
    * Waits for consumer to get message
    * Typically, a point-to-point channel - one receiver of a message
    * Usually, used for sending information or document messages between endpoints
* Subscribable
    * Allow multiple consumers
    * Doesnt buffer its messages
    * Usually event messages
    
    
```java
private DirectChannel channel;
```

###Messages
* Header
    * Custom - as well
* Payload

##Adapters
###Available Adapters
* Data Stores (JDBC, JPA)
* File Stores (File System, FTP, SFTP)
* HTTP
* Mail (SMTP)
* Messaging
* Web Services (SOAP, REST, JSON)


## Some links
https://en.wikipedia.org/wiki/Enterprise_Integration_Patterns
https://dzone.com/articles/spring-boot-example-of-spring-integration-and-acti
https://www.baeldung.com/spring-integration
https://www.youtube.com/watch?v=icIosLjHu3I&list=PLr2Nvl0YJxI5-QasO8XY5m8Fy34kG-YF2&index=2&t=0s
https://codenotfound.com/spring-kafka-spring-integration-example.html

HEnterprise Integration Patterns
integration-context.xml