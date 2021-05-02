package kr.lul.heavymachine.core.shell

import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author justburrow* @since 2021/05/01
 */
@Unroll
class SimpleShellBlueprintSpec extends Specification {
  public static final def LOGGER = LoggerFactory.getLogger(SimpleShellBlueprintSpec)

  def "new : illegal command"() {
    given:
    LOGGER.info("[GIVEN] command=$command")

    when:
    new SimpleShellBlueprint(command)

    then:
    def e = thrown(IllegalArgumentException)
    LOGGER.info("[THEN] e=$e", e)

    e != null
    e.message.startsWith(message)

    where:
    command || message
    null    || "command is null."
    ""      || "command is empty."
    "a\rb"  || "command does not match : pattern="
    "a\tb"  || "command does not match : pattern="
    "a\nb"  || "command does not match : pattern="
  }

  def "getCommand"() {
    given:
    LOGGER.info("[GIVEN] original='$original'")

    when:
    def blueprint = new SimpleShellBlueprint(original)
    LOGGER.info("[WHEN] blueprint=$blueprint")

    then:
    blueprint.command == command

    where:
    original || command
    "a b"    || ["a", "b"]
    " a b"   || ["a", "b"]
    "a b "   || ["a", "b"]
    "a  b"   || ["a", "b"]
  }
}