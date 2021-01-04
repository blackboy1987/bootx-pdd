
package com.bootx.plugin.craw.pojo.oneSixEightEightPlugin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties
public class SkuProps implements Serializable {

    private String prop;
    private List<Value> value;
    public void setProp(String prop) {
         this.prop = prop;
     }
     public String getProp() {
         return prop;
     }

    public void setValue(List<Value> value) {
         this.value = value;
     }
     public List<Value> getValue() {
         return value;
     }

}