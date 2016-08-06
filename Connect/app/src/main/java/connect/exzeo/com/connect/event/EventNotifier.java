package connect.exzeo.com.connect.event;

/**
 * Created by subodh on 6/8/16.
 */
public class EventNotifier {
    public static ILisner lisner;

    public EventNotifier(ILisner lisner){
        EventNotifier.lisner = lisner;
    }

    public EventNotifier(){}

    public void notifyEvent(String phoneNumber,String latitude,String longitude){

    }
}
