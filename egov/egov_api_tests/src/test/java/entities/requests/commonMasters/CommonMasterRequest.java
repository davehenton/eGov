package entities.requests.commonMasters;

import org.codehaus.jackson.annotate.JsonProperty;

public class CommonMasterRequest {

    @JsonProperty("RequestInfo1")
    private RequestInfo RequestInfo;

    public RequestInfo getRequestInfo() {
        return RequestInfo;
    }

    public void setRequestInfo(RequestInfo RequestInfo) {
        this.RequestInfo = RequestInfo;
    }
}
