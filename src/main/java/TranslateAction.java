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
                settings.fileDirectory = project.getBasePath() + "/languages/";
            }

            String zhFilePath = settings.fileDirectory + "zh.json";
            String enFilePath = settings.fileDirectory + "en.json";
            String koFilePath = settings.fileDirectory + "ko.json";
            String viFilePath = settings.fileDirectory + "vi.json";
            String key = JSONUtil.findKeyInJSONFile(zhFilePath, selectedText);
            if (key != null) {
                int start = model.getSelectionStart();
                int end = model.getSelectionEnd();
                WriteCommandAction.runWriteCommandAction(project, () ->
                        document.replaceString(start, end, String.format(settings.replacementText, key))
                );
                model.removeSelection();
            } else {
                try {
                    TranslateUtil translateUtil = new TranslateUtil();
                    String enRes = translateUtil.toEnglish(selectedText);
                    String koRes = translateUtil.toKoren(selectedText);
                    String viRes = translateUtil.toVietnamese(selectedText);
                    JSONUtil.writeInJSONFile(zhFilePath, selectedText, selectedText);
                    JSONUtil.writeInJSONFile(koFilePath, selectedText, koRes);
                    JSONUtil.writeInJSONFile(enFilePath, selectedText, enRes);
                    JSONUtil.writeInJSONFile(viFilePath, selectedText, viRes);
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
