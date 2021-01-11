package com.bootx.pdd.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PopBaseHttpResponse {

    @JsonProperty("error_response")
    private ErrorResponse errorResponse;

    @JsonProperty("request_id")
    private String requestId;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorResponse {

        @JsonProperty("request_id")
        private String requestId;

        @JsonProperty("error_code")
        private Integer errorCode;

        @JsonProperty("error_msg")
        private String errorMsg;

        @JsonProperty("sub_code")
        private String subCode;

        @JsonProperty("sub_msg")
        private String subMsg;

        public String getRequestId() {
            return requestId;
        }

        public Integer getErrorCode() {
            return errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public String getSubCode() {
            return subCode;
        }

        public String getSubMsg() {
            return subMsg;
        }
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
