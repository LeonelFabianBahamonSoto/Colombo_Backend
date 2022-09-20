package co.edu.colomboamericano.caelstudent.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.repository.StudentRepository;
import co.edu.colomboamericano.caelstudent.service.StudentService;
import lombok.extern.slf4j.Slf4j;

@Service
@Scope("singleton")
@Slf4j
public class StudentServiceImp implements StudentService
{
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public StudentServiceImp( StudentRepository studentRepository )
	{
		this.studentRepository = studentRepository;
	};

	@Override
	@Transactional( readOnly = true )
	public List<Student> findAll()
	{
		return studentRepository.findAll();
	}
	
	@Override
	@Transactional( readOnly = true )
	public Optional<Student> findByDocument( String documentNumber )
	{
		Optional<Student> student = studentRepository.findByDocumentNumber( documentNumber );
		
//		if( student == null ){
//			throw new Exception("No existe un estudiante registrado con el numero de documento indicado");
//		};
		
		return student;
	}
	
	@Override
	@Transactional( readOnly = true )
	public String authenticate( String documentNumber, String password ) throws Exception
	{
		if( documentNumber == null ) {
			throw new Exception("El numero de documento no puede ser nulo");
		};
		
		if( password == null ) {
			throw new Exception("La contrasenia no puede ser nulo");
		};
		
		Integer isStudent = studentRepository.studentAuthentication(documentNumber, password, "1");
		
		if( isStudent == null ) {
			throw new Exception("El estudiante no esta registrado");
		};
		
		log.info( "THE STUDENT RESPONSE: " + isStudent );

		return "12345qwert";
	};

	@Override
	@Transactional( readOnly = true )
	public Optional<Student> findById(Integer id)
	{
		return studentRepository.findById(id);
	}
	
	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Student save(Student entity) throws Exception
	{
		entity.setPassword( passwordEncoder.encode( entity.getPassword() ) );
		return studentRepository.save( entity );
	}

	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Student update(Student entity) throws Exception
	{
		return studentRepository.save( entity );
	}

	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void delete(Student entity) throws Exception {
		studentRepository.delete(entity);
	}

	@Override
	@Transactional( readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public void deleteById(Integer id) throws Exception {
		studentRepository.deleteById(id);
	}
}
