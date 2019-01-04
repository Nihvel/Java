# Java
My class for Database is easy to import in your project.

1.
  Add the " pgjdbc-core-parent-1.0.8.jar " library, in your project.

2.
  Edit your " pom.xml " file to load the new library:
  <dependencies>
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>pgjdbc-core-parent</artifactId>
      <version>1.0.8</version>
     </dependency>
  </dependencies>

3.
  Change default url, user and password variables to match your connection

3.1
  UPDATE- If you want to use PgPass, check out the DatabasePgPass class
  
4.
  Call it from the void main():
  
4.1
  Establish a connection:
  Database db = new Database();

4.1.1
  For the DatabasePgPass: Database db = new Database(host, port, db, user);

4.2
  Select:
  List<String> result = db.arraySelect("SELECT name FROM user WHERE id='"+id_user+"' ");
    if ( result.get(0).equals(User_id) ){ ... }
  /* Use the FOR to assign each value of the list to a variable */

4.3
  UPSERT:
  /* Postgres does not have a Upsert function, you'll need to add to your database as a stored procedure
     read my " add_stored_procedure_in_postgres " to know how to do it */
  String add_user = "SELECT upsert_user('"+User+"','"+Email+"','"+id_user+"'); ";
  db.runSql(add_user);

4.4
  UPDATE:
  String update_user = "UPDATE user SET email='"+Email+"', WHERE id = '"+id_user+"'; ";
  db.updateSql(update_user);

That should be all!
