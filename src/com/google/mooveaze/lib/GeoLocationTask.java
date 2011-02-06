package com.google.mooveaze.lib;

import android.os.AsyncTask;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;

public class GeoLocationTask extends AsyncTask<String, Void, Location> {
    private static final String URL = "http://local.yahooapis.com/MapsService/V1/geocode?appid=WPa.RN7V34HTuIeClhDW1m6.ippi8LbvUl2wh4rvvNoc404G1smYIgBmnaZuC.Mc3xF2&location=";

    protected Location doInBackground(String... strings) {

        try {
            RestClient.Response response = new RestClient().get(URL + URLEncoder.encode(strings[0], "UTF-8"));

            if(response.statusCode == 200) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = builder.parse(new ByteArrayInputStream(response.entity.getBytes()));
                document.getDocumentElement().normalize();
                return new Location(Double.parseDouble(getTextFromNode(document, "Latitude")), Double.parseDouble(getTextFromNode(document, "Longitude")));
            }
        }
        catch(Exception e) {
            Log.error(e);
        }

        return null;
    }

    private String getTextFromNode(Document dom, String name) {
        String text = null;
        NodeList nodes = dom.getElementsByTagName(name);

        if(nodes.getLength() > 0) {
            NodeList childNodes = nodes.item(0).getChildNodes();
            text = childNodes.item(0).getNodeValue();
        }

        return text;
    }
}
