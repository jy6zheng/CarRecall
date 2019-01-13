package academy.learnprogramming.carrecall;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;

public class Jsonparser {

    public static void main(String[] args) throws JSONException {

        ArrayList carList = new ArrayList<>();
        RecallClient call = new RecallClient();
        String str = call.searchRecall("toyota");
        //System.out.println(str);
        try {

            JSONObject jso = new JSONObject(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
