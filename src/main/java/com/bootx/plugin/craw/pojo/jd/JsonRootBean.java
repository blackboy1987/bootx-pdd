
package com.bootx.plugin.craw.pojo.jd;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRootBean implements Serializable {

    private boolean compatible;
    private Product product;
    public void setCompatible(boolean compatible) {
         this.compatible = compatible;
     }
     public boolean getCompatible() {
         return compatible;
     }
    public void setProduct(Product product) {
         this.product = product;
     }
     public Product getProduct() {
         return product;
     }

}