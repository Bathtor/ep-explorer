package com.lkroll.ep.mapviewer

import org.scalajs.dom
import org.scalajs.dom.MouseEvent
import dom.document
import scalatags.JsDom.all._
import scribe.Logging
import scala.scalajs.js.Date

object AboutModal extends Logging {
  def render(): Unit = {
    document.body.appendChild(modal);
  }

  val toggle = span(id := "about-toggle", i(cls := "fas fa-question-circle")).render;
  toggle.onclick = (_ev: MouseEvent) => {
    modal.classList.toggle("hidden");
  };

  val closer = span(cls := "closer", i(cls := "far fa-window-close")).render;
  closer.onclick = (_ev: MouseEvent) => {
    modal.classList.add("hidden");
  };

  val modal = div(
    id := "about",
    cls := "hidden",
    closer,
    h3("About", cls := "color-primary-2", style := "margin-top: 0.5em"),
    div(
      p(span(s"Code v${build.BuildInfo.version}", id := "version")),
      p(span(s"Copyright © $copyrightYear "), a(href := "http://lkroll.com", "Lars Kroll")),
      div(
        span("All "),
        a(href := "http://eclipsephase.com/releases", "Eclipse Phase"),
        span(" content by "),
        a(href := "http://eclipsephase.com", "Posthuman Studios, LLC"),
        span(
          " used under the ",
          a(href := "http://creativecommons.org/licenses/by-nc-sa/3.0/us/", "Creative Commons"),
          span(" Attribution-Noncommercial-Share Alike 3.0 Unported License.")
        )
      ),
      p(span("Powered by "), img(src := "https://www.scala-js.org/assets/badges/scalajs-0.6.17.svg")),
      p(span("Sources can be found on "), a(href := "https://github.com/Bathtor/ep-explorer", "Github")),
      p(
        span("Please consider "),
        a(
          target := "_blank",
          rel := "noopener noreferrer",
          href := "https://github.com/users/Bathtor/sponsorship",
          "sponsoring"
        ),
        span(" me, if you like this service, as it helps me pay for the server its running on.")
      ),
      p(span("Check out my "),
        a(href := "https://eptools.lkroll.com/", "EP Tools"),
        span(" for more Eclipse Phase related goodies."))
    )
  ).render;

  lazy val copyrightYear: String = {
    val start = 2019;
    val now = new Date().getFullYear();
    if (start < now) {
      s"$start-$now"
    } else {
      start.toString()
    }
  }
}
