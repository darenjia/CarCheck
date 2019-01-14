package com.bokun.bkjcb.carcheck.Model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by DengShuai on 2018/11/12.
 * Description :
 */
@Entity
public class CheckResult {
    @Id(assignable = true)
    private long id;
    private int resultID;
    private int result;
    private String imageUrl;
    private String remark;
    private ToOne<CheckPlan> plan;

    public CheckResult() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ToOne<CheckPlan> getPlan() {
        return plan;
    }
}
