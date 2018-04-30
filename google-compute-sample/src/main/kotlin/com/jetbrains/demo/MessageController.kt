package com.jetbrains.demo

import org.springframework.web.bind.annotation.*


interface IMessage {
  val text: String
  val priority: String
}

data class Message(
  override val text: String,
  override val priority: String,
  val widget: Array<Widget>
) : IMessage

object Blurb {
  fun getBlurb(fromData: Boolean): String {
    this.otherData = "blurb"
    return if (fromData) this.blurbData else this.otherData
  }

  val blurbData = "flerber"

  var otherData = "should not be blurb"
}

private fun generateBlurb(): Widget {
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

  @RequestMapping("/submessage")
  fun subMessage(): SubMessage {
    val customSubWidget = SubWidget("trinket")
    val defaultSubWidget = SubWidget()
    val defaultSubObjectSubWidget = SubWidget(SubObject.getSubObject(true))
    val blurbSubWidget = SubWidget(SubObject.getSubObject(false))
    var generatedSubWidget = SubWidget()
    generatedSubWidget = MessageController.generateSubBlurb()

    val privateSubWidget = MessageController.generateSub1()

    return SubMessage(
        "Hello from SubCloud SubDebugger",
        "High",
        "Surprise!",
        arrayOf(
            defaultSubWidget,
            customSubWidget,
            defaultSubWidget,
            defaultSubObjectSubWidget,
            generatedSubWidget,
            privateSubWidget
        )
    )
  }

  companion object {
    fun generateSubBlurb(): SubWidget {
      return SubWidget("subgenerated")
    }
    private fun generateSub1(): SubWidget {
      return SubWidget("private1")
    }
    private fun generateSub2(): SubWidget {
      return SubWidget("private2")
    }
  }

  object SubObject {
    fun getSubObject(fromData: Boolean): String {
      return if (fromData) this.subData else "subobject"
    }

    val subData = "burber"
  }

  data class SubMessage(
      override val text: String,
      override val priority: String,
      val newProp: String = "Test",
      val widget: Array<SubWidget>
  ): IMessage

  // Primary constructor needs a 'type' parameter.
  class SubWidget(val type: String) {
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
    constructor() : this("default") {
      // init blocks are executed before secondary constructor body.
      val anotherAssignment = this.assignedType
    }
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
    constructor() : this("default") {
        // init blocks are executed before secondary constructor body.
        val anotherAssignment = this.assignedType
    }
}
