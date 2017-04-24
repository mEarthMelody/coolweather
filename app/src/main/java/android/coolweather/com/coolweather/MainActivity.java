package android.coolweather.com.coolweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView)findViewById(R.id.tv);
        float mm = 2 >> 2;
        textview.setText(mm + "");
        Log.d("MainActivity","id is " );

        //sendRequestWithOKHttp();
    }

    private void sendRequestWithOKHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.0.2.2/get_data.xml")
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    parseXMLwithPull(data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseXMLwithPull(String data) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(data));
        int eventType = parser.getEventType();
        String id = "";
        String name = "";
        String version = "";
        while(eventType != XmlPullParser.END_DOCUMENT){
            String nodeName = parser.getName();
            switch(eventType){
                case XmlPullParser.START_TAG:
                    if("id".equals(nodeName)){
                        id = parser.nextText();
                    }else if("name".equals(nodeName)){
                        name = parser.nextText();
                }else if("version".equals(nodeName)){
                        version = parser.nextText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("app".equals(nodeName)){
                        Log.d("MainActivity","id is " + id);
                        Log.d("MainActivity","id is " + name);
                        Log.d("MainActivity","id is " + version);
                    }
                    break;
                    default:
                        break;
            }
            eventType = parser.next();

        }
    }
}
