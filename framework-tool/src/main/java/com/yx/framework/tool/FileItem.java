package com.yx.framework.tool;

import java.io.*;

/**
 * <p>文件包装类 ， 支持本地文件 、 字节数组和输入流三种方式 。</p>
 *
 * @author 王涛
 * @date 2019-11-26 09:50:25
 */
public class FileItem {

    private Contract contract;

    /**
     * 基于本地文件的构造器，适用于上传本地文件。
     *
     * @param file 本地文件
     */
    public FileItem(final File file) {
        this.contract = new LocalContract(file);
    }

    /**
     * 基于文件绝对路径的构造器，适用于上传本地文件。
     *
     * @param filePath 文件绝对路径
     */
    public FileItem(String filePath) {
        this(new File(filePath));
    }

    /**
     * 基于文件名和字节数组的构造器。
     *
     * @param fileName 文件名
     * @param content  文件字节数组
     */
    public FileItem(String fileName, byte[] content) {
        this(fileName, content, null);
    }

    /**
     * 基于文件名、字节数组和媒体类型的构造器。
     *
     * @param fileName 文件名
     * @param content  文件字节数组
     * @param mimeType 媒体类型，如：image/jpeg, text/plain
     */
    public FileItem(String fileName, byte[] content, String mimeType) {
        this.contract = new ByteArrayContract(fileName, content, mimeType);
    }

    /**
     * 基于文件名和字节流的构造器，适应于全流式上传，减少本地内存开销。
     *
     * @param fileName 文件名
     * @param stream   文件字节流
     */
    public FileItem(String fileName, InputStream stream) {
        this(fileName, stream, null);
    }

    /**
     * 基于文件名、字节流和媒体类型的构造器，适应于全流式上传，减少本地内存开销。
     *
     * @param fileName 文件名
     * @param stream   文件字节流
     * @param mimeType 媒体类型，如：image/jpeg, text/plain
     */
    public FileItem(String fileName, InputStream stream, String mimeType) {
        this.contract = new StreamContract(fileName, stream, mimeType);
    }

    public boolean isValid() {
        return this.contract.isValid();
    }

    public String getFileName() {
        return this.contract.getFileName();
    }

    public String getMimeType() throws IOException {
        return this.contract.getMimeType();
    }

    public long getFileLength() {
        return this.contract.getFileLength();
    }

    public void write(OutputStream output) throws IOException {
        this.contract.write(output);
    }

    private interface Contract {

        /**
         * 是否验证
         *
         * @return
         */
        boolean isValid();

        /**
         * 获取文件名
         *
         * @return
         */
        String getFileName();

        /**
         * 获取Mime类型
         *
         * @return
         */
        String getMimeType();

        /**
         * 获取文件长度
         *
         * @return
         */
        long getFileLength();

        /**
         * 写入流
         *
         * @param output
         * @throws IOException
         */
        void write(OutputStream output) throws IOException;
    }

    private static class LocalContract implements Contract {
        private File file;

        public LocalContract(File file) {
            this.file = file;
        }

        @Override
        public boolean isValid() {
            return this.file != null && this.file.exists() && this.file.isFile();
        }

        @Override
        public String getFileName() {
            return this.file.getName();
        }

        @Override
        public String getMimeType() {
            return "application/octet-stream";
        }

        @Override
        public long getFileLength() {
            return this.file.length();
        }

        @Override
        public void write(OutputStream output) throws IOException {
            InputStream input = null;
            try {
                input = new FileInputStream(this.file);
                byte[] buffer = new byte[1024 * 4];
                int n;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                }
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        }
    }

    private static class ByteArrayContract implements Contract {
        private String fileName;
        private byte[] content;
        private String mimeType;

        public ByteArrayContract(String fileName, byte[] content, String mimeType) {
            this.fileName = fileName;
            this.content = content;
            this.mimeType = mimeType;
        }

        @Override
        public boolean isValid() {
            return this.content != null && this.fileName != null;
        }

        @Override
        public String getFileName() {
            return this.fileName;
        }

        @Override
        public String getMimeType() {
            if (this.mimeType == null) {
                return "application/octet-stream";
            } else {
                return this.mimeType;
            }
        }

        @Override
        public long getFileLength() {
            return this.content.length;
        }

        @Override
        public void write(OutputStream output) throws IOException {
            output.write(this.content);
        }
    }

    private static class StreamContract implements Contract {
        private String fileName;
        private InputStream stream;
        private String mimeType;

        public StreamContract(String fileName, InputStream stream, String mimeType) {
            this.fileName = fileName;
            this.stream = stream;
            this.mimeType = mimeType;
        }

        @Override
        public boolean isValid() {
            return this.stream != null && this.fileName != null;
        }

        @Override
        public String getFileName() {
            return this.fileName;
        }

        @Override
        public String getMimeType() {
            if (this.mimeType == null) {
                return "application/octet-stream";
            } else {
                return this.mimeType;
            }
        }

        @Override
        public long getFileLength() {
            return 0L;
        }

        @Override
        public void write(OutputStream output) throws IOException {
            try {
                byte[] buffer = new byte[1024 * 4];
                int n;
                while (-1 != (n = stream.read(buffer))) {
                    output.write(buffer, 0, n);
                }
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }
    }

}
