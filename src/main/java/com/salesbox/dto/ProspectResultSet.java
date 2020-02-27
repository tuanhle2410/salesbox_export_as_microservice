package com.salesbox.dto;


import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

/**
 * Created by GEM on 11/28/2017.
 */
public class ProspectResultSet implements Serializable{

    @Type(type="uuid-char")
    private UUID uuid;
    @Type(type="uuid-char")
    private UUID unit_id;
    @Type(type="uuid-char")
    private UUID user_id;
    @Type(type="uuid-char")
    private UUID power_sponsor_id;
    @Type(type="uuid-char")
    private UUID owner_id;
    @Type(type="uuid-char")
    private UUID sales_method_id;
    @Type(type="uuid-char")
    private UUID line_of_business_id;
    @Type(type="uuid-char")
    private UUID organisation_id;


    private Date contract_date;
    private Date won_lost_date;
    private Date last_sync_time;
    private BigInteger days_in_pipeline;
    private String first_next_step;
    private String description_first_next_step;
    private Integer disc_profile_first_next_step;
    private String second_next_step;
    private String description_second_next_step;
    private Integer disc_profile_second_next_step;
    private Double profit;
    private String description;
    private Integer prospect_progress;
    private Double gross_value;
    private Integer number_activity_left;
    private Double number_meeting_left;
    private BigInteger number_active_task;
    private BigInteger number_active_meeting;
    private Boolean is_favorite;
    private Boolean won;
    private Date created_date;
    private String owner_first_name;
    private String owner_last_name;
    private String owner_avatar;
    private Integer owner_disc_profile;
    private Date las_sync_time;
    private String leadboxer_id;
    private Boolean visit_more;
    private String prospect_owner_name;
    @Type(type="uuid-char")
    private UUID enterprise_id;
    private BigInteger number_team_member;
    private String last_name;
    private String first_name;
    private String contact_email;
    private String contact_phone;
    private Integer lose_meeting_ratio;
    private String sales_method_name;
    private String lob_name;
    private String o_name;
    private String power_sponsor_name;
    private String serial_number;
    private Double net_value;
    private String organisation_email;
    private Date delivery_start;
    private Date delivery_end;
    @Type(type="uuid-char")
    private UUID current_step_id;
    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getUnit_id()
    {
        return unit_id;
    }

    public void setUnit_id(UUID unit_id)
    {
        this.unit_id = unit_id;
    }

    public UUID getUser_id()
    {
        return user_id;
    }

    public void setUser_id(UUID user_id)
    {
        this.user_id = user_id;
    }

    public UUID getPower_sponsor_id()
    {
        return power_sponsor_id;
    }

    public void setPower_sponsor_id(UUID power_sponsor_id)
    {
        this.power_sponsor_id = power_sponsor_id;
    }

    public UUID getOwner_id()
    {
        return owner_id;
    }

    public void setOwner_id(UUID owner_id)
    {
        this.owner_id = owner_id;
    }

    public UUID getSales_method_id()
    {
        return sales_method_id;
    }

    public void setSales_method_id(UUID sales_method_id)
    {
        this.sales_method_id = sales_method_id;
    }

    public UUID getLine_of_business_id()
    {
        return line_of_business_id;
    }

    public void setLine_of_business_id(UUID line_of_business_id)
    {
        this.line_of_business_id = line_of_business_id;
    }

    public UUID getOrganisation_id()
    {
        return organisation_id;
    }

    public void setOrganisation_id(UUID organisation_id)
    {
        this.organisation_id = organisation_id;
    }

    public Date getContract_date()
    {
        return contract_date;
    }

    public void setContract_date(Date contract_date)
    {
        this.contract_date = contract_date;
    }

    public Date getWon_lost_date()
    {
        return won_lost_date;
    }

    public void setWon_lost_date(Date won_lost_date)
    {
        this.won_lost_date = won_lost_date;
    }

    public BigInteger getDays_in_pipeline()
    {
        return days_in_pipeline;
    }

    public void setDays_in_pipeline(BigInteger days_in_pipeline)
    {
        this.days_in_pipeline = days_in_pipeline;
    }

    public String getFirst_next_step()
    {
        return first_next_step;
    }

    public void setFirst_next_step(String first_next_step)
    {
        this.first_next_step = first_next_step;
    }

    public String getDescription_first_next_step()
    {
        return description_first_next_step;
    }

    public void setDescription_first_next_step(String description_first_next_step)
    {
        this.description_first_next_step = description_first_next_step;
    }

    public Integer getDisc_profile_first_next_step()
    {
        return disc_profile_first_next_step;
    }

    public void setDisc_profile_first_next_step(Integer disc_profile_first_next_step)
    {
        this.disc_profile_first_next_step = disc_profile_first_next_step;
    }

    public String getSecond_next_step()
    {
        return second_next_step;
    }

    public void setSecond_next_step(String second_next_step)
    {
        this.second_next_step = second_next_step;
    }

    public String getDescription_second_next_step()
    {
        return description_second_next_step;
    }

    public void setDescription_second_next_step(String description_second_next_step)
    {
        this.description_second_next_step = description_second_next_step;
    }

    public Integer getDisc_profile_second_next_step()
    {
        return disc_profile_second_next_step;
    }

    public void setDisc_profile_second_next_step(Integer disc_profile_second_next_step)
    {
        this.disc_profile_second_next_step = disc_profile_second_next_step;
    }

    public Double getProfit()
    {
        return profit;
    }

    public void setProfit(Double profit)
    {
        this.profit = profit;
    }

    public Integer getProspect_progress()
    {
        return prospect_progress;
    }

    public void setProspect_progress(Integer prospect_progress)
    {
        this.prospect_progress = prospect_progress;
    }

    public Double getGross_value()
    {
        return gross_value;
    }

    public void setGross_value(Double gross_value)
    {
        this.gross_value = gross_value;
    }

    public Integer getNumber_activity_left()
    {
        return number_activity_left;
    }

    public void setNumber_activity_left(Integer number_activity_left)
    {
        this.number_activity_left = number_activity_left;
    }

    public Double getNumber_meeting_left()
    {
        return number_meeting_left;
    }

    public void setNumber_meeting_left(Double number_meeting_left)
    {
        this.number_meeting_left = number_meeting_left;
    }

    public BigInteger getNumber_active_task()
    {
        return number_active_task;
    }

    public void setNumber_active_task(BigInteger number_active_task)
    {
        this.number_active_task = number_active_task;
    }

    public BigInteger getNumber_active_meeting()
    {
        return number_active_meeting;
    }

    public void setNumber_active_meeting(BigInteger number_active_meeting)
    {
        this.number_active_meeting = number_active_meeting;
    }

    public Boolean getIs_favorite()
    {
        return is_favorite;
    }

    public void setIs_favorite(Boolean is_favorite)
    {
        this.is_favorite = is_favorite;
    }

    public Boolean getWon()
    {
        return won;
    }

    public void setWon(Boolean won)
    {
        this.won = won;
    }

    public Date getCreated_date()
    {
        return created_date;
    }

    public void setCreated_date(Date created_date)
    {
        this.created_date = created_date;
    }

    public String getOwner_first_name()
    {
        return owner_first_name;
    }

    public void setOwner_first_name(String owner_first_name)
    {
        this.owner_first_name = owner_first_name;
    }

    public String getOwner_last_name()
    {
        return owner_last_name;
    }

    public void setOwner_last_name(String owner_last_name)
    {
        this.owner_last_name = owner_last_name;
    }

    public String getOwner_avatar()
    {
        return owner_avatar;
    }

    public void setOwner_avatar(String owner_avatar)
    {
        this.owner_avatar = owner_avatar;
    }

    public Integer getOwner_disc_profile()
    {
        return owner_disc_profile;
    }

    public void setOwner_disc_profile(Integer owner_disc_profile)
    {
        this.owner_disc_profile = owner_disc_profile;
    }

    public Date getLas_sync_time()
    {
        return las_sync_time;
    }

    public void setLas_sync_time(Date las_sync_time)
    {
        this.las_sync_time = las_sync_time;
    }

    public String getLeadboxer_id()
    {
        return leadboxer_id;
    }

    public void setLeadboxer_id(String leadboxer_id)
    {
        this.leadboxer_id = leadboxer_id;
    }

    public Boolean getVisit_more()
    {
        return visit_more;
    }

    public void setVisit_more(Boolean visit_more)
    {
        this.visit_more = visit_more;
    }

    public String getProspect_owner_name()
    {
        return prospect_owner_name;
    }

    public void setProspect_owner_name(String prospect_owner_name)
    {
        this.prospect_owner_name = prospect_owner_name;
    }

    public UUID getEnterprise_id()
    {
        return enterprise_id;
    }

    public void setEnterprise_id(UUID enterprise_id)
    {
        this.enterprise_id = enterprise_id;
    }

    public BigInteger getNumber_team_member()
    {
        return number_team_member;
    }

    public void setNumber_team_member(BigInteger number_team_member)
    {
        this.number_team_member = number_team_member;
    }

    public Integer getLose_meeting_ratio()
    {
        return lose_meeting_ratio;
    }

    public void setLose_meeting_ratio(Integer lose_meeting_ratio)
    {
        this.lose_meeting_ratio = lose_meeting_ratio;
    }

    public String getSales_method_name()
    {
        return sales_method_name;
    }

    public void setSales_method_name(String sales_method_name)
    {
        this.sales_method_name = sales_method_name;
    }

    public String getLob_name()
    {
        return lob_name;
    }

    public void setLob_name(String lob_name)
    {
        this.lob_name = lob_name;
    }

    public String getO_name()
    {
        return o_name;
    }

    public void setO_name(String o_name)
    {
        this.o_name = o_name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getLast_sync_time()
    {
        return last_sync_time;
    }

    public void setLast_sync_time(Date last_sync_time)
    {
        this.last_sync_time = last_sync_time;
    }

    public String getPower_sponsor_name()
    {
        return power_sponsor_name;
    }

    public void setPower_sponsor_name(String power_sponsor_name)
    {
        this.power_sponsor_name = power_sponsor_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getContact_email()
    {
        return contact_email;
    }

    public void setContact_email(String contact_email)
    {
        this.contact_email = contact_email;
    }

    public String getContact_phone()
    {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone)
    {
        this.contact_phone = contact_phone;
    }

    public String getSerial_number()
    {
        return serial_number;
    }

    public void setSerial_number(String serial_number)
    {
        this.serial_number = serial_number;
    }

    public Double getNet_value()
    {
        return net_value;
    }

    public void setNet_value(Double net_value)
    {
        this.net_value = net_value;
    }

	public String getOrganisation_email() {
		return organisation_email;
	}

	public void setOrganisation_email(String organisation_email) {
		this.organisation_email = organisation_email;
	}

    public Date getDelivery_start()
    {
        return delivery_start;
    }

    public void setDelivery_start(Date delivery_start)
    {
        this.delivery_start = delivery_start;
    }

    public UUID getCurrent_step_id()
    {
        return current_step_id;
    }

    public void setCurrent_step_id(UUID current_step_id)
    {
        this.current_step_id = current_step_id;
    }
}
