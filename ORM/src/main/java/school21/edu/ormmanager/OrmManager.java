package school21.edu.ormmanager;

public interface OrmManager {
  void save(Object entity);
  void update(Object entity);
  public <T> T fidById(Long id, Class<T> aClass);
}
