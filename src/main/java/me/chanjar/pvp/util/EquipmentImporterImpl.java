package me.chanjar.pvp.util;

import com.supwisdom.spreadsheet.mapper.bean.BeanPropertyWriteException;
import com.supwisdom.spreadsheet.mapper.f2w.WorkbookReader;
import com.supwisdom.spreadsheet.mapper.f2w.excel.Excel2WorkbookReader;
import com.supwisdom.spreadsheet.mapper.model.core.Cell;
import com.supwisdom.spreadsheet.mapper.model.core.Sheet;
import com.supwisdom.spreadsheet.mapper.model.core.Workbook;
import com.supwisdom.spreadsheet.mapper.model.meta.*;
import com.supwisdom.spreadsheet.mapper.w2o.*;
import com.supwisdom.spreadsheet.mapper.w2o.setter.LambdaPropertySetter;
import com.supwisdom.spreadsheet.mapper.w2o.setter.PropertySetter;
import com.supwisdom.spreadsheet.mapper.w2o.setter.PropertySetterTemplate;
import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.model.EquipmentSkill;
import me.chanjar.pvp.equipment.model.EquipmentType;
import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Component
public class EquipmentImporterImpl implements EquipmentImporter {

  private EquipmentRepository equipmentRepository;

  @Override
  public List<Equipment> importXlsx(InputStream xlsxStream) {

    Workbook workbook = getWorkbook(xlsxStream);
    WorkbookMeta workbookMeta = getWorkbookMeta();

    if (!validateWorkbook(workbook, workbookMeta)) {

    }

    return importWorkbook(workbook, workbookMeta);

  }

  private Workbook getWorkbook(InputStream xlsxStream) {

    WorkbookReader workbookReader = new Excel2WorkbookReader();
    Workbook workbook = workbookReader.read(xlsxStream);

    Sheet firstSheet = workbook.getFirstSheet();
    workbook.getSheets().clear();
    workbook.addSheet(firstSheet);

    return workbook;
  }

  private WorkbookMeta getWorkbookMeta() {

    // 注意，这里可以不需要HeadMeta
    WorkbookMeta workbookMeta = new WorkbookMetaBean();
    SheetMeta sheetMeta = new SheetMetaBean("所有装备", 2);

    /**
     类型,ID,出价,入价,物理攻击,物理防御,每5秒回血,法术攻击,法术防御,每5秒回蓝,最大法力,最大生命,移动速度%,冷却缩减%,攻击速度%,暴击率%,物理吸血%,依赖,"唯一被动","唯一被动data",唯一主动,"唯一主动data",备注
     */
    int i = 0;
    // 类型,ID,出价,入价
    sheetMeta.addFieldMeta(new FieldMetaBean("type", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("id", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("sellPrice", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("price", ++i));

    // 物理攻击,物理防御,每5秒回血,法术攻击,法术防御,每5秒回蓝
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.attack", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.defense", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.hpRecoverPer5Secs", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.manaAttack", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.manaDefense", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.mpRecoverPer5Secs", ++i));

    // 最大法力,最大生命,移动速度%,冷却缩减%,攻击速度%,暴击率%,物理吸血%
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.maxMp", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.maxHp", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.speedPct", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.cdCutdownPct", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.attackSpeedPct", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.bustProbabilityPct", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.attackSuckBloodPct", ++i));

    // 依赖,"唯一被动","唯一被动data",唯一主动,"唯一主动data",备注
    sheetMeta.addFieldMeta(new FieldMetaBean("dependsOn", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.passiveSkills_fake", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.passiveSkills", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.initiativeSkills_fake", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("attribute.initiativeSkills", ++i));
    sheetMeta.addFieldMeta(new FieldMetaBean("remark", ++i));

    workbookMeta.addSheetMeta(sheetMeta);

    return workbookMeta;
  }

  private boolean validateWorkbook(Workbook workbook, WorkbookMeta workbookMeta) {

    // TODO
    return true;
  }

  private List<Equipment> importWorkbook(Workbook workbook, WorkbookMeta workbookMeta) {

    Workbook2ObjectComposer workbook2ObjectComposer = new DefaultWorkbook2ObjectComposer();
    Sheet2ObjectComposer sheet2ObjectComposer = new DefaultSheet2ObjectComposer();
    sheet2ObjectComposer.ignoreFields("attribute.passiveSkills_fake", "attribute.initiativeSkills_fake");

    // 定义对象工厂，用来创建对象的
    sheet2ObjectComposer.setObjectFactory((row, sheetMeta1) -> equipmentRepository.newModel());

    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.attack"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.defense"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.hpRecoverPer5Secs"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.manaAttack"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.manaDefense"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.mpRecoverPer5Secs"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.maxMp"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.maxHp"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.speedPct"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.cdCutdownPct"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.attackSpeedPct"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.bustProbabilityPct"));
    sheet2ObjectComposer.addPropertySetter(createIntPropertySetter("attribute.attackSuckBloodPct"));

    sheet2ObjectComposer.addPropertySetter(new LambdaPropertySetter(cellValue -> {
      if ("攻击".equals(cellValue)) {
        return EquipmentType.ATTACK;
      }
      if ("辅助".equals(cellValue)) {
        return EquipmentType.ASSIST;
      }
      if ("防御".equals(cellValue)) {
        return EquipmentType.DEFENSE;
      }
      if ("打野".equals(cellValue)) {
        return EquipmentType.HUNTER;
      }
      if ("法术".equals(cellValue)) {
        return EquipmentType.MANA;
      }
      if ("移动".equals(cellValue)) {
        return EquipmentType.MOVE;
      }
      return null;
    }).matchField("type"));

    sheet2ObjectComposer.addPropertySetter(new LambdaPropertySetter(cellValue -> {
      String text = (String) cellValue;
      text = text.replaceAll("，", ",");
      return Arrays.asList(text.split(","));
    }).matchField("dependsOn"));

    sheet2ObjectComposer.addPropertySetter(createSkillsPropertySetter("attribute.passiveSkills"));
    sheet2ObjectComposer.addPropertySetter(createSkillsPropertySetter("attribute.initiativeSkills"));

    workbook2ObjectComposer.addSheet2ObjectComposer(sheet2ObjectComposer);

    List<List<Equipment>> fooListList = (List) workbook2ObjectComposer.compose(workbook, workbookMeta);

    return fooListList.get(0);

  }


  private PropertySetter createIntPropertySetter(String field) {

    return new PropertySetterTemplate() {

      @Override
      public void setProperty(Object object, Cell cell, FieldMeta fieldMeta) {
        String propertyPath = fieldMeta.getName();
        String propertyValue = cell.getValue();

        try {
          if (StringUtils.isNotBlank(propertyValue)) {
            beanHelper.setProperty(object, propertyPath, convertToProperty(propertyValue));
          } else {
            beanHelper.setProperty(object, propertyPath, 0);
          }

        } catch (BeanPropertyWriteException e) {
          throw new Workbook2ObjectComposeException(e);
        }
      }

      @Override
      protected Object convertToProperty(String cellValue) {
        return cellValue;
      }
    }.matchField(field);

  }

  private LambdaPropertySetter createSkillsPropertySetter(String field) {

    Function<String, List<EquipmentSkill>> function = cellValue -> {
      String text = cellValue;
      text = text.replaceAll("；", ";");

      String[] skillStringArray = text.split("\n");
      if (ArrayUtils.isEmpty(skillStringArray)) {
        return Collections.emptyList();
      }

      List<EquipmentSkill> skills = new ArrayList<>();
      for (String skillString : skillStringArray) {
        if (StringUtils.isBlank(skillString)) {
          continue;
        }
        String[] attrs = skillString.split(";");
        EquipmentSkill skill = new EquipmentSkill();
        skill.setName(attrs[0]);
        skill.setDesc(attrs[1]);
        skill.setAddable(!"0".equals(attrs[2]));
        skills.add(skill);
      }
      return skills;
    };

    return (LambdaPropertySetter) new LambdaPropertySetter(function).matchField(field);

  }

  @Autowired
  public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

}
