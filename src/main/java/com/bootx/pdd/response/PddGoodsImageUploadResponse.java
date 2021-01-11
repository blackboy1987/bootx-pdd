package com.bootx.pdd.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PddGoodsImageUploadResponse extends PopBaseHttpResponse {

    /**
     * 商品图片上传响应对象
     */
    @JsonProperty("goods_image_upload_response")
    private GoodsImageUploadResponse goodsImageUploadResponse;

    public GoodsImageUploadResponse getGoodsImageUploadResponse() {
        return goodsImageUploadResponse;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GoodsImageUploadResponse {

        /**
         * 返回图片url
         */
        @JsonProperty("image_url")
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }
    }
}