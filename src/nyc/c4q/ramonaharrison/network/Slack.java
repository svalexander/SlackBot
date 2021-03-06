package nyc.c4q.ramonaharrison.network;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import nyc.c4q.ramonaharrison.model.Attachment;
import nyc.c4q.ramonaharrison.network.response.*;
import nyc.c4q.ramonaharrison.util.Token;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
//import org.apache.commons.codec.binary.Hex;

/**
 * Created by Ramona Harrison
 * on 8/26/16
 *
 * A class for interacting with Slack's Web API.
 *
 */

public class Slack {

    private static final String API_KEY = Token.findApiToken();
    private static final String USER_NAME= "Smartbot";
    private static final String USER_ICON="http://i50.photobucket.com/albums/f329/liudmilaura/Yin_zpshsyvcsod.jpg";
    private static final String BASE_URL = "https://slack.com/api/";
    private static final String ENDPOINT_TEST = "api.test";
    private static final String ENDPOINT_LIST_CHANNELS = "channels.list";
    private static final String ENDPOINT_LIST_MESSAGES = "channels.history";
    private static final String ENDPOINT_POST_MESSAGE = "chat.postMessage";
    private static final String ENDPOINT_DELETE_MESSAGE = "chat.delete";

    public static final String BOTS_CHANNEL_ID = "C2ABKERFT";
    public static final String BOTSTEST_CHANNEL_ID = "C2ATALHQQ";


    /**
     * Static method to test the Slack API.
     *
     * @return the Response indicating ok/error or null if the connection failed.
     */
    public static Response testApi() {
        URL testUrl = HTTPS.stringToURL(BASE_URL + ENDPOINT_TEST  + "?token=" + API_KEY);

        JSONObject object = HTTPS.get(testUrl);

        return new Response(object);
    }

    /**
     * Static method to list all public channels on the Slack team.
     *
     * @return the ListChannelsResponse indicating ok/error or null if the connection failed.
     */
    public static ListChannelsResponse listChannels() {

        URL listChannelsUrl = HTTPS.stringToURL(BASE_URL + ENDPOINT_LIST_CHANNELS + "?token=" + API_KEY);

        return new ListChannelsResponse(HTTPS.get(listChannelsUrl));
    }

    /**
     * Static method to list the last 100 message on a given channel.
     *
     * @param  channelId the id of the channel from which to list messages.
     * @return the ListMessagesResponse indicating ok/error or null if the connection failed.
     */
    public static ListMessagesResponse listMessages(String channelId) {

        URL listMessagesUrl = HTTPS.stringToURL(BASE_URL + ENDPOINT_LIST_MESSAGES + "?token=" + API_KEY + "&channel=" + channelId);
        return new ListMessagesResponse(HTTPS.get(listMessagesUrl));
    }

    public static ListUsers listUsers(String channelId) {

        URL listUsersUrl = HTTPS.stringToURL(BASE_URL + ENDPOINT_LIST_MESSAGES + "?token=" + API_KEY + "&channel=" + channelId );

        return new ListUsers(HTTPS.get(listUsersUrl));
    }

    /**
     * Static method to send a message to the #bots channel.
     *
     * @param  messageText the message text.
     * @return the SendMessageResponse indicating ok/error or null if the connection failed.
     */
    public static SendMessageResponse sendMessage(String messageText) {

        try {
            messageText = URLEncoder.encode(messageText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        URL sendMessageUrl = HTTPS.stringToURL(BASE_URL + ENDPOINT_POST_MESSAGE + "?token=" + API_KEY +"&icon_url="+ USER_ICON+"&username="+USER_NAME +"&channel=" + BOTS_CHANNEL_ID + "&text=" + messageText);

        return new SendMessageResponse(HTTPS.get(sendMessageUrl));
    }

    /**
     * Static method to send a message with one or more attachments to the #bots channel.
     *
     * @param  //messageText the message text.
     * @param  //attachments a list of one of more attachments to be parsed to a JSON-encoded URL string parameter.
     * @return the SendMessageResponse indicating ok/error or null if the connection failed.
     */
    public static SendMessageResponse sendMessageWithAttachments(JSONArray obj) throws UnsupportedEncodingException {

        String attachment =obj.toJSONString();

        //try {
            String messageText = URLEncoder.encode(attachment);

//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
        //System.out.println(messageText);
        URL sendMessageUrl = HTTPS.stringToURL(BASE_URL + ENDPOINT_POST_MESSAGE + "?token=" + API_KEY + "&icon_url="+ USER_ICON+"&username="+USER_NAME+"&channel=" + BOTS_CHANNEL_ID + "&text="+ "Text"+"&attachments=" + messageText );

        return new SendMessageResponse(HTTPS.get(sendMessageUrl));

        // TODO (optional): implement this method! See https://api.slack.com/docs/message-attachments
        //throw new RuntimeException("Method not implemented!");
    }

    /**
     * Static method to delete an existing message from the #bots channel.
     *
     * @param  messageTs the message timestamp.
     * @return the DeleteMessageResponse indicating ok/error or null if the connection failed.
     */
    public static DeleteMessageResponse deleteMessage(String messageTs) {
        URL deleteMessageUrl = HTTPS.stringToURL(BASE_URL + ENDPOINT_DELETE_MESSAGE + "?token=" + API_KEY + "&channel=" + BOTS_CHANNEL_ID + "&ts=" + messageTs);

        return new DeleteMessageResponse(HTTPS.get(deleteMessageUrl));
    }
}
