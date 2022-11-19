package com.azhon.bridge.bean;

import java.util.List;

/**
 * createDate: 2022/11/17 on 16:44
 * desc:
 *
 * @author azhon
 */


public class BridgeBean {
    private String bridgeName;
    private String path;
    private List<BridgeInfoBean> bridgeList;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBridgeName() {
        return bridgeName;
    }

    public void setBridgeName(String bridgeName) {
        this.bridgeName = bridgeName;
    }

    public List<BridgeInfoBean> getBridgeList() {
        return bridgeList;
    }

    public void setBridgeList(List<BridgeInfoBean> bridgeList) {
        this.bridgeList = bridgeList;
    }

    public static class BridgeInfoBean {
        private String url;
        private String desc;
        private String methodName;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }
}
