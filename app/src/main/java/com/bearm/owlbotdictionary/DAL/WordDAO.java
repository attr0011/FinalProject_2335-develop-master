package com.bearm.owlbotdictionary.DAL;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bearm.owlbotdictionary.R;
import com.bearm.owlbotdictionary.Interfaces.IVolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class references information from the course's labs, slides, and the android wiki
* The WordDao class represents JSon object responses, requests for the query and context for new request Queues
*/
public class WordDAO {

    private Context context;
    private RequestQueue queue;
    private JSONObject myResponse;

    public WordDAO(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    /**
    * The requestData function gets the response and callback of IVolley
    *  
    */
    public void requestData(String url, final IVolleyCallback callback) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RESPONSE_OK", String.valueOf(response));

                myResponse = response;
                callback.getResponse(myResponse);


            }
            /**
             * Error response is entered by user
             */
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RESPONSE_NOT_OK", "Error: " + error);
                Toast.makeText(context, "Please write down valid word. ", Toast.LENGTH_LONG).show();

            }
        }) {
            
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
//                hjvvs
                headers.put("Authorization", context.getString(R.string.credentials));
                return headers;
            }
        };

        queue.add(jsObjRequest);

    }


}
