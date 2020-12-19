
package com.bootx.controller.api.user.demo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonRootBean {

    private int type;
    private String content;

    @JsonProperty("date")
    private List<Data> data;
    private String code;
    private String message;
    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

    public void setDate(List<Data> data) {
         this.data = data;
     }
     public List<Data> getDate() {
         return data;
     }

    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

}