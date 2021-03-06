package zw.co.afrosoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.domain.Address;
import zw.co.afrosoft.domain.Student;
import zw.co.afrosoft.domain.Subject;
import zw.co.afrosoft.dto.InQueryRequest;
import zw.co.afrosoft.dto.StudentRequest;
import zw.co.afrosoft.dto.StudentResponse;
import zw.co.afrosoft.dto.UpdateStudentRequest;
import zw.co.afrosoft.persistence.AddressRepository;
import zw.co.afrosoft.persistence.StudentRepository;
import zw.co.afrosoft.persistence.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
   StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SubjectRepository subjectRepository;

    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public Student createStudent(StudentRequest studentRequest){
        Student student= new Student(studentRequest);

        Address address = new Address();
        address.setCity(studentRequest.getCity());
        address.setStreet(studentRequest.getStreet());
        addressRepository.save(address);
        student.setAddress(address);
        studentRepository.save(student);

        List<Subject> subjectList = new ArrayList<>();

        if (studentRequest.getSubjects()!=null){
            for (Subject studentReq: studentRequest.getSubjects()) {
                Subject subject = new Subject();
                subject.setSubjectName(studentReq.getSubjectName());
                subject.setMarkObtained(studentReq.getMarkObtained());
                subject.setStudent(student);
                subjectList.add(subject);
            }

        }
        subjectRepository.saveAll(subjectList);
        return student;
    }
    public Student updateStudent(UpdateStudentRequest updateStudentRequest){
        Student student = studentRepository.findById(updateStudentRequest.getId()).get();
        if (!updateStudentRequest.getFirstname().isEmpty() && updateStudentRequest.getFirstname()!=null){
            student.setFirstname(updateStudentRequest.getFirstname());
        }
        student = studentRepository.save(student);
        return student;
    }

    public List<Student> getStudentByFirstname(String firstname){
        return studentRepository.findStudentByFirstname(firstname);
    }

    public List<Student> getStudentSorted(){
        Sort sort = Sort.by(Sort.Direction.ASC,"firstname","lastname","email");
        return studentRepository.findAll(sort);
    }

    public List<Student> getStudentByFirstnameAndLastname(String firstname,String lastname){
        return studentRepository.getByFirstNameAndLastname(firstname, lastname);
    }

    public List<Student> getStudentByFirstnameIn(InQueryRequest inQueryRequest){
        return studentRepository.findStudentByFirstnameIn(inQueryRequest.getFirstnames());
    }

    public String deleteStudent(Long id){
        studentRepository.deleteById(id);
        return "student has been successfully deleted";
    }

    public List<Student> getAllStudentWithPagination(int pageNo,int pageSize){
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
       return studentRepository.findAll(pageable).getContent();
    }

    public List<Student> getStudentByCity(String city){
        return studentRepository.getStudentsByCity(city);
    }
}
