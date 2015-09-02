import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

    private static final String outputFileName = "A.zip";
    private static final String smbUrl = "smb://192.168.35.7/C/A.zip";
    private static final String username = "vagrant";
    private static final String password = "vagrant";

    private static void clean() throws IOException {
        Path filePath = Paths.get(outputFileName);
        Files.deleteIfExists(filePath);
    }

    private static void download() throws IOException {
        InputStream is = null;
        OutputStream os = null;

        try {
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("?", username, password);
            SmbFile file = new SmbFile(smbUrl, auth);
            is = file.getInputStream();
            os = new FileOutputStream(outputFileName);
            IOUtils.copyLarge(is, os);
        } catch (Exception e) {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }

    public static void main(String[] args) throws IOException {
        clean();

        long start = System.currentTimeMillis();
        download();
        long end = System.currentTimeMillis();

        System.out.println("File has been downloaded in " + ((end - start) / 1000) + "s");
    }
}
