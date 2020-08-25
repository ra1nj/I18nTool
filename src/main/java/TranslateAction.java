import appSetting.AppSettingsState;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.http.util.TextUtils;
import util.JSONUtil;
import util.TranslateUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class TranslateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        final Editor mEditor = e.getData(PlatformDataKeys.EDITOR);
        if (null == mEditor) {
            return;
        }
        SelectionModel model = mEditor.getSelectionModel();
        final String selectedText = model.getSelectedText().replace("\'", "").replace("\"", "");
        if (TextUtils.isEmpty(selectedText)) {
            return;
        } else {
            final Document document = mEditor.getDocument();
            final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
            AppSettingsState settings = AppSettingsState.getInstance();
            if (settings.fileDirectory == null || settings.fileDirectory == "") {
                settings.fileDirectory = project.getBasePath() + "/languages";
            }
            String sourceFilePath = settings.fileDirectory + "/"+settings.sourceLanguage+".json";
            String key = JSONUtil.findKeyInJSONFile(sourceFilePath, selectedText);
            if (key != null) {
                int start = model.getSelectionStart();
                int end = model.getSelectionEnd();
                WriteCommandAction.runWriteCommandAction(project, () ->
                        document.replaceString(start, end, String.format(settings.replacementText, key))
                );
                model.removeSelection();
            } else {
                try {
                    JSONUtil.writeInJSONFile(sourceFilePath, selectedText, selectedText);
                    TranslateUtil translateUtil = new TranslateUtil();
                    for(int i=0;i<settings.targetLanguages.size();i++){
                        String langCode = settings.targetLanguages.get(i);
                        String targetFilePath = settings.fileDirectory + "/"+langCode+".json";
                        String transRes = translateUtil.translate(selectedText,langCode);
                        JSONUtil.writeInJSONFile(targetFilePath, selectedText, transRes);
                    }
                    int start = model.getSelectionStart();
                    int end = model.getSelectionEnd();
                    WriteCommandAction.runWriteCommandAction(project, () ->
                            document.replaceString(start, end, String.format(settings.replacementText, selectedText))
                    );
                    model.removeSelection();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }


        }
    }

}
