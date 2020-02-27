package com.salesbox.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

/**
 * @author phongnv on 2018-01-10
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganisationResultSet implements Serializable {

	@Type(type = "uuid-char")
	private UUID uuid;
	@Type(type = "uuid-char")
	private UUID owner_id;
	@Type(type = "uuid-char")
	private UUID shared_organisation_id;
	
	private String full_name;
	private String email;
	private String phone;
	private String owner_first_name;
	private String owner_last_name;
	private String owner_avatar;
	private Integer owner_disc_profile;
	private Double order_intake; 
	private Double won_profit; 
	private Double gross_pipeline; 
	private Double net_pipeline;
	private BigInteger number_team_member;
	private BigInteger number_active_task;
	private BigInteger number_active_meeting;
	private BigInteger number_active_prospect;
	private Boolean is_favorite;
	private Double relationship;
	private Date last_viewed;
	private Integer recent_action_type;
	private String avatar;
	private String name;
	private Date next_meeting_date;
	private Date next_task_date;
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(UUID owner_id) {
		this.owner_id = owner_id;
	}

	public UUID getShared_organisation_id() {
		return shared_organisation_id;
	}

	public void setShared_organisation_id(UUID shared_organisation_id) {
		this.shared_organisation_id = shared_organisation_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getOwner_first_name() {
		return owner_first_name;
	}

	public void setOwner_first_name(String owner_first_name) {
		this.owner_first_name = owner_first_name;
	}

	public String getOwner_last_name() {
		return owner_last_name;
	}

	public void setOwner_last_name(String owner_last_name) {
		this.owner_last_name = owner_last_name;
	}

	public String getOwner_avatar() {
		return owner_avatar;
	}

	public void setOwner_avatar(String owner_avatar) {
		this.owner_avatar = owner_avatar;
	}

	public Integer getOwner_disc_profile() {
		return owner_disc_profile;
	}

	public void setOwner_disc_profile(Integer owner_disc_profile) {
		this.owner_disc_profile = owner_disc_profile;
	}

	public Double getOrder_intake() {
		return order_intake;
	}

	public void setOrder_intake(Double order_intake) {
		this.order_intake = order_intake;
	}

	public Double getWon_profit() {
		return won_profit;
	}

	public void setWon_profit(Double won_profit) {
		this.won_profit = won_profit;
	}

	public Double getGross_pipeline() {
		return gross_pipeline;
	}

	public void setGross_pipeline(Double gross_pipeline) {
		this.gross_pipeline = gross_pipeline;
	}

	public Double getNet_pipeline() {
		return net_pipeline;
	}

	public void setNet_pipeline(Double net_pipeline) {
		this.net_pipeline = net_pipeline;
	}

	public BigInteger getNumber_team_member() {
		return number_team_member;
	}

	public void setNumber_team_member(BigInteger number_team_member) {
		this.number_team_member = number_team_member;
	}

	public BigInteger getNumber_active_task() {
		return number_active_task;
	}

	public void setNumber_active_task(BigInteger number_active_task) {
		this.number_active_task = number_active_task;
	}

	public BigInteger getNumber_active_meeting() {
		return number_active_meeting;
	}

	public void setNumber_active_meeting(BigInteger number_active_meeting) {
		this.number_active_meeting = number_active_meeting;
	}

	public BigInteger getNumber_active_prospect() {
		return number_active_prospect;
	}

	public void setNumber_active_prospect(BigInteger number_active_prospect) {
		this.number_active_prospect = number_active_prospect;
	}

	public Boolean getIs_favorite() {
		return is_favorite;
	}

	public void setIs_favorite(Boolean is_favorite) {
		this.is_favorite = is_favorite;
	}

	public Double getRelationship() {
		return relationship;
	}

	public void setRelationship(Double relationship) {
		this.relationship = relationship;
	}

	public Date getLast_viewed() {
		return last_viewed;
	}

	public void setLast_viewed(Date last_viewed) {
		this.last_viewed = last_viewed;
	}

	public Integer getRecent_action_type() {
		return recent_action_type;
	}

	public void setRecent_action_type(Integer recent_action_type) {
		this.recent_action_type = recent_action_type;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getNext_meeting_date()
	{
		return next_meeting_date;
	}

	public void setNext_meeting_date(Date next_meeting_date)
	{
		this.next_meeting_date = next_meeting_date;
	}

	public Date getNext_task_date()
	{
		return next_task_date;
	}

	public void setNext_task_date(Date next_task_date)
	{
		this.next_task_date = next_task_date;
	}
}
