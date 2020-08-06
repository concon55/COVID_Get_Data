package covidData;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import static spark.Spark.*;

public class SmsApp {
	
	public static void getData(){
		get("/", (req, res) -> "TEST");

        post("/sms", (req, res) -> {
            res.type("application/xml");
            String messageToSend = "";
            String getMsg = req.queryParams("Body").replaceAll(" ", "");
            if(getMsg.equalsIgnoreCase("us")){
            	messageToSend = USData.getData();
            }else{
            	messageToSend = StateData.getStateData(getMsg.toUpperCase());
            }
            Body body = new Body
                    .Builder(messageToSend)
                    .build();
            Message sms = new Message
                    .Builder()
                    .body(body)
                    .build();
            MessagingResponse twiml = new MessagingResponse
                    .Builder()
                    .message(sms)
                    .build();
            return twiml.toXml();
        });
	}
	

    public static void main(String[] args) {
    	getData();
        
    }
}
