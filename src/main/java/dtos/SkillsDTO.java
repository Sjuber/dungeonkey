package dtos;

import entities.Skills;

public class SkillsDTO {

    private Integer Animal_Handling;
    private Integer Arcana;
    private Integer Athletics;
    private Integer Deception;
    private Integer History;
    private Integer Insight;
    private Integer Intimidation;
    private Integer Investigation;
    private Integer Medicine;
    private Integer Nature;
    private Integer Perception;
    private Integer Performance;
    private Integer Persuasion;
    private Integer Religion;
    private Integer Sleight_of_Hand;
    private Integer Stealth;
    private Integer Survival;

    public SkillsDTO(Skills skills) {
        this.Animal_Handling = skills.getAnimal_Handling();
        this.Arcana = skills.getArcana();
        this.Athletics = skills.getAthletics();
        this.Deception = skills.getDeception();
        this.History = skills.getHistory();
        this.Insight = skills.getInsight();
        this.Intimidation = skills.getIntimidation();
        this.Investigation = skills.getInvestigation();
        this.Medicine = skills.getMedicine();
        this.Nature = skills.getNature();
        this.Perception = skills.getPerception();
        this.Performance = skills.getPerformance();
        this.Persuasion = skills.getPersuasion();
        this.Religion = skills.getReligion();
        this.Sleight_of_Hand = skills.getSleight_of_Hand();
        this.Stealth = skills.getStealth();
        this.Survival = skills.getSurvival();
    }

    public SkillsDTO() {
    }

    public Integer getAnimal_Handling() {
        return Animal_Handling;
    }

    public void setAnimal_Handling(Integer Animal_Handling) {
        this.Animal_Handling = Animal_Handling;
    }

    public Integer getArcana() {
        return Arcana;
    }

    public void setArcana(Integer Arcana) {
        this.Arcana = Arcana;
    }

    public Integer getAthletics() {
        return Athletics;
    }

    public void setAthletics(Integer Athletics) {
        this.Athletics = Athletics;
    }

    public Integer getDeception() {
        return Deception;
    }

    public void setDeception(Integer Deception) {
        this.Deception = Deception;
    }

    public Integer getHistory() {
        return History;
    }

    public void setHistory(Integer History) {
        this.History = History;
    }

    public Integer getInsight() {
        return Insight;
    }

    public void setInsight(Integer Insight) {
        this.Insight = Insight;
    }

    public Integer getIntimidation() {
        return Intimidation;
    }

    public void setIntimidation(Integer Intimidation) {
        this.Intimidation = Intimidation;
    }

    public Integer getInvestigation() {
        return Investigation;
    }

    public void setInvestigation(Integer Investigation) {
        this.Investigation = Investigation;
    }

    public Integer getMedicine() {
        return Medicine;
    }

    public void setMedicine(Integer Medicine) {
        this.Medicine = Medicine;
    }

    public Integer getNature() {
        return Nature;
    }

    public void setNature(Integer Nature) {
        this.Nature = Nature;
    }

    public Integer getPerception() {
        return Perception;
    }

    public void setPerception(Integer Perception) {
        this.Perception = Perception;
    }

    public Integer getPerformance() {
        return Performance;
    }

    public void setPerformance(Integer Performance) {
        this.Performance = Performance;
    }

    public Integer getPersuasion() {
        return Persuasion;
    }

    public void setPersuasion(Integer Persuasion) {
        this.Persuasion = Persuasion;
    }

    public Integer getReligion() {
        return Religion;
    }

    public void setReligion(Integer Religion) {
        this.Religion = Religion;
    }

    public Integer getSleight_of_Hand() {
        return Sleight_of_Hand;
    }

    public void setSleight_of_Hand(Integer Sleight_of_Hand) {
        this.Sleight_of_Hand = Sleight_of_Hand;
    }

    public Integer getStealth() {
        return Stealth;
    }

    public void setStealth(Integer Stealth) {
        this.Stealth = Stealth;
    }

    public Integer getSurvival() {
        return Survival;
    }

    public void setSurvival(Integer Survival) {
        this.Survival = Survival;
    }
//    public AbillityScores getDtos(SkillsDTO abiScores) {
//        AbillityScores abiScor = new AbillityScores(abiScores.getStrength(), abiScores.getDexterity(), abiScores.getConstitution(), abiScores.getWisdom(), abiScores.getIntelligence(), abiScores.getCharisma());
//        return abiScor;
//    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SkillsDTO{Animal_Handling=").append(Animal_Handling);
        sb.append(", Arcana=").append(Arcana);
        sb.append(", Athletics=").append(Athletics);
        sb.append(", Deception=").append(Deception);
        sb.append(", History=").append(History);
        sb.append(", Insight=").append(Insight);
        sb.append(", Intimidation=").append(Intimidation);
        sb.append(", Investigation=").append(Investigation);
        sb.append(", Medicine=").append(Medicine);
        sb.append(", Nature=").append(Nature);
        sb.append(", Perception=").append(Perception);
        sb.append(", Performance=").append(Performance);
        sb.append(", Persuasion=").append(Persuasion);
        sb.append(", Religion=").append(Religion);
        sb.append(", Sleight_of_Hand=").append(Sleight_of_Hand);
        sb.append(", Stealth=").append(Stealth);
        sb.append(", Survival=").append(Survival);
        sb.append('}');
        return sb.toString();
    }


}
