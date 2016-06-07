/**
 * Created by yasassri on 10/26/15.
 */
        import org.eclipse.paho.client.mqttv3.MqttClient;
        import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
        import org.eclipse.paho.client.mqttv3.MqttException;
        import org.eclipse.paho.client.mqttv3.MqttMessage;
        import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

        import java.util.Random;

public class MQTTTopicPublisher {

    public static void main(String[] args) {

        String topic        = "yasas";
        String content      = "Message from MqttPublish client";

        int qos             = 0;

        String brokerURL      = "tcp://localhost:1883";

        String clientId     = "JavaSample";
        boolean addRandomSuffixClientID = false; // This will add a Random suffix between 1 and 100000 to the clientID

        boolean setCleanSession = true; // This will set the clean Session to TRUE or FALSE

        int noOfMessagesToPublish = 1;

        MemoryPersistence persistence = new MemoryPersistence();
        try {
            if (addRandomSuffixClientID){
                Random random = new Random();
                clientId = clientId + random.nextInt(100000)+1;
            }
            MqttClient sampleClient = new MqttClient(brokerURL, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();

            // Setting the clean session
            connOpts.setCleanSession(setCleanSession);
            System.out.println("Clean Session is set to " +setCleanSession);

            System.out.println("Connecting to brokerURL: "+brokerURL);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            // Publishing Messages
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            for (int i = 0; i < noOfMessagesToPublish; i++) {
                sampleClient.publish(topic, message);
                System.out.println("Message " +(i+1) +"published");
            }

            //Disconnecting the Publisher
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}