package com.afour.automation.jsonPojo;

public class Plan4cloud {

private VmsToBeMapped vmsToBeMapped;
private String cloudName;
private Boolean rightSize;
private String cloudProvider;
private Boolean chargeable;

/**
*
* @return
* The vmsToBeMapped
*/
public VmsToBeMapped getVmsToBeMapped() {
return vmsToBeMapped;
}

/**
*
* @param vmsToBeMapped
* The vmsToBeMapped
*/
public void setVmsToBeMapped(VmsToBeMapped vmsToBeMapped) {
this.vmsToBeMapped = vmsToBeMapped;
}

/**
*
* @return
* The cloudName
*/
public String getCloudName() {
return cloudName;
}

/**
*
* @param cloudName
* The cloudName
*/
public void setCloudName(String cloudName) {
this.cloudName = cloudName;
}

/**
*
* @return
* The rightSize
*/
public Boolean getRightSize() {
return rightSize;
}

/**
*
* @param rightSize
* The rightSize
*/
public void setRightSize(Boolean rightSize) {
this.rightSize = rightSize;
}

/**
*
* @return
* The cloudProvider
*/
public String getCloudProvider() {
return cloudProvider;
}

/**
*
* @param cloudProvider
* The cloudProvider
*/
public void setCloudProvider(String cloudProvider) {
this.cloudProvider = cloudProvider;
}

/**
*
* @return
* The chargeable
*/
public Boolean getChargeable() {
return chargeable;
}

/**
*
* @param chargeable
* The chargeable
*/
public void setChargeable(Boolean chargeable) {
this.chargeable = chargeable;
}

}