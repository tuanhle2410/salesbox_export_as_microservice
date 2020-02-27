package com.salesbox.file;

import com.salesbox.common.Constant;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/21/14
 * Time: 11:49 AM
 */

@Service
public class FileAccessor
{
    private File rootDir = new File(Constant.DEFAULT_FILE_LOCATION);

    public byte[] getFile(String fileId) throws IOException
    {
        File retrieveFile = selectFileFromFileName(fileId);
        if (!retrieveFile.exists())
        {
            throw new IOException("File not found");
        }

        if (!retrieveFile.canRead())
        {
            throw new IOException("File isn't readable");
        }
        return IOUtils.toByteArray(new FileInputStream(retrieveFile));
    }

    public File selectFileFromFileName(String fileId)
    {
        String subDirectoryName = fileId.substring(fileId.length()-3);
        File subDirectory = new File(rootDir, subDirectoryName);
        if (!subDirectory.exists())
        {
            subDirectory.mkdirs();
        }
        String fullPath = Constant.DEFAULT_FILE_LOCATION + File.separator + subDirectoryName + File.separator + fileId;
        return new File(fullPath);
    }

    public void saveFile(String fileId, byte[] data) throws IOException
    {
        File saveFile = selectFileFromFileName(fileId);
        if (saveFile.exists())
        {
            throw new IOException("File has existed");
        }

        IOUtils.write(data, new FileOutputStream(saveFile));
    }

    public void deleteFile(String fileId) throws IOException
    {
        File deleteFile = selectFileFromFileName(fileId);
        if (!deleteFile.exists())
        {
            throw new IOException("File not found");
        }

        if (!deleteFile.delete())
        {
            throw new IOException("File can't deleted");
        }
    }
}
