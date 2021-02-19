package com.bootx.util.pdd;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PddException extends Exception {

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

    public PddException(Exception e){
        super(e.getMessage());
        this.errorCode = 500;
        this.errorMsg = e.getMessage();
        this.subCode = "500";
        this.subMsg = e.getMessage();
    }

    public PddException(String errorMsg){
        super();
        this.errorCode = 500;
        this.errorMsg = errorMsg;
        this.subCode = "500";
        this.subMsg = errorMsg;
    }


    public PddException(PopBaseHttpResponse.ErrorResponse errorResponse){
        super(errorResponse.getErrorMsg());
        this.errorCode = errorResponse.getErrorCode();
        this.requestId = errorResponse.getRequestId();
        this.errorMsg = errorResponse.getErrorMsg();
        this.subCode = errorResponse.getSubCode();
        this.subMsg = errorResponse.getSubMsg();
    }

    public static PddException toException(Exception e){
        return new PddException(e);

    }

    public static PddException toException(PopBaseHttpResponse.ErrorResponse errorResponse){
        return new PddException(errorResponse);
    }



    public static PddException toException(String errorMsg){
        return new PddException(errorMsg);

    }
}
