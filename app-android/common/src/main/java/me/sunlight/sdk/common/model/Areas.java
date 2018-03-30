package me.sunlight.sdk.common.model;

public class Areas {
   private String areaid;  
   private String name;  
   private String parentid;  
     
   public String getAreaid() {  
       return areaid;  
   }  
 
   public void setAreaid(String areaid) {  
       this.areaid = areaid;  
   }  
 
   public String getName() {  
       return name;  
   }  
 
   public void setName(String name) {  
       this.name = name;  
   }  
 
   public String getParentid() {  
       return parentid;  
   }  
 
   public void setParentid(String parentid) {  
       this.parentid = parentid;  
   }  
 
   @Override  
   public String toString() {  
       return "areaid:" + areaid + ", name:" + name + ", parentid:" + parentid;  
   }  
} 