package hello.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

//它可以把同类的配置信息自动封装成实体类
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
