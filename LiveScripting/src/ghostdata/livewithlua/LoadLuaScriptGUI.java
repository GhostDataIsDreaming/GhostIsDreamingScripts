package ghostdata.livewithlua;

import ghostdata.livewithlua.environment.script.LiveScript;
import org.dreambot.api.methods.MethodProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LoadLuaScriptGUI {
    public JPanel rootPanel;
    private JButton loadScriptFromFileButton;
    private JButton newEmptyScriptButton;
    protected JButton loadSelectedSampleButton;
    public JList<String> scriptSamples;

    public LoadLuaScriptGUI() {
        LiveScriptingWithLuaV2.instance().sampleLoader.updateSampleList(scriptSamples);

        loadScriptFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LiveScriptingWithLuaV2.instance().currentScript = LiveScript.loadFileWithFileChooser();
                    LiveScriptingWithLuaV2.instance()._loadFrame.setVisible(false);

                    LiveScriptingWithLuaV2.instance().luaScriptEditor.luaEditorTextPane.setText(LiveScriptingWithLuaV2.instance().currentScript.getLinesAsString());
                    LiveScriptingWithLuaV2.instance()._editorFrame.setVisible(true);

                    LiveScriptingWithLuaV2.instance().ready = true;
                } catch (IOException ex) {
                    MethodProvider.logError(ex);
                    throw new RuntimeException(ex);
                }
            }
        });

        newEmptyScriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiveScriptingWithLuaV2.instance()._editorFrame.setVisible(true);
                LiveScriptingWithLuaV2.instance()._loadFrame.setVisible(false);

                LiveScriptingWithLuaV2.instance().currentScript = new LiveScript();
                LiveScriptingWithLuaV2.instance().currentScript.load();
                LiveScriptingWithLuaV2.instance().currentScript.setEdited(true);
                LiveScriptingWithLuaV2.instance().luaScriptEditor.luaEditorTextPane.setText(LiveScriptingWithLuaV2.instance().currentScript.getLinesAsString());
                LiveScriptingWithLuaV2.instance().ready = true;
            }
        });
        loadSelectedSampleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String selected = scriptSamples.getSelectedValue();

                LiveScriptingWithLuaV2.instance().sampleLoader.selectSample(selected);
            }
        });
    }
}
