//file:noinspection NonAsciiCharacters
package kr.lul.heavymachine.core.prompt


import kr.lul.heavymachine.core.machine.MapControl
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author justburrow* @since 2021/05/07
 */
@Unroll
class PromptMachineSpec extends Specification {
  public static final def LOGGER = LoggerFactory.getLogger(PromptMachineSpec)

  def "execute(control) : docker 명령어로 시험."() {
    given:
    def blueprint = new SimplePromptBlueprint("docker -l=debug image prune")
    LOGGER.info("[GIVEN] blueprint=$blueprint")
    def handler = new SimplePromptHandler(Map.of("zzzzz", "11111"))
    handler.addInput("Are you sure you want to continue? [y/N] ", "y")
    LOGGER.info("[GIVEN] handler=$handler")

    def machine = new PromptMachine(blueprint, handler)
    LOGGER.info("[GIVEN] machine=$machine")

    def control = new MapControl()
    LOGGER.info("[GIVEN] control=$control")

    when:
    def outcome = machine.execute(control)
    LOGGER.info("[WHEN] outcome=$outcome")

    then:
    outcome.success
  }

  def "execute(control) : ls -la /"() {
    given:
    def handler = new SimplePromptHandler(Map.of("aaa", "bbb"))
    def machine = new PromptMachine(new SimplePromptBlueprint("ls -al /"), handler)

    when:
    def outcome = machine.execute(new MapControl())

    then:
    outcome.success
  }
}