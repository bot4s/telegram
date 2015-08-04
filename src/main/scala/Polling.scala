trait Polling {
  this: TelegramBot =>
  /**
   * getUpdates
   *
   * Use this method to receive incoming updates using long polling (wiki). An Array of Update objects is returned.
   * Parameters 	Type 	Required 	Description
   * offset 	Integer 	Optional 	Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
   * limit 	Integer 	Optional 	Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
   * timeout 	Integer 	Optional 	Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
   *   Notes
   *     1. This method will not work if an outgoing webhook is set up.
   *     2. In order to avoid getting duplicate updates, recalculate offset after each server response.
   */
  def getUpdates(offset: Option[Int] = None,
                 limit: Option[Int] = None,
                 timeout: Option[Int] = None): Array[Update] = {

    println("Offset: " + offset.getOrElse("None"))

    getAs[Array[Update]]("getUpdates",
      "offset"  -> offset,
      "limit"   -> limit,
      "timeout" -> timeout)
  }
}