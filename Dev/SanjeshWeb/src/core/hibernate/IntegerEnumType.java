package core.hibernate;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class IntegerEnumType implements UserType, ParameterizedType {

    @SuppressWarnings("rawtypes")
    private Class<? extends Enum> enumClass;
    private Method toIntMethod, fromIntMethod;

    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClass");
        try {
            enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
        } catch (ClassNotFoundException cfne) {
            throw new HibernateException("Enum class not found", cfne);
        }

        String toIntMethodName = parameters.getProperty("toIntMethod", "toInt");
        try {
            toIntMethod = enumClass.getMethod(toIntMethodName);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new HibernateException("Failed to obtain toInt method", e);
        }

        String fromIntMethodName = parameters.getProperty("fromIntMethod", "fromInt");
        try {
            fromIntMethod = enumClass.getMethod(fromIntMethodName, int.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new HibernateException("Failed to obtain fromInt method", e);
        }
    }

    @Override
    public int[] sqlTypes() {
        return new int[] { org.hibernate.type.IntegerType.INSTANCE.sqlType() };
    }

    @Override
    public Class<?> returnedClass() {
        return enumClass;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        int value = rs.getInt(names[0]);
        try {
            return fromIntMethod.invoke(null, value);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new HibernateException("Error while invoking fromInt method '"
                    + fromIntMethod.getName() + "'", e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
            SessionImplementor session) throws HibernateException, SQLException {
        try {
            int i = (int) toIntMethod.invoke(value);
            st.setInt(index, i);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new HibernateException("Error while invoking toInt method '"
                    + toIntMethod.getName() + "'", e);
        }
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
