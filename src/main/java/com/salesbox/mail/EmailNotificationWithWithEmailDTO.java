package com.salesbox.mail;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/10/2017.
 */
public class EmailNotificationWithWithEmailDTO
{
    private String subject;
    private List<Recipient> recipientList = new ArrayList<>();
    List<Content> contentList = new ArrayList<>();
    List<MultipartFile> attachmentFiles;

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

    public List<MultipartFile> getAttachmentFiles()
    {
        return attachmentFiles;
    }

    public void setAttachmentFiles(List<MultipartFile> attachmentFiles)
    {
        this.attachmentFiles = attachmentFiles;
    }
}
