How to run
bash# 1. Start Spring Boot
cd factory-demo
mvn spring-boot:run

# 2. Open frontend.html in your browser (no server needed)
open frontend.html

Project structure
factory-demo/
├── pom.xml
├── frontend.html                        ← single-file vanilla UI
└── src/main/java/com/factory/demo/
├── model/
│   ├── Notification.java            ← «interface» Product
│   ├── EmailNotification.java       ← Concrete Product
│   ├── SmsNotification.java         ← Concrete Product
│   └── PushNotification.java        ← Concrete Product
├── factory/
│   ├── NotificationFactory.java     ← «abstract» Creator
│   ├── ConcreteFactories.java       ← 3 Concrete Creators
│   └── NotificationFactoryRegistry  ← maps type → factory
└── controller/
└── NotificationController.java  ← REST API

Pattern roles explained
RoleClassResponsibilityProductNotificationInterface all notification types implementConcrete ProductsEmailNotification, SmsNotification, PushNotificationActual logic per channelAbstract CreatorNotificationFactoryDeclares createNotification() + notify() templateConcrete CreatorsEmailNotificationFactory etc.Override createNotification() to return the right productRegistryNotificationFactoryRegistryResolves "EMAIL" → correct factory at runtime
The key rule: the NotificationController never calls new EmailNotification() anywhere — it only talks to the abstract NotificationFactory. 


![img.png](img.png)