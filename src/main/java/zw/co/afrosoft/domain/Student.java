package zw.co.afrosoft.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.afrosoft.dto.StudentRequest;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Transient
    private String fullname;

    @OneToMany(mappedBy = "student")
    private List<Subject> subjects;


    public Student(StudentRequest studentRequest){
        this.firstname= studentRequest.getFirst_name();
        this.lastname= studentRequest.getLastname();
        this.email= studentRequest.getEmail();
        this.subjects= (List<Subject>) studentRequest.getSubject();
    }
}
