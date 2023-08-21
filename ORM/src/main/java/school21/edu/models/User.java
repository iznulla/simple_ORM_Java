package school21.edu.models;


import school21.edu.annotations.OrmColumn;
import school21.edu.annotations.OrmColumnId;
import school21.edu.annotations.OrmEntity;

@OrmEntity(table = "simple_user")
public class User {
  public User() {}
  public User(String firstName, String lastName, Integer age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  @OrmColumnId
  private Long id;

  @OrmColumn(name = "first_name", length = 10)
  private String firstName;
  @OrmColumn(name = "last_name", length = 10)
  private String lastName;
  @OrmColumn(name = "age")
  private Integer age;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
  @Override
  public String toString() {
    return String.format("User {id=%s,"
        + "firstName=%s,"
        + "lastName=%s,"
        + "age=%s}  ", this.id, this.firstName, this.lastName, this.age);
  }
}
