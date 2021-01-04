
package com.bootx.plugin.craw.pojo.oneSixEightEightPlugin;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
public class Value implements Serializable {

    private String imageUrl;
    private String name;
    public void setImageUrl(String imageUrl) {
         this.imageUrl = imageUrl;
     }
     public String getImageUrl() {
         return imageUrl;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

}