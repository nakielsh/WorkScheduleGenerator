package pw.edu.wsg.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pw.edu.wsg.appuser.AppUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);

    @Query(value = "SELECT * FROM employee WHERE app_user_id = ?1", nativeQuery = true)
    List<Employee> findByAppUserId(Long id);

    void deleteEmployeeByName(String name);
}