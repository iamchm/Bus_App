package com.example.r1713941_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
    Button search;
    TextView errMessage;
    EditText edtitle;
    Geocoder coder;
    RadioButton r1, r2;
    Document doc = null;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coder = new Geocoder(this);
        edtitle = (EditText) findViewById(R.id.title);
        search = (Button) findViewById(R.id.button);
        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
    }

    public void onClick(View view) throws MalformedURLException, UnsupportedEncodingException {
        String result = "";
        double posx[] = new double[100];
        double posy[] = new double[100];
        String splitResult, x, y;
        String u = "";

        if (r1.isChecked()) {
            u = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?serviceKey=MMlh0CuN67bWrzuIEsjbDhPLyfNu6TQht1mQ7Yk5Vq7h4eVlPXVm8fPJkCYUdQOoK0EdnOXQDKUi31rPtkuBQA%3D%3D&busRouteId=100100596";
        } else if(r2.isChecked()){
            u = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?serviceKey=MMlh0CuN67bWrzuIEsjbDhPLyfNu6TQht1mQ7Yk5Vq7h4eVlPXVm8fPJkCYUdQOoK0EdnOXQDKUi31rPtkuBQA%3D%3D&busRouteId=100100522";
        }


        GetXMLTask task = new GetXMLTask(this);
        task.execute(u);
    }

    @SuppressLint("NewApi")
    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        private Activity context;

        public GetXMLTask(Activity context) {
            this.context = context;
        }

        @Override
        protected Document doInBackground(String... urls) {

            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder db;

                db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error",
                        Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            String title;
            if (r1.isChecked()) {
               title="400";
            } else {
               title="2016";
            }
            String posx="";
            String posy="";
            int i;
            NodeList nodeList = doc.getElementsByTagName("itemList");
            for (i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("gpsX");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                posx += ((Node) nameList.item(0)).getNodeValue() + " ,";

                NodeList websiteList = fstElmnt.getElementsByTagName("gpsY");
                Element websiteElement = (Element) websiteList.item(0);
                websiteList = websiteElement.getChildNodes();
                posy += ((Node) websiteList.item(0)).getNodeValue()+" ,";
            }

            String[] posxx,posyy;
            posxx=posx.split(",");
            posyy=posy.split(",");

            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("item",i);
            intent.putExtra("title", title);
            intent.putExtra("posxx", posxx);
            intent.putExtra("posyy", posyy);
            startActivity(intent);

        }
    }
}