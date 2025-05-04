package model.other;

public enum ContentType {

    // 文本类型
    TEXT_PLAIN("text/plain; charset=utf-8"),
    TEXT_HTML("text/html; charset=utf-8"),
    TEXT_CSS("text/css"),
    TEXT_JAVASCRIPT("text/javascript"),

    // 应用类型
    APPLICATION_JSON("application/json; charset=utf-8"),
    APPLICATION_XML("application/xml"),
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_ZIP("application/zip"),
    APPLICATION_JAVASCRIPT("application/javascript"),

    // 多部分数据
    MULTIPART_FORM_DATA("multipart/form-data"),

    // 图片类型
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_GIF("image/gif"),
    IMAGE_WEBP("image/webp"),
    IMAGE_SVG_XML("image/svg+xml"),

    // 音频与视频
    AUDIO_MPEG("audio/mpeg"),
    VIDEO_MP4("video/mp4"),

    // 字体
    FONT_WOFF("font/woff"),
    FONT_WOFF2("font/woff2"),

    // 自定义或未知类型
    UNKNOWN("application/octet-stream");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ContentType fromValue(String value) {
        for (ContentType ct : values()) {
            if (ct.value.equalsIgnoreCase(value)) {
                return ct;
            }
        }
        return UNKNOWN;
    }
}
