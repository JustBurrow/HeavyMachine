//file:noinspection NonAsciiCharacters
package kr.lul.heavymachine.core.prompt

import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.Map.entry

/**
 * @author justburrow* @since 2021/05/06
 */
@Unroll
class SimplePromptHandlerSpec extends Specification {
  public static final def LOGGER = LoggerFactory.getLogger(SimplePromptHandlerSpec)

  def "addInput : illegal"() {
    given:
    LOGGER.info("[GIVEN] prompt=$prompt, input=$input")

    def handler = new SimplePromptHandler()
    LOGGER.info("[GIVEN] handler=$handler")

    when:
    handler.addInput(prompt, input)

    then:
    def e = thrown(IllegalArgumentException)
    LOGGER.info("[THEN] e=$e", e)
    e.message == message

    where:
    prompt | input || message
    null   | null  || "prompt is null."
    ""     | null  || "prompt is empty."
    "a"    | null  || "input is null."
  }

  def "addInput : 충돌"() {
    given:
    LOGGER.info("[GIVEN] inputs=$input")

    def handler = new SimplePromptHandler()
    input.each {
      handler.addInput(it.key, it.value)
    }
    LOGGER.info("[GIVEN] handler=$handler")

    when:
    handler.addInput(conflict.key, conflict.value)

    then:
    def e = thrown(IllegalArgumentException)
    LOGGER.info("[THEN] e=$e", e)
    with(e.message) {
      it.contains("prompt conflict")
      it.contains("('${conflict.key}'=>'${conflict.value}')")
    }

    where:
    input                                  | conflict
    [entry("k", "V"), entry("a", "A")]     | entry("kk", "VV")
    [entry("a", "A"), entry("kkk", "VVV")] | entry("kk", "VV")
  }

  def "stdOutHandler : stdout 핸들러가 입력을 하는지 테스트."() {
    given:
    def stdin = Mock(OutputStreamWriter)
    //noinspection SpellCheckingInspection
    def stdout = new StringReader("abcd\nefghp1\nijk\np2lmnop1qrs")
    def stderr = Mock(InputStreamReader)

    def handler = new SimplePromptHandler(Map.of("p1", "i1", "p2", "i2", "p3", "i3"))
    LOGGER.info("[GIVEN] handler=$handler")

    def stdOutHandler = handler.stdOutHandler(stdin, stdout, stderr)
    LOGGER.info("[GIVEN] stdOutHandler=$stdOutHandler")

    when:
    def thread = new Thread(stdOutHandler)
    LOGGER.info("[WHEN] thread=$thread")
    thread.start()
    thread.join(100L)

    then:
    noExceptionThrown()

    2 * stdin.write("i1")
    1 * stdin.write("i2")
    0 * stdin.write("i3")

    3 * stdin.write('\n'.charAt(0))
    3 * stdin.flush()
  }

  def "stdOutHandler : docker 명령으로 입력 테스트."() {
    given:
    "docker -l=debug image prune"
  }
}