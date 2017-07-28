package me.chanjar.pvp.equipment.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 装备附加的技能。包含唯一主动，唯一被动<br>
 * 参考：<br>
 * http://news.17173.com/z/pvp/content/04202017/110034569_all.shtml
 * https://zhuanlan.zhihu.com/p/26442470
 */
public class EquipmentSkill {

  /**
   * 名称
   */
  private String name;

  /**
   * 说明
   */
  private String desc;

  /**
   * 是否可被累加。
   */
  private boolean addable;

  public EquipmentSkill() {
  }

  public EquipmentSkill(String name, String desc, boolean addable) {
    this.name = name;
    this.desc = desc;
    this.addable = addable;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isAddable() {
    return addable;
  }

  public void setAddable(boolean addable) {
    this.addable = addable;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("name", name)
        .append("desc", desc)
        .append("addable", addable)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EquipmentSkill that = (EquipmentSkill) o;

    if (addable != that.addable) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    return desc != null ? desc.equals(that.desc) : that.desc == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (desc != null ? desc.hashCode() : 0);
    result = 31 * result + (addable ? 1 : 0);
    return result;
  }
}
