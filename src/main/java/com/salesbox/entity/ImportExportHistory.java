package com.salesbox.entity;

import com.salesbox.entity.enums.ActionType;
import com.salesbox.entity.enums.ObjectType;

import javax.persistence.*;

/**
 * Created by hunglv on 8/26/14.
 */

@Entity
@Table(name = "import_export_history")
public class ImportExportHistory extends BaseEntity
{
    @Column(name = "entity_type")
    @Enumerated(EnumType.ORDINAL)
    private ObjectType objectType;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private ActionType actionType;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(name = "username")
    String username;

    @Column(name = "file_name")
    String fileName;

    public ObjectType getObjectType()
    {
        return objectType;
    }

    public void setObjectType(ObjectType objectType)
    {
        this.objectType = objectType;
    }

    public ActionType getActionType()
    {
        return actionType;
    }

    public void setActionType(ActionType actionType)
    {
        this.actionType = actionType;
    }

    public Enterprise getEnterprise()
    {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise)
    {
        this.enterprise = enterprise;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
}
