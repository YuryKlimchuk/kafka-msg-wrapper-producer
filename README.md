It is spring-boot-starter that provide new layer of abstraction around standart spring-kafka liblary for sending msgs to kafka.<br>

To add to spring-boot project:<br>
1. Add to pom<br>
		<dependency>
			<groupId>com.hydroyura.springboot.kafka.msg.wrapper</groupId>
			<artifactId>producer-starter</artifactId>
			<version>0-SNAPSHOT</version>
		</dependency>
2. Specify kafka server in application.yaml:<br>
    kafka-msg-wrapper:
      producer:
        url: localhost:9092
   
3. Inject MsgWrapperProducer where you need send msgs.<br>
    @Autowired
    private MsgWrapperProducer producer;

4. Use sendEvent(String topic, Object msg, String eventType) for sending msg<br>
   topic - topic name in kafka
   msg - any object what you want to send
   eventType - any label for grouping msgs

 
This wraper creates put your msg in special container:<br>
  
  public class MsgContainer {
    private Object body;
    private String type, className;
  }
<br>
  It allows standartize msg handaling on consumer side.
