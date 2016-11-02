package com.lobinary.框架.RabbitMQ.单发多收之任务派发;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.lobinary.java.多线程.TU;
import com.lobinary.框架.RabbitMQ.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
  
/**
 * 
 * <pre>
	接收端和场景1不同点：
		1、使用“task_queue”声明消息队列，并使消息队列durable
		2、在使用channel.basicConsume接收消息时使autoAck为false，即不自动会发ack，由channel.basicAck()在消息处理完成后发送消息。
		3、使用了channel.basicQos(1)保证在接收端一个消息没有处理完时不会接收另一个消息，即接收端发送了ack后才会接收下一个消息。在这种情况下发送端会尝试把消息发送给下一个not busy的接收端。
		注意点：
		1）It's a common mistake to miss the basicAck. It's an easy error, but the consequences are serious. Messages will be redelivered when your client quits (which may look like random redelivery), but RabbitMQ will eat more and more memory as it won't be able to release any unacked messages.
		2）Note on message persistence
		Marking messages as persistent doesn't fully guarantee that a message won't be lost. Although it tells RabbitMQ to save the message to disk, there is still a short time window when RabbitMQ has accepted a message and hasn't saved it yet. Also, RabbitMQ doesn't do fsync(2) for every message -- it may be just saved to cache and not really written to the disk. The persistence guarantees aren't strong, but it's more than enough for our simple task queue. If you need a stronger guarantee you can wrap the publishing code in atransaction.
		3）Note about queue size
		If all the workers are busy, your queue can fill up. You will want to keep an eye on that, and maybe add more workers, or have some other strategy.
 * </pre>
 *
 * @ClassName: Worker
 * @author 919515134@qq.com
 * @date 2016年10月26日 下午2:36:48
 * @version V1.0.0
 */
public class Worker {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = RabbitMQConfig.getFactory();
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    
    channel.basicQos(1);
    
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      
      System.out.println(" [x] Received '" + message + "'");
      doWork(message);
      System.out.println(" [x] Done");
      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }
}
  
  private static void doWork(String task) throws InterruptedException {
	  TU.l("正在执行任务"+task);
	  TU.s(1000);
  }
}