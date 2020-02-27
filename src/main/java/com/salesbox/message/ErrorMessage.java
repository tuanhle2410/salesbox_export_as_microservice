package com.salesbox.message;

/**
 * Created by hungnh on 6/11/15.
 */
public enum ErrorMessage
{
    DEADLOCK_DETECTED,
    GOOGLE_TOKEN_HAS_BEEN_REVOKED,
    ANOTHER_SYNC_CHANEL_IS_ACTIVATED,
    NO_ACTIVE_SYNC_CHANEL,
    MAILBOX_NOT_ENABLED_FOR_API,
    AUTHENTICATE_OUTLOOK_ERROR,
    AUTHENTICATE_O365_ERROR,
    AUTHENTICATE_FORTNOX_ERROR,
    THIS_GOOGLE_ACCOUNT_HAS_ALREADY_CONNECT_TO_ANOTHER_SALESBOX_ACCOUNT,
    THIS_VISMA_ACCOUNT_HAS_ALREADY_CONNECT_TO_ANOTHER_SALESBOX_ACCOUNT,
    THIS_OUTLOOK_ACCOUNT_HAS_ALREADY_CONNECT_TO_ANOTHER_SALESBOX_ACCOUNT,
    THIS_OFFICE365_ACCOUNT_HAS_ALREADY_CONNECT_TO_ANOTHER_SALESBOX_ACCOUNT,
    PERSONAL_INTEGRATION_ACCOUNT_HAS_ALREADY_EXISTED,


    OUTLOOK_TOKEN_HAS_BEEN_REVOKED,
    O365_TOKEN_HAS_BEEN_REVOKED,

    STORAGE_NOT_FOUND,
    STORAGE_EXISTED
}
