package com.salesbox.exception;

import com.salesbox.entity.enums.ResponseCodeConstants;
import org.springframework.http.HttpStatus;

/**
 * [File description]
 *
 * @author lent
 * @version 1.0
 * @since 4/29/14
 */
public class ServiceException extends Exception
{
// ------------------------------ FIELDS ------------------------------

    // TODO: suport i18n, resource string instead
    private String message;
    private HttpStatus httpStatusCode;
    private Throwable cause;
    private StackTraceElement[] stackTraceElements;
    private String additionInfo;
    private Object object;

// --------------------------- CONSTRUCTORS ---------------------------

    public ServiceException(String message)
    {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    public ServiceException(ResponseCodeConstants responseCode)
    {
        this(responseCode.getDisplay(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
    
    public ServiceException(String message, Object object)
    {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, null, object);
    }

    public ServiceException(String message, HttpStatus errorCode)
    {
        this(message, errorCode, null);
    }

    public ServiceException(String message, Throwable cause)
    {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    public ServiceException(String message, HttpStatus errorCode, Throwable cause)
    {
        this.message = message;
        this.cause = cause;
        this.httpStatusCode = errorCode;
        if (cause != null)
        {
            this.stackTraceElements = cause.getStackTrace();
        }
    }

    public ServiceException(String message, HttpStatus errorCode, Throwable cause, Object object)
    {
        this.message = message;
        this.cause = cause;
        this.httpStatusCode = errorCode;
        if (object instanceof String)
        {
            this.additionInfo = object.toString();
        }
        else
        {
            this.object = object;
        }
        if (cause != null)
        {
            this.stackTraceElements = cause.getStackTrace();
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override
    public Throwable getCause()
    {
        return cause;
    }

    public HttpStatus getHttpStatusCode()
    {
        return httpStatusCode;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }

    public StackTraceElement[] getStackTraceElements()
    {
        return stackTraceElements;
    }

    public String getAdditionInfo()
    {
        return additionInfo;
    }

    public void setAdditionInfo(String additionInfo)
    {
        this.additionInfo = additionInfo;
    }

    public Object getObject()
    {
        return object;
    }

    public void setObject(Object object)
    {
        this.object = object;
    }
}
