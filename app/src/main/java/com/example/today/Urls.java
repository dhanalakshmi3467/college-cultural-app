package com.example.today;

public class Urls {

    public static final String HOST = "http://192.168.29.214/college-app";


    public static final String LOGIN_URL = String.format("%s/login.php", HOST);
    public static final String REG_URL = String.format("%s/andphpreg.php", HOST);
    public static final String CREATE_EVENT_URL = String.format("%s/CreateEvent.php?id=", HOST);
    public static final String GET_EVENT_TYPE_URL = String.format("%s/getEventType.php", HOST);
    public static final String GET_EVENTS_URL = String.format("%s/getEvents.php", HOST);
    public static final String IS_EVENT_BOOKED_URL = String.format("%s/isEventBooked.php", HOST);
    public static final String REGISTER_EVENT_URL = String.format("%s/RegisterEvent.php", HOST);
    public static final String UNREGISTER_EVENT_URL = String.format("%s/UnRegisterEvent.php", HOST);
    public static final String MY_EVENTS_REGISTERED_URL = String.format("%s/getEventsBooked.php?email=", HOST);

    public static final String CAMPUS_NEWS_URL = String.format("%s/news.php", HOST);

    public static final String EDIT_EVENT_URL = String.format("%s/update.php", HOST);
    public static final String DELETE_EVENT_URL = String.format("%s/delete.php", HOST);
    public static final String NEWS_DETAILS_URL = String.format("%s/newsdetails.php?id=", HOST);


}
