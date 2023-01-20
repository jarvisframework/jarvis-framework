package com.yx.framework.tool.util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.transform.TransformerConfigurationException;
import java.io.*;
import java.util.*;


/**
 * <p>ftp工具类</p>
 *
 * @author 刘恩江
 * @date 2022-03-18 12:08:42
 */
public class FtpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtils.class);
    private FTPClient ftp = new FTPClient();

    public ArrayList<String> arFiles;
    protected String farServerUrl;
    protected Integer serverPort;
    protected String serverUser;
    protected String serverPwd;
    protected String path;


    public FtpUtils(String farServerUrl, Integer serverPort, String serverUser, String serverPwd, String path) {
        this.farServerUrl = farServerUrl;
        this.serverPort = serverPort;
        this.serverUser = serverUser;
        this.serverPwd = serverPwd;
        this.path = path;
    }

    /**
     * 登陆FTP服务器
     *
     * @return 是否登录成功
     * @throws IOException
     */
    public boolean login() throws IOException {
        ftp.connect(farServerUrl, serverPort);
        if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            if (ftp.login(serverUser, serverPwd)) {
                ftp.setControlEncoding("GBK");
                return true;
            }
        }
        if (ftp.isConnected()) {
            ftp.disconnect();
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public boolean deleteFile(String fileName) throws IOException {
        login();
        boolean flag = false;
        try {
            ftp.dele(path + fileName);
            flag = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            flag = false;
        }

        return flag;
    }

    /**
     * 关闭数据链接
     *
     * @throws IOException
     */
    public void disConnection() throws IOException {
        if (this.ftp.isConnected()) {
            this.ftp.disconnect();
        }
    }

    /**
     * 递归遍历出目录下面所有文件
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @throws IOException
     */
    public String List(String pathName) throws IOException {
        StringBuffer filename = new StringBuffer();
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            String directory = pathName;
            //更换目录到当前目录
            ftp.changeWorkingDirectory(directory);
            ftp.enterLocalPassiveMode();
            FTPFile[] files = ftp.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        String n = new String(files[i].getName().getBytes("gbk"), "utf-8");
                        if (i == files.length - 1) {
                            filename.append(n + ",");
                        } else {
                            filename.append(n + ",");
                        }
                    }
                }
            }
        }
        return filename.toString();
    }

    /**
     * 获取指定文件夹内的文件名称
     *
     * @return
     */
    public String getFilenames() throws IOException {
        String names = "";
        try {
            names = List(path);
            disConnection();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                disConnection();
            } catch (IOException e) {
                disConnection();
                LOGGER.error(e.getMessage(), e);
            }
        }
        return names;
    }

    /**
     * 下载文件流转换二维数据表格
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public List<List<String>> download(String fileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        List<List<String>> table = new ArrayList<List<String>>();
        LOGGER.info("开始读取文件！");
        try {
            ftpClient.connect(farServerUrl, serverPort);
            LOGGER.info(path+fileName);
            ftpClient.login(serverUser, serverPwd);
            fileName = path + fileName;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ftpClient.setBufferSize(1024 * 1024);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(fileName, baos);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(baos.toByteArray());
            BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream));
            String lines = "";
            while (true) {
                LOGGER.info("读取一行！");
                lines = reader.readLine();
                lines = reader.readLine();

                LOGGER.info("LINES"+lines);
                if (StringUtils.isBlank(lines)) {
                    break;
                }
                List<String> t = Arrays.asList(lines.split("\t"));
                table.add(t);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            ftpClient.disconnect();
        }
        ftpClient.disconnect();
        return table;

    }

    /**
     * 上传FTP文件table
     *
     * @param table
     * @param fileName
     * @throws IOException
     */
    public boolean storeFtp(List<List<String>> table, String fileName) throws IOException {
        File file = createText(table, fileName);
        InputStream local = null;
        try {
            ftp.connect(farServerUrl, serverPort);
            ftp.login(serverUser, serverPwd);

            boolean flag = ftp.changeWorkingDirectory(path);
            if (!flag) {
                ftp.makeDirectory(path);
            }
            File a = new File(path + fileName);
            if (a.exists()) {
                deleteFile(fileName);
            }
            //设置字符集
            ftp.setControlEncoding("UTF-8");
            //启动被动模式
            ftp.enterLocalPassiveMode();
            //指定上传路径
            ftp.changeWorkingDirectory(path);
            //指定文件类型
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //读取本地文件
            local = new FileInputStream(file);
            boolean res = ftp.storeFile(fileName, local);
            disConnection();
            return res;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            disConnection();
            return false;
        }

    }

    /**
     * 上传FTP文件xml
     * @param xmlStr
     * @param fileName
     * @return
     * @throws IOException
     */
    public boolean storeFtpXml(String xmlStr, String fileName) throws IOException, DocumentException, TransformerConfigurationException {
        File file = createXml(xmlStr, fileName);
        InputStream local = null;
        try {
            ftp.connect(farServerUrl, serverPort);
            ftp.login(serverUser, serverPwd);

            boolean flag = ftp.changeWorkingDirectory(path);
            if (!flag) {
                ftp.makeDirectory(path);

            }
            File a = new File(path +"/"+ fileName);
            if (a.exists()) {
                deleteFile(fileName);
            }
            //设置字符集
            ftp.setControlEncoding("UTF-8");
            //启动被动模式
            ftp.enterLocalPassiveMode();
            //指定上传路径
            ftp.changeWorkingDirectory(path);
            //指定文件类型
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //读取本地文件
            local = new FileInputStream(file);
            boolean res = ftp.storeFile(fileName, local);
            disConnection();
            local.close();
            file.delete();
            return res;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            disConnection();
            local.close();
            file.delete();
            return false;
        }

    }

    /**
     * 创建文件并返回
     *
     * @param table
     * @param fileName
     * @return
     */
    public File createText(List<List<String>> table, String fileName) throws IOException {
        String pat = System.getProperty("user.dir");
        String jPath = String.format("%s\\static\\pouSheng", pat);
        jPath = String.format("%s\\%s", jPath, path);
        jPath = String.format("%s\\%s", jPath, fileName);
        File file = new File(jPath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            return null;
        }
        //创建文件流，写入文件
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, false);
            for (List<String> line : table) {
                StringBuilder sb = new StringBuilder();
                Integer count = line.size();
                for (int i = 0; i < count; i++) {
                    if (i + 1 < count) {
                        sb.append(line.get(i));
                        sb.append("\t");
                    } else {
                        sb.append(line.get(i));
                    }
                }
                fw.write(sb.toString() + "\n");
            }
            table.clear();
            fw.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return file;
    }

    public static File createXml(String xmlStr,String fileName) throws DocumentException, TransformerConfigurationException {
        File file = null;
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            // 5、设置生成xml的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");


            // 6、生成xml文件
            file = new File(fileName);
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("生成rss.xml失败"+fileName);
        }
        return file;
    }

}
