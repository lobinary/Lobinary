package com.ljr.PomUpgrate.dto;

public class Dependency {
	/**
	 * 
		<dependency>
		    <groupId>org.mobicents.servlet.sip</groupId>
		    <artifactId>sip-servlets-annotations</artifactId>
		    <version>4.0.111</version>
		</dependency>

	 */
	private String groupId;
	private String artifactId;
	private String version;
	
	public Dependency(String dependency字符串) {
		this.groupId = 通过标签捕获标签中字段(dependency字符串, "groupId");
		this.artifactId = 通过标签捕获标签中字段(dependency字符串, "artifactId");
		this.version = 通过标签捕获标签中字段(dependency字符串, "version");
	}
	
	private String 通过标签捕获标签中字段(String 源字符串,String 捕获的标签){
		return 源字符串.substring(源字符串.indexOf("<"+捕获的标签+">")+捕获的标签.length()+2,源字符串.indexOf("</"+捕获的标签+">"));
	}
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Dependency [groupId=" + groupId + ", artifactId=" + artifactId + ", version=" + version + "]";
	}
	
	public String 注释(){
		return "<!-- Dependency [groupId=" + groupId + ", artifactId=" + artifactId +  "] -->";
	}

	public String pom(String 独立注释) {
		return "		<dependency>\n"+
				"			"+注释()+"\n"+
				"			"+独立注释+"\n"+
			"			<groupId>"+groupId+"</groupId>\n"+
		    "			<artifactId>"+artifactId+"</artifactId>\n"+
		    "		</dependency>";
	}

}
