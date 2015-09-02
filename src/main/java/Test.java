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

    private static void clean() throws IOException {
        Path filePath = Paths.get(outputFileName);
        Files.delete(filePath);
    }

    private static void download() throws IOException {
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("?", "vagrant", "vagrant");
        SmbFile file = new SmbFile(smbUrl, auth);
        InputStream is = file.getInputStream();
        OutputStream os = new FileOutputStream(outputFileName);
        IOUtils.copyLarge(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    public static void main(String[] args) throws IOException {
        clean();

        long start = System.currentTimeMillis();
        download();
        long end = System.currentTimeMillis();

        System.out.println("Downloaded in " + (end - start) / 1000 + "s");
    }
}
