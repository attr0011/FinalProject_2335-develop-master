package com.bearm.owlbotdictionary.Interfaces;

import org.json.JSONObject;
/**
* Interface for IVolley call back and the response
* This class references information from the course's labs, slides, and the android wiki
*/
public interface IVolleyCallback {

    void getResponse(JSONObject response);
}
