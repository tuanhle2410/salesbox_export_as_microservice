package com.salesbox.task.dto;

import com.salesbox.annotation.OrikaMapper;
import com.salesbox.dto.ActivityDTO;
import com.salesbox.dto.TaskCommonDTO;
import com.salesbox.dto.WorkDataActivityDTO;
import com.salesbox.entity.Task;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * [File description]
 *
 * @author lent
 * @version 1.0
 * @since 5/1/14
 */
@OrikaMapper(mapClass = Task.class, excludes = {"focusWorkData", "focusActivity", "metadatas"})
public class TaskDTO extends TaskCommonDTO
{
    private UUID uuid;

    private String organisationName;
    private String organisationEmail;

    private UUID prospectId;

    private String prospectDescription;

    private UUID sharedContactId;

    private String contactName;
    private String contactEmail;
    private String contactPhone;

    private String contactAvatar;
    private String contactDiscProfile;

    private UUID categoryId;
    private String categoryName;

    private WorkDataActivityDTO focusWorkData;
    private ActivityDTO focusActivity;

    private UUID ownerId;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerAvatar;
    String ownerDiscProfile;

    private UUID creatorId;
    String creatorFirstName;
    String creatorLastName;
    String creatorAvatar;
    String creatorDiscProfile;

    private String note;
    private String type;
    private Boolean finished;
    private Boolean deleted;
    private Boolean accepted;
    private Date updatedDate;
    private Date createdDate;
    private Date distributionDate;

    private List<MetadataDTO> metadatas;

    private TagDTO tagDTO;

    private boolean isCallbackTask;

    private String googleTaskId;
    private String googleTaskListId;
    private String office365TaskId;

    public boolean getIsCallbackTask()
    {
        return isCallbackTask;
    }

    public void setIsCallbackTask(boolean isCallbackTask)
    {
        this.isCallbackTask = isCallbackTask;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getOrganisationName()
    {
        return organisationName;
    }

    public void setOrganisationName(String organisationName)
    {
        this.organisationName = organisationName;
    }

    public UUID getProspectId()
    {
        return prospectId;
    }

    public void setProspectId(UUID prospectId)
    {
        this.prospectId = prospectId;
    }

    public String getProspectDescription()
    {
        return prospectDescription;
    }

    public void setProspectDescription(String prospectDescription)
    {
        this.prospectDescription = prospectDescription;
    }

    public UUID getSharedContactId()
    {
        return sharedContactId;
    }

    public void setSharedContactId(UUID sharedContactId)
    {
        this.sharedContactId = sharedContactId;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public UUID getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public WorkDataActivityDTO getFocusWorkData()
    {
        return focusWorkData;
    }

    public void setFocusWorkData(WorkDataActivityDTO focusWorkData)
    {
        this.focusWorkData = focusWorkData;
    }

    public ActivityDTO getFocusActivity()
    {
        return focusActivity;
    }

    public void setFocusActivity(ActivityDTO focusActivity)
    {
        this.focusActivity = focusActivity;
    }

    public String getOwnerFirstName()
    {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName)
    {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName()
    {
        return ownerLastName;
    }

    public String getOwnerAvatar()
    {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar)
    {
        this.ownerAvatar = ownerAvatar;
    }

    public UUID getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId)
    {
        this.ownerId = ownerId;
    }

    public void setOwnerLastName(String ownerLastName)
    {
        this.ownerLastName = ownerLastName;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
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

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Boolean getAccepted()
    {
        return accepted;
    }

    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    public Date getDistributionDate()
    {
        return distributionDate;
    }

    public void setDistributionDate(Date distributionDate)
    {
        this.distributionDate = distributionDate;
    }

    public UUID getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId)
    {
        this.creatorId = creatorId;
    }

    public List<MetadataDTO> getMetadatas()
    {
        return metadatas;
    }

    public void setMetadatas(List<MetadataDTO> metadatas)
    {
        this.metadatas = metadatas;
    }

    public TagDTO getTagDTO()
    {
        return tagDTO;
    }

    public void setTagDTO(TagDTO tagDTO)
    {
        this.tagDTO = tagDTO;
    }

    public String getOwnerDiscProfile()
    {
        return ownerDiscProfile;
    }

    public void setOwnerDiscProfile(String ownerDiscProfile)
    {
        this.ownerDiscProfile = ownerDiscProfile;
    }

    public String getCreatorFirstName()
    {
        return creatorFirstName;
    }

    public void setCreatorFirstName(String creatorFirstName)
    {
        this.creatorFirstName = creatorFirstName;
    }

    public String getCreatorLastName()
    {
        return creatorLastName;
    }

    public void setCreatorLastName(String creatorLastName)
    {
        this.creatorLastName = creatorLastName;
    }

    public String getCreatorAvatar()
    {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar)
    {
        this.creatorAvatar = creatorAvatar;
    }

    public String getCreatorDiscProfile()
    {
        return creatorDiscProfile;
    }

    public void setCreatorDiscProfile(String creatorDiscProfile)
    {
        this.creatorDiscProfile = creatorDiscProfile;
    }

    public boolean isCallbackTask()
    {
        return isCallbackTask;
    }

    public void setCallbackTask(boolean isCallbackTask)
    {
        this.isCallbackTask = isCallbackTask;
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

	public String getOrganisationEmail() {
		return organisationEmail;
	}

	public void setOrganisationEmail(String organisationEmail) {
		this.organisationEmail = organisationEmail;
	}

    public String getContactAvatar()
    {
        return contactAvatar;
    }

    public void setContactAvatar(String contactAvatar)
    {
        this.contactAvatar = contactAvatar;
    }

    public String getContactDiscProfile()
    {
        return contactDiscProfile;
    }

    public void setContactDiscProfile(String contactDiscProfile)
    {
        this.contactDiscProfile = contactDiscProfile;
    }
}
