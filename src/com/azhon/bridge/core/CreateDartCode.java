package com.azhon.bridge.core;

import com.azhon.bridge.bean.BridgeBean;
import com.azhon.bridge.common.Constants;
import com.azhon.bridge.util.StringUtil;

import java.util.List;

/**
 * createDate: 2022/11/18 on 15:34
 * desc:
 *
 * @author azhon
 */


public class CreateDartCode {
    /**
     * 生成代码
     */
    public static String createCode(String moduleName, List<BridgeBean> list) {
        String clsName = StringUtil.toCamelCase(moduleName) + Constants.GENERATED_CLS_NAME;
        final StringBuilder codes = new StringBuilder();
        codes.append(createImportCode(moduleName, list));
        codes.append("\n");
        codes.append("class ").append(clsName).append(" {").append("\n");
        codes.append(createBridgeFiledCode(list));
        codes.append("\n");
        codes.append("  final Map<String, Function> _functions = {").append("\n");
        codes.append(createMethodCode(list));
        codes.append("  };").append("\n");
        codes.append("\n");
        codes.append("  Map<String, Function> get bridges => _functions;");
        codes.append("\n");
        codes.append("}");
        return codes.toString();
    }

    /**
     * 导包代码
     */
    private static String createImportCode(String moduleName, List<BridgeBean> list) {
        StringBuilder importCodes = new StringBuilder();
        importCodes.append("///This file is automatically generated by the [FlutterModuleBridge] plugin, your modifications will be lost.").append("\n");
        importCodes.append("\n");
        for (BridgeBean bean : list) {
            importCodes.append("import 'package:").append(moduleName).append(bean.getPath()).append("';").append("\n");
        }
        return importCodes.toString();
    }

    /**
     * 桥变量代码
     */
    private static String createBridgeFiledCode(List<BridgeBean> list) {
        StringBuilder bridgeCodes = new StringBuilder();
        for (BridgeBean bean : list) {
            String filedName = StringUtil.firstLowCase(bean.getBridgeName());
            bridgeCodes.append("  static final ").append(bean.getBridgeName()).append(" ").append(filedName).append(" = ").append(bean.getBridgeName()).append("();").append("\n");
        }
        return bridgeCodes.toString();
    }

    /**
     * 方法代码
     */
    private static String createMethodCode(List<BridgeBean> list) {
        StringBuilder methodCodes = new StringBuilder();
        for (BridgeBean bean : list) {
            List<BridgeBean.BridgeInfoBean> bridgeList = bean.getBridgeList();
            for (BridgeBean.BridgeInfoBean infoBean : bridgeList) {
                String filedName = StringUtil.firstLowCase(bean.getBridgeName());
                methodCodes.append("    ").append(infoBean.getUrl()).append(": ").append(filedName).append(".").append(infoBean.getMethodName()).append(",").append("\n");
            }
        }
        return methodCodes.toString();
    }
}
