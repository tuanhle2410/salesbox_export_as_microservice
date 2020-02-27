package com.salesbox.constant;

/**
 * Created by HUNGLV2 on 3/19/2016.
 */
public class Office365API
{
    private static final String mainVersion = "v1.0";
    private static final String betaVersion = "beta";


    public static final String GET_TOKEN = "https://login.microsoftonline.com/common/oauth2/token";
    public static final String GET_USER_INFO = "https://graph.microsoft.com/" + mainVersion + "/me";


    public static final String GET_CALENDAR_LIST = "https://graph.microsoft.com/" + mainVersion + "/me/calendars";
    public static final String GET_EVENTS_BY_CALENDAR_ID = "https://graph.microsoft.com/" + mainVersion + "/me/calendars/{calendarId}/events?$Select=Start,End,Subject,BodyPreview,Location,Attendees,iCalUId";
    public static final String UPDATE_CALENDAR = "https://graph.microsoft.com/" + mainVersion + "/me/calendars/{calendarId}";
    public static final String DELETE_CALENDAR = "https://graph.microsoft.com/" + mainVersion + "/me/calendars/{calendarId}";

    public static final String GET_EVENT_BY_ID = "https://graph.microsoft.com/" + mainVersion + "/me/events/{eventId}?$Select=Id,Start,End,Subject,BodyPreview,Location,Attendees";


    public static final String ADD_EVENT = "https://graph.microsoft.com/" + mainVersion + "/me/calendars/{calendarId}/events";
    public static final String UPDATE_EVENT = "https://graph.microsoft.com/" + mainVersion + "/me/events/{eventId}";
    public static final String DELETE_EVENT = "https://graph.microsoft.com/" + mainVersion + "/me/events/";


    public static final String ADD_SUBSCRIPTION_NOTIFICATION = "https://graph.microsoft.com/" + mainVersion + "/subscriptions";
    public static final String CALENDAR_RESOURCE_FOLLOW_CHANGE = "me/calendars/{calendarId}/events";

    public static final String GET_CONTACT_LIST = "https://graph.microsoft.com/" + mainVersion + "/me/contacts";
    public static final String GET_ONE_CONTACT = "https://graph.microsoft.com/" + mainVersion + "/me/contacts/{contactId}";
    public static final String GET_CATEGORIES = "https://graph.microsoft.com/" + mainVersion + "/me/outlook/masterCategories";

    public static final String GET_MAILBOX_SETTING = "https://graph.microsoft.com/" + mainVersion + "/me/mailboxsettings";

    public static final String CONTACT_RESOURCE_FOLLOW_CHANGE = "me/contacts";

    public static final String WEBHOOK_MAIL = "/me/messages";

    public static final String ADD_MESSAGE_URL = "https://graph.microsoft.com/v1.0/me/sendMail";




    public static final String O365_SALESBOX_BASIC = "qa-salesbox_basic";

    public static final String O365_SALESBOX = "qa-salesbox";

    public static final String O365_SALESBOX_REDPILL = "qa-salesbox_redpill";

    public static final String O365_SALESBOX_DANYMAKERAB = "qa-salesbox_danymakerab";

    public static final String O365_SALESBOX_N2S = "qa-salesbox_n2s";

    public static final String O365_SALESBOX_ROUPEZ = "qa-salesbox_roupez";

    public static final String O365_SALESBOX_OCCASION = "qa-salesbox_occasion";

    public static final String O365_SALESBOX_TIPRO = "qa-salesbox_tipro";

    public static final String O365_SALESBOX_OUR = "qa-salesbox_our";

    public static final String O365_SALESBOX_VARDERINGSDATA = "qa-salesbox_varderingsdata";

    public static final String O365_SALESBOX_WESELECT = "qa-salesbox_weselect";

}
