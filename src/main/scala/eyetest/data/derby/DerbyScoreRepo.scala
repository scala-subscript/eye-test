package eyetest.data
package derby

import subscript.language

import subscript.Predef._

import java.sql._

class DerbyScoreRepo(val conn: Connection) extends ScoreRepo {

  val scoresOfStmt =
    s"SELECT * FROM SCORES WHERE USERNAME = ? ORDER BY ${Col.DATE} DESC"

  val writeStmt =
    s"INSERT INTO SCORES (${Col.USERNAME}, ${Col.RIGHT_EYE}, ${Col.LEFT_EYE}) VALUES (?, ?, ?)"

  override script..

    scoresOf(user: String) = {* val pstmt = conn.prepareStatement(scoresOfStmt)
                                    pstmt.setString(1, user)
                                var result: List[Score] = Nil
                                try     {val resultSet = pstmt.executeQuery()
                                         def dbl(name: String) = resultSet.getDouble(name)
                                         while(resultSet.next()) result ::= ((dbl(Col.RIGHT_EYE),
                                                                              dbl(Col. LEFT_EYE), resultSet.getTimestamp(Col.DATE)))}
                                catch   {case t:Throwable => t.printStackTrace()}: PartialFunction[Throwable, Unit]
                                finally {pstmt.close()}
                                result
                             *}

    write(user : String,
          score: (Double,Double)) = {* val pstmt = conn.prepareStatement(writeStmt)
                                       pstmt.setString(1, user)
                                       pstmt.setDouble(2, score._1)
                                       pstmt.setDouble(3, score._2)
                                       try     {pstmt.executeUpdate()}
                                       catch   {case e:Throwable => e.printStackTrace()}: PartialFunction[Throwable, Unit]
                                       finally {pstmt.close()}
                                    *}

}
