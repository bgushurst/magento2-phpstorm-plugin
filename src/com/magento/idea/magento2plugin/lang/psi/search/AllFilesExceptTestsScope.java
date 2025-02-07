/*
 * Copyright © Magento, Inc. All rights reserved.
 * See COPYING.txt for license details.
 */

package com.magento.idea.magento2plugin.lang.psi.search;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.magento.idea.magento2plugin.lang.roots.MagentoTestSourceFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AllFilesExceptTestsScope extends GlobalSearchScope {

    public static final String SCOPE_NAME = "All Files Except Tests";
    private static AllFilesExceptTestsScope instance;
    private final Project project;

    /**
     * Get search scope instance.
     *
     * @param project Project
     *
     * @return AllFilesExceptTestsScope
     */
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    public static synchronized AllFilesExceptTestsScope getInstance(
            final @Nullable Project project
    ) {
        if (instance == null) {
            instance = new AllFilesExceptTestsScope(project);
        }

        return instance;
    }

    /**
     * Magento search scope constructor.
     *
     * @param project Project
     */
    private AllFilesExceptTestsScope(final @Nullable Project project) {
        super(project);
        this.project = project;
    }

    @Override
    public @NotNull String getDisplayName() {
        return SCOPE_NAME;
    }

    @Override
    public boolean contains(final @NotNull VirtualFile file) {
        assert project != null;
        return GlobalSearchScope.allScope(project).contains(file)
                && !(new MagentoTestSourceFilter().isTestSource(file, project));
    }

    @Override
    public boolean isSearchInModuleContent(final @NotNull Module module) {
        return true;
    }

    @Override
    public boolean isSearchInLibraries() {
        return true;
    }
}
