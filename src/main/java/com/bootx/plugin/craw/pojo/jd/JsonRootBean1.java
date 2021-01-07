
package com.bootx.plugin.craw.pojo.jd;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRootBean1 implements Serializable {

    private boolean compatible;
    private Product1 product;
    public void setCompatible(boolean compatible) {
         this.compatible = compatible;
     }
     public boolean getCompatible() {
         return compatible;
     }
    public void setProduct(Product1 product) {
         this.product = product;
     }
     public Product1 getProduct() {
         return product;
     }

}