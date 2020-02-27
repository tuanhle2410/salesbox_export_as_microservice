package com.salesbox.mail;

import javax.mail.Message;

/**
 * Created by hungnh on 6/26/15.
 */
public class Recipient
{
    private Message.RecipientType recipientType;
    private String emailAddress;

    public Recipient()
    {
    }

    public Recipient(Message.RecipientType recipientType, String emailAddress)
    {
        this.recipientType = recipientType;
        this.emailAddress = emailAddress;
    }

    public Message.RecipientType getRecipientType()
    {
        return recipientType;
    }

    public void setRecipientType(Message.RecipientType recipientType)
    {
        this.recipientType = recipientType;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
}
