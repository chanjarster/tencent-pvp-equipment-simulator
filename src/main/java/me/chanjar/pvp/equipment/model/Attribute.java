package me.chanjar.pvp.equipment.model;

public class Attribute {

  /**
   * 装备类型
   */
  private EquipmentType type;

  /**
   * 物理攻击
   */
  private int attack;
  /**
   * 物理防御
   */
  private int defense;
  /**
   * 每5秒回血
   */
  private int hpRecoverPer5Secs;
  /**
   * 法术攻击
   */
  private int manaAttack;
  /**
   * 法术防御
   */
  private int manaDefense;
  /**
   * 每5秒回蓝
   */
  private int mpRecoverPer5Secs;
  /**
   * 最大法力
   */
  private int maxMana;
  /**
   * 最大生命
   */
  private int maxHp;
  /**
   * 移动速度%
   */
  private int speedPct;
  /**
   * 冷却缩减%
   */
  private int cdCutdownPct;
  /**
   * 攻击速度%
   */
  private int attackSpeedPct;
  /**
   * 暴击率%
   */
  private int bustProbabilityPct;
  /**
   * 物理吸血%
   */
  private int attackSuckBloodPct;

  /**
   * 唯一被动
   */
  private String exclusivePassive;

  /**
   * 唯一主动
   */
  private String exclusiveSkill;

  public EquipmentType getType() {
    return type;
  }

  public void setType(EquipmentType type) {
    this.type = type;
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  public int getDefense() {
    return defense;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public int getHpRecoverPer5Secs() {
    return hpRecoverPer5Secs;
  }

  public void setHpRecoverPer5Secs(int hpRecoverPer5Secs) {
    this.hpRecoverPer5Secs = hpRecoverPer5Secs;
  }

  public int getManaAttack() {
    return manaAttack;
  }

  public void setManaAttack(int manaAttack) {
    this.manaAttack = manaAttack;
  }

  public int getManaDefense() {
    return manaDefense;
  }

  public void setManaDefense(int manaDefense) {
    this.manaDefense = manaDefense;
  }

  public int getMpRecoverPer5Secs() {
    return mpRecoverPer5Secs;
  }

  public void setMpRecoverPer5Secs(int mpRecoverPer5Secs) {
    this.mpRecoverPer5Secs = mpRecoverPer5Secs;
  }

  public int getMaxMana() {
    return maxMana;
  }

  public void setMaxMana(int maxMana) {
    this.maxMana = maxMana;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public void setMaxHp(int maxHp) {
    this.maxHp = maxHp;
  }

  public int getSpeedPct() {
    return speedPct;
  }

  public void setSpeedPct(int speedPct) {
    this.speedPct = speedPct;
  }

  public int getCdCutdownPct() {
    return cdCutdownPct;
  }

  public void setCdCutdownPct(int cdCutdownPct) {
    this.cdCutdownPct = cdCutdownPct;
  }

  public int getAttackSpeedPct() {
    return attackSpeedPct;
  }

  public void setAttackSpeedPct(int attackSpeedPct) {
    this.attackSpeedPct = attackSpeedPct;
  }

  public int getBustProbabilityPct() {
    return bustProbabilityPct;
  }

  public void setBustProbabilityPct(int bustProbabilityPct) {
    this.bustProbabilityPct = bustProbabilityPct;
  }

  public int getAttackSuckBloodPct() {
    return attackSuckBloodPct;
  }

  public void setAttackSuckBloodPct(int attackSuckBloodPct) {
    this.attackSuckBloodPct = attackSuckBloodPct;
  }

}
