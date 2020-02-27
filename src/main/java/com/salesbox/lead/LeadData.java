package com.salesbox.lead;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 8/23/14
 * Time: 3:09 PM
 */
public class LeadData
{
    String id;
    String email;
    String firstName;
    String lastName;
    String companyName;
    Boolean facebookLike = false;
    Boolean facebookComment = false;
    Boolean facebookShare = false;
    Boolean linkedInLike = false;
    Boolean linkedInComment = false;
    Boolean linkedInShare = false;
    Boolean mailChimpClick = false;
    Boolean mailChimpOpen = false;

    long totalClick = 0;
    long totalOpen = 0;

    public LeadData()
    {

    }

    public LeadData(String id, String firstName, String lastName, String email)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public LeadData(String id, String name)
    {
        this.id = id;
        this.firstName = name;
    }

    public LeadData(String id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Boolean getFacebookLike()
    {
        return facebookLike;
    }

    public void setFacebookLike(Boolean facebookLike)
    {
        this.facebookLike = facebookLike;
    }

    public Boolean getFacebookComment()
    {
        return facebookComment;
    }

    public void setFacebookComment(Boolean facebookComment)
    {
        this.facebookComment = facebookComment;
    }

    public Boolean getFacebookShare()
    {
        return facebookShare;
    }

    public void setFacebookShare(Boolean facebookShare)
    {
        this.facebookShare = facebookShare;
    }

    public Boolean getLinkedInLike()
    {
        return linkedInLike;
    }

    public void setLinkedInLike(Boolean linkedInLike)
    {
        this.linkedInLike = linkedInLike;
    }

    public Boolean getLinkedInComment()
    {
        return linkedInComment;
    }

    public void setLinkedInComment(Boolean linkedInComment)
    {
        this.linkedInComment = linkedInComment;
    }

    public Boolean getLinkedInShare()
    {
        return linkedInShare;
    }

    public void setLinkedInShare(Boolean linkedInShare)
    {
        this.linkedInShare = linkedInShare;
    }

    public Boolean getMailChimpClick()
    {
        return mailChimpClick;
    }

    public void setMailChimpClick(Boolean mailChimpClick)
    {
        this.mailChimpClick = mailChimpClick;
    }

    public Boolean getMailChimpOpen()
    {
        return mailChimpOpen;
    }

    public void setMailChimpOpen(Boolean mailChimpOpen)
    {
        this.mailChimpOpen = mailChimpOpen;
    }


    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public long getTotalClick()
    {
        return totalClick;
    }

    public void setTotalClick(long totalClick)
    {
        this.totalClick = totalClick;
    }

    public long getTotalOpen()
    {
        return totalOpen;
    }

    public void setTotalOpen(long totalOpen)
    {
        this.totalOpen = totalOpen;
    }
}
