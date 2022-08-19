package ghostdata.livewithlua;

import ghostdata.livewithlua.environment.script.LiveScript;
import org.dreambot.api.methods.MethodProvider;
import org.luaj.vm2.ast.Str;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public interface SampleLoader {

    public void updateSampleList(JList<String> list);

    public void selectSample(String selected);

    class LocalScriptSampleLoader implements SampleLoader {

        @Override
        public void updateSampleList(JList<String> scriptSamples) {
            try {
                List<String> samplesFound = new ArrayList<>();
                CodeSource source = LiveScriptingWithLuaV2.class.getProtectionDomain().getCodeSource();
                if (source != null) {
                    URL jar = source.getLocation();
                    ZipInputStream zip = new ZipInputStream(jar.openStream());
                    ZipEntry entry = null;

                    while ((entry = zip.getNextEntry()) != null) {
                        String name = entry.getName();
                        if (!entry.isDirectory() && name.startsWith("samples/")) {
                            samplesFound.add(entry.getName().substring("samples/".length()));
                        }
                    }
                } else {
                    LiveScriptingWithLuaV2.instance().loadLuaScriptGUI.loadSelectedSampleButton.setEnabled(false);
                    scriptSamples.setListData(new String[] { "No Samples Found" });
                }

                scriptSamples.setListData(samplesFound.toArray(new String[samplesFound.size()]));
            } catch (Exception e) {
                LiveScriptingWithLuaV2.instance().loadLuaScriptGUI.loadSelectedSampleButton.setEnabled(false);
                scriptSamples.setListData(new String[] { "No Samples Found" });
            }
        }

        @Override
        public void selectSample(String selected) {
            try {
                String lines = "";
                InputStream stream = LiveScriptingWithLuaV2.class.getClassLoader().getResourceAsStream("samples/" + selected);

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        lines += line;
                        lines += System.lineSeparator();
                    }
                }

                LiveScriptingWithLuaV2.instance().currentScript = new LiveScript();
                LiveScriptingWithLuaV2.instance().currentScript.setContent(lines, false, false);
                LiveScriptingWithLuaV2.instance().currentScript.setEdited(true);
                LiveScriptingWithLuaV2.instance().currentScript.started = false;
                LiveScriptingWithLuaV2.instance().luaScriptEditor.luaEditorTextPane.setText(lines);

                LiveScriptingWithLuaV2.instance()._editorFrame.setVisible(true);
                LiveScriptingWithLuaV2.instance()._loadFrame.setVisible(false);

                LiveScriptingWithLuaV2.instance().ready = true;
            } catch (Exception e) {
                MethodProvider.logError(e);
            }
        }
    }

    class SDNSampleLoader implements SampleLoader {

        static String GITHUB_URL = "https://raw.githubusercontent.com/GhostDataIsDreaming/GhostIsDreamingScripts/main/LiveScripting/";

        static Map<String, String> SAMPLE_URLS = new HashMap<>() {
            {
                put("simplecombat.lua", GITHUB_URL + "samples/simplecombat.lua");
                put("WoodcutAnywhere.lua", GITHUB_URL + "samples/WoodcutAnywhere.lua");
                put("MineAnywhere.lua", GITHUB_URL + "samples/MineAnywhere.lua");
            }
        };

        Map<String, String> loadedSamples = new HashMap<>();

        public SDNSampleLoader() {
            SAMPLE_URLS.entrySet().forEach((e) -> {
                String sample = e.getKey();
                String _url = e.getValue();

                MethodProvider.log("Trying " + sample);
                MethodProvider.log("URL " + _url);

                try {
                    URL url = new URL(_url);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String inputLine;
                    while ((inputLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                        stringBuilder.append(System.lineSeparator());
                    }

                    bufferedReader.close();
                    String lines = stringBuilder.toString().trim();

                    System.out.println("Found...\n" + lines);

                    loadedSamples.put(sample, lines);
                } catch (Exception ex) {
                    MethodProvider.logError(ex);
                }
            });
        }

        @Override
        public void updateSampleList(JList<String> list) {
            if (loadedSamples.isEmpty()) {
                list.setListData(new String[] { "--- No Samples Found ---", "--- Check Console for error ---"});
                LiveScriptingWithLuaV2.instance().loadLuaScriptGUI.loadSelectedSampleButton.setEnabled(false);
                return;
            }

            list.setListData(loadedSamples.keySet().toArray(new String[0]));
        }

        @Override
        public void selectSample(String selected) {
            String lines = loadedSamples.get(selected);

            LiveScriptingWithLuaV2.instance().currentScript = new LiveScript();
            LiveScriptingWithLuaV2.instance().currentScript.setContent(lines, false, false);
            LiveScriptingWithLuaV2.instance().currentScript.setEdited(true);
            LiveScriptingWithLuaV2.instance().currentScript.started = false;
            LiveScriptingWithLuaV2.instance().luaScriptEditor.luaEditorTextPane.setText(lines);

            LiveScriptingWithLuaV2.instance()._editorFrame.setVisible(true);
            LiveScriptingWithLuaV2.instance()._loadFrame.setVisible(false);

            LiveScriptingWithLuaV2.instance().ready = true;
        }
    }
}
