package com.example.sanidhya.m_xpress;

public class Constants {

    public static final String PREFERENCES = "Preferences";

    public static final String USER_EMAIL_PREF = "UserEmailPreference";
    public static final String USERNAME_PREF = "UserNamePreference";
    public static final String PIC_URI_PREF = "PicURIPreference";
    public static final String UID_PREF = "UID_Preference";
    public static final int RC_SIGN_IN = 123;

    public static final String URL = "http://127.0.0.1:5000";
    public static final String LOGIN_URL = URL+"/api/login"; // user_id
    public static final String UPLOAD_URL =  URL+"/api/card/upload";
    public static final String FEED_URL = URL+"/api/cards"; //lat lng filter=nearby
    public static final String COMMENTS_URL = URL+"/api/card/"; // + card_id
    public static final String COMMENT_URL =  URL+"/api/comment"; // card_id, user_id, text

}