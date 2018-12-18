package by.bsuir.bank.service;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class StaticDataProvider {

    public List<String> getStaticDataByClassName(String className) {
        List<String> data = new ArrayList<>();
        try {
            Class theClass = Class.forName(className);

            Field[] fields = theClass.getDeclaredFields();

            for (Field field : fields) {
                if (Modifier.isPublic(field.getModifiers())) {
                    Enum en = Enum.valueOf(theClass, field.getName().toUpperCase());
                    if (en.getClass().getMethod("getName") != null) {
                        data.add((String) en.getClass().getMethod("getName").invoke(en));
                    }
                }
            }
        } catch (ReflectiveOperationException e) {
        }

        return data;
    }
}
