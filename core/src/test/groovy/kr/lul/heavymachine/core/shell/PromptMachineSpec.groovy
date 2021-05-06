package kr.lul.heavymachine.core.shell

import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author justburrow* @since 2021/05/06
 */
@Unroll
class PromptMachineSpec extends Specification {
  public static final def LOGGER = LoggerFactory.getLogger(PromptMachineSpec)

  def "execute(control) : 프롬프트가 없는 경우"() {
    def blueprint = new SimpleShellBlueprint("ls -alh /")
    def handler = new SimplePromptHandler()
    def machine = new PromptMachine(blueprint, handler)
  }
}