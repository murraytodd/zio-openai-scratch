package zoneent.openai

import zio.{Console, ZIO, ZIOAppDefault}
import zio.openai.*
import zio.openai.model.CreateCompletionRequest.Prompt
import zio.openai.model.Temperature

object Chat extends ZIOAppDefault:

  def generatePrompt(animal: String): Prompt =
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

  def execute =  for {
    result <- Completions.createCompletion(
      model = "text-davinci-003",
      prompt = generatePrompt("racoon"),
      temperature = Temperature(0.6)
    )
    _ <- Console.printLine("Names: " + result.choices.flatMap(_.text.toOption).mkString(", "))
  } yield ()

  override def run = execute.provide(Completions.default)
