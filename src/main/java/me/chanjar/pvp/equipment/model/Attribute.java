package me.chanjar.pvp.equipment.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class Attribute implements Cloneable {

  private int attack;

  private int defense;

  private int hpRecoverPer5Secs;

  private int manaAttack;

  private int manaDefense;

  private int mpRecoverPer5Secs;

  private int maxMp;

  private int maxHp;

  private int speedPct;

  private int cdCutdownPct;

  private int attackSpeedPct;

  private int bustProbabilityPct;

  private int attackSuckBloodPct;

  private List<EquipmentSkill> passiveSkills = new ArrayList<>(2);

  private List<EquipmentSkill> initiativeSkills = new ArrayList<>(1);

  /**
   * 和另一个属性相加
   *
   * @param another
   * @return 获得相加的结果
   */
  public Attribute plus(Attribute another) {

    Attribute result = new Attribute();
    result.setAttack(attack + another.getAttack());
    result.setAttack(defense + another.getDefense());
    result.setAttack(hpRecoverPer5Secs + another.getHpRecoverPer5Secs());
    result.setAttack(manaAttack + another.getManaAttack());
    result.setAttack(manaDefense + another.getManaDefense());
    result.setAttack(mpRecoverPer5Secs + another.getMpRecoverPer5Secs());
    result.setAttack(maxMp + another.getMaxMp());
    result.setAttack(maxHp + another.getMaxHp());
    result.setAttack(speedPct + another.getSpeedPct());
    result.setAttack(cdCutdownPct + another.getCdCutdownPct());
    result.setAttack(attackSpeedPct + another.getAttackSpeedPct());
    result.setAttack(bustProbabilityPct + another.getBustProbabilityPct());
    result.setAttack(attackSuckBloodPct + another.getAttackSuckBloodPct());
    result.getPassiveSkills().addAll(passiveSkills);
    result.getPassiveSkills().addAll(another.getPassiveSkills());
    result.getInitiativeSkills().addAll(initiativeSkills);
    result.getInitiativeSkills().addAll(another.getInitiativeSkills());
    return result;
  }

  /**
   * 减掉另一个属性
   *
   * @param another
   * @return
   */
  public Attribute minus(Attribute another) {

    Attribute result = new Attribute();
    result.setAttack(attack - another.getAttack());
    result.setAttack(defense - another.getDefense());
    result.setAttack(hpRecoverPer5Secs - another.getHpRecoverPer5Secs());
    result.setAttack(manaAttack - another.getManaAttack());
    result.setAttack(manaDefense - another.getManaDefense());
    result.setAttack(mpRecoverPer5Secs - another.getMpRecoverPer5Secs());
    result.setAttack(maxMp - another.getMaxMp());
    result.setAttack(maxHp - another.getMaxHp());
    result.setAttack(speedPct - another.getSpeedPct());
    result.setAttack(cdCutdownPct - another.getCdCutdownPct());
    result.setAttack(attackSpeedPct - another.getAttackSpeedPct());
    result.setAttack(bustProbabilityPct - another.getBustProbabilityPct());
    result.setAttack(attackSuckBloodPct - another.getAttackSuckBloodPct());

    result.getPassiveSkills().addAll(passiveSkills);
    another.getPassiveSkills().forEach(s -> result.getPassiveSkills().remove(s));

    result.getInitiativeSkills().addAll(initiativeSkills);
    another.getInitiativeSkills().forEach(s -> result.getInitiativeSkills().remove(s));

    return result;
  }

  /**
   * 物理攻击
   */
  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  /**
   * 物理防御
   */
  public int getDefense() {
    return defense;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  /**
   * 每5秒回血
   */
  public int getHpRecoverPer5Secs() {
    return hpRecoverPer5Secs;
  }

  public void setHpRecoverPer5Secs(int hpRecoverPer5Secs) {
    this.hpRecoverPer5Secs = hpRecoverPer5Secs;
  }

  /**
   * 法术攻击
   */
  public int getManaAttack() {
    return manaAttack;
  }

  public void setManaAttack(int manaAttack) {
    this.manaAttack = manaAttack;
  }

  /**
   * 法术防御
   */
  public int getManaDefense() {
    return manaDefense;
  }

  public void setManaDefense(int manaDefense) {
    this.manaDefense = manaDefense;
  }

  /**
   * 每5秒回蓝
   */
  public int getMpRecoverPer5Secs() {
    return mpRecoverPer5Secs;
  }

  public void setMpRecoverPer5Secs(int mpRecoverPer5Secs) {
    this.mpRecoverPer5Secs = mpRecoverPer5Secs;
  }

  /**
   * 最大法力
   */
  public int getMaxMp() {
    return maxMp;
  }

  public void setMaxMp(int maxMp) {
    this.maxMp = maxMp;
  }

  /**
   * 最大生命
   */
  public int getMaxHp() {
    return maxHp;
  }

  public void setMaxHp(int maxHp) {
    this.maxHp = maxHp;
  }

  /**
   * 移动速度%
   */
  public int getSpeedPct() {
    return speedPct;
  }

  public void setSpeedPct(int speedPct) {
    this.speedPct = speedPct;
  }

  /**
   * 冷却缩减%
   */
  public int getCdCutdownPct() {
    return cdCutdownPct;
  }

  public void setCdCutdownPct(int cdCutdownPct) {
    this.cdCutdownPct = cdCutdownPct;
  }

  /**
   * 攻击速度%
   */
  public int getAttackSpeedPct() {
    return attackSpeedPct;
  }

  public void setAttackSpeedPct(int attackSpeedPct) {
    this.attackSpeedPct = attackSpeedPct;
  }

  /**
   * 暴击率%
   */
  public int getBustProbabilityPct() {
    return bustProbabilityPct;
  }

  public void setBustProbabilityPct(int bustProbabilityPct) {
    this.bustProbabilityPct = bustProbabilityPct;
  }

  /**
   * 物理吸血%
   */
  public int getAttackSuckBloodPct() {
    return attackSuckBloodPct;
  }

  public void setAttackSuckBloodPct(int attackSuckBloodPct) {
    this.attackSuckBloodPct = attackSuckBloodPct;
  }

  /**
   * 唯一被动技能
   *
   * @return
   */
  public List<EquipmentSkill> getPassiveSkills() {
    return passiveSkills;
  }

  public void setPassiveSkills(List<EquipmentSkill> passiveSkills) {
    if (passiveSkills == null) {
      return;
    }
    this.passiveSkills = passiveSkills;
  }

  /**
   * 唯一主动技能
   *
   * @return
   */
  public List<EquipmentSkill> getInitiativeSkills() {
    return initiativeSkills;
  }

  public void setInitiativeSkills(List<EquipmentSkill> initiativeSkills) {
    if (initiativeSkills == null) {
      return;
    }
    this.initiativeSkills = initiativeSkills;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("attack", attack)
        .append("defense", defense)
        .append("hpRecoverPer5Secs", hpRecoverPer5Secs)
        .append("manaAttack", manaAttack)
        .append("manaDefense", manaDefense)
        .append("mpRecoverPer5Secs", mpRecoverPer5Secs)
        .append("maxMp", maxMp)
        .append("maxHp", maxHp)
        .append("speedPct", speedPct)
        .append("cdCutdownPct", cdCutdownPct)
        .append("attackSpeedPct", attackSpeedPct)
        .append("bustProbabilityPct", bustProbabilityPct)
        .append("attackSuckBloodPct", attackSuckBloodPct)
        .append("passiveSkills", passiveSkills)
        .append("initiativeSkills", initiativeSkills)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Attribute attribute = (Attribute) o;

    if (attack != attribute.attack) return false;
    if (defense != attribute.defense) return false;
    if (hpRecoverPer5Secs != attribute.hpRecoverPer5Secs) return false;
    if (manaAttack != attribute.manaAttack) return false;
    if (manaDefense != attribute.manaDefense) return false;
    if (mpRecoverPer5Secs != attribute.mpRecoverPer5Secs) return false;
    if (maxMp != attribute.maxMp) return false;
    if (maxHp != attribute.maxHp) return false;
    if (speedPct != attribute.speedPct) return false;
    if (cdCutdownPct != attribute.cdCutdownPct) return false;
    if (attackSpeedPct != attribute.attackSpeedPct) return false;
    if (bustProbabilityPct != attribute.bustProbabilityPct) return false;
    if (attackSuckBloodPct != attribute.attackSuckBloodPct) return false;
    if (passiveSkills != null ? !passiveSkills.equals(attribute.passiveSkills) : attribute.passiveSkills != null)
      return false;
    return initiativeSkills != null ?
        initiativeSkills.equals(attribute.initiativeSkills) :
        attribute.initiativeSkills == null;
  }

  @Override
  public int hashCode() {
    int result = attack;
    result = 31 * result + defense;
    result = 31 * result + hpRecoverPer5Secs;
    result = 31 * result + manaAttack;
    result = 31 * result + manaDefense;
    result = 31 * result + mpRecoverPer5Secs;
    result = 31 * result + maxMp;
    result = 31 * result + maxHp;
    result = 31 * result + speedPct;
    result = 31 * result + cdCutdownPct;
    result = 31 * result + attackSpeedPct;
    result = 31 * result + bustProbabilityPct;
    result = 31 * result + attackSuckBloodPct;
    result = 31 * result + (passiveSkills != null ? passiveSkills.hashCode() : 0);
    result = 31 * result + (initiativeSkills != null ? initiativeSkills.hashCode() : 0);
    return result;
  }

  @Override
  public Attribute clone() {
    Attribute attribute = new Attribute();
    attribute.plus(this);
    return attribute;
  }
}
