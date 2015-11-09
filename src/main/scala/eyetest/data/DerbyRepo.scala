package eyetest.data

import java.sql._
import java.io.Closeable

class DerbyRepo(dbname: String) extends Closeable {
  
  Class.forName("org.apache.derby.jdbc.EmbeddedDriver")
  val conn = DriverManager.getConnection(s"jdbc:derby:$dbname;create=true")

  try createSchema()
  catch {
    case e: SQLException if e.getMessage.contains("already exists") => // ignore
    case e: Throwable => throw e
  }

  def createSchema() {
    val stmt = conn.createStatement()
    try {
      stmt.executeUpdate {"""
        CREATE TABLE USERS (
          USERNAME VARCHAR(255) NOT NULL PRIMARY KEY
        )
      """}
    
      stmt.executeUpdate {"""
        CREATE TABLE SCORES (
        ID INT NOT NULL PRIMARY KEY
      , USERNAME VARCHAR(255) NOT NULL
      , TEST_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP
      , RIGHT_EYE DOUBLE NOT NULL
      , LEFT_EYE  DOUBLE NOT NULL
      )
      """}
    }
    finally stmt.close()
  }

  override def close() {
    conn.close()
  }

}