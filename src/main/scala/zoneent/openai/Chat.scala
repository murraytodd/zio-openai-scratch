package zoneent.openai

import zio.{Console, ZIO, ZIOAppDefault}
import zio.openai.*
import zio.openai.model.CreateCompletionRequest.Prompt
import zio.openai.model.Temperature
import zio.openai.model.CreateCompletionRequest

object Chat extends ZIOAppDefault:

  def generateAnimalPrompt(animal: String): Prompt =
    Prompt.String(
      s"""Suggest three names for an animal that is a supervillain.
        |
        |Animal: Cat
        |Names: Doctor Sharpclaw, Furious Fluffball, Catnip Carnivore
        |Animal: Dog
        |Names: Ruff the Digger, Killer Canine, Barks the Barbarian
        |Animal: Squirrel
        |Names: Nut-gazmo, Fuzzy Fury, Pecan Pickpocket
        |Animal: ${animal.capitalize}
        |Names:""".stripMargin
    )

  def singleLine = Prompt.String(
    s"""The following is a hilarious conversation between Captain Jean-luc Picard and Commander Worf from Star Trek 
      |after they walk into a Vulcan bar.
      |
      |Picard: Bartender! Please, a glass of your finest red wine.
      |Worf: Captain, Vulcans do not drink alcohol.
      |Bartender: We do stock Romulan ale, however. Would you like a glass?
      |Worf: Captain, I think this is a trap.
      |Picard:""".stripMargin
  )

  def suggestNames =  for {
    result <- Completions.createCompletion(
      model = "text-davinci-003",
      prompt = generateAnimalPrompt("racoon"),
      temperature = Temperature(0.6),
      maxTokens = CreateCompletionRequest.MaxTokens(2000)
    )
    _ <- Console.printLine("Names: " + result.choices.flatMap(_.text.toOption).mkString(", "))
  } yield ()

  def finishStory = for {
    result <- Completions.createCompletion(
      model = "text-davinci-003",
      prompt = singleLine,
      temperature = Temperature(0.7)
    )
    _ <- Console.printLine(result.choices.flatMap(_.text.toOption).mkString(" "))
    _ <- Console.printLine("END")
  } yield () 

  override def run = finishStory.provide(Completions.default)
    // suggestNames.provide(Completions.default)
