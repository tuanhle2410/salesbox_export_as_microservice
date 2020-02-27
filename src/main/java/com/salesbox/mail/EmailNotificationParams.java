package com.salesbox.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 6/26/15.
 */
public class EmailNotificationParams
{
    private String subject;
    private List<Recipient> recipientList = new ArrayList<>();
    List<Content> contentList = new ArrayList<>();
    private List<Attachment> attachmentList = new ArrayList<>();
    private boolean isBCCToCustomer = false;

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public List<Recipient> getRecipientList()
    {
        return recipientList;
    }

    public void setRecipientList(List<Recipient> recipientList)
    {
        this.recipientList = recipientList;
    }

    public List<Content> getContentList()
    {
        return contentList;
    }

    public void setContentList(List<Content> contentList)
    {
        this.contentList = contentList;
    }

    public List<Attachment> getAttachmentList()
    {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList)
    {
        this.attachmentList = attachmentList;
    }

    public boolean isBCCToCustomer()
    {
        return isBCCToCustomer;
    }

    public void setBCCToCustomer(boolean isBCCToCustomer)
    {
        this.isBCCToCustomer = isBCCToCustomer;
    }
}
