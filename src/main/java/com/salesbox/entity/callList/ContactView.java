package com.salesbox.entity.callList;

import com.salesbox.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by admin on 5/27/2017.
 */
@Entity
@Table(name = "v_contact")
public class ContactView extends BaseEntity
{
    @Column(name = "organisation_id")
    @Type(type = "pg-uuid")
    private UUID organisationId;

    @Column(name = "call_list_account_id")
    @Type(type = "pg-uuid")
    private UUID callListAccountId;

    @Column(name = "owner_id")
    @Type(type = "pg-uuid")
    private UUID ownerId;

    @Column(name = "owner_share_contact_id")
    @Type(type = "pg-uuid")
    private UUID ownerShareContactId;

    @Column(name = "unit_id")
    @Type(type = "pg-uuid")
    private UUID unitId;

    @Column(name = "enterprise_id")
    @Type(type = "pg-uuid")
    private UUID enterpriseId;

    @Column(name = "industry_id")
    @Type(type = "pg-uuid")
    private UUID industryId;

    @Column(name = "type_id")
    @Type(type = "pg-uuid")
    private UUID typeId;

    @Column(name = "relation_id")
    @Type(type = "pg-uuid")
    private UUID relationId;

    @Column(name = "user_last_name_aggregation")
    private String userLastNameAggregation;

    @Column(name = "user_first_name_aggregation")
    private String userFirstNameAggregation;

    @Column(name = "custom_field_value_aggregation")
    private String customFieldValueAggregation;

    @Column(name = "owner_last_name")
    private String ownerLastName;

    @Column(name = "owner_first_name")
    private String ownerFirstName;

    @Column(name = "organisation_name")
    private String organisationName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "phone")
    private String phone;


    @Column(name = "email")
    private String email;

    @Column(name = "street")
    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "country")
    private String country;

    @Column(name = "title")
    private String title;

    @Column(name = "industry_name")
    private String industryName;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "relation_name")
    private String relationName;

    @Column(name = "deleted")
    private Boolean deleted;

    public UUID getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(UUID organisationId) {
        this.organisationId = organisationId;
    }

    public UUID getCallListAccountId()
    {
        return callListAccountId;
    }

    public void setCallListAccountId(UUID callListAccountId)
    {
        this.callListAccountId = callListAccountId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getOwnerShareContactId() {
        return ownerShareContactId;
    }

    public void setOwnerShareContactId(UUID ownerShareContactId) {
        this.ownerShareContactId = ownerShareContactId;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public UUID getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(UUID enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public UUID getIndustryId() {
        return industryId;
    }

    public void setIndustryId(UUID industryId) {
        this.industryId = industryId;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    public UUID getRelationId() {
        return relationId;
    }

    public void setRelationId(UUID relationId) {
        this.relationId = relationId;
    }

    public String getUserLastNameAggregation() {
        return userLastNameAggregation;
    }

    public void setUserLastNameAggregation(String userLastNameAggregation) {
        this.userLastNameAggregation = userLastNameAggregation;
    }

    public String getUserFirstNameAggregation() {
        return userFirstNameAggregation;
    }

    public void setUserFirstNameAggregation(String userFirstNameAggregation) {
        this.userFirstNameAggregation = userFirstNameAggregation;
    }

    public String getCustomFieldValueAggregation() {
        return customFieldValueAggregation;
    }

    public void setCustomFieldValueAggregation(String customFieldValueAggregation) {
        this.customFieldValueAggregation = customFieldValueAggregation;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
