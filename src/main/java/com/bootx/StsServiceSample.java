package com.bootx;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
public final class StsServiceSample {
    public static AssumeRoleResponse.Credentials getStsToken() {
        String endpoint = "sts.aliyuncs.com";
        String AccessKeyId = "LTAI4G2uQ7SLt8VMxQrQdu6S";
        String accessKeySecret = "bVWi6dMGDHXpurmFAvIGdHWAzQwvdm";
        String roleArn = "acs:ram::1012231837312488:role/fileupload";
        String roleSessionName = "ZhiPaiCLoudSts";
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "", "Sts", endpoint);
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", AccessKeyId, accessKeySecret);
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(1000L);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            return response.getCredentials();
        } catch (ClientException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        getStsToken();
    }
}