package kr.lul.heavymachine.shell

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author justburrow* @since 2021/04/25
 */
@Unroll
@SuppressWarnings('NonAsciiCharacters')
class ShellMachineSpec extends Specification {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShellMachineSpec)

  private ShellMachine machine

  def setup() {
    machine = new ShellMachine()
  }

  def "프롬프트 없는 명령 처리"() {
    given:
    LOGGER.info("[GIVEN] command=$command")

    when:
    def output = machine.run(new ShellCommand(command))
    LOGGER.info("[WHEN] output=$output")

    then:
    output.success
    output.exitCode == 0

    where:
    command              || _
    "ls -alh"            || _
    "docker image ls -a" || _
  }

  def "존재하지 않는 명령"() {
    given:
    LOGGER.info("[GIVEN] command=$command")

    when:
    def output = machine.run(new ShellCommand(command))
    LOGGER.info("[WHEN] output=$output")

    then:
    !output.success
    output.exitCode != 0

    where:
    command              || _
    "aaaZZZZZZZZ99999999" | _
  }
}