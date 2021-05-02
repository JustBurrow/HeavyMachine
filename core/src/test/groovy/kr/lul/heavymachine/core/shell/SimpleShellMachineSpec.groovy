package kr.lul.heavymachine.core.shell

import kr.lul.heavymachine.core.machine.MapControl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author justburrow* @since 2021/05/01
 */
@SuppressWarnings('NonAsciiCharacters')
@Unroll
class SimpleShellMachineSpec extends Specification {
  public static final Logger LOGGER = LoggerFactory.getLogger(SimpleShellMachineSpec)

  def "new(null)"() {
    when:
    new SimpleShellMachine(null)

    then:
    def e = thrown(IllegalArgumentException)
    LOGGER.info("[THEN] e=$e", e)

    e != null
    e.message == "blueprint is null."
  }

  def "getBlueprint"() {
    given:
    def blueprint = new SimpleShellBlueprint("ls")
    LOGGER.info("[GIVEN] blueprint=$blueprint")

    def machine = new SimpleShellMachine(blueprint)
    LOGGER.info("[GIVEN] machine=$machine")

    when:
    def actual = machine.getBlueprint()
    LOGGER.info("[WHEN] actual=$actual")

    then:
    actual == blueprint
  }

  def "buildCommand(null)"() {
    given:
    def blueprint = new SimpleShellBlueprint("ls")
    LOGGER.info("[GIVEN] blueprint=$blueprint")

    def machine = new SimpleShellMachine(blueprint)
    LOGGER.info("[GIVEN] machine=$machine")

    def control = new MapControl()

    LOGGER.info("[GIVEN] control=$control")

    when:
    def command = machine.buildCommand(control)
    LOGGER.info("[WHEN] command=$command")

    then:
    command == ["ls"]
  }

  def "execute(null)"() {
    given:
    def machine = new SimpleShellMachine(new SimpleShellBlueprint("ls"))
    LOGGER.info("[GIVEN] machine=$machine")

    when:
    machine.execute(null)

    then:
    def e = thrown(IllegalArgumentException)
    LOGGER.info("[THEN] e=$e", e)

    e.message == "control is null."
  }

  def "execute(control) : 성공"() {
    given:
    def machine = new SimpleShellMachine(new SimpleShellBlueprint("ls"))
    LOGGER.info("[GIVEN] machine=$machine")

    def control = new MapControl()
    LOGGER.info("[GIVEN] control=$control")

    when:
    def outcome = machine.execute(control)
    LOGGER.info("[WHEN] outcome=$outcome")

    then:
    outcome.exitCode == 0
    outcome.success
    !outcome.fail
  }

  def "execute(control) : 실패"() {
    given:
    def machine = new SimpleShellMachine(new SimpleShellBlueprint("cat /"))
    LOGGER.info("[GIVEN] machine=$machine")

    def control = new MapControl()
    LOGGER.info("[GIVEN] control=$control")

    when:
    def outcome = machine.execute(control)
    LOGGER.info("[WHEN] outcome=$outcome")

    then:
    outcome.exitCode > 0
    !outcome.success
    outcome.fail
  }
}