package net.stremo.graphqljpa.entities;


//import org.json.JSONObject;
import org.springframework.util.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@MappedSuperclass
public class BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public BaseEntity() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


//    public void setDataFromJSON(JSONObject o) {
//        Arrays.stream(getClass().getDeclaredFields())
//                .forEach(field -> {
//                    String fieldName = field.getName();
//                    try {
//                        if (o.isNull(fieldName))
//                            return;
//                        Method method = getClass().getMethod("set" + StringUtils.capitalize(fieldName), field.getType());
//                        switch (field.getType().getSimpleName()) {
//                            case "String":
//                                method.invoke(this, o.getString(fieldName));
//                                break;
//                            case "Long":
//                                if (o.get(fieldName).getClass() == String.class)
//                                    method.invoke(this, Long.parseLong(o.getString(fieldName)));
//                                else if (o.get(fieldName).getClass() == Long.class)
//                                    method.invoke(this, o.getLong(fieldName));
//                                break;
////                            case "List":
////                                break;
////                            default:
////                                System.out.println(field.getType().getSimpleName() + " kann nicht behandelt werden");
//                        }
//                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                        e.printStackTrace();
//                    }
//                });
//    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getClass().getSimpleName() + ":{");
        Arrays.stream(getClass().getDeclaredFields())
                .forEach(field -> {
                    String fieldName = field.getName();
                    builder.append("\t").append(fieldName).append(":\t'");
                    try {
                        Method method = getClass().getMethod("get" + StringUtils.capitalize(fieldName));
                        if(method.getReturnType().getSimpleName().equals("List")) {
                            List l = (List) method.invoke(this);
                            builder.append(l.size()).append(" items'\n");
                            return;
                        }
                        builder.append(method.invoke(this)).append("'\n");
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | StackOverflowError e ) {
                        e.printStackTrace();
                    }
                });
        builder.append("}");
        return builder.toString();
    }


}
