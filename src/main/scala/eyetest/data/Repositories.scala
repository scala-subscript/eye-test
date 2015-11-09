package eyetest.data

import java.io.Closeable

trait Repositories extends Closeable {

  def user: UserRepo

  def score: ScoreRepo

}