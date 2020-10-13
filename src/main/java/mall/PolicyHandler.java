package mall;

import mall.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverSehipped_UpdateStatus(@Payload Sehipped sehipped){

        if(sehipped.isMe()){
            Optional<Order> orderOptional = orderRepository.findById(sehipped.getOrderId());
            Order order = orderOptional.get();
            order.setStatus(sehipped.getStatus());

            orderRepository.save(order);

            //System.out.println("##### listener UpdateStatus : " + sehipped.toJson());
        }
    }


}
