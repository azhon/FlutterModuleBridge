package com.azhon.bridge.util;

import com.azhon.bridge.common.Constants;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * createDate: 2022/11/16 on 16:22
 * desc:
 *
 * @author azhon
 */


public class FileUtil {

    /**
     * 获取项目[/lib]目录下with Bridge 的所有dart类
     */
    public static Collection<VirtualFile> getBridgeDartFile(Project project) {
        PsiManager psiManager = PsiManager.getInstance(project);
        VirtualFile libFile = project.getBaseDir().findChild(Constants.ROOT_DIR);
        if (libFile == null || !libFile.exists()) {
            NotificationUtil.showNotify("Error: [lib] directory not found!");
            return null;
        }
        Collection<VirtualFile> files = new ArrayList<>();
        recursiveGetAllFiles(files, libFile);
        return files.stream().filter(file -> {
            PsiFile psiFile = psiManager.findFile(file);
            ///必须是dart文件
            boolean isDartFile = file.getName().endsWith(Constants.DART_FILE_TYPE);
            return isDartFile && psiFile.getText().contains(Constants.WITH_CLASS_NAME);
        }).collect(Collectors.toList());
    }

    /**
     * 递归获取文件
     */
    private static void recursiveGetAllFiles(Collection<VirtualFile> list, VirtualFile file) {
        VirtualFile[] children = file.getChildren();
        for (VirtualFile child : children) {
            if (child.isDirectory()) {
                recursiveGetAllFiles(list, child);
            } else {
                list.add(child);
            }
        }
    }

    /**
     * 获取文件导报路径
     * eg: ~/module_bridge_example/module/module_a/lib/main.dart
     *
     * @return /main.dart
     */
    public static String getImportFilePath(VirtualFile file) {
        String path = file.getPath();
        return path.substring(path.indexOf(Constants.ROOT_DIR) + Constants.ROOT_DIR.length());
    }

    /**
     * 获取模块名称
     */
    public static String getModuleName(Project project) {
        try {
            Yaml yaml = new Yaml();
            FileInputStream inputStream = new FileInputStream(project.getBasePath() + File.separator + Constants.PUBSPEC_FILE);
            Map<String, Object> map = yaml.load(inputStream);
            return map.get("name").toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
