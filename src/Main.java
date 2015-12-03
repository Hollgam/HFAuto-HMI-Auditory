import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
//	private final static String QUEUE_NAME = "hello";
	private final static String SERVER_ADDRESS = "10.152.239.103";
    private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(SERVER_ADDRESS);
		factory.setUsername("pavlo");
		factory.setPassword("1234");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

//		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
//				System.out.println(" [x] Received '" + message + "'");
				
				if (message.equals("left")) {
					Sound sound = new Sound();
					sound.play("sounds/TOR_left.wav");
					System.out.println(" [x] Left");
					
				} else if (message.equals("right")) {
					Sound sound = new Sound();
					sound.play("sounds/TOR_right.wav");
					System.out.println(" [x] Right");
					
				} else {
					System.out.println(" [x] Neither left nor right");
				}
				
			}
		};
        channel.basicConsume(queueName, true, consumer);
	}
}