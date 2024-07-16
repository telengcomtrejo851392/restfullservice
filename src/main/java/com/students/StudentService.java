package com.students;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
    
    // DB repository mock
    private Map<Long, Student> repository = Arrays.asList(
        new Student[]{
                new Student(1, "Alan","Turing"),
                new Student(2, "Sebastian","Bach"),
                new Student(3, "Pablo","Picasso"),
        }).stream()
        .collect(Collectors.toConcurrentMap(Student::getId, Function.identity()));
    
    //Diseño de conexion a repos de base de datos
    
    
    // DB id sequence mock
    private AtomicLong sequence = new AtomicLong(3);
    
    public List<Student> readAll() {
        return repository.values().stream().collect(Collectors.toList());
    }
    
    public Student read(Long id) {
        return repository.get(id);
    }

    public Student create(Student student) {
        long key = sequence.incrementAndGet();
        student.setId(key);
        repository.put(key, student);
        return student;
    }
    
    public Student update(Long id, Student student) {
        student.setId(id);
        Student oldStudent = repository.replace(id, student);
        return oldStudent == null ? null : student;
    }
    
    public void delete(Long id) {
        repository.remove(id);
    }
    
    public boolean NeuralEngine(String base64) {
        /*long key = sequence.incrementAndGet();
        student.setId(key);
        repository.put(key, student);
        return student;*/
    	
    	String connectionUrl = "jdbc:sqlserver://OmarTrejoMontes;databaseName=NeuralEngineForHumans;user=sa;password=Itz3elILov3eYou92$";
    	try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();)
    	{
    		String SQL = "INSERT INTO StoreBrainNeuralCortex VALUES (" + base64 +")";
    		boolean rs = stmt.execute(SQL);
    		return rs;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}    	
    	//return "";
    }
}
