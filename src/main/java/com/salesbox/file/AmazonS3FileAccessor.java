package com.salesbox.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.salesbox.common.BaseService;
import com.salesbox.common.Constant;
import com.salesbox.config.webscope.ScopeConst;
import com.salesbox.exception.ServiceException;
import com.salesbox.message.LocalizationMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 8/4/14
 * Time: 9:37 AM
 */
@Service
public class AmazonS3FileAccessor extends BaseService
{
    @Value("${accessKey}")
    String accessKey;

    @Value("${secretKey}")
    String secretKey;

    @Value("${aws.bucket}")
    String S3BucketName;

    @Value("${aws.document.bucket}")
    String S3DocumentBucketName;

    @Value("${location.file.template}")
    private String templateFileLocation;

    private static final String IPAD = "1_";
    private static final String IPHONE = "2_";

    @Bean
    @Scope(ScopeConst.SINGLETON)
    public AmazonS3 getS3Client()
    {
        BasicAWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(Regions.EU_WEST_1).build();
    }

    public S3Object getFile(String fileId) throws ServiceException
    {
        try
        {
            AmazonS3 s3 = getS3Client();
            String fileName = getObjectName(fileId);
            return s3.getObject(S3BucketName, fileName);
        }
        catch (AmazonServiceException ex)
        {
            throw new ServiceException(LocalizationMessage.IMAGE_NOT_FOUND.toString());
        }
    }

    public S3Object getDocumentFile(String fileId) throws ServiceException
    {
        AmazonS3 s3 = getS3Client();
        String fileName = getObjectName(fileId);
        return s3.getObject(S3DocumentBucketName, fileName);
    }

    public byte[] getReportFile(String fileId, String languageCode, String fileExtension) throws IOException, AmazonServiceException
    {
        AmazonS3 s3 = getS3Client();
        String fileName = getObjectName(fileId) + Constant.reportFileDelimiter + languageCode + Constant.reportFileDelimiter + fileExtension;
        S3Object s3Object = s3.getObject(S3DocumentBucketName, fileName);
        s3Object.getKey();
        return IOUtils.toByteArray(s3Object.getObjectContent());
    }

    public void copyFile(String srcFileId, String desFileId) throws ServiceException
    {
        AmazonS3 s3 = getS3Client();
        String srcFileName = getObjectName(srcFileId);
        String desFileName = getObjectName(desFileId);
        s3.copyObject(S3BucketName, srcFileName, S3BucketName, desFileName);
    }

    public S3Object getFile(String bucketName, String fileName) throws ServiceException
    {
        AmazonS3 s3 = getS3Client();
        return s3.getObject(bucketName, fileName);
    }

    public byte[] getBytesOfFile(String bucketName, String fileName) throws ServiceException, IOException
    {
        S3Object s3Object = getFile(bucketName, fileName);
        return IOUtils.toByteArray(s3Object.getObjectContent());
    }

    public void savePhotoFile(String fileId, File file) throws IOException
    {
        AmazonS3 s3 = getS3Client();
        String fileName = getObjectName(fileId);
        s3.putObject(S3BucketName, fileName, file);
    }


    public String saveExportFile(String fileId, InputStream inputStream) throws IOException
    {
        AmazonS3 s3 = getS3Client();
        Calendar exportTime = Calendar.getInstance();
        String folderName = "export" + "/" + String.valueOf(exportTime.get(Calendar.YEAR)) + String.valueOf(exportTime.get(Calendar.MONTH) + 1) + String.valueOf(exportTime.get(Calendar.DAY_OF_MONTH));
        // fix utf-8 enterprise name
        String encodedUTF8Filename = URLEncoder.encode(fileId, "UTF-8");

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentDisposition("attachment;filename=" + encodedUTF8Filename);
        objectMetadata.setContentType("application/vnd.ms-excel");

        PutObjectRequest putObjectRequest = new PutObjectRequest(S3DocumentBucketName, folderName + "/" + fileId, inputStream, objectMetadata);
        s3.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
        return "https://s3-eu-west-1.amazonaws.com/" + S3DocumentBucketName + "/" + folderName + "/" + fileId;
    }

    public void saveDocumentFile(String fileId, File file) throws IOException
    {
        AmazonS3 s3 = getS3Client();
        String fileName = getObjectName(fileId);
        s3.putObject(S3DocumentBucketName, fileName, file);
    }

    public void saveReportFile(String fileId, String languageCode, File file, String fileExtension)
    {
        AmazonS3 s3 = getS3Client();
        String fileName = getObjectName(fileId) + Constant.reportFileDelimiter + languageCode + Constant.reportFileDelimiter + fileExtension;
        s3.putObject(S3DocumentBucketName, fileName, file);
    }

    public String saveDailyReportFile(String filePath, String fileName, InputStream inputStream)
    {
        AmazonS3 s3 = getS3Client();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentDisposition("attachment;filename=" + fileName);
        objectMetadata.setContentType("application/vnd.ms-excel");

        PutObjectRequest putObjectRequest = new PutObjectRequest("salesboxreportdaily", filePath + "/" + fileName, inputStream, objectMetadata);
        s3.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));

        return "https://s3-eu-west-1.amazonaws.com/salesboxreportdaily/" + filePath + "/" + fileName;
    }

    public String saveReceiptFile(String filePath, String fileName, InputStream inputStream)
    {
        AmazonS3 s3 = getS3Client();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentDisposition("attachment;filename=" + fileName);
        objectMetadata.setContentType("application/pdf");

        PutObjectRequest putObjectRequest = new PutObjectRequest("salesbox-receipt", filePath + "/" + fileName, inputStream, objectMetadata);
        s3.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));

        return "https://s3-eu-west-1.amazonaws.com/salesbox-receipt/" + filePath + "/" + fileName;
    }

    public ObjectMetadata getObjectMetadata(String bucketName, String fileName)
    {
        AmazonS3 s3 = getS3Client();
        return s3.getObjectMetadata(bucketName, fileName);
    }

    public void deleteFile(String fileId) throws IOException
    {
        AmazonS3 s3 = getS3Client();
        String fileName = getObjectName(fileId);
        s3.deleteObject(S3BucketName, fileName);
    }

    public void deleteFiles(String fileId)
    {
        AmazonS3 s3 = getS3Client();
        DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(S3BucketName);
        List<DeleteObjectsRequest.KeyVersion> listKey = new ArrayList<>();
        DeleteObjectsRequest.KeyVersion keyVersion;

        String fileName = getObjectName(fileId);
        keyVersion = new DeleteObjectsRequest.KeyVersion(fileName);
        listKey.add(keyVersion);

        String fileIpad = getObjectName(IPAD + fileId);
        keyVersion = new DeleteObjectsRequest.KeyVersion(fileIpad);
        listKey.add(keyVersion);

        String fileIphone = getObjectName(IPHONE + fileId);
        keyVersion = new DeleteObjectsRequest.KeyVersion(fileIphone);
        listKey.add(keyVersion);

        deleteRequest.setKeys(listKey);
        s3.deleteObjects(deleteRequest);
    }

    private String getObjectName(String fileId)
    {
        String folderName = fileId.substring(fileId.length() - 3) + "/";
        return folderName + fileId;
    }

    public String getHtmlTemplateString(String templateFileName, String languageName) throws ServiceException, IOException
    {
        byte[] templateBytes;
        try
        {
            String bucketName = templateFileLocation + "/" + languageName;
            doLog("<<<<< Bucket Is >>>>>>>>> ", bucketName + "");
            templateBytes = getBytesOfFile(bucketName, templateFileName);

        }
        catch (Exception ex)
        {
            String bucketName = templateFileLocation + "/" + "en";
            doLog("<<<<< Bucket Is >>>>>>>>> ", bucketName + "");
            templateBytes = getBytesOfFile(bucketName, templateFileName);
        }

        InputStream inputStream = new ByteArrayInputStream(templateBytes);
        String templateString = IOUtils.toString(inputStream, "UTF-8");

        return templateString;
    }

    public void doLog(String header, String content)
    {
        doLogGlobal(header, content, "/srv/logs/salesbox_daily_mail.log");
    }

}
