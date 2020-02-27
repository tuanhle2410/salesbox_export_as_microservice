package com.salesbox.mail;

import com.salesbox.common.BaseService;
import com.salesbox.common.Constant;
import com.salesbox.dao.LanguageDAO;
import com.salesbox.entity.Language;
import com.salesbox.exception.ServiceException;
import com.salesbox.message.LocalizationMessage;
import com.salesbox.message.LoggerMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/14/14
 * Time: 7:44 AM
 */
@org.springframework.stereotype.Service
public class MailSender extends BaseService
{
    @Value("${smtp_host}")
    private String smtpHost;

    @Value("${smtp_port}")
    private String smtpPort;

    @Value("${smtp_username}")
    private String smtpUsername;

    @Value("${smtp_password}")
    private String smtpPassword;

    @Value("${salesbox_crm}")
    private String salesboxCRM;

    @Value("${salesbox_message}")
    private String salesboxMessage;

    @Value("${send_mail}")
    private boolean sendMail;

    @Value("${customer.report.sent}")
    boolean isSentReport;

    @Value("${customer.mailservice.sent}")
    boolean isSentMail;

    @Value("${logger.environment}")
    private String loggerEnvironment;

    @Value("${prefix.url}")
    private String prefixUrl;

    @Value("${smtp_meeting_username}")
    private String smtpMeetingUsername;

    @Value("${smtp_meeting_password}")
    private String smtpMeetingPassword;

    @Value("${smtp_meeting_sender}")
    private String noReplyCRM;


    public final static Integer maxAttemptToSend = 3;

    @Autowired
    LanguageDAO languageDAO;

    private static final Logger logger = Logger.getLogger(MailSender.class);

    @Async
    public void sendExceptionMail(EmailNotificationParams emailNotificationParams, Language language)
    {
        try
        {
            attemptToSend(emailNotificationParams, language);
        }
        catch (Exception ex)
        {
            logger.error(LoggerMessage.MAIL_SENDER.toString(), ex);
        }
    }

    @Async
    public void sendMail(EmailNotificationParams emailNotificationParams, Language language)
    {
//        if (!sendMail)
//        {
//            return;
//        }

        for (int i = 0; i < maxAttemptToSend; i++)
        {
            try
            {
                attemptToSend(emailNotificationParams, language);
                break;
            }
            catch (Exception ex)
            {
                logger.error(LoggerMessage.MAIL_SENDER.toString(), ex);
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void attemptToSend(EmailNotificationParams emailNotificationParams, Language language) throws MessagingException, UnsupportedEncodingException
    {
        Session session = createSession();

        MimeMessage message = new MimeMessage(session);
        message.setSubject(emailNotificationParams.getSubject(), "UTF-8");

        populateMessageSender(language, message);
        populateRecipientList(emailNotificationParams, message);

        Multipart multipart = new MimeMultipart();
        addContentPart(emailNotificationParams, multipart);
        addAttachmentPart(emailNotificationParams, multipart);
        message.setContent(multipart);

        Transport.send(message);
    }

    public void sendMailReceiptPayment(EmailNotificationParams emailNotificationParams, Language language)
    {
        if (!sendMail)
        {
            return;
        }

        try
        {
            attemptToSend(emailNotificationParams, language);
        }
        catch (Exception ex)
        {
            logger.error(LoggerMessage.MAIL_SENDER.toString(), ex);
        }
    }


    @Async
    public void sendMailAddingInBulk(EmailNotificationParams emailNotificationParams)
    {
//        if (!sendMail)
//        {
//            return;
//        }

        try
        {
            Session session = createSessionForInfo();

            MimeMessage message = new MimeMessage(session);
            message.setSubject(emailNotificationParams.getSubject(), "UTF-8");

            message.setFrom(new InternetAddress(salesboxCRM, "The Salesbox Team"));
            populateRecipientList(emailNotificationParams, message);

            Multipart multipart = new MimeMultipart();
            addContentPart(emailNotificationParams, multipart);
            addAttachmentPart(emailNotificationParams, multipart);
            message.setContent(multipart);

            Transport.send(message);
        }
        catch (Exception ex)
        {
            logger.error(LoggerMessage.MAIL_SENDER.toString(), ex);
        }
    }

    @Async
    public void sendMailCampaign(EmailNotificationParams emailNotificationParams, Language language)
    {
        if (!sendMail)
        {
            return;
        }

        try
        {
            Session session = createSessionForInfo();

            MimeMessage message = new MimeMessage(session);
            message.setSubject(emailNotificationParams.getSubject(), "UTF-8");

            message.setFrom(new InternetAddress(salesboxCRM, "The Salesbox Team"));
            populateRecipientList(emailNotificationParams, message);

            Multipart multipart = new MimeMultipart();
            addContentPart(emailNotificationParams, multipart);
            addAttachmentPart(emailNotificationParams, multipart);
            message.setContent(multipart);

            Transport.send(message);
        }
        catch (Exception ex)
        {
            logger.error(LoggerMessage.MAIL_SENDER.toString(), ex);
        }
    }

    @Async
    public void sendMailUsingAlias(EmailNotificationParams emailNotificationParams, Language language, String alias)
    {
        if (!sendMail)
        {
            return;
        }

        try
        {
            Session session = createSessionForAlias();

            MimeMessage message = new MimeMessage(session);
            message.setSubject(emailNotificationParams.getSubject(), "UTF-8");

            populateMessageSender(language, message, alias);
            populateRecipientList(emailNotificationParams, message);

            Multipart multipart = new MimeMultipart();
            addContentPart(emailNotificationParams, multipart);
            addAttachmentPart(emailNotificationParams, multipart);
            message.setContent(multipart);

            Transport.send(message);
        }
        catch (Exception ex)
        {
            logger.error(LoggerMessage.MAIL_SENDER.toString(), ex);
        }
    }

    private void addAttachmentPart(EmailNotificationParams emailNotificationParams, Multipart multipart) throws MessagingException
    {
        for (Attachment attachment : emailNotificationParams.getAttachmentList())
        {
            DataSource source = new ByteArrayDataSource(attachment.getData(), attachment.getType().getValue());

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(attachment.getName());

            multipart.addBodyPart(attachmentBodyPart);
        }
    }

    private void addContentPart(EmailNotificationParams emailNotificationParams, Multipart multipart) throws MessagingException
    {
        this.addContentPartFromListContent(emailNotificationParams.getContentList(), multipart);
    }

    private void populateRecipientList(EmailNotificationParams emailNotificationParams, MimeMessage message) throws MessagingException
    {
        for (Recipient recipient : emailNotificationParams.getRecipientList())
        {
            message.addRecipients(recipient.getRecipientType(), InternetAddress.parse(recipient.getEmailAddress()));
        }
        if (emailNotificationParams.isBCCToCustomer() && isSentReport)
        {
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(Constant.reportMail1));
        }
    }

    private void populateMessageSender(Language language, MimeMessage message) throws MessagingException, UnsupportedEncodingException
    {
        if (language == null)
        {
            language = languageDAO.findByName("en");
        }

        String fromName = getLocalizationValueByLanguageAndCode(language, LocalizationMessage.THE_SALESBOX_CRM_TEAM.toString());
        message.setFrom(new InternetAddress(salesboxCRM, fromName));
    }

    private void populateMessageSender(Language language, MimeMessage message, String alias) throws MessagingException, UnsupportedEncodingException
    {
        message.setFrom(new InternetAddress(noReplyCRM, alias));
    }


    private Session createSession()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        return Session.getInstance(props,
                new Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(smtpUsername, smtpPassword);
                    }
                }
        );
    }

    private Session createSessionForAlias()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        return Session.getInstance(props,
                new Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(smtpMeetingUsername, smtpMeetingPassword);
                    }
                }
        );
    }

    private Session createSessionForInfo()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        return Session.getInstance(props,
                new Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(smtpUsername, smtpPassword);
                    }
                }
        );
    }

    private void addContentPartFromListContent(List<Content> contentList, Multipart multipart) throws MessagingException
    {
        for (Content content : contentList)
        {
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            String contentValue = content.getValue();
            int i = 1;
            for (String param : content.getParams())
            {
                if (param != null)
                {
                    contentValue = contentValue.replace("[param" + i + "]", param);
                }
                else
                {
                    contentValue = contentValue.replace("[param" + i + "]", "");
                }
                i++;
            }

            if (content.getType().equals(Content.Type.PLAIN_TEXT))
            {
                messageBodyPart.setText(contentValue, "UTF-8");
            }
            else if (content.getType().equals(Content.Type.HTML))
            {
                messageBodyPart.setContent(contentValue, content.getType().getValue());
            }

            multipart.addBodyPart(messageBodyPart);
        }
    }

    //Send mailk from EmailDTO
    @Async
    public void sendMailForEmailDTO(EmailNotificationWithWithEmailDTO emailNotificationWithWithEmailDTO, Language language) throws ServiceException
    {
        if (!sendMail)
        {
            return;
        }

        for (int i = 0; i < maxAttemptToSend; i++)
        {
            try
            {
                processSendMailForEmailDTO(emailNotificationWithWithEmailDTO, language);
                break;
            }
            catch (Exception ex)
            {
                if (i == (maxAttemptToSend - 1))
                {
                    throw new ServiceException("SEND MAIL ERROR");
                }
                logger.error(LoggerMessage.MAIL_SENDER.toString(), ex);
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processSendMailForEmailDTO(EmailNotificationWithWithEmailDTO emailNotificationWithWithEmailDTO, Language language) throws MessagingException, IOException
    {
        Session session = createSession();

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(salesboxCRM, "The Salesbox Team"));
        message.setSubject(emailNotificationWithWithEmailDTO.getSubject(), "UTF-8");

        populateMessageSender(language, message);
        for (Recipient recipient : emailNotificationWithWithEmailDTO.getRecipientList())
        {
            message.addRecipients(recipient.getRecipientType(), InternetAddress.parse(recipient.getEmailAddress()));
        }

        Multipart multipart = new MimeMultipart();

        this.addContentPartFromListContent(emailNotificationWithWithEmailDTO.getContentList(), multipart);
        populateMailAttachmentFromEmailDTO(emailNotificationWithWithEmailDTO, multipart);
        message.setContent(multipart);

        Transport.send(message);
    }

    private void populateMailAttachmentFromEmailDTO(EmailNotificationWithWithEmailDTO emailNotificationWithWithEmailDTO, Multipart multipart) throws MessagingException, IOException
    {
        for (MultipartFile file : emailNotificationWithWithEmailDTO.getAttachmentFiles())
        {
            DataSource source = new ByteArrayDataSource(file.getInputStream(), file.getContentType());

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(file.getOriginalFilename());
            multipart.addBodyPart(attachmentBodyPart);
        }
    }

    public void sendMailByType(EmailNotificationParams emailNotificationParams, Language language, MailType mailType)
    {
//        if (!sendMail)
//        {
//            return;
//        }

        for (int i = 0; i < maxAttemptToSend; i++)
        {
            try
            {
                attemptToSendByMailType(emailNotificationParams, language, mailType);
                break;
            }
            catch (Exception ex)
            {
                logger.error(LoggerMessage.MAIL_SENDER.toString(), ex);
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void attemptToSendByMailType(EmailNotificationParams emailNotificationParams, Language language, MailType mailType) throws MessagingException, UnsupportedEncodingException
    {
        Session session = createSession();

        MimeMessage message = new MimeMessage(session);
        message.setSubject(emailNotificationParams.getSubject(), "UTF-8");
        populateMessageSenderByType(language, message, mailType);
        populateRecipientList(emailNotificationParams, message);

        Multipart multipart = new MimeMultipart();
        addContentPart(emailNotificationParams, multipart);
        addAttachmentPart(emailNotificationParams, multipart);
        message.setContent(multipart);

        Transport.send(message);
    }

    private void populateMessageSenderByType(Language language, MimeMessage message, MailType mailType) throws UnsupportedEncodingException, MessagingException
    {

        if (language == null)
        {
            language = languageDAO.findByName("en");
        }

        String fromName = getLocalizationValueByLanguageAndCode(language, LocalizationMessage.THE_SALESBOX_CRM_TEAM.toString());

        if (mailType == MailType.LATE_MEETING_TYPE)
        {
            message.setFrom(new InternetAddress(salesboxMessage, fromName));
        }
        else
        {
            message.setFrom(new InternetAddress(salesboxCRM, fromName));
        }
    }
}
