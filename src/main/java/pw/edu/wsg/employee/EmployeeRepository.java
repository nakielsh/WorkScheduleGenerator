package pw.edu.wsg.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);

    @Query(value = "SELECT * FROM employee WHERE app_user_id = ?1", nativeQuery = true)
    List<Employee> findByAppUserId(Long id);

    @Modifying
    @Query(value = "DELETE FROM employee e WHERE e.name =:name AND e.app_user_id = :id", nativeQuery = true)
    void deleteEmployeeByNameAndApp_user_id(@Param("name") String name, @Param("id") Long id);

//    void deleteEmployeeById(Long id);

}