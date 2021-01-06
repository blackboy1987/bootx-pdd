
package com.bootx.plugin.craw.pojo.jd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageAndVideoJson implements Serializable {
    private String mainVideoId;

    public String getMainVideoId() {
        return mainVideoId;
    }

    public void setMainVideoId(String mainVideoId) {
        this.mainVideoId = mainVideoId;
    }
}