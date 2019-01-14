package com.bokun.bkjcb.carcheck.Model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by DengShuai on 2018/11/12.
 * Description :
 */
@Entity
public class CheckPlan {

    @Id(assignable = true)
    private long id;
    private String ProjectName;
    private String ProjectAddress;
    private String Area;
    private String Qu;
    private double BuildingAllArea;
    private double BuildingAllBerth;
    private int type = 0;
    @Backlink
    private ToMany<CheckResult> results;

    public CheckPlan() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectAddress() {
        return ProjectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        ProjectAddress = projectAddress;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getQu() {
        return Qu;
    }

    public void setQu(String qu) {
        Qu = qu;
    }

    public double getBuildingAllArea() {
        return BuildingAllArea;
    }

    public void setBuildingAllArea(double buildingAllArea) {
        BuildingAllArea = buildingAllArea;
    }

    public double getBuildingAllBerth() {
        return BuildingAllBerth;
    }

    public void setBuildingAllBerth(double buildingAllBerth) {
        BuildingAllBerth = buildingAllBerth;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ToMany<CheckResult> getResults() {
        return results;
    }
}
