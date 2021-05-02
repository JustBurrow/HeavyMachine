package kr.lul.heavymachine.core.machine


import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

/**
 * @author justburrow* @since 2021/05/02
 */
@Unroll
class MapControlSpec extends Specification {
  public static final def LOGGER = LoggerFactory.getLogger(MapControlSpec)

  def "new()"() {
    when:
    def control = new MapControl()
    LOGGER.info("[WHEN] control=$control")

    then:
    control.executorService() != null
  }

  def "put : illegal key"() {
    given:
    LOGGER.info("[GIVEN] key=$key")

    def control = new MapControl()

    when:
    control.put(key, null)

    then:
    def e = thrown(IllegalArgumentException)
    LOGGER.info("[THEN] e=$e", e)

    e.message == message

    where:
    key  || message
    null || "key is null."
    ""   || "key is empty."
  }

  def "get"() {
    given:
    def k1 = "a"
    def o1 = 1
    def k2 = "b"
    def o2 = "BBBBBBBBBB"
    LOGGER.info("[GIVEN] k1=$k1, o1=$o1, k2=$k2, o2=$o2")

    def control = new MapControl()
    LOGGER.info("[GIVEN] control=$control")

    when:
    control.put(k1, o1)
    control.put(k2, o2)
    LOGGER.info("[WHEN] control=$control")

    then:
    control.get(k1) == o1
    control.get(k1, Integer) == o1
    control.get(k2) == o2
    control.get(k2, String) == o2
    control.get(k2, CharSequence) == o2
  }

  def "get(key, type) : illegal"() {
    given:
    def control = new MapControl()
    control.put("k", Instant.now())
    LOGGER.info("[GIVEN] control=$control")

    when:
    control.get("k", String)

    then:
    def e = thrown(ClassCastException)
    LOGGER.info("[THEN] e=$e", e)

    e != null
  }
}