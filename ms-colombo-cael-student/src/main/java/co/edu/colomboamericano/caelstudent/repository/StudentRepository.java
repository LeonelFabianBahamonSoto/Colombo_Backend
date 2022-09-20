package co.edu.colomboamericano.caelstudent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.colomboamericano.caelstudent.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
	@Query( value = "SELECT CASE WHEN exists "
			+ "( SELECT * FROM paginaweb.estudiantes WHERE numero_documento=?1 AND password=?2 AND activo=?3 ) "
			+ "THEN 1 ELSE 0 END", nativeQuery = true)
	Integer studentAuthentication( String documentNumber, String password, String active );

	Optional<Student> findByDocumentNumber( String documentNumber );
}