/*
 * GenericResource.java
 */

package bo.edu.ucbcba.simplescheduling.resource;

import bo.edu.ucbcba.simplescheduling.model.MyClass;
import bo.edu.ucbcba.simplescheduling.model.Student;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class GenericResource {
    
    private static final Map<Integer, Student> studentMap = new HashMap<>();
    private static final Map<String, MyClass> myClassMap = new HashMap<>();
    
    public static void putStudent(Student student) {
        if (student != null) {
            getStudentMap().put(student.getStudentId(), student);
        }
    }
    
    public static Student getStudent(Integer studentId) {
        return getStudentMap().get(studentId);
    }
    
    public static boolean removeStudent(Integer studentId) {
        return getStudentMap().remove(studentId) != null;
    }

    /**
     * @return the studentMap
     */
    public static Map<Integer, Student> getStudentMap() {
        return studentMap;
    }
    
    /*MyClassMethods*/
    
    public static Map<String, MyClass> getClassMap() {
        return myClassMap;
    }
    
    
    public static MyClass getClass(String classCode){
        return getClassMap().get(classCode);
    }
    
    public static boolean removeClass(String classCode){
        return getClassMap().remove(classCode) != null;
    }
    
    public static void putClass(MyClass myclass){
        if(myclass != null ){
            getClassMap().put(myclass.getCode(), myclass);
        }
    }

}
