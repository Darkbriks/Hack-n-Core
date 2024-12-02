package org.darkbriks.hacknCore.utils;

import org.darkbriks.hacknCore.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

public class YAML
{
    public static <T extends Record> void saveRecord(T record, File file, boolean async)
    {
        if (async) { new Thread(() -> saveRecord(record, file)).start(); }
        else { saveRecord(record, file); }
    }

    private static <T extends Record> void saveRecord(T record, File file)
    {
        if (!file.exists())
        {
            try { file.getParentFile().mkdirs(); file.createNewFile(); }
            catch (IOException e)
            {
                Logger.error("Error creating file: " + e.getMessage(), true);
                return;
            }
        }

        Yaml yaml = new Yaml();
        Map<String, Object> data = recordToMap(record);

        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(fos))
        {
            yaml.dump(data, writer);
        }
        catch (IOException e)
        {
            Logger.error("Error writing to file: " + e.getMessage(), true);
        }
    }

    private static <T extends Record> Map<String, Object> recordToMap(T record)
    {
        Map<String, Object> map = new HashMap<>();
        for (RecordComponent component : record.getClass().getRecordComponents())
        {
            try
            {
                map.put(component.getName(), component.getAccessor().invoke(record));
            }
            catch (ReflectiveOperationException e)
            {
                Logger.error("Error getting record component value: " + e.getMessage(), true);
            }
        }
        return map;
    }

    public static <T extends Record> T loadRecord(Class<T> recordClass, File file)
    {
        if (!file.exists())
        {
            Logger.warn("File not found: " + file.getPath(), true);
            return null;
        }

        Yaml yaml = new Yaml();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(fis))
        {
            Map<String, Object> data = yaml.load(reader);
            if (data == null)
            {
                Logger.warn("File is empty: " + file.getPath(), true);
                return null;
            }
            return mapToRecord(recordClass, data);
        }
        catch (IOException e)
        {
            Logger.error("Error reading file: " + e.getMessage(), true);
        }
        catch (ReflectiveOperationException e)
        {
            Logger.error("Error creating record instance: " + e.getMessage(), true);
        }
        return null;
    }

    private static <T extends Record> T mapToRecord(Class<T> recordClass, Map<String, Object> data) throws ReflectiveOperationException
    {
        Constructor<T> constructor = recordClass.getDeclaredConstructor(data.values().stream().map(Object::getClass).toArray(Class[]::new));
        Object[] args = data.values().toArray();
        return constructor.newInstance(args);
    }
}
