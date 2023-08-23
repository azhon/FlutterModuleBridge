package com.azhon.bridge.core;

import com.azhon.bridge.bean.BridgeBean;
import com.azhon.bridge.util.FileUtil;
import com.azhon.bridge.util.NotificationUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * createDate: 2022/11/16 on 16:06
 * desc:
 *
 * @author azhon
 */


class ModuleBridgeAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        if (project == null) return;
        Collection<VirtualFile> bridgeFile = FileUtil.getBridgeDartFile(project);
        if (bridgeFile == null || bridgeFile.isEmpty()) {
            //没有需要生成的类
            NotificationUtil.showNotify(project, "No dart file need to be generate！");
            return;
        }
        final List<BridgeBean> bridgeList = new ArrayList<>();
        for (VirtualFile file : bridgeFile) {
            bridgeList.add(GenerateBridgeParser.parserFile(project, file));
        }
        GenerateBridgeWriter.writerFile(project, bridgeList);
        NotificationUtil.showNotify(project, "Generate Bridge Successful！");
    }
}
