package appSetting;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageSettingListComponent {
    private final List<LanguageVO> dataList = new Language().langList;
    private final JPanel myMainPanel;
    private JBList targetLangList;
    private JBList sourceLangList;
    private JBLabel targetLang = new JBLabel();
    private JBLabel sourceLang = new JBLabel();


    public LanguageSettingListComponent() {
        targetLangList = new JBList(dataList);
        targetLangList.setCellRenderer(new MyCellRenderer());
        targetLangList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ListSelectionListener listener = e -> setTargetLang();
        targetLangList.addListSelectionListener(listener);
        JScrollPane targetLangPane =  new JBScrollPane(targetLangList);
        sourceLangList = new JBList(dataList);
        sourceLangList.setCellRenderer(new MyCellRenderer());
        sourceLangList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionListener sourceListListener = e -> setSourceLang();
        sourceLangList.addListSelectionListener(sourceListListener);
        JScrollPane sourceLangPane =  new JBScrollPane(sourceLangList);
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(targetLang,targetLangPane,1,true)
                .addLabeledComponent(sourceLang,sourceLangPane,1,true)
                .getPanel();

    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    private void setTargetLang(){
        List<String> codes = getSelectedTargetLangCodes();
        String text = "Selected Target Languages: ";
        for(String code:codes){
            text += code+"、";
        }
        targetLang.setText(text);
    }

    private void setSourceLang(){
        String text = "Selected Source Language: ";
        text += getSelectedSourceLangCode();
        sourceLang.setText(text);
    }



    public String getSelectedSourceLangCode(){
        if(sourceLangList.getSelectedValue() != null){
            return ((LanguageVO) sourceLangList.getSelectedValue()).code;
        }else {
            return "";
        }
    }

    public List<String> getSelectedTargetLangCodes(){
        List<LanguageVO> list = targetLangList.getSelectedValuesList();
        List<String> res = new ArrayList();
        for(LanguageVO obj:list){
            res.add(obj.code);
        }
        return res;
    }

    public void setSelectedSourceCode(String code){
        int index = 0;
        for(int i =0;i<dataList.size();i++){
            if(dataList.get(i).code == code){
                index = i;
            }
        }
        sourceLangList.setSelectedIndex(index);
    }

    public void setSelectedTargetCodes(List<String> langList){
        int[] indices = new int[langList.size()];
        for (int i=0;i<langList.size();i++){
            for(int j=0;j<dataList.size();j++){
                if(dataList.get(j).code == langList.get(i)){
                    indices[i] = j;
                }
            }
        }
        targetLangList.setSelectedIndices(indices);
    }
}

class MyCellRenderer extends JLabel implements ListCellRenderer<LanguageVO> {

    // This is the only method defined by ListCellRenderer.
    // We just reconfigure the JLabel each time we're called.

    public Component getListCellRendererComponent(
            JList<? extends  LanguageVO> list,           // the list
            LanguageVO value,            // value to display
            int index,               // cell index
            boolean isSelected,      // is the cell selected
            boolean cellHasFocus)    // does the cell have focus
    {
        String s = String.format("%s(%s)",value.value,value.code);
        setText(s);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
