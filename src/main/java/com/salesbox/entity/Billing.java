package com.salesbox.entity;

import com.salesbox.entity.enums.CardType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/10/14
 * Time: 2:34 PM
 */

@Entity
@Table(name = "billing")
public class Billing extends BaseEntity
{
// ------------------------------ FIELDS ------------------------------

    @Column(name = "organisation_number")
    private String organisationNumber;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "csc_code")
    private String cscCode;

    @Column(name = "card_owner_name")
    private String cardOwnerName;

    @Column(name = "card_type")
    @Enumerated(EnumType.ORDINAL)
    private CardType cardType;

    @Column(name = "card_exp_date")
    private String cardExpDate;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getCardExpDate()
    {
        return cardExpDate;
    }

    public void setCardExpDate(String expDate)
    {
        this.cardExpDate = expDate;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public String getCardOwnerName()
    {
        return cardOwnerName;
    }

    public void setCardOwnerName(String cardOwnerName)
    {
        this.cardOwnerName = cardOwnerName;
    }

    public CardType getCardType()
    {
        return cardType;
    }

    public void setCardType(CardType cardType)
    {
        this.cardType = cardType;
    }

    public String getCscCode()
    {
        return cscCode;
    }

    public void setCscCode(String cscCode)
    {
        this.cscCode = cscCode;
    }

    public String getOrganisationNumber()
    {
        return organisationNumber;
    }

    public void setOrganisationNumber(String organisationNumber)
    {
        this.organisationNumber = organisationNumber;
    }

    public String getVatNumber()
    {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber)
    {
        this.vatNumber = vatNumber;
    }
}
