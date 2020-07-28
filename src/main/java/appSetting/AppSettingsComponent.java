package appSetting;

// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.


import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a JPanel for the Settings Dialog.
 */
public class AppSettingsComponent {
    private final JPanel myMainPanel;
    private final JBTextField myReplacementText = new JBTextField();
    private final TextFieldWithBrowseButton myDirectoryPath = new TextFieldWithBrowseButton();
    private final JBList myLanguageList = new JBList();


    private void setFilePathComponent() {
        TextBrowseFolderListener listener = new TextBrowseFolderListener(new FileChooserDescriptor(false, true, false, false, false, false));
        myDirectoryPath.addBrowseFolderListener(listener);
    }

    private void setLanguageListComponent() {
        myLanguageList.setModel(JBList.createDefaultListModel(Language.langMap));
    }


    public AppSettingsComponent() {
        setLanguageListComponent();
        setFilePathComponent();
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter the directory path you want to store,if the path doesn't exist ,we will create one for you: "), myDirectoryPath, 1, true)
                .addLabeledComponent(new JBLabel("You can change the default replacement format for your own: "), myReplacementText, 1, true)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myDirectoryPath;
    }


    @NotNull
    public String getDirectoryPath() {
        return myDirectoryPath.getText();
    }

    public void setDirectoryPath(@NotNull String newText) {
        myDirectoryPath.setText(newText);
    }


    @NotNull
    public String getReplacementText() {
        return myReplacementText.getText();
    }

    public void setReplacementText(@NotNull String newText) {
        myReplacementText.setText(newText);
    }


}
