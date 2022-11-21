package com.azhon.bridge.core;

import com.azhon.bridge.bean.BridgeBean;
import com.azhon.bridge.common.Constants;
import com.azhon.bridge.util.FileUtil;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.util.ThrowableRunnable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * createDate: 2022/11/16 on 21:59
 * desc:
 *
 * @author azhon
 */


public class GenerateBridgeWriter {
    /**
     * 根据模板生成辅助类
     */
    public static void writerFile(Project project, List<BridgeBean> list) {
        VirtualFile dirFile = createDir(project);
        if (dirFile == null) return;
        try {
            String moduleName = FileUtil.getModuleName(project);
            //生成代码
            String code = CreateDartCode.createCode(moduleName, list);
            WriteAction.run((ThrowableRunnable<Throwable>) () -> creteBridgeFile(project, dirFile, moduleName, code));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建辅助类
     */
    private static void creteBridgeFile(Project project, VirtualFile dirFile, String moduleName, String code) {
        PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(dirFile);
        if (psiDirectory == null) return;
        PsiFileFactory factory = PsiFileFactory.getInstance(project);
        String fileName = moduleName + Constants.GENERATED_FILE_NAME;
        PsiFile psiFile = psiDirectory.findFile(fileName);
        if (psiFile != null) {
            try {
                OutputStream out = psiFile.getVirtualFile().getOutputStream(dirFile);
                out.write(code.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            psiDirectory.add(factory.createFileFromText(fileName, getDartFileType(), code));
        }
    }

    /**
     * 创建 lib/generated/bridge 文件夹
     */
    private static VirtualFile createDir(Project project) {
        VirtualFile libFile = ProjectUtil.guessProjectDir(project).findChild(Constants.ROOT_DIR);
        if (libFile == null) return null;
        try {
            WriteAction.run((ThrowableRunnable<Throwable>) () -> {
                VirtualFile generated = libFile.findChild(Constants.GENERATED_DIR);
                if (generated == null) {
                    generated = libFile.createChildDirectory(project, Constants.GENERATED_DIR);
                }
                VirtualFile bridge = generated.findChild(Constants.BRIDGE_DIR);
                if (bridge == null) {
                    generated.createChildDirectory(project, Constants.BRIDGE_DIR);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return libFile.findFileByRelativePath(Constants.GENERATED_DIR + File.separator + Constants.BRIDGE_DIR);
    }

    /**
     * 反射获取dart file type
     */
    private static FileType getDartFileType() {
        try {
            Class<?> cls = Class.forName("com.jetbrains.lang.dart.DartFileType");
            return (FileType) cls.getField("INSTANCE").get(new Object());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PlainTextFileType.INSTANCE;
    }
}
