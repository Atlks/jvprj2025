package util.entty;

public class UploadedFile {
    private String fieldName;         // 表单字段名，例如 "file"
    private String originalFileName;  // 客户端上传的文件原始名称
    private String contentType;       // 文件的 MIME 类型，例如 image/jpeg
    private byte[] content;           // 文件的二进制内容

    public UploadedFile(String fieldName, String originalFileName, String contentType, byte[] content) {
        this.fieldName = fieldName;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.content = content;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
