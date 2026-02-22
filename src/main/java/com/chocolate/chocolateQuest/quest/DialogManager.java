package com.chocolate.chocolateQuest.quest;

import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.HashMap;
import java.util.Map;

public class DialogManager
{
    static Map<String, TextEntry> currentEntries;
    static String currentFile;
    static final String nameTag = "@name:";
    static final String promptTag = "@prompt:";
    static final String endTag = "#####";
    
    public static void readText(final String lang, final DialogOption option) {
        loadOptions(lang, option.folder);
        TextEntry entry = DialogManager.currentEntries.get(option.name);
        if (entry == null) {
            entry = new TextEntry();
        }
        if (entry.text == null) {
            entry.text = new String[] { "(@name stares at you)" };
        }
        if (entry.name == null || entry.name.isEmpty()) {
            entry.name = option.name;
        }
        if (entry.prompt == null || entry.prompt.isEmpty()) {
            entry.prompt = option.name;
        }
        option.name = entry.name;
        option.prompt = entry.prompt;
        option.text = entry.text;
    }
    
    public static void loadOptions(final String lang, final String fileName) {
        if (DialogManager.currentFile != fileName) {
            DialogManager.currentFile = fileName;
            DialogManager.currentEntries = new HashMap<String, TextEntry>();
            File file = new File(BDHelper.getAppDir(), getDefaultFileName(fileName));
            loadTagsFromFile(file);
            file = new File(BDHelper.getAppDir(), getDefaultFileNameWithLang(fileName, lang));
            loadTagsFromFile(file);
        }
    }
    
    public static String[] getOptionNames(final String fileName) {
        loadOptions("en_UK", fileName);
        if (DialogManager.currentEntries != null && !DialogManager.currentEntries.isEmpty()) {
            final String[] names = new String[DialogManager.currentEntries.size()];
            final Object[] entries = DialogManager.currentEntries.values().toArray();
            for (int i = 0; i < names.length; ++i) {
                names[i] = ((TextEntry)entries[i]).name;
            }
            return names;
        }
        return null;
    }
    
    protected static boolean loadTagsFromFile(final File file) {
        try {
            if (!file.exists()) {
                return false;
            }
            final BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            final List<String> list = new ArrayList<String>();
            TextEntry currentEntry = null;
            String line;
            while ((line = stream.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("@name:")) {
                    if (currentEntry != null) {
                        final String[] text = new String[list.size()];
                        list.toArray(text);
                        currentEntry.text = text;
                        DialogManager.currentEntries.put(currentEntry.name, currentEntry);
                        list.clear();
                    }
                    currentEntry = new TextEntry();
                    currentEntry.name = line.replace("@name:", "");
                }
                else if (line.startsWith("@prompt:") && currentEntry != null) {
                    currentEntry.prompt = line.replace("@prompt:", "");
                }
                else {
                    if (currentEntry == null) {
                        continue;
                    }
                    list.add(line);
                }
            }
            if (currentEntry != null) {
                final String[] text = new String[list.size()];
                list.toArray(text);
                currentEntry.text = text;
                DialogManager.currentEntries.put(currentEntry.name, currentEntry);
            }
            stream.close();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    protected static String getDefaultFileName(final String fileName) {
        return BDHelper.getInfoDir() + fileName + ".dialog";
    }
    
    protected static String getDefaultFileNameWithLang(final String fileName, final String lang) {
        return BDHelper.getInfoDir() + fileName + "." + lang + ".dialog";
    }
    
    public static void saveText(final DialogOption option) {
        final List<String> list = new ArrayList<String>();
        try {
            final File file = new File(BDHelper.getAppDir(), getDefaultFileName(option.folder));
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            TextEntry current = DialogManager.currentEntries.get(option.name);
            if (current == null) {
                current = new TextEntry(option.name, option.prompt, option.text);
                DialogManager.currentEntries.put(option.name, current);
            }
            else {
                current.prompt = option.prompt;
                current.text = option.text;
            }
            final FileWriter writer = new FileWriter(file);
            final BufferedWriter stream = new BufferedWriter(writer);
            for (final TextEntry entry : DialogManager.currentEntries.values()) {
                stream.write("@name:" + entry.name);
                stream.newLine();
                stream.write("@prompt:" + entry.prompt);
                stream.newLine();
                for (final String line : entry.text) {
                    stream.write(line);
                    stream.newLine();
                }
                stream.write("#####");
                stream.newLine();
            }
            stream.close();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
