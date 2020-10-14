package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.notification.NotificationException;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.externalSystem.model.task.TaskData;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.impl.file.PsiDirectoryImpl;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.intellij.psi.impl.file.PsiPackageImpl;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.ArrayUtil;
import com.kenshoo.pl.intellij.codegen.EntityGeneratorService;
import com.kenshoo.pl.intellij.model.EntityInput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EntityController {

    public static EntityController INSTANCE = new EntityController();

    public void generateEntity(PsiJavaDirectoryImpl directory, EntityInput entityInput) {
        EntityGeneratorService.INSTANCE.generateEntity(directory, entityInput);
    }
}
