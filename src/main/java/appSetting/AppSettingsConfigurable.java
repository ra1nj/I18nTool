package appSetting;

// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Provides controller functionality for application settings.
 */
public class AppSettingsConfigurable implements Configurable {
    private AppSettingsComponent mySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "i18n Settings";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modified = !mySettingsComponent.getReplacementText().equals(settings.replacementText);
        modified |= !mySettingsComponent.getDirectoryPath().equals(settings.fileDirectory);
        modified |= !mySettingsComponent.getTargetLanguages().equals(settings.targetLanguages);
        modified |= !mySettingsComponent.getSourceLanguage().equals(settings.sourceLanguage);
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.replacementText = mySettingsComponent.getReplacementText();
        settings.fileDirectory = mySettingsComponent.getDirectoryPath();
        settings.targetLanguages = mySettingsComponent.getTargetLanguages();
        settings.sourceLanguage = mySettingsComponent.getSourceLanguage();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.setReplacementText(settings.replacementText);
        mySettingsComponent.setDirectoryPath(settings.fileDirectory);
        mySettingsComponent.setTargetLanguages(settings.targetLanguages);
        mySettingsComponent.setSourceLanguage(settings.sourceLanguage);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
