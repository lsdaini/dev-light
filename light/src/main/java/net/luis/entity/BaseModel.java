package net.luis.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable{
	
	private static final long serialVersionUID = -6998073315677397505L;
	private Long id; //主键id
	private Date bussDate; //业务时间
	private String enabled; //是否可用
	private Date createdTime; //创建时间
	private String createdBy; //创建人
	private Date lastModifiedTime; //修改时间
	private String lastModifiedBy; //修改人
	private Date confirmTime; //审核时间
	private String confirmBy; //审核人
	private String luFillStatus; //是否新建：新建-103001,导入-103002
	private String luIsEditStatus; //是否完成:未完成-102001,已完成-102002
	private String luConfirmStatus; //审核状态：未审核-101001,已审核-101002
	private String definitionone; //
	private String definitiontwo; //
	private String definitionthree;//
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getBussDate() {
		return bussDate;
	}
	public void setBussDate(Date bussDate) {
		this.bussDate = bussDate;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getConfirmBy() {
		return confirmBy;
	}
	public void setConfirmBy(String confirmBy) {
		this.confirmBy = confirmBy;
	}
	public String getLuFillStatus() {
		return luFillStatus;
	}
	public void setLuFillStatus(String luFillStatus) {
		this.luFillStatus = luFillStatus;
	}
	public String getLuIsEditStatus() {
		return luIsEditStatus;
	}
	public void setLuIsEditStatus(String luIsEditStatus) {
		this.luIsEditStatus = luIsEditStatus;
	}
	public String getLuConfirmStatus() {
		return luConfirmStatus;
	}
	public void setLuConfirmStatus(String luConfirmStatus) {
		this.luConfirmStatus = luConfirmStatus;
	}
	public String getDefinitionone() {
		return definitionone;
	}
	public void setDefinitionone(String definitionone) {
		this.definitionone = definitionone;
	}
	public String getDefinitiontwo() {
		return definitiontwo;
	}
	public void setDefinitiontwo(String definitiontwo) {
		this.definitiontwo = definitiontwo;
	}
	public String getDefinitionthree() {
		return definitionthree;
	}
	public void setDefinitionthree(String definitionthree) {
		this.definitionthree = definitionthree;
	}
}
