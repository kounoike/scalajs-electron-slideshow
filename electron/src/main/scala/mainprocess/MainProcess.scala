package mainprocess

import typings.electron.ElectronNs.BrowserWindowConstructorOptions
import typings.electron.electronMod.BrowserWindow
import typings.electron.electronMod.^.app
import typings.electron.electronStrings
import scala.scalajs.js.timers._

import scala.collection.mutable

    object MainProcess {

      val windows: mutable.Set[BrowserWindow] = mutable.Set()

      val urls = Seq("https://www.google.co.jp/", "https://www.yahoo.co.jp", "https://www.asahi.com/", "https://www.nikkei.com")

      def updateUrl(window: BrowserWindow, urlIndex: Int): Unit = {
        val url = urls(urlIndex % urls.length)
        println(urlIndex, url)
        window.loadURL(url)
        val nextIndex = if (urlIndex < urls.length - 1) urlIndex + 1 else 0
        setTimeout(10000) {
          updateUrl(window, nextIndex)
        }
      }

      def createWindow(): Unit = {
        /**
         * Creating the BrowserWindow object, with the desired options.
         */
        val window = new BrowserWindow(
          BrowserWindowConstructorOptions(
            height = 780,
            width = 1280,
            x = 0,
            y = 0,
            frame = false,
            titleBarStyle = electronStrings.hidden
          )
        )
        window.show()

        updateUrl(window, 0)

    window.on_closed(electronStrings.closed, () => windows -= window)

    windows += window
  }

  def main(args: Array[String]): Unit = {
    app.on_ready(electronStrings.ready, _ => createWindow())
    app.on_windowallclosed(electronStrings.`window-all-closed`, () => app.quit())
  }
}
