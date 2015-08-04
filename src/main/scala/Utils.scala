/**
 * Created by mukel on 8/4/15.
 */
object Utils {
  def tokenFromFile(file: String): String = {
    scala.io.Source.fromFile(file).getLines().next()
  }
}
