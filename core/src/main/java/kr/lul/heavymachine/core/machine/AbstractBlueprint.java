package kr.lul.heavymachine.core.machine;

import java.util.UUID;

import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/04/30
 */
public abstract class AbstractBlueprint implements Blueprint {
  protected final UUID id;
  protected final String name;
  protected final String description;

  public AbstractBlueprint() {
    this(UUID.randomUUID());
  }

  public AbstractBlueprint(UUID id) {
    this(id, id.toString());
  }

  public AbstractBlueprint(UUID id, String name) {
    this(id, name, name);
  }

  public AbstractBlueprint(UUID id, String name, String description) {
    this.id = notNull(id, "id");
    this.name = notEmpty(name, "name");
    this.description = notEmpty(description, "description");
  }

  @Override
  public UUID getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return this.id.equals(((AbstractBlueprint) o).id);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }
}