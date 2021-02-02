package com.udemy.budgettingapp.dto;

public class CategoryDto {
   private String value;
   private String label;
   
   public CategoryDto() {
	   
   }
   
   public CategoryDto(String id, String name) {
	   this.value = id;
	   this.label = name;
   }

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}   
}
