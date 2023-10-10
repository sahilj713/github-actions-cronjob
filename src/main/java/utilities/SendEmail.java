package utilities;

import org.apache.commons.io.FileUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SendEmail {

    private static final int BUFFER_SIZE = 4096;

    public static void sendEmail(int[] testResult) {

        String to = "mehul.halari@intelera.tech";
        final String user = "inteleraautomation@gmail.com";
        final String password = "gxuulnkcchvlcjve";

        Properties props = System.getProperties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {
            Format dateFormat = new SimpleDateFormat("MM-dd-yy");
            String executionReportPath = System.getProperty("user.dir") + "//reports//reports_ " + dateFormat.format(new Date()) + "//test-output";
            String zipFile = SendEmail.createZipFile(executionReportPath);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            int pass = testResult[0];
            int fail = testResult[1];
            int skip = testResult[2];
            int total = pass + fail + skip;
            int passrate = (pass * 100) / (pass + fail + skip);
            message.setSubject("(" + ConfigReader.getConfigPropertyVal("app_type") + ") Test Execution Result - Pass rate: " + passrate + "%");

            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setContent("<html>  \n" +
                    "<head>\n" +
                    "<style>  \n" +
                    "table, th, td {  \n" +
                    "  font-family: arial, sans-serif;\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}  \n" +
                    "</style>  \n" +
                    "</head>\n" +
                    "<body>  \n" +
                    "<p>Hello, <br><br> Test automation execution has been completed!</p>\n" +
                    "<h4>Test Execution Summary:</h4>\n" +
                    "<table>  \n" +
                    "<tr><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Skipped</th></tr>  \n" +
                    "<tr><td>" + total + "</td><td>" + pass + "</td><td>" + fail + "</td><td>" + skip + "</td></tr>  \n" +
                    "</table>  \n" +
                    "<p>Please find an attachment for detailed report.<br><br>Thanks,<br>QA Team</p>\n" +
                    "</body>\n" +
                    "</html> ", "text/html");

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            DataSource source = new FileDataSource(zipFile);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(zipFile);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);

            Transport.send(message);
            SendEmail.deleteZip(zipFile);
            System.out.println("------Test execution result sent to " + to + "------");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String createZipFile(String reportPath) throws Exception {
        String zipFileName = reportPath + ".zip";
        Path sourceFolderPath = Paths.get(reportPath);
        Path zipPath = Paths.get(zipFileName);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                Files.copy(file, zos);
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        zos.close();
        return zipFileName;
    }

    private static void deleteZip(String filePath) throws IOException {
        try {
            FileUtils.forceDelete(new File(filePath));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
