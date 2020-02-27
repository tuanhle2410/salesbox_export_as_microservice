package com.salesbox.message;

/**
 * Created by hungnh on 1/17/15.
 */
public enum LoggerMessage
{
    MAIL_SENDER("===MAIL SENDER ERROR==="),
    APPLICATION_CONFIG("===APPLICATION CONFIG ERROR==="),
    WEB_MVC_CONFIG("===WEB MVC CONFIG ERROR==="),
    NOTIFICATION("===NOTIFICATION ERROR==="),
    INTEGRATION("===INTEGRATION ERROR==="),
    STRIPE_PAYMENT("===STRIPE PAYMENT EXCEPTION==="),
    FACEBOOK("===FACEBOOK SERVICE ERROR==="),
    LINKEDIN("===LINKEDIN SERVICE ERROR==="),
    MAILCHIMP("===MAILCHIMP SERVICE ERROR==="),
    PHOTO("===PHOTO ERROR==="),
    LEAD_BOXER("===LEAD BOXER SERVICE ERROR===");

    private String extension;

    private LoggerMessage(String extension)
    {
        this.extension = extension;
    }

    public String toString()
    {
        return extension;
    }
}
