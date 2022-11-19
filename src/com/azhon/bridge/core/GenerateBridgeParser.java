package com.azhon.bridge.core;

import com.azhon.bridge.bean.BridgeBean;
import com.azhon.bridge.common.Constants;
import com.azhon.bridge.element.*;
import com.azhon.bridge.util.FileUtil;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import java.util.ArrayList;
import java.util.List;

/**
 * createDate: 2022/11/16 on 21:59
 * desc:
 *
 * @author azhon
 */


public class GenerateBridgeParser {
    /**
     * 解析dart文件
     *
     * @param project
     * @param file    .dart
     */
    public static BridgeBean parserFile(Project project, VirtualFile file) {
        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        PsiElement[] elements = psiFile.getChildren();

        Class<?> dartTypeCls = getDartTypeTokens();
        IElementType classDefinition = new ClassDefinitionElement(dartTypeCls).get();
        final BridgeBean bridge = new BridgeBean();
        //导包
        bridge.setPath(FileUtil.getImportFilePath(file));
        for (PsiElement element : elements) {
            ASTNode node = element.getNode();
            if (node.getElementType() != classDefinition) {
                continue;
            }
            //获取类名
            ASTNode classNameNode = node.getChildren(new ComponentNameElement(dartTypeCls).getTokenSet())[0];
            bridge.setBridgeName(classNameNode.getText());
            //获取（成员、方法）
            ASTNode codeBodyNode = node.getChildren(new ClassBodyElement(dartTypeCls).getTokenSet())[0];
            //获取 成员
            ASTNode membersNode = codeBodyNode.getChildren(new ClassMembersElement(dartTypeCls).getTokenSet())[0];
            //获取 方法
            ASTNode[] methodNodes = membersNode.getChildren(new MethodDeclarationElement(dartTypeCls).getTokenSet());

            List<BridgeBean.BridgeInfoBean> list = new ArrayList<>();
            for (ASTNode methodNode : methodNodes) {
                BridgeBean.BridgeInfoBean bridgeInfoBean = parseMethod(dartTypeCls, methodNode);
                if (bridgeInfoBean != null) {
                    list.add(bridgeInfoBean);
                }
            }
            bridge.setBridgeList(list);
        }
        return bridge;
    }

    /**
     * 解析method
     */
    private static BridgeBean.BridgeInfoBean parseMethod(Class<?> dartTypeCls, ASTNode methodNode) {
        //获取返回值类型
        ASTNode returnTypeNode = methodNode.getChildren(new ReturnTypeElement(dartTypeCls).getTokenSet())[0];
        if (!(Constants.RETURN_TYPE.equals(returnTypeNode.getText()) ||
                Constants.RETURN_TYPE_FUTURE.equals(returnTypeNode.getText()))) {
            return null;
        }
        BridgeBean.BridgeInfoBean bean = new BridgeBean.BridgeInfoBean();
        //获取方法名
        ASTNode methodNameNode = methodNode.getChildren(new ComponentNameElement(dartTypeCls).getTokenSet())[0];
        bean.setMethodName(methodNameNode.getText());
        //获取注解
        ASTNode[] metaNodes = methodNode.getChildren(new MetaDataElement(dartTypeCls).getTokenSet());
        //此方法没有注解
        if (metaNodes.length == 0) {
            return null;
        }
        ASTNode metaNode = metaNodes[0];
        ASTNode[] annotationNode = metaNode.getChildren(TokenSet.ANY);
        ArgumentsElement argumentsElement = new ArgumentsElement(dartTypeCls);
        StringBuilder sb = new StringBuilder();
        for (ASTNode astNode : annotationNode) {
            //注解配置的具体url，desc
            if (astNode.getElementType() != argumentsElement.get()) {
                sb.append(astNode.getText());
            }
        }
        //方法的注解不是@Url则不处理
        if (!Constants.URL_ANNOTATION.equals(sb.toString())) {
            return null;
        }
        ASTNode astNode = metaNode.getChildren(argumentsElement.getTokenSet())[0];
        parseAnnotationParams(dartTypeCls, astNode, bean);
        return bean;
    }

    /**
     * 解析注解中的url,desc参数
     */
    private static void parseAnnotationParams(Class<?> dartTypeCls, ASTNode annotationNode, BridgeBean.BridgeInfoBean bean) {
        ASTNode argumentsList = annotationNode.getChildren(new ArgumentListElement(dartTypeCls).getTokenSet())[0];
        ASTNode[] argumentsNode = argumentsList.getChildren(new NamedArgumentElement(dartTypeCls).getTokenSet());
        for (ASTNode node : argumentsNode) {
            ASTNode keyNode = node.getChildren(new ParameterNameReferenceExpressionElement(dartTypeCls).getTokenSet())[0];
            ASTNode valueNode = node.getChildren(new StringLiteralExpressionElement(dartTypeCls).getTokenSet())[0];
            if (Constants.KEY_URL.equals(keyNode.getText())) {
                bean.setUrl(valueNode.getText());
            }
            if (Constants.KEY_DESC.equals(keyNode.getText())) {
                bean.setDesc(valueNode.getText());
            }
        }
    }

    /**
     * 反射获取dart相关类
     */
    private static Class<?> getDartTypeTokens() {
        try {
            return Class.forName("com.jetbrains.lang.dart.DartTokenTypes");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
