package com.tutorialspoint;

import java.io.Serializable;  
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "user") 

public class User implements Serializable {  
   private static final long serialVersionUID = 1L; 
   private Integer id;
   private String name; 
   private int age;  
   
   public User(){} 
    
   public User(String name, int age){  

      this.name = name; 
      this.age = age; 
   }  

	/*
	 * @XmlElement public void setId(int id) { this.id = id; }
	 */
   public String getName() { 
      return name; 
   } 
 
   public void setName(String name) { 
      this.name = name; 
   } 
   public int getAge() { 
      return age; 
   } 

   public void setAge(int age) { 
      this.age = age; 
   }
   
   public Integer getId() {
		return id;
	} 
   
   public Integer setId(Integer idIn) {
	   this.id = idIn;
	   return 1;
   }
   @Override 
   public boolean equals(Object object){ 
      if(object == null){ 
         return false; 
      }else if(!(object instanceof User)){ 
         return false; 
      }else { 
         User user = (User)object; 
         if(name.equals(user.getName()) 
            && age ==(user.getAge() ) ){ 
               return true; 
         }
      } 
      return false; 
   }


} 