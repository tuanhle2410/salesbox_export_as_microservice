package com.salesbox.entity;

import com.salesbox.entity.enums.DiscProfileType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * User: luult
 * Date: 5/21/14
 */
@Entity
@Table(name = "prospect_progress")
public class ProspectProgress extends BaseEntity
{
    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "is_finished")
    private Boolean finished = Boolean.FALSE;

    @Column(name = "number_meeting")
    private Integer numberMeeting = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /*Removed by abdul. as its not in production*/
    //Stores updated Date when Sent-Quote/Contract is clicked
    @Column(name ="sent_quote_contract_date")
    private java.util.Date sentQuoteContractDate;

    @Column(name = "index")
    private Integer index;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "meeting")
    private Integer meeting;

    @Column(name = "disc_profile")
    private DiscProfileType discProfile;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "actual_meeting")
    private Double actualMeeting;

    @Column(name = "actual_disc_profile")
    private DiscProfileType actualDiscProfile;

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Activity getActivity()
    {
        return activity;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
        this.index = activity.getIndex();
        this.name = activity.getName();
        this.description = activity.getDescription();
        this.meeting = activity.getMeeting();
        this.discProfile = activity.getDiscProfile();
        this.progress = activity.getProgress();
        this.actualMeeting = activity.getActualMeeting();
        this.actualDiscProfile = activity.getActualDiscProfile();
    }

    public Boolean getFinished()
    {
        return finished;
    }

    public void setFinished(Boolean finished)
    {
        this.finished = finished;
    }

    public Integer getNumberMeeting()
    {
        return numberMeeting;
    }

    public void setNumberMeeting(Integer numberMeeting)
    {
        this.numberMeeting = numberMeeting;
    }
    
	/**
	 * @return the sentQuoteContractDate
	 */
	public java.util.Date getSentQuoteContractDate() {
		return sentQuoteContractDate;
	}

	/**
	 * @param sentQuoteContractDate the sentQuoteContractDate to set
	 */
	public void setSentQuoteContractDate(java.util.Date sentQuoteContractDate) {
		this.sentQuoteContractDate = sentQuoteContractDate;
	}

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getMeeting()
    {
        return meeting;
    }

    public void setMeeting(Integer meeting)
    {
        this.meeting = meeting;
    }

    public DiscProfileType getDiscProfile()
    {
        return discProfile;
    }

    public void setDiscProfile(DiscProfileType discProfile)
    {
        this.discProfile = discProfile;
    }

    public Integer getProgress()
    {
        return progress;
    }

    public void setProgress(Integer progress)
    {
        this.progress = progress;
    }

    public Double getActualMeeting()
    {
        return actualMeeting;
    }

    public void setActualMeeting(Double actualMeeting)
    {
        this.actualMeeting = actualMeeting;
    }

    public DiscProfileType getActualDiscProfile()
    {
        return actualDiscProfile;
    }

    public void setActualDiscProfile(DiscProfileType actualDiscProfile)
    {
        this.actualDiscProfile = actualDiscProfile;
    }
}
