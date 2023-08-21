package school21.edu.ormmanager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;
import school21.edu.JdbcManager.JdbcUtils;
import school21.edu.annotations.OrmColumnId;
import school21.edu.annotations.OrmEntity;


public class OrmManagerImpl implements OrmManager {

  public OrmManagerImpl() {
    this.init();
  }
  @Override
  public void save(Object entity) {
    try {
      String query = getQueryDetails(entity, "save");
      PreparedStatement statement = JdbcUtils.preStatement(query);
      Field[] fields = entity.getClass().getDeclaredFields();
      for (int i = 1; i < fields.length; ++i) {
        fields[i].setAccessible(true);
        statement.setObject(i, fields[i].get(entity));
      }
      statement.executeUpdate();
      fields[0].set(entity, getIncrementId(entity, fields[0]));
      statement.close();
      System.out.println(getQueryDetails(entity, "save"));
    } catch (SQLException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void update(Object entity) {
    try {
      String query = getQueryDetails(entity, "update");
      PreparedStatement statement = JdbcUtils.preStatement(query);
      Field[] fields = entity.getClass().getDeclaredFields();
      fields[0].setAccessible(true);
      statement.setLong(fields.length, Long.parseLong(String.valueOf(fields[0].get(entity))));
      for (int i = 1; i < fields.length; ++i) {
        fields[i].setAccessible(true);
        statement.setObject(i, fields[i].get(entity));
      }
      statement.executeUpdate();
      statement.close();
      System.out.println(query);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
  @Override
  public <T> T fidById(Long id, Class<T> aClass) {
    try {
      String query = String.format("SELECT * FROM %s WHERE id=?",
          aClass.getAnnotation(OrmEntity.class).table());
      PreparedStatement statement = JdbcUtils.preStatement(query);
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      T obj = aClass.getConstructor().newInstance();
      Field[] fields = obj.getClass().getDeclaredFields();
      fields[0].setAccessible(true);
      fields[0].set(obj, Long.parseLong(String.valueOf(id)));
      resultSet.next();
      for (int i = 1; i < fields.length; ++i) {
        fields[i].setAccessible(true);
        Object value = resultSet.getObject(i + 1);
        fields[i].set(obj, value);
      }
      System.out.println(query);
      statement.close();
      return obj;
    } catch (SQLException | InstantiationException
             | IllegalAccessException | NoSuchMethodException |
        InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void init() {
    try {
      Path schemaUrl = Paths.get("target/classes/schema.sql").normalize();
      if (!Files.exists(schemaUrl)) {
        throw new RuntimeException("File schema.sql not found. Compile the project using "
            + "'mvn clean compile' to generate the file.");
      }
      String schema = Files.lines(schemaUrl).collect(Collectors.joining("\n"));
      PreparedStatement statement = JdbcUtils.preStatement(schema);
      statement.execute();
      statement.close();
      System.out.println(schema);
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  private Long getIncrementId(Object obj, Field field) throws SQLException {
    OrmEntity ormEntity = obj.getClass().getAnnotation(OrmEntity.class);
    PreparedStatement statement = JdbcUtils.preStatement(String.format("select max(id) from %s ", ormEntity.table()));
    ResultSet resultSet = statement.executeQuery();
    resultSet.next();
    field.setAccessible(true);
    return resultSet.getLong(1);
  }

  private String getQueryDetails(Object obj, String status) {
    StringBuilder query = new StringBuilder();
    StringBuilder values = new StringBuilder();
    Field[] fields = obj.getClass().getDeclaredFields();
    OrmEntity ormEntity = obj.getClass().getAnnotation(OrmEntity.class);
    if (status.equals("save")) {
      query.append(String.format("INSERT INTO %s "
          + "(", ormEntity.table()));
      for (int i = 1; i < fields.length; ++i) {
        query.append(String.format("%s", fields[i].getName()));
        values.append("?");
        if (i != fields.length - 1) {
          query.append(",");
          values.append(",");
        }
      }
      query.append(") VALUES (").append(values).append(");");
    }
    else {
      query.append(String.format("UPDATE %s SET  ", ormEntity.table()));
      if (obj.getClass().getAnnotation(OrmColumnId.class) != null) {
        OrmColumnId ormColumnId = obj.getClass().getAnnotation(OrmColumnId.class);
        query.append(ormColumnId.id());
      }
      for (int i = 1; i < fields.length; ++i) {
        query.append(String.format("%s=?", fields[i].getName()));
        if (i != fields.length - 1) {
          query.append(",");
        }
      }
      query.append(" WHERE id=?;");
    }
    return query.toString();
  }
}
