
package com.bootx.plugin.craw.pojo.tMall;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuList implements Serializable {

    private String names;
    private String pvs;
    private String skuId;

    public void setNames(String names) {
         this.names = names;
     }
     public String getNames() {
         return names;
     }

    public void setPvs(String pvs) {
         this.pvs = pvs;
     }
     public String getPvs() {
         return pvs;
     }

    public void setSkuId(String skuId) {
         this.skuId = skuId;
     }
     public String getSkuId() {
         return skuId;
     }

}