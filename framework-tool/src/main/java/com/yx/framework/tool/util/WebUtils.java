package com.yx.framework.tool.util;

import com.jarvis.framework.core.constant.CharsetConstants;
import com.yx.framework.tool.FileItem;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;


/**
 * <p>Http工具类</p>
 *
 * @author 王涛
 * @date 2019-11-25 18:08:42
 */
public abstract class WebUtils {

    /**
     * 默认流式读取缓冲区大小
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * 默认编码字符集
     */
    private static final String DEFAULT_CHARSET = CharsetConstants.UTF8;

    /**
     * 忽略SSL检查
     */
    private static boolean ignoreSSLCheck = true;

    /**
     * 忽略HOST检查
     */
    private static boolean ignoreHostCheck = true;

    /**
     * 默认连接超时时间
     */
    private static int DEFAULT_CONNECT_TIMEOUT = 15000;

    /**
     * 默认读取超时时间
     */
    private static int DEFAULT_READ_TIMEOUT = 30000;

    /**
     * 私有化构造器
     */
    private WebUtils() {
    }

    public static void setIgnoreSSLCheck(boolean ignoreSSLCheck) {
        WebUtils.ignoreSSLCheck = ignoreSSLCheck;
    }

    public static void setIgnoreHostCheck(boolean ignoreHostCheck) {
        WebUtils.ignoreHostCheck = ignoreHostCheck;
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, params, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url            请求地址
     * @param params         请求参数
     * @param connectTimeout 连接超时时间
     * @param readTimeout    读取超时时间
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, charset, connectTimeout, readTimeout, null, null);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url
     * @param params
     * @param charset
     * @param connectTimeout
     * @param readTimeout
     * @param headerMap
     * @param proxy
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap, Proxy proxy) throws IOException {
        String contentType = String.format("%s;charset=%s", HttpContentType.APPLICATION_FORM, charset);
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return _doPost(url, contentType, headerMap, content, connectTimeout, readTimeout, proxy);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url
     * @param body
     * @param charset
     * @param connectTimeout
     * @param readTimeout
     * @param headerMap
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String body, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String contentType = String.format("%s;charset=%s", HttpContentType.TEXT_PLAIN, charset);
        byte[] content = body.getBytes(charset);
        return _doPost(url, contentType, headerMap, content, connectTimeout, readTimeout, null);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url         请求地址
     * @param contentType 请求类型
     * @param content     请求字节数组
     * @return 响应字符串
     */
    public static String doPost(String url, String contentType, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        return _doPost(url, contentType, null, content, connectTimeout, readTimeout, null);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url         请求地址
     * @param contentType 请求类型
     * @param content     请求字节数组
     * @param headerMap   请求头部参数
     * @return 响应字符串
     */
    public static String doPost(String url, String contentType, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap, Proxy proxy) throws IOException {
        return _doPost(url, contentType, headerMap, content, connectTimeout, readTimeout, proxy);
    }

    /**
     * 执行XML HTTP POST 请求。
     *
     * @param url
     * @param params
     * @param xmlBody
     * @return
     * @throws IOException
     */
    public static String doPostWithXml(String url, Map<String, String> params, String xmlBody) throws IOException {
        return doPost(url, null, params, xmlBody, HttpContentType.TEXT_XML, DEFAULT_CHARSET);
    }

    /**
     * 执行JSON HTTP POST 请求。
     *
     * @param url
     * @param params
     * @param jsonBody
     * @return
     * @throws IOException
     */
    public static String doPostWithJson(String url, Map<String, String> heardParams, Map<String, String> params, String jsonBody) throws IOException {
        return doPost(url, heardParams, params, jsonBody, HttpContentType.APPLICATION_JSON, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url
     * @param headerMap
     * @param params
     * @param body
     * @param contentType
     * @param charset
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> headerMap, Map<String, String> params, String body, String contentType, String charset) throws IOException {
        String queryString = buildQuery(params, charset);
        String fullUrl = buildRequestUrl(url, queryString);
        return _doPost(fullUrl, contentType, headerMap, body.getBytes(charset), DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, null);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url
     * @param contentType
     * @param content
     * @param connectTimeout
     * @param readTimeout
     * @param headerMap
     * @param proxy
     * @return
     * @throws IOException
     */
    private static String _doPost(String url, String contentType, Map<String, String> headerMap, byte[] content, int connectTimeout, int readTimeout, Proxy proxy) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp;
        try {
            conn = getConnection(new URL(url), HttpMethod.POST, contentType, headerMap, proxy);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     *
     * @param url        请求地址
     * @param fileParams 文件请求参数
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, int connectTimeout, int readTimeout) throws IOException {
        if (fileParams == null || fileParams.isEmpty()) {
            return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
        } else {
            return doPost(url, params, fileParams, DEFAULT_CHARSET, connectTimeout, readTimeout);
        }
    }

    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, fileParams, charset, connectTimeout, readTimeout, null);
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     *
     * @param url        请求地址
     * @param fileParams 文件请求参数
     * @param charset    字符集，如UTF-8, GBK, GB2312
     * @param headerMap  需要传递的header头，可以为空
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset,
                                int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        if (fileParams == null || fileParams.isEmpty()) {
            return doPost(url, params, charset, connectTimeout, readTimeout, headerMap, null);
        } else {
            return _doPostWithFile(url, params, fileParams, charset, connectTimeout, readTimeout, headerMap);
        }
    }


    /**
     * 执行请求
     * content_type: application/json
     *
     * @param url
     * @param params
     * @param charset
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static String doPostWithJson(String url, Map<String, Object> params, String charset, int connectTimeout, int readTimeout) throws IOException {
        String contentType = String.format("%s;charset=%s", HttpContentType.APPLICATION_JSON, charset);
        byte[] content = {};

        String body = JacksonUtils.beanToJson(params);
        if (body != null) {
            content = body.getBytes(charset);
        }
        return _doPost(url, contentType, null, content, connectTimeout, readTimeout, null);
    }

    private static String _doPostWithFile(String url, Map<String, String> params, Map<String, FileItem> fileParams,
                                          String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        // 随机分隔线
        String boundary = String.valueOf(System.nanoTime());
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp;
        try {
            String contentType = String.format("%s;charset=%s;boundary=%s", HttpContentType.FORM_DATA, charset, boundary);
            conn = getConnection(new URL(url), HttpMethod.POST, contentType, headerMap, null);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);

            // 组装文本请求参数
            Set<Entry<String, String>> textEntrySet = params.entrySet();
            for (Entry<String, String> textEntry : textEntrySet) {
                byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(), charset);
                out.write(entryBoundaryBytes);
                out.write(textBytes);
            }

            // 组装文件请求参数
            Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
            for (Entry<String, FileItem> fileEntry : fileEntrySet) {
                FileItem fileItem = fileEntry.getValue();
                if (!fileItem.isValid()) {
                    throw new IOException("FileItem is invalid");
                }
                byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
                out.write(entryBoundaryBytes);
                out.write(fileBytes);
                fileItem.write(out);
            }

            // 添加请求结束标志
            byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
            out.write(endBoundaryBytes);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\";filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @return 响应字符串
     */
    public static String doGet(String url) throws IOException {
        return doGet(url, null);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp;
        try {
            String contentType = String.format("Content-Type=%s;charset=%s", HttpContentType.APPLICATION_FORM, charset);
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), HttpMethod.GET, contentType, null, null);
            rsp = getResponseAsString(conn);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String contentType, Map<String, String> headerMap, Proxy proxy) throws IOException {
        HttpURLConnection conn;
        if (proxy == null) {
            conn = (HttpURLConnection) url.openConnection();
        } else {
            conn = (HttpURLConnection) url.openConnection(proxy);
        }
        if (headerMap != null && headerMap.size() > 0) {
            for (Map.Entry<String, String> m : headerMap.entrySet()) {
                conn.setRequestProperty(m.getKey(), m.getValue());
            }
        }
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection connHttps = (HttpsURLConnection) conn;
            if (ignoreSSLCheck) {
                try {
                    SSLContext ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, new TrustManager[]{new TrustAllTrustManager()}, new SecureRandom());
                    connHttps.setSSLSocketFactory(ctx.getSocketFactory());
                    connHttps.setHostnameVerifier((hostname, session) -> true);
                } catch (Exception e) {
                    throw new IOException(e.toString());
                }
            } else {
                if (ignoreHostCheck) {
                    connHttps.setHostnameVerifier((hostname, session) -> true);
                }
            }
            conn = connHttps;
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty(HttpHeader.USER_AGENT, "Lnet");
        conn.setRequestProperty(HttpHeader.CONTENT_TYPE, contentType);
        return conn;
    }

    private static URL buildGetUrl(String url, String query) throws IOException {
        if (StringUtils.isEmpty(query)) {
            return new URL(url);
        }

        return new URL(buildRequestUrl(url, query));
    }

    public static String buildRequestUrl(String url, String... queries) {
        if (queries == null || queries.length == 0) {
            return url;
        }

        StringBuilder newUrl = new StringBuilder(url);
        boolean hasQuery = url.contains("?");
        boolean hasPrepend = url.endsWith("?") || url.endsWith("&");

        for (String query : queries) {
            if (!StringUtils.isEmpty(query)) {
                if (!hasPrepend) {
                    if (hasQuery) {
                        newUrl.append("&");
                    } else {
                        newUrl.append("?");
                        hasQuery = true;
                    }
                }
                newUrl.append(query);
                hasPrepend = false;
            }
        }
        return newUrl.toString();
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return StringUtils.EMPTY;
        }

        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            String contentEncoding = conn.getContentEncoding();
            if (HttpHeader.CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
                return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
            } else {
                return getStreamAsString(conn.getInputStream(), charset);
            }
        } else {
            // OAuth bad request always return 400 status
            if (conn.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                InputStream error = conn.getErrorStream();
                if (error != null) {
                    return getStreamAsString(error, charset);
                }
            }
            // Client Error 4xx and Server Error 5xx
            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
    }

    public static String getRequestBody(HttpServletRequest request) throws IOException {
        return getStreamAsString(request.getInputStream(), getResponseCharset(request.getContentType()));
    }

    public static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[DEFAULT_BUFFER_SIZE];
            int read;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    public static String getResponseCharset(String contentType) {
        String charset = DEFAULT_CHARSET;
        if (!StringUtils.isEmpty(contentType)) {
            String[] params = contentType.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }
        return charset;
    }

    /**
     * 使用默认的UTF-8字符集反编码请求参数值。
     *
     * @param value 参数值
     * @return 反编码后的参数值
     */
    public static String decode(String value) {
        return decode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用默认的UTF-8字符集编码请求参数值。
     *
     * @param value 参数值
     * @return 编码后的参数值
     */
    public static String encode(String value) {
        return encode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用指定的字符集反编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        String[] pairs = query.split("&");
        Map<String, String> result = new HashMap<>(pairs.length);
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }
        return result;
    }

    public static boolean isFormPost(HttpServletRequest request) {
        String contentType = request.getContentType();
        return (contentType != null && contentType.contains(HttpContentType.APPLICATION_FORM) && HttpMethod.POST.equals(request.getMethod()));
    }

    public static String getParameters(HttpServletRequest request) {
        try (InputStream is = new BufferedInputStream(request.getInputStream(), DEFAULT_BUFFER_SIZE)) {
            int contentLength = Integer.valueOf(request.getHeader(HttpHeader.CONTENT_LENGTH));
            byte[] bytes = new byte[contentLength];
            int readCount = 0;
            while (readCount < contentLength) {
                readCount += is.read(bytes, readCount, contentLength - readCount);
            }
            return new String(bytes, DEFAULT_CHARSET);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 获取HTTP ContentType
     *
     * @param request
     * @return
     */
    public static String getContentType(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (!StringUtils.isEmpty(contentType)) {
            return contentType.split(";")[0];
        }
        return null;
    }

    public static class TrustAllTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    public static abstract class HttpMethod {
        public static final String GET = "GET";
        public static final String HEAD = "HEAD";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
        public static final String DELETE = "DELETE";
        public static final String OPTIONS = "OPTIONS";
        public static final String TRACE = "TRACE";
    }

    public static abstract class HttpContentType {

        public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
        public static final String APPLICATION_XML = "application/xml";
        public static final String APPLICATION_JSON = "application/json";
        public static final String APPLICATION_EXCEL = "application/vnd.ms-excel";
        public static final String APPLICATION_HESSIAN = "x-application/hessian";

        public static final String TEXT_PLAIN = "text/plain";
        public static final String TEXT_XML = "text/xml";
        public static final String TEXT_HTML = "text/html";
        public static final String TEXT_JAVASCRIPT = "text/javascript";

        public static final String FORM_DATA = "multipart/form-data";

    }

    public static abstract class HttpHeader {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String AGE = "Age";
        public static final String ALLOW = "Allow";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String CONTENT_DISPOSITION = "Content-Disposition";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String CONTENT_LANGUAGE = "Content-Language";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_LOCATION = "Content-Location";
        public static final String CONTENT_MD5 = "ContentMD5";
        public static final String CONTENT_RANGE = "Content-Range";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String COOKIE = "Cookie";
        public static final String DATE = "Date";
        public static final String ETAG = "ETag";
        public static final String EXPIRES = "Expires";
        public static final String EXPECT = "Expect";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String LAST_MODIFIED = "Last-Modified";
        public static final String LOCATION = "Location";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String RETRY_AFTER = "Retry-After";
        public static final String SERVER = "Server";
        public static final String SET_COOKIE = "Set-Cookie";
        public static final String TE = "TE";
        public static final String TRAILER = "Trailer";
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VARY = "Vary";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";
        public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
        public static final String CONTENT_ENCODING_GZIP = "gzip";
    }

}
