import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;

/**
 * Created by yasassri on 6/6/16.
 */
public class MQTTTopicSubscriber {


    public static void main(String[] args) {

        String topic        = "yasas";

        // If authorization is set to true you need to include user name and the password
        boolean setAuthorization = true;
        String userName = "admin";
        String password = "admin";

        String brokerURL      = "tcp://localhost:1883"; // The broker URL in <HOST>:<PORT> format

        String clientId     = "JavaSample";
        boolean addRandomSuffixClientID = false; // This will add a Random suffix between 1 and 100000 to the clientID

        MemoryPersistence persistence = new MemoryPersistence();

        boolean setCleanSession = false; // This will set the clean Session to TRUE or FALSE

        try {
            if (addRandomSuffixClientID){
                Random random = new Random();
                clientId = clientId + random.nextInt(100000)+1;
            }
            MqttClient sampleClient = new MqttClient(brokerURL, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            sampleClient.setCallback(new SimpleMQTTCallback());

            if (setAuthorization){
                connOpts.setUserName(userName);
                connOpts.setPassword(password.toCharArray());
            }

            // Setting the clean session
            connOpts.setCleanSession(setCleanSession);
            System.out.println("Clean Session is set to " +setCleanSession);

            System.out.println("Connecting to brokerURL: "+brokerURL);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            sampleClient.subscribe(topic);
            System.out.println("Listening to the Topic!!");

            //Disconnecting the Publisher
            //sampleClient.disconnect();
           // System.out.println("Disconnected");
//            System.exit(0);
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
