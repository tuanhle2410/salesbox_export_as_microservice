package com.salesbox.entity.task;

/**
 * Created by admin on 4/9/2017.
 */

import com.salesbox.entity.*;
import com.salesbox.entity.enums.TaskType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

/**
 * Created by admin on 4/1/2017.
 */

@MappedSuperclass
public class TaskBase extends BaseEntity
{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private WorkDataActivity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "focus_activity_id")
    private Activity focusActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "focus_work_data_id")
    private WorkDataActivity focusWorkData;

    @Column(name = "date_and_time")
    private Date dateAndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "note")
    private String note;

    @Column(name = "type")
    private TaskType type;

    @Column(name = "is_finished")
    private Boolean finished = Boolean.FALSE ;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "accepted")
    private Boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "distribution_date")
    private Date distributionDate;

    @Column(name = "prioritise_generated")
    private Boolean prioritiseGenerated = Boolean.FALSE;

    @Column(name = "call_back_later_generated")
    private Boolean callBackLaterGenerated = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_tag_id")
    private TaskTag taskTag;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name ="task_metadatas", joinColumns=@JoinColumn(name="task_id", referencedColumnName = "uuid"))
    private Set<Metadata> metadatas = new HashSet<>();

    @Column(name = "maestrano_id")
    private String maestranoId;

    @Type(type = "pg-uuid")
    @Basic
    @Column(name = "prospect_id")
    private UUID prospectId;


    @Transient
    private List<String> metadataValues = new ArrayList<>();

    public String getMaestranoId() {
        return maestranoId;
    }

    public void setMaestranoId(String maestranoId) {
        this.maestranoId = maestranoId;
    }

    @Column(name = "google_task_id")
    private String googleTaskId;

    @Column(name = "google_task_list_id")
    private String googleTaskListId;

    @Column(name = "office365_task_id")
    private String office365TaskId;





    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public Organisation getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }

    public WorkDataActivity getCategory()
    {
        return category;
    }

    public void setCategory(WorkDataActivity category)
    {
        this.category = category;
    }

    public Activity getFocusActivity()
    {
        return focusActivity;
    }

    public void setFocusActivity(Activity focusActivity)
    {
        this.focusActivity = focusActivity;
    }

    public WorkDataActivity getFocusWorkData()
    {
        return focusWorkData;
    }

    public void setFocusWorkData(WorkDataActivity focusWorkData)
    {
        this.focusWorkData = focusWorkData;
    }

    public Date getDateAndTime()
    {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime)
    {
        this.dateAndTime = dateAndTime;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public TaskType getType()
    {
        return type;
    }

    public void setType(TaskType type)
    {
        this.type = type;
    }

    public Boolean getFinished()
    {
        return finished;
    }

    public void setFinished(Boolean finished)
    {
        this.finished = finished;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public Boolean getAccepted()
    {
        return accepted;
    }

    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public Date getDistributionDate()
    {
        return distributionDate;
    }

    public void setDistributionDate(Date distributionDate)
    {
        this.distributionDate = distributionDate;
    }

    public User getCreator()
    {
        return creator;
    }

    public void setCreator(User creator)
    {
        this.creator = creator;
    }

    public Boolean getPrioritiseGenerated()
    {
        return prioritiseGenerated;
    }

    public void setPrioritiseGenerated(Boolean prioritiseGenerated)
    {
        this.prioritiseGenerated = prioritiseGenerated;
    }

    public TaskTag getTaskTag()
    {
        return taskTag;
    }

    public void setTaskTag(TaskTag taskTag)
    {
        this.taskTag = taskTag;
    }

    public Set<Metadata> getMetadatas() {
        return metadatas;
    }

    public void setMetadatas(Set<Metadata> metadataValues) {
        this.metadatas = metadataValues;
    }

    public List<String> getMetadataValues() {
        return metadataValues;
    }

    public void setMetadataValues(List<String> metadataValues) {
        this.metadataValues = metadataValues;
    }

    public Boolean getCallBackLaterGenerated()
    {
        return callBackLaterGenerated;
    }

    public void setCallBackLaterGenerated(Boolean callBackLaterGenerated)
    {
        this.callBackLaterGenerated = callBackLaterGenerated;
    }

    public String getGoogleTaskId()
    {
        return googleTaskId;
    }

    public void setGoogleTaskId(String googleTaskId)
    {
        this.googleTaskId = googleTaskId;
    }

    public String getOffice365TaskId()
    {
        return office365TaskId;
    }

    public void setOffice365TaskId(String office365TaskId)
    {
        this.office365TaskId = office365TaskId;
    }

    public String getGoogleTaskListId()
    {
        return googleTaskListId;
    }

    public void setGoogleTaskListId(String googleTaskListId)
    {
        this.googleTaskListId = googleTaskListId;
    }

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }


}
