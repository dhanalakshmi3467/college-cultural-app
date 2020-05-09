package com.example.today;

public class Urls {

    public static final String HOST = "http://192.168.29.214/college-app";

    public static final String DAY1_URL = String.format("%s/api.php",HOST);
    public static final String DAY2_URL = String.format("%s/api1.php",HOST);
    public static final String DAY3_URL = String.format("%s/api2.php",HOST);
    public static final String ANOTHER_URL = String.format("%s/displayitems.php?id=" , HOST);
    public static final String ANOTHER1_URL = String.format("%s/displayitems1.php?id=",HOST);
    public static final String ANOTHER2_URL = String.format("%s/displayitems2.php?id=" ,HOST);
    public static final String LOGIN_URL = String.format("%s/login.php",HOST);
    public static final String REG_URL = String.format("%s/andphpreg.php",HOST);
    public static final String SCHEDULE_URL = String.format("%s/displaydata.php",HOST);
    public static final String SCHEDULE1_URL = String.format("%s/viewdata.php?id=",HOST);
    public static final String CREATE_EVENT_URL = String.format("%s/CreateEvent.php?id=",HOST);
    public static final String GET_EVENT_TYPE_URL = String.format("%s/getEventType.php",HOST);
    public static final String GET_EVENTS_URL = String.format("%s/getEvents.php?type=",HOST);
    public static final String IS_EVENT_BOOKED_URL = String.format("%s/isEventBooked.php",HOST);
    public static final String REGISTER_EVENT_URL = String.format("%s/RegisterEvent.php",HOST);
    public static final String UNREGISTER_EVENT_URL = String.format("%s/UnRegisterEvent.php",HOST);
    public static final String MY_EVENTS_REGISTERED_URL = String.format("%s/getEventsBooked.php?email=",HOST);

}
