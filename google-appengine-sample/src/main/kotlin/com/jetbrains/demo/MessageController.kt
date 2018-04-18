package com.jetbrains.demo

import org.springframework.web.bind.annotation.*


data class Message(
  val text: String,
  val priority: String,
  val widget: Array<Widget>
)

object Blurb {
  fun getBlurb(fromData: Boolean): String {
    return if (fromData) this.blurbData else "blurb"
  }

  val blurbData = "flerber"
}

private fun generateBlurb(): {
  return Widget("generated")
}

@RestController
class MessageController {
  @RequestMapping("/message")
  fun message(): Message {
    val customWidget = Widget("trinket")
    val defaultWidget = Widget()
    val defaultBlurbWidget = Widget(Blurb.getBlurb(true))
    val blurbWidget = Widget(Blurb.getBlurb(false))
    var generatedWidget = Widget()
    generatedWidget = generateBlurb()

    return Message(
        "Hello from Cloud Debugger",
        "High",
        arrayOf(
            defaultWidget,
            customWidget,
            defaultBlurbWidget,
            blurbWidget,
            generatedWidget
        )
    )
  }
}

// Primary constructor needs a 'type' parameter.
class Widget(val type: String) {
  val assignedType: String;

  // Declare a field.
  val classifiedAs = "Widget"

  // Use the parameter outside of any blocks.
  val allCaps = type.toUpperCase()

  init {
    // Use the parameter in an init block.
    this.assignedType = type
  }

  // Create a secondary constructor which delegates to the primary
  // constructor.
  constructor(): this("default") {
    // init blocks are executed before secondary constructor body.
    val anotherAssignment = this.assignedType
  }
}