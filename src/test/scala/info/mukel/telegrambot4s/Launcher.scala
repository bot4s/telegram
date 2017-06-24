package info.mukel.telegrambot4s

import info.mukel.telegrambot4s.examples.RegexBot

object Launcher {
  def main(args: Array[String]): Unit = {
    new RegexBot("211113992:AAGlXLvA9lwhltbdMBUYBasBmw1giegeqfc").run()
  }
}
